/* Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.security.captcha;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.InitializingBean;

import org.springframework.security.context.SecurityContextHolder;


/**
 * Filter for web integration of the {@link CaptchaServiceProxy}.
 * <p>
 * It basically intercept calls containing the specific validation parameter, uses the {@link CaptchaServiceProxy} to
 * validate the request, and update the {@link CaptchaSecurityContext} if the request passed the validation.
 * <p>
 * This Filter should be placed after the ContextIntegration filter and before the {@link
 * CaptchaChannelProcessorTemplate} filter in the filter stack in order to update the {@link CaptchaSecurityContext}
 * before the humanity verification routine occurs.
 * <p>
 * This filter should only be used in conjunction with the {@link CaptchaSecurityContext}<br>
 *
 * @author marc antoine Garrigue
 * @version $Id: CaptchaValidationProcessingFilter.java 2743 2008-03-17 11:06:32Z luke_t $
 */
public class CaptchaValidationProcessingFilter implements InitializingBean,
    Filter {
    /** * logger. */
    private static Log logger = LogFactory.getLog(CaptchaValidationProcessingFilter.class);

    /** * captcha service. */
    private CaptchaServiceProxy captchaService = null;

    /** * captcha validation parameter. */
    private String captchaValidationParameter = "_captcha_parameter";

    /** * @throws Exception exception. */
    public void afterPropertiesSet() throws Exception {
        if (this.captchaService == null) {
            throw new IllegalArgumentException(
                "CaptchaServiceProxy must be defined ");
        }

        if ((this.captchaValidationParameter == null)
                || "".equals(captchaValidationParameter)) {
            throw new IllegalArgumentException(
                "captchaValidationParameter must not be empty or null");
        }
    }

    /**
     * Does nothing. We use IoC container lifecycle services instead.
     */
    public void destroy() {
    }

    /**
     * @param request servlet request.
     * @param response servlet response
     * @param chain Filter chain
     * @throws IOException io
     * @throws ServletException servlet
     */
    public void doFilter(ServletRequest request, ServletResponse response,
        FilterChain chain) throws IOException, ServletException {
        String captchaResponse = request.getParameter(captchaValidationParameter);

        if (request instanceof HttpServletRequest
                && (captchaResponse != null)) {
            logger.debug("captcha validation parameter found");

            // validate the request against CaptchaServiceProxy
            boolean valid = false;

            logger.debug("try to validate");

            //get session
            HttpSession session = ((HttpServletRequest) request).getSession();

            if (session != null) {
                String id = session.getId();
                valid = this.captchaService.validateReponseForId(id,
                        captchaResponse);
                logger.debug(
                    "captchaServiceProxy says : request is valid = "
                    + valid);

                if (valid) {
                    logger.debug("update the context");
                    ((CaptchaSecurityContext) SecurityContextHolder
                    .getContext()).setHuman();

                    //logger.debug("retrieve original request from ")
                } else {
                    logger.debug("captcha test failed");
                }
            } else {
                logger.debug(
                    "no session found, user don't even ask a captcha challenge");
            }
        } else {
            logger.debug(
                "captcha validation parameter not found, do nothing");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("chain ...");
        }

        chain.doFilter(request, response);
    }

    /** * @return captcha service proxy. */
    public CaptchaServiceProxy getCaptchaService() {
        return captchaService;
    }

    /** * @return captcha validation parameter. */
    public String getCaptchaValidationParameter() {
        return captchaValidationParameter;
    }

    /**
     * Does nothing. We use IoC container lifecycle services instead.
     *
     * @param filterConfig ignored
     *
     * @throws ServletException ignored
     */
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /** * @param captchaService CaptchaServiceProxy. */
    public void setCaptchaService(CaptchaServiceProxy captchaService) {
        this.captchaService = captchaService;
    }

    /** * @param captchaValidationParameter String. */
    public void setCaptchaValidationParameter(
        String captchaValidationParameter) {
        this.captchaValidationParameter = captchaValidationParameter;
    }
}
