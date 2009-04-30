package com.family168.security.jcaptcha.engine;

import java.awt.Color;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;


/**
 * Captcha增强版本.
 *
 * @author david.turing@gmail.com
 * @modifyTime 21:01:52
 * @description Install Captcha Instruction
 * 1，add captchaValidationProcessingFilter to applicationContext-acegi-security.xml
 * 2，modify applicationContext-security-captcha.xml:
 * 2.1 make sure that captchaValidationProcessingFilter Call captchaService
 * 2.2 config CaptchaEngine for captchaService (refer imageCaptchaService)
 * 2.3 write your own CaptchaEngine(eg:SpringSideCaptchaEngine, SpringSideCaptchaEngineEx...)
 * 2.4 config the following, so that SpringSide use SpringSideCaptchaEngineEx to
 * generate the captcha image.
 * <constructor-arg type="com.octo.captcha.engine.CaptchaEngine" index="1">
 * <ref bean="SpringSideCaptchaEngineEx"/>
 * </constructor-arg>
 * 3，Test your captcha
 *
 * @author Lingo
 * @since 2007-04-07
 * @version 1.0
 */
public class SimpleCaptchaEngine extends ListImageCaptchaEngine {
    /**
     * 建立初始化工厂.
     */
    protected void buildInitialFactories() {
        /**
         * Set Captcha Word Length Limitation which should not over 6
         */

        // 生成文字的最小数量
        Integer minAcceptedWordLength = CaptchaConstants.DEFAULT_WORD_MIN;

        // 生成文字的最大数量
        Integer maxAcceptedWordLength = CaptchaConstants.DEFAULT_WORD_MAX;

        // 图片高度
        Integer imageHeight = CaptchaConstants.DEFAULT_PIC_HEIGHT;

        // 图片宽度
        Integer imageWidth = CaptchaConstants.DEFAULT_PIC_WIDTH;

        // 文字最小字体
        Integer minFontSize = CaptchaConstants.DEFAULT_FONT_SIZE_MIN;

        // 文字最大字体
        Integer maxFontSize = CaptchaConstants.DEFAULT_FONT_SIZE_MIN;

        // 随机生成文字的范围
        WordGenerator wordGenerator = (new RandomWordGenerator(
                "0123456789"));

        // 白色背景
        BackgroundGenerator backgroundGenerator = new GradientBackgroundGenerator(imageWidth,
                imageHeight, Color.white, Color.white);

        // 根据最大和最小字体，生成文字
        FontGenerator fontGenerator = new RandomFontGenerator(minFontSize,
                maxFontSize);

        // 生成彩色文字
        Color[] colors = new Color[] {
                Color.black, Color.blue, Color.green, Color.red
            };
        RandomListColorGenerator colorGenerator = new RandomListColorGenerator(colors);

        TextPaster textPaster = new DecoratedRandomTextPaster(minAcceptedWordLength,
                maxAcceptedWordLength, colorGenerator, new TextDecorator[0]);

        /**
         * ok, generate the WordToImage Object for logon service to use.
         */
        WordToImage wordToImage = new ComposedWordToImage(fontGenerator,
                backgroundGenerator, textPaster);
        addFactory(new GimpyFactory(wordGenerator, wordToImage));
    }
}
