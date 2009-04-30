package com.family168.taglib.page;


/**
 * 下一页.
 *
 * @author Lingo
 */
public class NextTag extends JumpTagSupport {
    /** serial. */
    static final long serialVersionUID = 0L;

    /** @return 如果是最后一页就不生成超链接. */
    protected boolean skip() {
        int currentPageNo = pagerTag.getCurrentPageNo();
        int pageCount = pagerTag.getPageCount();

        return currentPageNo >= pageCount;
    }

    /** @return 跳转到当前页+1. */
    protected int getJumpPage() {
        int currentPageNo = pagerTag.getCurrentPageNo();

        return currentPageNo + 1;
    }
}
