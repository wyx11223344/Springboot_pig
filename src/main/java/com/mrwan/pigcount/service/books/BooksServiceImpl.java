package com.mrwan.pigcount.service.books;

import com.github.pagehelper.PageHelper;
import com.mrwan.pigcount.mapper.PicListMapper;
import com.mrwan.pigcount.mapper.PigListMapper;
import com.mrwan.pigcount.pojo.pageInfoB;
import com.mrwan.pigcount.pojo.picList;
import com.mrwan.pigcount.pojo.pigList;
import com.mrwan.pigcount.utils.FtpFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.cache.annotation.Cacheable;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BooksServiceImpl implements BooksService {

    @Autowired
    private PicListMapper picListMapper;

    @Autowired
    private PigListMapper pigListMapper;

    /**
     * 记账图片上传
     * @param files
     * @param username
     * @return
     */
    @Override
    @CacheEvict(value = "picList" , allEntries = true)
    public String picAddNewId(MultipartFile[] files, String username){
        String picIds = "";
        try {
            for (int i = 0; i < files.length; i++ ){
                //对文文件的全名进行截取然后在后缀名进行删选。
                int begin = files[i].getOriginalFilename().indexOf(".");
                int last = files[i].getOriginalFilename().length();
                //获得文件后缀名
                String name = files[i].getOriginalFilename().substring(begin, last);
                String sendName = new Date().getTime() + i + name;
                InputStream inputStream = files[i].getInputStream();
                //上传工具
                if (!FtpFileUtil.uploadFile(sendName, inputStream, "/" + username + "/")){
                    return "";
                }else {
                    picList picNewList = new picList(null, sendName, "/" + username + "/", (long) 0);
                    if (this.picListMapper.picAddNewId(picNewList) == 0){
                        return "";
                    }else {
                        picIds = picIds.equals("") ? String.valueOf(picNewList.getId()) : picIds + "," + picNewList.getId();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return picIds;
    }

    /**
     * 新增记账数据
     * @param pigList
     * @return
     */
    @Override
    @CacheEvict(value = "pigList" , allEntries = true)
    public int booksAdd(pigList pigList){
        int count = 0;
        try {
            count = this.pigListMapper.booksAdd(pigList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 记账数据查询
     * @param username
     * @param page
     * @param pageSize
     * @param type
     * @param stime
     * @param etime
     * @return
     */
    @Override
    @Cacheable(value = "pigList" , key = "targetClass + methodName + #username + #page + #pageSize + #type + #stime + #etime")
    public pageInfoB<pigList> pigListFind(String username, Integer page, Integer pageSize, String type, Long stime, Long etime) {
        pageInfoB<pigList> pageInfo = null;
        try {
            PageHelper.startPage(page,pageSize);
            List<pigList> pigList = this.pigListMapper.pigListFind(username, type, stime, etime);
            pageInfo = new pageInfoB<pigList>(pigList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }

    /**
     * 记账统计查询
     * @param username
     * @param stime
     * @param etime
     * @return
     */
    @Override
    @Cacheable(value = "pigList" , key = "targetClass + methodName + #username + #stime + #etime")
    public List<Map> pigCountType(String username, Long stime, Long etime) {
        List<Map> pigList = null;
        try {
            pigList = this.pigListMapper.pigCountType(username, stime, etime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pigList;
    }

}
