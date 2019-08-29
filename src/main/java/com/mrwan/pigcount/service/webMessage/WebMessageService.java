package com.mrwan.pigcount.service.webMessage;

import com.mrwan.pigcount.pojo.typeList;

import java.util.List;

public interface WebMessageService {
    /**
     * 基础列表获取
     * @return
     */
    List<typeList> listGet(Integer type);

    /**
     * 修改类型
     * @param changetype
     * @return
     */
    int listChange(typeList changetype);
}
