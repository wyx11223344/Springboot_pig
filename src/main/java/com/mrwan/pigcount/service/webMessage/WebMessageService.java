package com.mrwan.pigcount.service.webMessage;

import com.mrwan.pigcount.pojo.pageInfoB;
import com.mrwan.pigcount.pojo.picList;
import com.mrwan.pigcount.pojo.typeList;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface WebMessageService {
    /**
     * 基础列表获取
     *
     * @return
     */
    List<typeList> listGet(Integer type);

    /**
     * 修改类型
     *
     * @param changetype
     * @return
     */
    int listChange(typeList changetype);

    /**
     * 查看可选择类型图片
     *
     * @return
     */
    List<picList> typePicNo();

    /**
     * 查看图片
     * @param type
     * @param create_itime
     * @param create_etime
     * @param page
     * @param pageSize
     * @param isDel
     * @return
     */
    pageInfoB<picList> typePicList(String type, Long create_itime, Long create_etime, Integer page, Integer pageSize, Boolean isDel);

    /**
     * 获取图片总类型
     *
     * @return
     */
    List<String> typePicType();

    /**
     * 图片新增和修改
     *
     * @param picList
     * @param new_url
     * @param file
     * @return
     */
    Boolean picChange(picList picList, String new_url, MultipartFile file) throws IOException;

    /**
     * 图片删除恢复
     * @param del
     * @return
     */
    int picDel(Boolean del, Integer id);

    /**
     * 把图片从服务器上删除
     * @return
     */
    int picThrow();

    /**
     * 通过ids查询图片信息
     * @param ids
     * @return
     */
    List<picList> PicListByID(String ids);
}
