package com.family168.taglib.page;


/**
 * 跳转到第一页.
 *
 * @author Lingo
 */
public class FirstTag extends JumpTagSupport {
    /** serial. */
    static final long serialVersionUID = 0L;

    /**
     * 是否生成超链接.
     *
     * @return 如果已经在第一页，就不生成超链接
     */
    protected boolean skip() {
        int currentPageNo = pagerTag.getCurrentPageNo();

        return currentPageNo <= 1;
    }

    /** @return 默认跳转到第一页. */
    protected int getJumpPage() {
        return 1;
    }
}
