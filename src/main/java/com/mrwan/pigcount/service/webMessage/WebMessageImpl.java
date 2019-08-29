package com.mrwan.pigcount.service.webMessage;

import com.mrwan.pigcount.mapper.TypeListMapper;
import com.mrwan.pigcount.pojo.typeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebMessageImpl implements WebMessageService {
    @Autowired
    private TypeListMapper typeListMapper;

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
    @CacheEvict(value = "typeList" , allEntries = true)
    public int listChange(typeList changetype){
        int count = 0;
        try {
            count = this.typeListMapper.listChange(changetype);
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }
}
