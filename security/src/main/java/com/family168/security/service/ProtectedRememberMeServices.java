package com.family168.security.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.Authentication;
import org.springframework.security.ui.rememberme.TokenBasedRememberMeServices;


/**
 * TokenBasedRememberMeServices的logout方法，在session失效的时候会抛出NullPointerException.
 * 这里对问题进行了修补.
 *
 * @author Lingo
 * @since 2007-06-05
 */
public class ProtectedRememberMeServices
    extends TokenBasedRememberMeServices {
    /**
     * 注销.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param authentication 可能为null
     */
    @Override
    public void logout(HttpServletRequest request,
        HttpServletResponse response, Authentication authentication) {
        //logger.info("start");
        //response.addCookie(makeCancelCookie(request));
        //logger.info("end");
        super.logout(request, response, authentication);
    }
}
