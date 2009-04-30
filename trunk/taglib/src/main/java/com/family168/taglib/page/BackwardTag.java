package com.family168.taglib.page;


/**
 * 后退几页.
 *
 * @author Lingo
 */
public class BackwardTag extends JumpTagSupport {
    /** serial. */
    static final long serialVersionUID = 0L;

    /** 默认后退5页. */
    private int step = PagerTag.DEFAULT_JUMP_STEP;

    /**
     * 是否不生成超链接.
     *
     * @return 如果当前是第一页就不生成超链接
     */
    protected boolean skip() {
        int currentPageNo = pagerTag.getCurrentPageNo();

        return (step < 1) || (currentPageNo <= 1);
    }

    /**
     * 计算后退到哪一页.
     *
     * @return 默认是跳转到pageNo - step页，如果结果小于1，就跳转到第一页
     */
    protected int getJumpPage() {
        int currentPageNo = pagerTag.getCurrentPageNo();

        int pageNo = currentPageNo - step;

        if (pageNo < 1) {
            pageNo = 1;
        }

        return pageNo;
    }

    /** @param step 设置后退的页数. */
    public void setStep(int step) {
        this.step = step;
    }
}
