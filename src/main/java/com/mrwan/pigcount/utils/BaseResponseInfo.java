package com.mrwan.pigcount.utils;

/**
 * 返回数据模板
 */
public class BaseResponseInfo {
    public int code;
    public String msg;
    public Object data;

    public BaseResponseInfo() {
        code = 500;
        msg = "服务器出错";
        data = null;
    }
}