package com.family168.security.jcaptcha.engine;

import java.awt.Color;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.TwistedAndShearedRandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;


/**
 * SpringSide Custom的认证图片.
 *
 * @author cac
 * Author Lingo
 * @since 2007-04-07
 * @version 1.0
 */
public class SpringSideCaptchaEngine extends ListImageCaptchaEngine {
    /**
     * 建立初始化工厂.
     */
    protected void buildInitialFactories() {
        WordGenerator wordGenerator = (new RandomWordGenerator(
                "0123456789"));

        //Integer minAcceptedWordLength, Integer maxAcceptedWordLength,Color[] textColors
        TextPaster textPaster = new RandomTextPaster(CaptchaConstants.DEFAULT_WORD_MIN,
                CaptchaConstants.DEFAULT_WORD_MAX, Color.WHITE);

        //Integer width, Integer height
        BackgroundGenerator backgroundGenerator = new FunkyBackgroundGenerator(CaptchaConstants.DEFAULT_PIC_WIDTH,
                CaptchaConstants.DEFAULT_PIC_HEIGHT);

        //Integer minFontSize, Integer maxFontSize
        FontGenerator fontGenerator = new TwistedAndShearedRandomFontGenerator(CaptchaConstants.DEFAULT_FONT_SIZE_MIN,
                CaptchaConstants.DEFAULT_FONT_SIZE_MAX);
        WordToImage wordToImage = new ComposedWordToImage(fontGenerator,
                backgroundGenerator, textPaster);
        addFactory(new GimpyFactory(wordGenerator, wordToImage));
    }
}
