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

import java.util.Iterator;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.InitializingBean;

import org.springframework.security.ConfigAttribute;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.security.securechannel.ChannelEntryPoint;
import org.springframework.security.securechannel.ChannelProcessor;

import org.springframework.util.Assert;


/**
 * CaptchaChannel template : Ensures the user has enough human privileges by review of the {@link
 * CaptchaSecurityContext} and using an abstract routine {@link
 * #isContextValidConcerningHumanity(CaptchaSecurityContext)} (implemented by sub classes)
 * <p>The component uses 2 main parameters for its configuration :
 *  <ul>
 *      <li>a keyword to be mapped to urls in the {@link
 *      org.springframework.security.securechannel.ChannelProcessingFilter} configuration<br>
 *      default value provided by sub classes.</li>
 *      <li>and a threshold : used by the routine {@link
 *      #isContextValidConcerningHumanity(CaptchaSecurityContext)} to evaluate whether the {@link
 *      CaptchaSecurityContext} is valid default value = 0</li>
 *  </ul>
 *  </p>
 *
 * @author Marc-Antoine Garrigue
 * @version $Id: CaptchaChannelProcessorTemplate.java 2743 2008-03-17 11:06:32Z luke_t $
 */
public abstract class CaptchaChannelProcessorTemplate
    implements ChannelProcessor, InitializingBean {
    /** * entry point. */
    private ChannelEntryPoint entryPoint;

    /** * logger. */
    protected Log logger = LogFactory.getLog(this.getClass());

    /** * keyword. */
    private String keyword = null;

    /** * thresold. */
    private int thresold = 0;

    /**
     * Verify if entryPoint and keyword are ok.
     *
     * @throws Exception if not
     */
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(entryPoint, "entryPoint required");
        Assert.hasLength(keyword, "keyword required");
    }

    /**
     * decide whether or not continue the request.
     *
     * @param invocation FilterInvocation
     * @param config ConfigAttributeDefinition
         * @throws IOException io
         * @throws ServletException servlet
     */
    public void decide(FilterInvocation invocation,
        ConfigAttributeDefinition config)
        throws IOException, ServletException {
        if ((invocation == null) || (config == null)) {
            throw new IllegalArgumentException("Nulls cannot be provided");
        }

        CaptchaSecurityContext context = null;
        context = (CaptchaSecurityContext) SecurityContextHolder.getContext();

        Iterator iter = config.getConfigAttributes().iterator();

        while (iter.hasNext()) {
            ConfigAttribute attribute = (ConfigAttribute) iter.next();

            if (supports(attribute)) {
                logger.debug("supports this attribute : " + attribute);

                if (!isContextValidConcerningHumanity(context)) {
                    logger.debug(
                        "context is not allowed to access ressource, redirect to captcha entry point");
                    redirectToEntryPoint(invocation);
                } else {
                    logger.debug(
                        "has been successfully checked this keyword, increment request count");
                    context.incrementHumanRestrictedResourcesRequestsCount();
                }
            } else {
                logger.debug("do not support this attribute");
            }
        }
    }

    /** * @return entry point. */
    public ChannelEntryPoint getEntryPoint() {
        return entryPoint;
    }

    /** * @return keyword. */
    public String getKeyword() {
        return keyword;
    }

    /** * @return thresold. */
    public int getThreshold() {
        return thresold;
    }

    /**
     * @param context captcha security context.
     * @return boolean
     */
    abstract boolean isContextValidConcerningHumanity(
        CaptchaSecurityContext context);

    /**
     * @param invocation FilterInvocation.
     * @throws IOException io
     * @throws ServletException servlet
     */
    private void redirectToEntryPoint(FilterInvocation invocation)
        throws IOException, ServletException {
        if (logger.isDebugEnabled()) {
            logger.debug(
                "context is not valid : redirecting to entry point");
        }

        entryPoint.commence(invocation.getRequest(),
            invocation.getResponse());
    }

    /** * @param entryPoint ChannelEntryPoint. */
    public void setEntryPoint(ChannelEntryPoint entryPoint) {
        this.entryPoint = entryPoint;
    }

    /** * @param keyword String. */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /** * @param threshold int. */
    public void setThreshold(int threshold) {
        this.thresold = threshold;
    }

    /**
     * @param attribute ConfigAttribute.
     * @return boolean
     */
    public boolean supports(ConfigAttribute attribute) {
        return (attribute != null)
        && (keyword.equals(attribute.getAttribute()));
    }
}
