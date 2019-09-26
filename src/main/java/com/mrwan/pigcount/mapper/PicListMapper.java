package com.mrwan.pigcount.mapper;

import com.mrwan.pigcount.pojo.picList;
import com.mrwan.pigcount.pojo.typeList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PicListMapper extends com.github.abel533.mapper.Mapper<typeList>{
    /**
     * 图片列表获取
     * @param type
     * @param create_itime
     * @param create_etime
     * @param isDel
     * @return
     */
    List<picList> ListGet(@Param("type") String type, @Param("create_itime") Long create_itime, @Param("create_etime") Long create_etime, @Param("isDel") Boolean isDel);

    /**
     * 获取能够用在类型的图片
     * @return
     */
    List<picList> TypeListNo();

    /**
     * 获取总图片类型
     * @return
     */
    List<String> TypeListType();

    /**
     * 修改图片数据库
     * @param picList
     * @return
     */
    int picChange(picList picList);

    /**
     * 图片新增
     * @param picList
     * @return
     */
    int picAddNew(picList picList);

    /**
     *
     * @param del
     * @return
     */
    int picDel(@Param("del") Boolean del, @Param("id") Integer id);
}
