package com.family168.core.page;


/**
 * 对于extjs使用的分页类.
 *
 * @author Lingo
 */
public class ExtjsPage extends Page {
    /** filterTxt. */
    private String filterTxt;

    /** filterValue. */
    private String filterValue;

    /** constructor. */
    public ExtjsPage() {
    }

    /**
     * 构造方法.
     *
     * @param start int
     * @param limit int
     * @param sort String
     * @param dir String
     */
    public ExtjsPage(int start, int limit, String sort, String dir) {
        this.setStart(start);
        this.setLimit(limit);
        this.setSort(sort);
        this.setDir(dir);
    }

    /**
     * 构造方法.
     *
     * @param start int
     * @param limit int
     * @param sort String
     * @param dir String
     * @param filterTxt String
     * @param filterValue String
     */
    public ExtjsPage(int start, int limit, String sort, String dir,
        String filterTxt, String filterValue) {
        this(start, limit, sort, dir);
        this.filterTxt = filterTxt;
        this.filterValue = filterValue;
    }

    /** @param start int. */
    public void setStart(int start) {
        this.start = start;

        if ((start < 0) || (pageSize < 1)) {
            pageNo = 1;
        } else {
            pageNo = (start / pageSize) + 1;
        }
    }

    /** @return limit. */
    public int getLimit() {
        return getPageSize();
    }

    /** @param limit int. */
    public void setLimit(int limit) {
        setPageSize(limit);
    }

    /** @return sort. */
    public String getSort() {
        return getOrderBy();
    }

    /** @param sort String. */
    public void setSort(String sort) {
        setOrderBy(sort);
    }

    /** @return dir. */
    public String getDir() {
        return getOrder();
    }

    /** @param dir String. */
    public void setDir(String dir) {
        setOrder(dir);
    }

    /** @return filterTxt. */
    public String getFilterTxt() {
        return filterTxt;
    }

    /** @param filterTxt String. */
    public void setFilterTxt(String filterTxt) {
        this.filterTxt = filterTxt;
    }

    /** @return filterValue. */
    public String getFilterValue() {
        return filterValue;
    }

    /** @param filterValue String. */
    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }
}
