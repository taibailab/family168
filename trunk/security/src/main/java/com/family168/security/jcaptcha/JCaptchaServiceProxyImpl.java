package com.family168.security.jcaptcha;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.security.captcha.CaptchaServiceProxy;


/**
 * 实现 CaptchaServiceProxy 用于acegi来校验，由spring注入jcaptchaService.
 *
 * @author sshwsfc@gmail.com
 * @author Lingo
 * @since 2007-04-07
 * @version 1.0
 */
public class JCaptchaServiceProxyImpl implements CaptchaServiceProxy {
    /**
     * logger.
     */
    private static Log logger = LogFactory.getLog(JCaptchaServiceProxyImpl.class);

    /**
     * service.
     */
    private CaptchaService jcaptchaService = null;

    /**
     * 验证输入的验证码是否有效.
     *
     * @param id session中的id
     * @param response 响应
     * @return boolean 是否通过验证
     */
    public boolean validateReponseForId(String id, Object response) {
        logger.debug("validating captcha response");

        try {
            boolean isHuman = jcaptchaService.validateResponseForID(id,
                    response).booleanValue();

            if (isHuman) {
                logger.debug("captcha passed");
            } else {
                logger.warn("captcha failed");
            }

            return isHuman;
        } catch (CaptchaServiceException cse) {
            // fixes known bug in JCaptcha
            logger.warn("captcha validation failed due to exception", cse);

            return false;
        }
    }

    /**
     * @param jcaptchaService JCaptchaService.
     */
    public void setJcaptchaService(CaptchaService jcaptchaService) {
        this.jcaptchaService = jcaptchaService;
    }
}
