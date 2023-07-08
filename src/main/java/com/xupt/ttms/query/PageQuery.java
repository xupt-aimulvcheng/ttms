package com.xupt.ttms.query;/*
 * @author ck
 * @date 2023/4/19
 * @apiNote
 */

public class PageQuery {
    private Integer page;
    private Integer limit;

    public PageQuery(int page, int limit) {
        this.page = page;
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "PageVo{" +
                "page=" + page +
                ", limit=" + limit +
                '}';
    }
}
