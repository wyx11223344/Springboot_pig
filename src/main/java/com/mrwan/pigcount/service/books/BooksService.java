package com.mrwan.pigcount.service.books;

import com.mrwan.pigcount.pojo.pageInfoB;
import com.mrwan.pigcount.pojo.pigList;
import org.springframework.web.multipart.MultipartFile;

public interface BooksService {

    /**
     * 图片上传返回id值
     * @param files
     * @return
     */
    String picAddNewId(MultipartFile[] files, String username);

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
     * @param page
     * @param pageSize
     * @param type
     * @param stime
     * @param etime
     */
    pageInfoB<pigList> pigListFind(String username, Integer page, Integer pageSize, String type, Long stime, Long etime);
}