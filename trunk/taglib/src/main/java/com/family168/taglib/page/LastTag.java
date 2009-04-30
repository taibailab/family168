package com.family168.taglib.page;


/**
 * 跳转到最后一页.
 *
 * @author Lingo
 */
public class LastTag extends JumpTagSupport {
    /** serial. */
    static final long serialVersionUID = 0L;

    /**
     * 是否生成超链接.
     *
     * @return 如果已经在最后一页，就不生成超链接
     */
    protected boolean skip() {
        int currentPageNo = pagerTag.getCurrentPageNo();
        int pageCount = pagerTag.getPageCount();

        return currentPageNo >= pageCount;
    }

    /** @return 默认跳转到最后一页. */
    protected int getJumpPage() {
        int pageCount = pagerTag.getPageCount();

        return pageCount;
    }
}
