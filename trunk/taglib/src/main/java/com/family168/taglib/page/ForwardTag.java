package com.family168.taglib.page;


/**
 * 前进几页.
 *
 * @author Lingo
 */
public class ForwardTag extends JumpTagSupport {
    /** serial. */
    static final long serialVersionUID = 0L;

    /** 默认前进5页. */
    private int step = PagerTag.DEFAULT_JUMP_STEP;

    /**
     * 是否不生成超链接.
     *
     * @return 如果当前是最后一页就不生成超链接
     */
    protected boolean skip() {
        int currentPageNo = pagerTag.getCurrentPageNo();
        int pageCount = pagerTag.getPageCount();

        return (step < 1) || (currentPageNo >= pageCount);
    }

    /**
     * 计算前进到哪一页.
     *
     * @return 默认是跳转到pageNo + step页，如果结果比总页数还大，就跳转到第一页
     */
    protected int getJumpPage() {
        int currentPageNo = pagerTag.getCurrentPageNo();
        int pageCount = pagerTag.getPageCount();
        int pageNo = currentPageNo + step;

        if (pageNo > pageCount) {
            pageNo = pageCount;
        }

        return pageNo;
    }

    /** @param step 设置前进的页数. */
    public void setStep(int step) {
        this.step = step;
    }
}
