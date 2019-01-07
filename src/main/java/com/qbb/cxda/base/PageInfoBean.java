package com.qbb.cxda.base;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class PageInfoBean<T> {
    private Long total;//总条数
    private int pageIndex;// 页码
    private int pageSize ;// 页面大小
    private int pageTotal; // 总页数
    private List<T> data;//数据

    public PageInfoBean(){}

    public PageInfoBean(PageInfo pageInfo){
        this.total = pageInfo.getTotal();
        this.pageIndex = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.pageTotal = pageInfo.getPages();
        this.data = pageInfo.getList();
    }

}
