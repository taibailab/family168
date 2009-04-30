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
import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.InitializingBean;

import org.springframework.security.securechannel.ChannelEntryPoint;
import org.springframework.security.util.PortMapper;
import org.springframework.security.util.PortMapperImpl;
import org.springframework.security.util.PortResolver;
import org.springframework.security.util.PortResolverImpl;

import org.springframework.util.Assert;


/**
 * The captcha entry point : redirect to the captcha test page.
 * <p>
 * This entry point can force the use of SSL : see {@link #getForceHttps()}
 * <p>
 * This entry point allows internal OR external redirect : see {@link #setOutsideWebApp(boolean)}<br />
 * / Original request can be added to the redirect path using a custom translation : see
 * {@link #setIncludeOriginalRequest(boolean)}<br />
 * The original request is translated using URLEncoding and the following translation mapping in the redirect url :
 *  <ul>
 *      <li>original url => {@link #getOriginalRequestUrlParameterName()}</li>
 *      <li>If {@link #isIncludeOriginalParameters()}</li>
 *      <li>original method => {@link #getOriginalRequestMethodParameterName()}</li>
 *      <li>original parameters => {@link #getOriginalRequestParametersParameterName()}</li>
 *      <li>The original parameters string is contructed using :
 *      <ul>
 *          <li>a parameter separator {@link #getOriginalRequestParametersSeparator()}</li>
 *          <li>a parameter name value pair separator for each parameter {@link
 *          #getOriginalRequestParametersNameValueSeparator()}</li>
 *      </ul>
 *      </li>
 *  </ul>
 *  <br><br>
 * Default values :
 * <pre>
 * forceHttps = false
 * includesOriginalRequest = true
 * includesOriginalParameters = false
 * isOutsideWebApp = false
 * originalRequestUrlParameterName = original_requestUrl
 * originalRequestParametersParameterName = original_request_parameters
 * originalRequestParametersNameValueSeparator = __
 * originalRequestParametersSeparator = ;;
 * originalRequestMethodParameterName = original_request_method
 * urlEncodingCharset = UTF-8
 * </pre>
 * </p>
 *
 * @author Marc-Antoine Garrigue
 * @version $Id: CaptchaEntryPoint.java 2743 2008-03-17 11:06:32Z luke_t $
 */
