package com.family168.taglib.page;

import javax.servlet.jsp.JspException;


/**
 * 当前显示的是否为当前页.
 *
 * @author Lingo
 */
public class IsCurrentPageTag extends PagerTagSupport {
    /** serial. */
    static final long serialVersionUID = 0L;

    /**
     * 根据pageContext中currentPageNo和pageNumber的值判断是否执行标签内部内容.
     *
     * @return EVAL_BODY_INCLUDE或SKIP_BODY
     * @throws JspException jsp异常
     */
    @Override
    public int doStartTag() throws JspException {
        super.doStartTag();

        Integer currentPageNo = (Integer) pageContext
            .getAttribute("currentPageNo");
        Integer pageNumber = (Integer) pageContext.getAttribute(
                "pageNumber");

        if (currentPageNo.equals(pageNumber)) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }
}
