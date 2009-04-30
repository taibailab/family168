package com.family168.taglib.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * 分页标签库.
 * 参考：[jsptags.com Pager] Tag Library v2.0
 *
 * @author Lingo
 */
public class PagerTag extends TagSupport {
    /** serial. */
    static final long serialVersionUID = 0L;

    //
    /** 似乎是在jsp中用来注册自己的ID. */
    public static final String DEFAULT_ID = "PAGER_TAG";

    /** 默认快进和快退都是跳转5页. */
    public static final int DEFAULT_JUMP_STEP = 5;

    /** 默认表示当前第几页的参数名. */
    public static final String DEFAULT_CURRENT_PAGE_NAME = "pageNo";

    /** 默认每页最多显示20项. */
    public static final int DEFAULT_PAGE_SIZE = 20;

    /** 默认最多显示10个页号. */
    public static final int DEFAULT_MAX_INDEX = 10;

    /** 显示页号的策略，当前页总在最前面. */
    public static final String FORWARD = "forward";

    /** 显示页号的策略，当前页在中间. */
    public static final String CENTER = "center";

    /** 显示页号的策略，当前页在全部页号的中间. */
    public static final String HALF_FULL = "half-full";

    //
    /** 当前页号，默认在第一页. */
    private int currentPageNo = 1;

    /** 显示页号的策略，默认是居中. */
    private String index = CENTER;

    //
    /** 记录总数. */
    private int totalCount = 0;

    /** 每页显示最大记录数. */
    private int pageSize = DEFAULT_PAGE_SIZE;

    /** 每次显示页号的最大数量. */
    private int maxIndex = DEFAULT_MAX_INDEX;

    /** 执行分页的查询URL，默认为当前页. */
    private String pageUrl = "";

    /** 分页查询传递的参数名. */
    private String params = null;

    /** 表示当前第几页的参数名. */
    private String currentPageName = DEFAULT_CURRENT_PAGE_NAME;

    /**
     * 执行跳转时执行的js函数名.
     * 如果设置了这个变量，说明使用用户自己定义的js，不会自动生成默认的js处理函数
     */
    private String jumpFunction = null;

    //
    /** 执行PagerTag之前，pageContext中保存的currentPageNo变量. */
    private Object oldCurrentPageNo = null;

    /** 执行PagerTag之前，pageContext中保存的pageCount变量. */
    private Object oldPageCount = null;

    /** 执行PagerTag之前，pageContext中保存的pageNumber变量. */
    private Object oldPageNumber = null;

    /**
     * 标签开始.
     *
     * @return 如果只有一页就不显示
     * @throws JspException jsp异常
     */
    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext
            .getRequest();

        try {
            currentPageNo = Integer.parseInt(request.getParameter(
                        currentPageName));
        } catch (Exception ex) {
            System.err.println(ex);
        }

        if (pageUrl == null) {
            pageUrl = request.getContextPath() + request.getServletPath();
        }

        StringBuffer buff = new StringBuffer();
        buff.append(
            "<form id=\"PAGER_TAG_FORM\" method=\"POST\" action=\"")
            .append(pageUrl).append("\" style=\"display:none;\">")
            .append("<input type=\"hidden\" name=\"pageNo\" value=\"\" />");

