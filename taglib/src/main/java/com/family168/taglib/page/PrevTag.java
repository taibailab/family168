package com.family168.taglib.page;


/**
 * 上一页.
 *
 * @author Lingo
 */
public class PrevTag extends JumpTagSupport {
    /** serial. */
    static final long serialVersionUID = 0L;

    /** @return 如果是第一页就不生成超链接. */
    protected boolean skip() {
        int currentPageNo = pagerTag.getCurrentPageNo();

        return currentPageNo <= 1;
    }

    /** @return 跳转到当前页-1. */
    protected int getJumpPage() {
        int currentPageNo = pagerTag.getCurrentPageNo();

        return currentPageNo - 1;
    }
}
