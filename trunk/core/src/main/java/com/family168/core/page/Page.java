package com.family168.core.page;

import java.util.ArrayList;
import java.util.Locale;


/**
 * 分页参数与分页结果.
 *
 * @author Lingo
 */
public class Page {
    // ==========================================
    // static fields...
    /** 正序. */
    public static final String ASC = "ASC";

    /** 倒序. */
    public static final String DESC = "DESC";

    // ==========================================
    // fields...
    /** 当前第几页，默认值为1，既第一页. */
    protected int pageNo = 1;

    /** 每页最大记录数，默认值为0，表示pageSize不可用. */
    protected int pageSize = 0;

    /** 排序字段名. */
    protected String orderBy = null;

    /** 使用正序还是倒序. */
    protected String order = ASC;

    /** 查询结果. */
    protected Object result = null;

    /** 总记录数，默认值为-1，表示totalCount不可用. */
    protected int totalCount = -1;

    /** 是否计算数据库中的记录总数. */
    protected boolean autoCount = false;

    /** 当前页第一条记录的索引，默认值为0，既第一页第一条记录. */
    protected int start = 0;

    /** 总页数，默认值为-1，表示pageCount不可用. */
    protected int pageCount = -1;

    // ==========================================
    // constructor...
    /** 构造方法. */
    public Page() {
        totalCount = 0;
        result = new ArrayList();
    }

    /**
     * 构造方法.
     *
     * @param result Object
     * @param totalCount int
     */
    public Page(Object result, int totalCount) {
        this.result = result;
        this.totalCount = totalCount;
    }

    /**
     * 构造方法.
     *
     * @param pageNo int
     * @param pageSize int
     * @param orderBy String
     * @param order String
     */
    public Page(int pageNo, int pageSize, String orderBy, String order) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
        this.setOrder(order);
        this.calculateStart();
    }

    // ==========================================
    // 工具方法
    /**
     * 是否为正序排序.
     *
     * @return boolean
     */
    public boolean isAsc() {
        return !DESC.equalsIgnoreCase(order);
    }

    /**
     * 取得倒转的排序方向.
     *
     * @return 如果dir=='ASC'就返回'DESC'，如果dir='DESC'就返回'ASC'
     */
    public String getInverseOrder() {
        if (DESC.equalsIgnoreCase(order)) {
            return ASC;
        } else {
            return DESC;
        }
    }

    /**
     * 页面显示最大记录数是否可用.
     *
     * @return pageSize > 0
     */
    public boolean isPageSizeEnabled() {
        return pageSize > 0;
    }

    /**
     * 页面第一条记录的索引是否可用.
     *
     * @return start
     */
    public boolean isStartEnabled() {
        return start >= 0;
    }

    /**
     * 排序是否可用.
     *
     * @return orderBy是否非空
     */
    public boolean isOrderEnabled() {
        return (orderBy != null) && (orderBy.trim().length() != 0);
    }

    /**
     * 是否有前一页.
     *
     * @return boolean
     */
    public boolean isPreviousEnabled() {
        return pageNo > 1;
    }

    /**
     * 是否有后一页.
     *
     * @return boolean
     */
    public boolean isNextEnabled() {
        return pageNo < pageCount;
    }

    /**
     * 总页数是否可用.
     *
     * @return boolean
     */
    public boolean isPageCountEnabled() {
        return pageCount >= 0;
    }

    /** 计算本页第一条记录的索引. */
    public void calculateStart() {
        if ((pageNo < 1) || (pageSize < 1)) {
            start = -1;
        } else {
            start = (pageNo - 1) * pageSize;
        }
    }

    /** 计算最大页数. */
    public void calculatePageCount() {
        if ((totalCount < 0) || (pageSize < 1)) {
            pageCount = -1;

            //} else if ((totalCount % pageSize) == 0) {
            //pageCount = totalCount / pageSize;
        } else {
            //pageCount = (totalCount / pageSize) + 1;
            pageCount = ((totalCount - 1) / pageSize) + 1;
        }
    }

    // ==========================================
    // getter and setter method...
    /** @return pageNo. */
    public int getPageNo() {
        return pageNo;
    }

    /** @param pageNo int. */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
        this.calculateStart();
    }

    /** @return pageSize. */
    public int getPageSize() {
        return pageSize;
    }

    /** @param pageSize int. */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        this.calculateStart();
        this.calculatePageCount();
    }

    /** @return orderBy. */
    public String getOrderBy() {
        return orderBy;
    }

    /** @param orderBy String. */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    /** @return order. */
    public String getOrder() {
        return order;
    }

    /** @param order String. */
    public void setOrder(String order) {
        if (ASC.equalsIgnoreCase(order) || DESC.equalsIgnoreCase(order)) {
            this.order = order.toUpperCase(Locale.CHINA);
        } else {
            throw new IllegalArgumentException(
                "order should be 'DESC' or 'ASC'");
        }
    }

    /** @return result. */
    public Object getResult() {
        return result;
    }

    /** @param result Object. */
    public void setResult(Object result) {
        this.result = result;
    }

    /** @return totalCount. */
    public int getTotalCount() {
        return totalCount;
    }

    /** @param totalCount int. */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        this.calculatePageCount();
    }

    /** @return autoCount. */
    public boolean isAutoCount() {
        return autoCount;
    }

    /** @param autoCount boolean. */
    public void setAutoCount(boolean autoCount) {
        this.autoCount = autoCount;
    }

    /** @return start. */
    public int getStart() {
        return start;
    }

    /** @return pageCount. */
    public int getPageCount() {
        return pageCount;
    }
}
