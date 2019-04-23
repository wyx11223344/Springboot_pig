package com.mrwan.pigcount.utils;

public class BaseResponseInfo {
    public int code;
    public String msg;
    public Object data;

    public BaseResponseInfo() {
        code = 0;
        msg = "";
        data = null;
    }
}