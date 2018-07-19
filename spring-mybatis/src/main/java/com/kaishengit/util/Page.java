package com.kaishengit.util;

import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 14:31 2018/7/19
 */
public class Page<T> {

    private Integer pageNo;
    private Integer totalPage;
    private Integer pageSize;
    private Integer start;
    private List<T> items;

    public Page(int count,int pageNo,int pageSize){
        //设置总页数
        int totalPage = (count + pageSize - 1) / pageSize;

        //设置当前页
        if(pageNo > totalPage) {
            pageNo = totalPage;
        }
        if(pageNo < 1) {
            pageNo = 1;
        }

        this.pageSize = pageSize;
        this.start = (pageNo - 1) * pageSize + 1;
        this.pageNo = pageNo;
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNo=" + pageNo +
                ", totalPage=" + totalPage +
                ", pageSize=" + pageSize +
                ", start=" + start +
                ", items=" + items +
                '}';
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getStart() {
        return start;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
