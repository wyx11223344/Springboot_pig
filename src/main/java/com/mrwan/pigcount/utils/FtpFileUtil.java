package com.mrwan.pigcount.utils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by WYX on 2019/9/10
 */
public class FtpFileUtil {

    //ftp服务器ip地址
    private static final String FTP_ADDRESS = "36.111.183.168";
    //端口号
    private static final int FTP_PORT = 21;
    //用户名
    private static final String FTP_USERNAME = "pigcount";
    //密码
    private static final String FTP_PASSWORD = "123321sxy?";
    /** 本地字符编码 */
    private static String LOCAL_CHARSET = "UTF-8";
    // FTP协议里面，规定文件名编码为iso-8859-1
    private static String SERVER_CHARSET = "ISO-8859-1";

    public  static boolean uploadFile(String originFileName, InputStream input, String FTP_BASEPATH){
        boolean success = false;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding(SERVER_CHARSET);//1. 设置编码
        try {
            int reply;
            ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            originFileName = new String(originFileName.getBytes(LOCAL_CHARSET),
                    SERVER_CHARSET);
            System.out.println(originFileName);
            System.out.println("开始上传");
            ftp.enterLocalPassiveMode();
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.makeDirectory(FTP_BASEPATH);
            ftp.changeWorkingDirectory(FTP_BASEPATH);
            ftp.storeFile(originFileName,input);
            input.close();
            ftp.logout();
            System.out.println("上传成功");
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

    public static boolean deleteFile(String filename, String FTP_BASEPATH) throws IOException {
        System.out.println(FTP_BASEPATH + filename );
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding(SERVER_CHARSET);//1. 设置编码
        ftpClient.connect(FTP_ADDRESS, FTP_PORT); //2. 连接ftp服务器
        ftpClient.login(FTP_USERNAME, FTP_PASSWORD); //3. 登录ftp服务器
        int replyCode = ftpClient.getReplyCode(); // 判断是否成功登录服务器
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            System.err.println("连接失败");
            return false;
        }
        filename = new String(filename.getBytes(LOCAL_CHARSET),
                SERVER_CHARSET);
        System.out.println(filename);
        boolean flag = false;
        try {
            ftpClient.changeWorkingDirectory(FTP_BASEPATH);
            System.out.println("开始删除文件");
            ftpClient.dele(filename);
            ftpClient.logout();
            flag = true;
            System.out.println("删除文件成功");

        } catch (Exception e) {
            System.out.println("删除文件失败");
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    public static void main(String[] args) throws IOException {
        String filename = "测试";
        System.out.println(filename = new String(filename.getBytes(StandardCharsets.UTF_8), SERVER_CHARSET));
    }
}