        for (String param : params.split(",")) {
            String paramName = param.trim();
            String paramValue = request.getParameter(paramName);

            try {
                if ((paramValue != null)
                        && (paramValue.trim().length() != 0)) {
                    buff.append("<input type=\"hidden\" name=\"")
                        .append(paramName).append("\" value=\"")
                        .append(paramValue).append("\"/>");
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }

        buff.append("</form>");

        if (jumpFunction == null) {
            buff.append("<script type=\"text/javascript\">")
                .append("function jumpFunc(pageNo){")
                .append("var form = document.getElementById(\"PAGER_TAG_FORM\");")
                .append("form.pageNo.value = pageNo;form.submit();}</script>");
        }

        try {
            pageContext.getOut().print(buff.toString());
        } catch (Exception ex) {
            System.err.println(ex);
        }

        //
        int pageCount = this.getPageCount();

        if (pageCount < 2) {
            return SKIP_BODY;
        } else {
            oldCurrentPageNo = setAttribute("currentPageNo", currentPageNo);
            oldPageCount = setAttribute("pageCount", pageCount);
            oldPageNumber = setAttribute("pageNumber", null);

            return EVAL_BODY_INCLUDE;
        }
    }

    /**
     * 标签结束.
     *
     * @return 继续执行页面其他部分
     */
    @Override
    public int doEndTag() {
        restoreAttribute("currentPageNo", oldCurrentPageNo);
        restoreAttribute("pageCount", oldPageCount);
        restoreAttribute("pageNumber", oldPageNumber);
        oldCurrentPageNo = null;
        oldPageCount = null;
        oldPageNumber = null;

        return EVAL_PAGE;
    }

    /** 释放资源. */
    @Override
    public void release() {
        oldCurrentPageNo = null;
        oldPageCount = null;
        oldPageNumber = null;
    }

    /**
     * 替换pageContext中的参数，返回老参数值.
     *
     * @param name 参数名
     * @param value 新设置的参数值
     * @return 老参数值
     */
    private Object setAttribute(String name, Object value) {
        Object oldValue = pageContext.getAttribute(name);
        pageContext.setAttribute(name, value);

        return oldValue;
    }

    /**
     * 重新设置pageContext中的老参数值，如果老参数值为null就删除对应参数.
     *
     * @param name 参数名
     * @param value 老参数值
     */
    private void restoreAttribute(String name, Object value) {
        if (value != null) {
            pageContext.setAttribute(name, value);
        } else {
            pageContext.removeAttribute(name);
        }
    }

    /** @return 获得当前页号. */
    public int getCurrentPageNo() {
        return currentPageNo;
    }

    /** @return 获得总页数. */
    public int getPageCount() {
        if ((totalCount % pageSize) == 0) {
            return totalCount / pageSize;
        } else {
            return (totalCount / pageSize) + 1;
        }
    }

    /**
     * 根据跳转的页号获得跳转超链接.
     *
     * @param jumpPage 跳转的页号
     * @return 生成的超链接
     */
    public String getPageUrl(int jumpPage) {
        if (jumpFunction == null) {
            return "javascript:jumpFunc(" + jumpPage + ");void(0);";
        } else {
            return "javascript:" + jumpFunction + "(" + jumpPage
            + ");void(0);";
        }
    }

    /** @return 获得显示的第一个页号. */
    public int getFirstIndexPage() {
        int firstPage = 0;
        int halfIndexPages = maxIndex / 2;

        if (FORWARD.equals(index)) {
            firstPage = Math.min(currentPageNo + 1, getPageCount());
        } else if (!(HALF_FULL.equals(index)
                && (currentPageNo < halfIndexPages))) {
            int pages = getPageCount();

            if (pages > maxIndex) {
                // put the current page in middle of the index
                firstPage = Math.max(0, currentPageNo - halfIndexPages);

                int indexPages = pages - firstPage;

                if (indexPages < maxIndex) {
                    firstPage -= (maxIndex - indexPages);
                }
            }
        }

        return firstPage + 1;
    }

    /**
     * 获得显示的最后一个页号.
     *
     * @param firstPage 显示的第一个页号
     * @return 显示的最后一个页号
     */
    public int getLastIndexPage(int firstPage) {
        int pages = getPageCount();
        int halfIndexPages = maxIndex / 2;
        int maxPages;

        if (HALF_FULL.equals(index) && (currentPageNo < halfIndexPages)) {
            maxPages = currentPageNo + halfIndexPages;
        } else {
            maxPages = firstPage + maxIndex;
        }

        return ((pages <= maxPages) ? pages : maxPages);
    }

    //
    /** @param totalCount 最大记录数. */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /** @param longTotalCount long型的最大记录数. */
    public void setLongTotalCount(long longTotalCount) {
        this.totalCount = Long.valueOf(longTotalCount).intValue();
    }

    /** @param pageSize 每页显示的最大数. */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /** @param maxIndex 每页显示的页号的最大数量. */
    public void setMaxIndex(int maxIndex) {
        this.maxIndex = maxIndex;
    }

    /** @param pageUrl 分页查询调用的url. */
    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    /** @param params 查询传递的参数名. */
    public void setParams(String params) {
        this.params = params;
    }

    /** @param currentPageName 表示当前页号的参数名. */
    public void setCurrentPageName(String currentPageName) {
        this.currentPageName = currentPageName;
    }

    /** @param jumpFunction 设置跳转调用的js函数. */
    public void setJumpFunction(String jumpFunction) {
        this.jumpFunction = jumpFunction;
    }
}
