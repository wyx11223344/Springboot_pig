package com.mrwan.pigcount.controller;

import com.mrwan.pigcount.mapper.PicListMapper;
import com.mrwan.pigcount.pojo.picList;
import com.mrwan.pigcount.pojo.typeList;
import com.mrwan.pigcount.service.webMessage.WebMessageService;
import com.mrwan.pigcount.utils.BaseResponseInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@RestController
public class webChangeControl {


    @Api(value="网页基础信息修改" , tags="网页基础信息修改")
    @RestController
    @RequestMapping(value = "webChange", method = RequestMethod.POST)
    public class webChange {

        @Autowired
        private WebMessageService webMessageService;

        /**
         * 修改和新增类型列表
         * @param id
         * @param type
         * @param typename
         * @param is_get
         * @param icon_name
         * @param bgc
         * @return
         */
        @ApiOperation(value = "修改和新增类型列表")
        @RequestMapping(value = "list_change")
        public BaseResponseInfo userGet(@RequestParam(value = "id", required = false) Integer id,
                                        @RequestParam(value = "type", required = false) Integer type,
                                        @RequestParam(value = "typename", required = false) String typename,
                                        @RequestParam(value = "is_get", required = false) Integer is_get,
                                        @RequestParam(value = "icon_name", required = false) String icon_name,
                                        @RequestParam(value = "bgc", required = false) String bgc){
            BaseResponseInfo res = new BaseResponseInfo();
            try {
                typeList changetype = new typeList(id , type , typename , is_get , icon_name , bgc);
                int count  = this.webMessageService.listChange(changetype);
                if ( count > 0 ){
                    res.code = 200;
                    res.msg = "修改成功";
                }else {
                    res.code = 1;
                    res.msg = "修改失败";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }

        /**
         * 修改和新增图片
         * @param id
         * @param type
         * @param pic_url
         * @param new_url
         * @param file
         * @return
         */
        @ApiOperation(value = "修改、删除和新增图片")
        @RequestMapping("pic_change")
        public BaseResponseInfo picChange(@RequestParam(value = "id", required = false) Integer id,
                                          @RequestParam(value = "type", required = false) String type,
                                          @RequestParam(value = "pic_url", required = false) String pic_url,
                                          @RequestParam(value = "new_url", required = false) String new_url,
                                          @RequestParam(value = "file", required = false) MultipartFile file,
                                          @RequestParam(value = "del", required = false) Boolean del){
            BaseResponseInfo res = new BaseResponseInfo();
            try {
                if (del != null){
                    int count = this.webMessageService.picDel(del, id);
                    if ( count >= 1 ){
                        res.code = 200;
                        res.msg = "操作成功";
                    }else {
                        res.code = 1;
                        res.msg = "操作失败";
                    }
                }else {
                    picList picList = new picList(id, pic_url, type, (long) 0);
                    Boolean isSuccess = this.webMessageService.picChange(picList, new_url, file);
                    if ( isSuccess ){
                        res.code = 200;
                        res.msg = "操作成功";
                    }else {
                        res.code = 1;
                        res.msg = "操作失败";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                res.code = -1;
                res.msg = "服务器出错";
            }
            return res;
        }
    }
}
