package com.family168.taglib.page;

import javax.servlet.jsp.JspException;


/**
 * 用于跳转的抽象类.
 * 子类需要继承两个方法
 * skip()用来判断是否自动生成超链接
 * getJumpPage()用来获得跳转到第几页
 *
 * @author Lingo
 */
public abstract class JumpTagSupport extends PagerTagSupport {
    /**
     * 标签开始.
     * 根据skip()判断是否生成超链接
     *
     * @return 一定会执行标签内部代码
     * @throws JspException jsp异常
     */
    @Override
    public int doStartTag() throws JspException {
        super.doStartTag();

        if (!skip()) {
            int jumpPage = getJumpPage();
            String pageUrl = pagerTag.getPageUrl(jumpPage);
            String link = "<a href=\"" + pageUrl + "\">";

            try {
                pageContext.getOut().println(link);
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }

        return EVAL_BODY_INCLUDE;
    }

    /**
     * 标签结束.
     * 根据skip()判断是否生成超链接
     *
     * @return 一定会执行页面其他部分
     * @throws JspException jsp异常
     */
    @Override
    public int doEndTag() throws JspException {
        if (!skip()) {
            try {
                pageContext.getOut().println("</a>");
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }

        super.doEndTag();

        return EVAL_PAGE;
    }

    /** @return 是否生成超链接. */
    protected abstract boolean skip();

    /** @return 获得跳转到哪一页. */
    protected abstract int getJumpPage();
}
