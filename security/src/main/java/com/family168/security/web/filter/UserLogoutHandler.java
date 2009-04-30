package com.family168.security.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.security.Authentication;
import org.springframework.security.ui.logout.LogoutHandler;


/**
 * 注销时，删除会话中的用户信息.
 *
 * @author Lingo
 * @since 2007-06-21
 */
public class UserLogoutHandler implements LogoutHandler {
    /** * logger. */
    private static Log logger = LogFactory.getLog(UserLogoutHandler.class);

    /**
     * 用户注销时，从session中删除登录的用户信息.
     *
     * @param request the HTTP request
     * @param response the HTTP resonse
     * @param authentication the current principal details
     */
    public void logout(HttpServletRequest request,
        HttpServletResponse response, Authentication authentication) {
        logger.info("start");

        HttpSession session = request.getSession(false);

        if (session != null) {
            logger.info(session);
            session.invalidate();
        }

        logger.info("end");
    }
}
