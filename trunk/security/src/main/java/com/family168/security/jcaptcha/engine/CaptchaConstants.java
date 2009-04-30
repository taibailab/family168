package com.family168.security.jcaptcha.engine;


/**
 * Captcha常量.
 *
 * @author Lingo
 */
public class CaptchaConstants {
    /** 文字最小数量. */
    public static final Integer DEFAULT_WORD_MIN = Integer.valueOf(4);

    /** 文字最大数量. */
    public static final Integer DEFAULT_WORD_MAX = Integer.valueOf(5);

    /** 图片高度. */
    public static final Integer DEFAULT_PIC_HEIGHT = Integer.valueOf(40);

    /** 图片宽度. */
    public static final Integer DEFAULT_PIC_WIDTH = Integer.valueOf(100);

    /** 最大字体. */
    public static final Integer DEFAULT_FONT_SIZE_MAX = Integer.valueOf(22);

    /** 最小字体. */
    public static final Integer DEFAULT_FONT_SIZE_MIN = Integer.valueOf(20);

    /** protected captcha. */
    protected CaptchaConstants() {
    }
}