public class CaptchaEntryPoint implements ChannelEntryPoint,
    InitializingBean {
    /** * logger. */
    private static Log logger = LogFactory.getLog(CaptchaEntryPoint.class);

    /** http port. */
    public static final int DEFAULT_HTTP_PORT = 80;

    /** https port. */
    public static final int DEFAULT_HTTPS_PORT = 443;

    /** * port mapper. */
    private PortMapper portMapper = new PortMapperImpl();

    /** * port resolver. */
    private PortResolver portResolver = new PortResolverImpl();

    /** * captcha form url. */
    private String captchaFormUrl;

    /** * original request method parameter name. */
    private String originalRequestMethodParameterName = "original_request_method";

    /** * original request parameters name value separator. */
    private String originalRequestParametersNameValueSeparator = "__";

    /** * original request parameters parameter name. */
    private String originalRequestParametersParameterName = "original_request_parameters";

    /** * original request parameters separator. */
    private String originalRequestParametersSeparator = ";;";

    /** * original request url parameter name. */
    private String originalRequestUrlParameterName = "original_requestUrl";

    /** * url encoding charset. */
    private String urlEncodingCharset = "UTF-8";

    /** * force https. */
    private boolean forceHttps = false;

    /** * include original parameters. */
    private boolean includeOriginalParameters = false;

    /** * include original request. */
    private boolean includeOriginalRequest = true;

    /** * is outside web app. */
    private boolean isOutsideWebApp = false;

    /** * @throws Exception any ex. */
    public void afterPropertiesSet() throws Exception {
        Assert.hasLength(captchaFormUrl, "captchaFormUrl must be specified");
        Assert.hasLength(originalRequestMethodParameterName,
            "originalRequestMethodParameterName must be specified");
        Assert.hasLength(originalRequestParametersNameValueSeparator,
            "originalRequestParametersNameValueSeparator must be specified");
        Assert.hasLength(originalRequestParametersParameterName,
            "originalRequestParametersParameterName must be specified");
        Assert.hasLength(originalRequestParametersSeparator,
            "originalRequestParametersSeparator must be specified");
        Assert.hasLength(originalRequestUrlParameterName,
            "originalRequestUrlParameterName must be specified");
        Assert.hasLength(urlEncodingCharset,
            "urlEncodingCharset must be specified");
        Assert.notNull(portMapper, "portMapper must be specified");
        Assert.notNull(portResolver, "portResolver must be specified");
        URLEncoder.encode("   fzaef �& � ", urlEncodingCharset);
    }

    /**
     * @param redirectUrl StringBuffer.
     * @param req http servlet request
     */
    private void buildInternalRedirect(StringBuffer redirectUrl,
        HttpServletRequest req) {
        // construct it
        StringBuffer simpleRedirect = new StringBuffer();

        String scheme = req.getScheme();
        String serverName = req.getServerName();
        int serverPort = portResolver.getServerPort(req);
        String contextPath = req.getContextPath();
        boolean includePort = true;

        if ("http".equals(scheme.toLowerCase(Locale.CHINA))
                && (serverPort == DEFAULT_HTTP_PORT)) {
            includePort = false;
        }

        if ("https".equals(scheme.toLowerCase(Locale.CHINA))
                && (serverPort == DEFAULT_HTTPS_PORT)) {
            includePort = false;
        }

        simpleRedirect.append(scheme);
        simpleRedirect.append("://");
        simpleRedirect.append(serverName);

        if (includePort) {
            simpleRedirect.append(":");
            simpleRedirect.append(serverPort);
        }

        simpleRedirect.append(contextPath);
        simpleRedirect.append(captchaFormUrl);

        if (forceHttps && req.getScheme().equals("http")) {
            Integer httpPort = Integer.valueOf(portResolver.getServerPort(
                        req));
            Integer httpsPort = (Integer) portMapper.lookupHttpsPort(httpPort);

            if (httpsPort != null) {
                if (httpsPort.intValue() == DEFAULT_HTTPS_PORT) {
                    includePort = false;
                } else {
                    includePort = true;
                }

                redirectUrl.append("https://");
                redirectUrl.append(serverName);

                if (includePort) {
                    redirectUrl.append(":");
                    redirectUrl.append(httpsPort);
                }

                redirectUrl.append(contextPath);
                redirectUrl.append(captchaFormUrl);
            } else {
                redirectUrl.append(simpleRedirect);
            }
        } else {
            redirectUrl.append(simpleRedirect);
        }
    }

    /**
     * @param request servlet request.
     * @param response servlet response
     * @throws IOException io
     * @throws ServletException servlet
     */
    public void commence(ServletRequest request, ServletResponse response)
        throws IOException, ServletException {
        StringBuffer redirectUrl = new StringBuffer();
        HttpServletRequest req = (HttpServletRequest) request;

        if (isOutsideWebApp) {
            redirectUrl = redirectUrl.append(captchaFormUrl);
        } else {
            buildInternalRedirect(redirectUrl, req);
        }

        if (includeOriginalRequest) {
            includeOriginalRequest(redirectUrl, req);
        }

        // add post parameter? DONE!
        if (logger.isDebugEnabled()) {
            logger.debug("Redirecting to: " + redirectUrl);
        }

        ((HttpServletResponse) response).sendRedirect(redirectUrl.toString());
    }

    /**
     *
     * @return the captcha test page to redirect to.
     */
    public String getCaptchaFormUrl() {
        return captchaFormUrl;
    }

    /** * @return boolean. */
    public boolean getForceHttps() {
        return forceHttps;
    }

    /** * @return OriginalRequestMethodParameterName. */
    public String getOriginalRequestMethodParameterName() {
        return originalRequestMethodParameterName;
    }

    /** * @return OriginalRequestParametersNameValueSeparator. */
    public String getOriginalRequestParametersNameValueSeparator() {
        return originalRequestParametersNameValueSeparator;
    }

    /** * @return OriginalRequestParametersParameterName. */
    public String getOriginalRequestParametersParameterName() {
        return originalRequestParametersParameterName;
    }

    /** * @return OriginalRequestParametersSeparator. */
    public String getOriginalRequestParametersSeparator() {
        return originalRequestParametersSeparator;
    }

    /** * @return OriginalRequestUrlParameterName. */
    public String getOriginalRequestUrlParameterName() {
        return originalRequestUrlParameterName;
    }

    /** * @return PortMapper. */
    public PortMapper getPortMapper() {
        return portMapper;
    }

    /** * @return PortResolver. */
    public PortResolver getPortResolver() {
        return portResolver;
    }

    /** * @return UrlEncodingCharset. */
    public String getUrlEncodingCharset() {
        return urlEncodingCharset;
    }

    /**
     * @param redirectUrl StringBuffer.
     * @param req http servlet request
     */
    private void includeOriginalRequest(StringBuffer redirectUrl,
        HttpServletRequest req) {
        // add original request to the url
        if (redirectUrl.indexOf("?") >= 0) {
            redirectUrl.append("&");
        } else {
            redirectUrl.append("?");
        }

        redirectUrl.append(originalRequestUrlParameterName);
        redirectUrl.append("=");

        try {
            redirectUrl.append(URLEncoder.encode(req.getRequestURL()
                                                    .toString(),
                    urlEncodingCharset));
        } catch (UnsupportedEncodingException e) {
            logger.warn(e);
        }

        //append method
        redirectUrl.append("&");
        redirectUrl.append(originalRequestMethodParameterName);
        redirectUrl.append("=");
        redirectUrl.append(req.getMethod());

        if (includeOriginalParameters) {
            // append query params
            redirectUrl.append("&");
            redirectUrl.append(originalRequestParametersParameterName);
            redirectUrl.append("=");

            StringBuffer qp = new StringBuffer();
            Enumeration parameters = req.getParameterNames();

            if ((parameters != null) && parameters.hasMoreElements()) {
                //qp.append("?");
                while (parameters.hasMoreElements()) {
                    String name = parameters.nextElement().toString();
                    String value = req.getParameter(name);
                    qp.append(name);
                    qp.append(originalRequestParametersNameValueSeparator);
                    qp.append(value);

                    if (parameters.hasMoreElements()) {
                        qp.append(originalRequestParametersSeparator);
                    }
                }
            }

            try {
                redirectUrl.append(URLEncoder.encode(qp.toString(),
                        urlEncodingCharset));
            } catch (Exception e) {
                logger.warn(e);
            }
        }
    }

    /** * @return isIncludeOriginalParameters. */
    public boolean isIncludeOriginalParameters() {
        return includeOriginalParameters;
    }

    /** * @return isIncludeOriginalRequest. */
    public boolean isIncludeOriginalRequest() {
        return includeOriginalRequest;
    }

    /** * @return isOutsideWebApp. */
    public boolean isOutsideWebApp() {
        return isOutsideWebApp;
    }

    /**
     * The URL where the <code>CaptchaProcessingFilter</code> login page can be found. Should be relative to
     * the web-app context path, and include a leading <code>/</code>
     *
     * @param captchaFormUrl String
     */
    public void setCaptchaFormUrl(String captchaFormUrl) {
        this.captchaFormUrl = captchaFormUrl;
    }

    /**
     * Set to true to force captcha form access to be via https. If this value is true (the default is false),
     * and the incoming request for the protected resource which triggered the interceptor was not already
     * <code>https</code>, then
     *
     * @param forceHttps boolean
     */
    public void setForceHttps(boolean forceHttps) {
        this.forceHttps = forceHttps;
    }

    /** * @param includeOriginalParameters boolean. */
    public void setIncludeOriginalParameters(
        boolean includeOriginalParameters) {
        this.includeOriginalParameters = includeOriginalParameters;
    }

    /**
     * If set to true, the original request url will be appended to the redirect url using the {@link
     * #getOriginalRequestUrlParameterName()}.
     *
     * @param includeOriginalRequest boolean.
     */
    public void setIncludeOriginalRequest(boolean includeOriginalRequest) {
        this.includeOriginalRequest = includeOriginalRequest;
    }

    /** * @param originalRequestMethodParameterName String. */
    public void setOriginalRequestMethodParameterName(
        String originalRequestMethodParameterName) {
        this.originalRequestMethodParameterName = originalRequestMethodParameterName;
    }

    /** * @param originalRequestParametersNameValueSeparator String. */
    public void setOriginalRequestParametersNameValueSeparator(
        String originalRequestParametersNameValueSeparator) {
        this.originalRequestParametersNameValueSeparator = originalRequestParametersNameValueSeparator;
    }

    /** * @param originalRequestParametersParameterName String. */
    public void setOriginalRequestParametersParameterName(
        String originalRequestParametersParameterName) {
        this.originalRequestParametersParameterName = originalRequestParametersParameterName;
    }

    /** * @param originalRequestParametersSeparator String. */
    public void setOriginalRequestParametersSeparator(
        String originalRequestParametersSeparator) {
        this.originalRequestParametersSeparator = originalRequestParametersSeparator;
    }

    /** * @param originalRequestUrlParameterName String. */
    public void setOriginalRequestUrlParameterName(
        String originalRequestUrlParameterName) {
        this.originalRequestUrlParameterName = originalRequestUrlParameterName;
    }

    /**
     * if set to true, the {@link #commence(ServletRequest, ServletResponse)} method uses the {@link
     * #getCaptchaFormUrl()} as a complete URL, else it as a 'inside WebApp' path.
     *
     * @param outsideWebApp boolean
     */
    public void setOutsideWebApp(boolean outsideWebApp) {
        this.isOutsideWebApp = outsideWebApp;
    }

    /** * @param portMapper PortMapper. */
    public void setPortMapper(PortMapper portMapper) {
        this.portMapper = portMapper;
    }

    /** * @param portResolver PortResolver. */
    public void setPortResolver(PortResolver portResolver) {
        this.portResolver = portResolver;
    }

    /** * @param urlEncodingCharset String. */
    public void setUrlEncodingCharset(String urlEncodingCharset) {
        this.urlEncodingCharset = urlEncodingCharset;
    }
}
