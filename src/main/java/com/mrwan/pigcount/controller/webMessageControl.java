package com.mrwan.pigcount.controller;

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

        @ApiOperation(value = "获取类型列表")
        @RequestMapping("list_get")
        public BaseResponseInfo userGet(@RequestParam(value = "type", required = false) Integer type){
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
    }

}
