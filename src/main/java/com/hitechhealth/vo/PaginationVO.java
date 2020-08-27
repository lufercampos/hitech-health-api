package com.hitechhealth.vo;

import java.util.ArrayList;

public class PaginationVO<T> {

    private int page = 1;
    private int pageSize = 10;
    private int totalCount = 0;
    private int totalPages = 0;
    private ArrayList<T> items;

    public PaginationVO() {
        items = new ArrayList<T>();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public ArrayList<T> getItems() {
        return items;
    }

    public void setItems(ArrayList<T> items) {
        this.items = items;
    }

    public int getTotalPages() {
        totalPages = 0;
        if (this.pageSize > 0) {
            if (totalCount % pageSize == 0) {
                totalPages = totalCount / pageSize;
            } else {
                totalPages = (totalCount / pageSize) + 1;
            }
        }
        return totalPages;
    }

}
