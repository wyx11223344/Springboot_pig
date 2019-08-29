package com.mrwan.pigcount.mapper;

import com.mrwan.pigcount.pojo.typeList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类型表，数据库操作入口
 */
@Mapper
public interface TypeListMapper extends com.github.abel533.mapper.Mapper<typeList> {
    /**
     * 基础列表获取
     * @param type
     * @return
     */
    List<typeList> listGet(@Param("type") Integer type);

    /**
     * 类型列表修改
     * @param changetype
     * @return
     */
    int listChange(typeList changetype);
}
