package com.family168.taglib.page;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * 一个用于实现分页其他标签的主类.
 *
 * @author Lingo
 */
public abstract class PagerTagSupport extends TagSupport {
    /** serial. */
    static final long serialVersionUID = 0L;

    /** 主标签. */
    protected PagerTag pagerTag = null;

    /**
     * 重新设置，或删除pageContext中的一个属性.
     *
     * @param name 属性名
     * @param oldValue 如果oldValue为null就删除这个属性
     */
    protected final void restoreAttribute(String name, Object oldValue) {
        if (oldValue != null) {
            pageContext.setAttribute(name, oldValue);
        } else {
            pageContext.removeAttribute(name);
        }
    }

    /**
     * 从pageContext中查找PagerTag主标签.
     *
     * @param pagerId 主标签定义的id
     * @return 如果PagerTag找不到就返回null
     */
    private PagerTag findRequestPagerTag(String pagerId) {
        Object obj = pageContext.getRequest().getAttribute(pagerId);

        if (obj instanceof PagerTag) {
            return (PagerTag) obj;
        }

        return null;
    }

    /**
     * 标签开始.
     *
     * @return 处理标签内部内容
     * @throws JspException jsp异常
     */
    @Override
    public int doStartTag() throws JspException {
        if (id != null) {
            pagerTag = findRequestPagerTag(id);

            if (pagerTag == null) {
                throw new JspTagException("pager tag with id of \"" + id
                    + "\" not found.");
            }
        } else {
            pagerTag = (PagerTag) findAncestorWithClass(this,
                    PagerTag.class);

            if (pagerTag == null) {
                pagerTag = findRequestPagerTag(PagerTag.DEFAULT_ID);

                if (pagerTag == null) {
                    throw new JspTagException(
                        "not nested within a pager tag"
                        + " and no pager tag found at request scope.");
                }
            }
        }

        return EVAL_BODY_INCLUDE;
    }

    /**
     * 标签结束.
     *
     * @return 执行标签之后的页面内容
     * @throws JspException jsp 异常
     */
    @Override
    public int doEndTag() throws JspException {
        pagerTag = null;

        return EVAL_PAGE;
    }

    /** 释放资源. */
    @Override
    public void release() {
        pagerTag = null;
        super.release();
    }
}
