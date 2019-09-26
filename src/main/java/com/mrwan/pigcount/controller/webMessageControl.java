package com.mrwan.pigcount.controller;

import com.mrwan.pigcount.pojo.pageInfoB;
import com.mrwan.pigcount.pojo.picList;
import com.mrwan.pigcount.pojo.typeList;
import com.mrwan.pigcount.utils.BaseResponseInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mrwan.pigcount.service.webMessage.WebMessageService;

import java.util.List;

@RestController
public class webMessageControl {


    @Api(value="网页基础数据获取" , tags="网页基础数据获取")
    @RestController
    @RequestMapping(value = "web" , method = RequestMethod.POST)
    public class webMessage {

        @Autowired
        private WebMessageService webMessageService;

        /**
         * 获取类型列表
         * @param type
         * @return
         */
        @ApiOperation(value = "获取类型列表")
        @RequestMapping("list_get")
        public BaseResponseInfo listGet(@RequestParam(value = "type", required = false) Integer type){
            BaseResponseInfo res = new BaseResponseInfo();
            try {
                List<typeList> typeList = this.webMessageService.listGet(type);
                res.data = typeList;
            } catch (Exception e) {
                e.printStackTrace();
            }
            res.code = 200;
            res.msg = "获取成功";
            return res;
        }

        /**
         * 类型图片列表获取
         * @param page
         * @param pageSize
         * @param type
         * @param create_itime
         * @param create_etime
         * @param choose
         * @param isDel
         * @return
         */
        @ApiOperation(value = "类型图片列表获取")
        @RequestMapping("type_pic_list")
        public BaseResponseInfo typePicList(@RequestParam(value = "page", required = false) Integer page,
                                            @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                            @RequestParam(value = "type", required = false) String type,
                                            @RequestParam(value = "create_itime", required = false) Long create_itime,
                                            @RequestParam(value = "create_etime", required = false) Long create_etime,
                                            @RequestParam(value = "choose", required = false) Boolean choose,
                                            @RequestParam(value = "isDel", required = false) Boolean isDel){
            BaseResponseInfo res = new BaseResponseInfo();
            try {
                if ( choose == null ){
                    choose = false;
                }
                if (choose == true){
                    List<picList> picList = this.webMessageService.typePicNo();
                    res.data = picList;
                }else {
                    pageInfoB<picList> picList = this.webMessageService.typePicList(type, create_itime, create_etime, page, pageSize, isDel);
                    res.data = picList;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            res.code = 200;
            res.msg = "获取成功";
            return res;
        }

        /**
         * 图片查询类型获取
         * @return
         */
        @ApiOperation(value = "图片查询类型获取")
        @RequestMapping("type_pic_type")
        public BaseResponseInfo typePicType(){
            BaseResponseInfo res = new BaseResponseInfo();
            try {
                List<String> picType = this.webMessageService.typePicType();
                res.data = picType;
            } catch (Exception e) {
                e.printStackTrace();
            }
            res.code = 200;
            res.msg = "获取成功";
            return res;
        }
    }

}
