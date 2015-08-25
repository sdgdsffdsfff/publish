package com.xiaomi.plugin.bean;

import java.util.List;

public class PageResults<T> {

    // 下一页
    private int pagenumber;

    // 当前页
    private int currentPage;

    // 每页个个数
    private int pageSize;

    // 总条数
    private int totalCount;

    // 总页数
    private int pagecount;

    // 记录
    private List<T> results;

    public int getPagecount() {
        return pagecount;
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
    }

    public int getPagenumber() {
        if (pagenumber <= 0) {
            return 1;
        } else {
            return pagenumber > pagecount ? pagecount : pagenumber;
        }
    }

    public void setPagenumber(int pagenumber) {
        this.pagenumber = pagenumber;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize <= 0 ? 10 : pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void resetPageNo() {
        pagenumber = currentPage + 1;
        pagecount = totalCount % pageSize == 0 ? totalCount / pageSize
                : totalCount / pageSize + 1;
    }

}