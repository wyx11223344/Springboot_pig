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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @Cacheable(value = "picList" , key = "targetClass + methodName + #type + #create_itime + #create_etime + #page + #pageSize + #isDel")
    public pageInfoB<picList> typePicList(String type, Long create_itime, Long create_etime, Integer page, Integer pageSize, Boolean isDel){
        pageInfoB<picList> pageInfo = null;
        try {
            PageHelper.startPage(page,pageSize);
            List<picList> picList = this.picListMapper.ListGet(type, create_itime, create_etime, isDel);
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

    /**
     * 图片修改
     * @param picList
     * @param new_url
     * @param file
     * @return
     * @throws IOException
     */
    @Override
    @CacheEvict(value = "picList" , allEntries = true)
    public Boolean picChange(picList picList, String new_url, MultipartFile file) throws IOException {
        //对文文件的全名进行截取然后在后缀名进行删选。
        int begin = file.getOriginalFilename().indexOf(".");
        int last = file.getOriginalFilename().length();
        //获得文件后缀名
        String a = file.getOriginalFilename().substring(begin, last);
        InputStream inputStream=file.getInputStream();
        if ( picList.getId() == null ){
            //新增
            Boolean flag = FtpFileUtil.uploadFile(new_url + a, inputStream, picList.getType());
            if(flag){
                picList picNewList = new picList(null, new_url + a, picList.getType(), (long) 0);
                int count = this.picListMapper.picAddNew(picNewList);
                if (count > 0){
                    flag = true;
                }else {
                    flag = false;
                }
                return flag;
            }else {
                return false;
            }
        }else {
            //修改
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

    /**
     * 删除图片
     * @param del
     * @param id
     * @return
     */
    @Override
    @CacheEvict(value = "picList" , allEntries = true)
    public int picDel(Boolean del, Integer id) {
        int count = 0;
        try {
            count = this.picListMapper.picDel(del, id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 把图片从服务器上删除
     * @return
     */
    @Override
    @CacheEvict(value = "picList" , allEntries = true)
    public int picThrow(){
        AtomicInteger count = new AtomicInteger();
        try {
            List<picList> delPicList = this.picListMapper.picOverTime();
            delPicList.forEach(pic -> {
                System.out.println(pic.getId());
                //删除文件
                try {
                    if (FtpFileUtil.deleteFile(pic.getPic_url(), pic.getType())){
                        int num = this.picListMapper.picDump(pic.getId());
                        if ( num == 0 ){
                            System.out.println("id为"+pic.getId()+"的图片数据库删除失败");
                        }else {
                            count.getAndIncrement();
                        }
                    }else {
                        System.out.println("id为"+pic.getId()+"的图片删除失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return count.get();
    }
}
