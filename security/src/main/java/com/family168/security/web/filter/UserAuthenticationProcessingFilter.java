package com.family168.security.web.filter;


//import java.io.IOException;

//import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.family168.security.domain.User;
import com.family168.security.manager.UserManager;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;

//import org.springframework.security.event.authentication.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;
import org.springframework.security.userdetails.UserDetails;


/**
 * 把User变量放入http session中,key为Constants.USER_IN_SESSION.
 *
 * @author cac
 * @author Lingo
 * @since 2007-03-25
 * @version 1.0
 */
public class UserAuthenticationProcessingFilter
    extends AuthenticationProcessingFilter {
    /**
     * 会话中用户标志.
     */
    public static final String USER_IN_SESSION = "loginUser";

    /**
     * UserDao.
     */
    private UserManager userManager = null;

    /**
     * @param userManager UserManager.
     */
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * 是否需要授权.
     *
     * @param request 请求
     * @param response 响应
     * @return 是否需要授权
     */
    protected boolean requiresAuthentication(HttpServletRequest request,
        HttpServletResponse response) {
        //logger.info("start");
        boolean requiresAuth = super.requiresAuthentication(request,
                response);

        HttpSession httpSession = request.getSession(true);

        if (!requiresAuth && (httpSession != null)
                && (httpSession.getAttribute(USER_IN_SESSION) == null)) {
            SecurityContext sc = SecurityContextHolder.getContext();
            Authentication auth = sc.getAuthentication();

            if ((auth != null)
                    && auth.getPrincipal() instanceof UserDetails) {
                UserDetails ud = (UserDetails) auth.getPrincipal();
                User user = userManager.getUserByLoginidAndPasswd(ud
                        .getUsername(), ud.getPassword());
                httpSession.setAttribute(USER_IN_SESSION, user);
            }
        }

        return requiresAuth;
    }

    // 验证成功，返回json消息
    /**
     * 覆盖超类的方法，在验证成功的时候返回json消息，而不是跳转.
     *
     * @param request 请求
     * @param response 响应
     * @param authResult 验证结果
     * @throws IOException 异常
     */

    /*
            @Override
            protected void successfulAuthentication(HttpServletRequest request,
                HttpServletResponse response, Authentication authResult)
                throws IOException {
                if (logger.isDebugEnabled()) {
                    logger.debug("Authentication success: "
                        + authResult.toString());
                }
                SecurityContextHolder.getContext().setAuthentication(authResult);
                if (logger.isDebugEnabled()) {
                    logger.debug(
                        "Updated SecurityContextHolder to contain the following Authentication: '"
                        + authResult + "'");
                }
                String targetUrl = determineTargetUrl(request);
                if (logger.isDebugEnabled()) {
                    logger.debug(
                        "Redirecting to target URL from HTTP Session (or default): "
                        + targetUrl);
                }
                onSuccessfulAuthentication(request, response, authResult);
                getRememberMeServices().loginSuccess(request, response, authResult);
                // Fire event
                if (this.eventPublisher != null) {
                    eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(
                            authResult, this.getClass()));
                }
                // 本来程序里是直接跳转的。但这里我们使用ajax发送登录信息，直接跳转就不行了。
                // sendRedirect(request, response, targetUrl);
                // 所以我们需要发送json数据，包括数据1，登录成功，2，已登录的用户真实姓名，3，默认跳转过去的页面。
                if (!targetUrl.startsWith("http")) {
                    targetUrl = request.getContextPath() + targetUrl;
                }
                sendRedirect(request, response,
                    "/login/loginSuccess.htm?callback="
                    + URLEncoder.encode(targetUrl, "UTF-8"));
            }
    */
}
