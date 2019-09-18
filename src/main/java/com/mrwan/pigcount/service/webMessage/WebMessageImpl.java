package com.mrwan.pigcount.service.webMessage;

import com.github.pagehelper.PageHelper;
import com.mrwan.pigcount.mapper.PicListMapper;
import com.mrwan.pigcount.mapper.TypeListMapper;
import com.mrwan.pigcount.pojo.pageInfoB;
import com.mrwan.pigcount.pojo.picList;
import com.mrwan.pigcount.pojo.typeList;
import com.mrwan.pigcount.utils.FtpFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@Service
public class WebMessageImpl implements WebMessageService {
    @Autowired
    private TypeListMapper typeListMapper;

    @Autowired
    private PicListMapper picListMapper;

    /**
     * 获取类型列表
     * @return
     */
    @Override
    @Cacheable(value = "typeList" , key = "targetClass + methodName + #type")
    public List<typeList> listGet(Integer type){
        List<typeList> typeList = null;
        try {
            typeList = this.typeListMapper.listGet(type);
        }catch (Exception e){
            e.printStackTrace();
        }
        return typeList;
    }

    /**
     * 类型列表修改
     * @param changetype
     * @return
     */
    @Override
    @Caching(evict = {
            @CacheEvict(value = "typeList" , allEntries = true),
            @CacheEvict(value = "picList" , key = "targetClass + 'typePicNo'")
    })
    public int listChange(typeList changetype){
        int count = 0;
        try {
            if (changetype.getId() == null){
                count = this.typeListMapper.listAdd(changetype);
            }else {
                count = this.typeListMapper.listChange(changetype);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 获取能够用在类型的图片
     * @return
     */
    @Override
    @Cacheable(value = "picList" , key = "targetClass + methodName")
    public List<picList> typePicNo(){
        List<picList> picList = null;
        try {
            picList = this.picListMapper.TypeListNo();
        }catch (Exception e){
            e.printStackTrace();
        }
        return picList;
    }

    /**
     * 图片获取
     * @param type
     * @param create_itime
     * @param create_etime
     * @return
     */
    @Override
    @Cacheable(value = "picList" , key = "targetClass + methodName + #type + #create_itime + #create_etime + #page + #pageSize")
    public pageInfoB<picList> typePicList(String type, Long create_itime, Long create_etime, Integer page, Integer pageSize){
        pageInfoB<picList> pageInfo = null;
        try {
            PageHelper.startPage(page,pageSize);
            List<picList> picList = this.picListMapper.ListGet(type, create_itime, create_etime);
            pageInfo = new pageInfoB<picList>(picList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return pageInfo;
    }

    /**
     * 图片类型获取
     * @return
     */
    @Override
    @Cacheable(value = "picList" , key = "targetClass + methodName")
    public List<String> typePicType(){
        List<String> picList = null;
        try {
            picList = this.picListMapper.TypeListType();
        }catch (Exception e){
            e.printStackTrace();
        }
        return picList;
    }

    @Override
    @CacheEvict(value = "picList" , allEntries = true)
    public Boolean picChange(picList picList, String new_url, MultipartFile file) throws IOException {
        //对文文件的全名进行截取然后在后缀名进行删选。
        int begin = file.getOriginalFilename().indexOf(".");
        int last = file.getOriginalFilename().length();
        //获得文件后缀名
        String a = file.getOriginalFilename().substring(begin, last);
        InputStream inputStream=file.getInputStream();
        FtpFileUtil.deleteFile(picList.getPic_url(), picList.getType());
        Boolean flag = FtpFileUtil.uploadFile(new_url + a, inputStream, picList.getType());
        if(flag){
            picList picNewList = new picList(picList.getId(), new_url + a, picList.getType(), (long) 0);
            int count = this.picListMapper.picChange(picNewList);
            if (count > 0){
                flag = true;
            }else {
                flag = false;
            }
            return flag;
        }else {
            return false;
        }
    }
}
