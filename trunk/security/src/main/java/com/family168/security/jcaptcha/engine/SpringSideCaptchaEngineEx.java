package com.family168.security.jcaptcha.engine;

import java.awt.Color;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.BaffleTextDecorator;
import com.octo.captcha.component.image.textpaster.textdecorator.LineTextDecorator;
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
public class SpringSideCaptchaEngineEx extends ListImageCaptchaEngine {
    /**
     * 建立初始化工厂.
     */
    protected void buildInitialFactories() {
        /**
         * Set Captcha Word Length Limitation which should not over 6
         */
        Integer minAcceptedWordLength = CaptchaConstants.DEFAULT_WORD_MIN;
        Integer maxAcceptedWordLength = CaptchaConstants.DEFAULT_WORD_MAX;

        /**
         * Set up Captcha Image Size: Height and Width
         */
        Integer imageHeight = CaptchaConstants.DEFAULT_PIC_HEIGHT;
        Integer imageWidth = CaptchaConstants.DEFAULT_PIC_WIDTH;

        /**
         * Set Captcha Font Size between 50 and 55
         */
        Integer minFontSize = CaptchaConstants.DEFAULT_FONT_SIZE_MIN;
        Integer maxFontSize = CaptchaConstants.DEFAULT_FONT_SIZE_MAX;

        /**
         * We just generate digit for captcha source char
         * Although you can use abcdefg......xyz
         */
        WordGenerator wordGenerator = (new RandomWordGenerator(
                "0123456789"));

        /**
         * cyt and unruledboy proved that backgroup not a factor of Security.
         * A captcha attacker won't affaid colorful backgroud, so we just use
         * white color, like google and hotmail.
         */
        BackgroundGenerator backgroundGenerator = new GradientBackgroundGenerator(imageWidth,
                imageHeight, Color.white, Color.white);

        /**
         * font is not helpful for security but it really increase difficultness for attacker
         */
        FontGenerator fontGenerator = new RandomFontGenerator(minFontSize,
                maxFontSize);

        /**
         * Note that our captcha color is Blue
         */
        SingleColorGenerator scg = new SingleColorGenerator(Color.blue);

        /**
         * decorator is very useful pretend captcha attack.
         * we use two line text decorators.
         */
        LineTextDecorator lineDecorator = new LineTextDecorator(1,
                Color.blue);

        // LineTextDecorator lineDecorator2 = new LineTextDecorator(1, Color.blue);
        TextDecorator[] textdecorators = new TextDecorator[1];

        textdecorators[0] = lineDecorator;

        // textdecorators[1] = lineDecorator2;
        TextPaster textPaster = new DecoratedRandomTextPaster(minAcceptedWordLength,
                maxAcceptedWordLength, scg,
                new TextDecorator[] {
                    new BaffleTextDecorator(Integer.valueOf(1), Color.white)
                });

        /**
         * ok, generate the WordToImage Object for logon service to use.
         */
        WordToImage wordToImage = new ComposedWordToImage(fontGenerator,
                backgroundGenerator, textPaster);
        addFactory(new GimpyFactory(wordGenerator, wordToImage));
    }
}
