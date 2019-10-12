package com.mrwan.pigcount.controller;

import com.mrwan.pigcount.pojo.Users;
import com.mrwan.pigcount.pojo.pageInfoB;
import com.mrwan.pigcount.pojo.pigList;
import com.mrwan.pigcount.service.books.BooksService;
import com.mrwan.pigcount.utils.BaseResponseInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class booksMessageControl {

    @RestController
    @Api(value="账本数据" , tags="账本数据控制")
    @RequestMapping(value = "books" , method = RequestMethod.POST)
    public class UserControlelr {
        @Autowired
        private BooksService booksService;

        /**
         * 账单记录新增、修改
         * @param req
         * @param money
         * @param notice
         * @param time
         * @param typeId
         * @param file
         * @return
         */
        @ApiOperation(value = "账单记录新增、修改")
        @RequestMapping("booksChange")
        public BaseResponseInfo booksChange(HttpServletRequest req ,
                                            @RequestParam(value = "money", required = true) Integer money,
                                            @RequestParam(value = "notice", required = false) String notice,
                                            @RequestParam(value = "time", required = true) long time,
                                            @RequestParam(value = "typeId", required = true) int typeId,
                                            @RequestParam(value = "file", required = false) MultipartFile[] file) {
            BaseResponseInfo res = new BaseResponseInfo();
            HttpSession uuu = req.getSession();
            List<Users> users = (List<Users>) uuu.getAttribute("username");
            String username = users.get(0).getUsername();
            try {
                String check = this.booksService.picAddNewId(file, username);
                if (check.equals("")){
                    res.code = -200;
                    res.msg = "上传文件出错";
                }else {
                    pigList pigList = new pigList(username, money, time, (long) 0, 1, notice, typeId, check);
                    int count = this.booksService.booksAdd(pigList);
                    if ( count > 0){
                        res.code = 200;
                        res.msg = "保存成功";
                    }else {
                        res.code = -1;
                        res.msg = "保存失败";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                res.code = -1;
                res.msg = "服务器出错";
            }
            return res;
        }

        /**
         * 账单查询
         * @param req
         * @param page
         * @param pageSize
         * @param type
         * @param stime
         * @param etime
         * @return
         */
        @ApiOperation(value = "账单查询")
        @RequestMapping("booksFind")
        public BaseResponseInfo booksFind(HttpServletRequest req,
                                          @RequestParam(value = "page", required = true) Integer page,
                                          @RequestParam(value = "pageSize", required = true) Integer pageSize,
                                          @RequestParam(value = "type", required = false) String type,
                                          @RequestParam(value = "stime", required = false) Long stime,
                                          @RequestParam(value = "etime", required = false) Long etime) {
            BaseResponseInfo res = new BaseResponseInfo();
            HttpSession uuu = req.getSession();
            List<Users> users = (List<Users>) uuu.getAttribute("username");
            if (users != null){
                String username = users.get(0).getUsername();
                try {
                    pageInfoB<pigList> pigList = this.booksService.pigListFind(username, page, pageSize, type, stime, etime);
                    res.data = pigList;
                    res.code = 200;
                    res.msg = "查询成功";
                } catch (Exception e) {
                    e.printStackTrace();
                    res.code = -1;
                    res.msg = "服务器出错";
                }
            }else {
                res.code = -200;
                res.msg = "请先登录";
            }
            return res;
        }
    }
}
