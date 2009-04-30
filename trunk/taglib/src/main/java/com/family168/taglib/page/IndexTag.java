package com.family168.taglib.page;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;


/**
 * 生成可选页号的标签.
 *
 * @author Lingo
 */
public class IndexTag extends PagerTagSupport implements BodyTag {
    /** serial. */
    static final long serialVersionUID = 0L;

    /** 标签内容. */
    private transient BodyContent bodyContent = null;

    /** 需要显示的页号. */
    private int page = 0;

    /** 需要显示的最大页号. */
    private int lastPage = 0;

    /** @param bodyContent BodyContent. */
    public void setBodyContent(BodyContent bodyContent) {
        this.bodyContent = bodyContent;
    }

    /**
     * 标签开始..
     *
     * @return 如果当前页小于需要显示的最后一页，就返回EVAL_BODY_TAG，否则返回BODY
     * @throws JspException jsp异常
     */
    public int doStartTag() throws JspException {
        super.doStartTag();

        int firstPage = pagerTag.getFirstIndexPage();
        lastPage = pagerTag.getLastIndexPage(firstPage);
        page = firstPage;

        return ((page <= lastPage) ? EVAL_BODY_TAG : SKIP_BODY);
    }

    /**
     * 内容执行之前.
     *
     * @throws JspException jsp异常
     */
    public void doInitBody() throws JspException {
        String pageUrl = pagerTag.getPageUrl(page);
        pageContext.setAttribute("pageNumber", page);
        pageContext.setAttribute("pageUrl", pageUrl);
        page++;
    }

    /**
     * 内容执行结束.
     *
     * @return 如果还需要有页号需要生成就返回EVAL_BODY_TAG，如果没有页号需要执行就返回SKIP_BODY
     * @throws JspException jsp异常
     */
    public int doAfterBody() throws JspException {
        if (page <= lastPage) {
            String pageUrl = pagerTag.getPageUrl(page);
            pageContext.setAttribute("pageNumber", page);
            pageContext.setAttribute("pageUrl", pageUrl);
            page++;

            return EVAL_BODY_TAG;
        } else {
            try {
                bodyContent.writeOut(bodyContent.getEnclosingWriter());

                return SKIP_BODY;
            } catch (IOException e) {
                throw new JspTagException(e.toString());
            }
        }
    }

    /**
     * 标签结束.
     *
     * @return 继续执行页面其他内容
     * @throws JspException jsp异常
     */
    public int doEndTag() throws JspException {
        bodyContent = null;

        super.doEndTag();

        return EVAL_PAGE;
    }

    /** 释放资源. */
    @Override
    public void release() {
        bodyContent = null;
        super.release();
    }
}
