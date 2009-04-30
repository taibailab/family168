package com.family168.core.test;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.mock.web.MockServletContext;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.RequestScope;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.SessionScope;


public class WebScopeTestExecutionListener
    extends AbstractTestExecutionListener {
    private static Log logger = LogFactory.getLog(WebScopeTestExecutionListener.class);
    protected static final RequestContextListener REQUEST_LISTENER = new RequestContextListener();
    protected static MockServletContext SERVLET_CONTEXT = new MockServletContext(
            "");
    protected MockHttpSession session = new MockHttpSession(SERVLET_CONTEXT);
    protected MockHttpServletRequest request = new MockHttpServletRequest(SERVLET_CONTEXT,
            "GET", "");
    protected MockHttpServletResponse response = new MockHttpServletResponse();
    protected ServletRequestEvent event = new ServletRequestEvent(SERVLET_CONTEXT,
            request);

    @Override
    public void prepareTestInstance(final TestContext testContext)
        throws Exception {
        AutowireCapableBeanFactory beanFactory = testContext.getApplicationContext()
                                                            .getAutowireCapableBeanFactory();

        if (beanFactory instanceof ConfigurableListableBeanFactory) {
            ConfigurableListableBeanFactory configurableBeanFactory = ConfigurableListableBeanFactory.class
                .cast(beanFactory);

            configurableBeanFactory.registerScope(WebApplicationContext.SCOPE_REQUEST,
                new RequestScope());
            configurableBeanFactory.registerScope(WebApplicationContext.SCOPE_SESSION,
                new SessionScope(false));
            configurableBeanFactory.registerScope(WebApplicationContext.SCOPE_GLOBAL_SESSION,
                new SessionScope(true));

            configurableBeanFactory.registerResolvableDependency(ServletRequest.class,
                new ObjectFactory() {
                    public Object getObject() {
                        RequestAttributes requestAttr = RequestContextHolder
                            .currentRequestAttributes();

                        if (!(requestAttr instanceof ServletRequestAttributes)) {
                            throw new IllegalStateException(
                                "Current request is not a servlet request");
                        }

                        return ((ServletRequestAttributes) requestAttr)
                        .getRequest();
                    }
                });
            configurableBeanFactory.registerResolvableDependency(HttpSession.class,
                new ObjectFactory() {
                    public Object getObject() {
                        RequestAttributes requestAttr = RequestContextHolder
                            .currentRequestAttributes();

                        if (!(requestAttr instanceof ServletRequestAttributes)) {
                            throw new IllegalStateException(
                                "Current request is not a servlet request");
                        }

                        return ((ServletRequestAttributes) requestAttr).getRequest()
                                .getSession();
                    }
                });
        }

        //logger.fatal("start");
        request.setSession(session);
        event = new ServletRequestEvent(SERVLET_CONTEXT, request);
        REQUEST_LISTENER.requestInitialized(event);
    }

    @Override
    public void beforeTestMethod(final TestContext testContext)
        throws Exception {
    }

    @Override
    public void afterTestMethod(final TestContext testContext)
        throws Exception {
        //logger.fatal("start");
        //REQUEST_LISTENER.requestDestroyed(event);
    }
}
