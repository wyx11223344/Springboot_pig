package com.mrwan.pigcount.utils;

import java.util.Date;

public class code_get {

    /**
     * 获取code值
     * @return
     * @throws Exception
     */
    public static String code_get() throws Exception {
        int date = (int) (new Date().getTime() / 1000);
        String code_string = generateRandomStr(10);
        String my_string = "LOVESHEN";
        String md5 = code_string + date + my_string;
        md5 = sign.MD5(md5);
        String sha1 = sign.sha1(md5);
        String arr = sha1.toUpperCase();
        return arr;
    }

    public static String generateRandomStr(int len) {
        //字符源，可以根据需要删减
        String generateSource = "0123456789abcdefghigklmnopqrstuvwxyz";
        String rtnStr = "";
        for (int i = 0; i < len; i++) {
            //循环随机获得当次字符，并移走选出的字符
            String nowStr = String.valueOf(generateSource.charAt((int) Math.floor(Math.random() * generateSource.length())));
            rtnStr += nowStr;
            generateSource = generateSource.replaceAll(nowStr, "");
        }
        return rtnStr;
    }

}
