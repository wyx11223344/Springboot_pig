package com.mrwan.pigcount.mapper;

import com.mrwan.pigcount.pojo.pageInfoB;
import com.mrwan.pigcount.pojo.pigList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PigListMapper {

    /**
     * 新增记账数据
     * @param pigList
     * @return
     */
    int booksAdd(pigList pigList);

    /**
     * 记账查询
     * @return
     * @param username
     * @param type
     * @param stime
     * @param etime
     */
    List<pigList> pigListFind(@Param("username") String username,
                                   @Param("type") String type,
                                   @Param("stime") Long stime,
                                   @Param("etime") Long etime);

    /**
     * 记账统计
     * @param username
     * @param stime
     * @param etime
     * @return
     */
    List<Map> pigCountType(@Param("username") String username,
                           @Param("stime") Long stime,
                           @Param("etime") Long etime);

    /**
     * 删除记账数据
     * @param ids
     * @return
     */
    int booksDel(@Param("ids") List ids);
}
