package com.family168.jbpm.spring;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;

import org.springframework.context.ApplicationContext;

import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * jbpm 工具集.
 *
 * @author Lingo
 */
public class JbpmUtils {
    /** * spring applicationContext.xml中的默认名. */
    public static final String DEFAULT_NAME = "jbpmConfiguration";

    /** * protected constructor. */
    protected JbpmUtils() {
    }

    /**
     * 获得JbpmContext.
     *
     * @param servletContext ServletContext
     * @param name spring bean的名称
     * @return JbpmContext
     */
    public static JbpmContext getJbpmContext(
        ServletContext servletContext, String name) {
        JbpmContext jbpmContext = JbpmContext
            .getCurrentJbpmContext();

        if (jbpmContext == null) {
            ApplicationContext context = WebApplicationContextUtils
                .getWebApplicationContext(servletContext);
            JbpmConfiguration jbpmConfiguration = (JbpmConfiguration) context
                .getBean(name);
            jbpmContext = jbpmConfiguration.createJbpmContext();
        }

        return jbpmContext;
    }

    /**
     * 获得JbpmContext，使用默认名称.
     *
     * @param servletContext ServletContext
     * @return JbpmContext
     */
    public static JbpmContext getJbpmContext(ServletContext servletContext) {
        return JbpmUtils.getJbpmContext(servletContext,
            JbpmUtils.DEFAULT_NAME);
    }

    /**
     * 获得JbpmContext，从pageContext里获得.
     *
     * @param pageContext PageContext
     * @param name String
     * @return JbpmContext
     */
    public static JbpmContext getJbpmContext(PageContext pageContext,
        String name) {
        return JbpmUtils.getJbpmContext(pageContext.getServletContext(),
            name);
    }

    /**
     * 获得JbpmContext，使用默认名称从pageContext里获得.
     *
     * @param pageContext PageContext
     * @return JbpmContext
     */
    public static JbpmContext getJbpmContext(PageContext pageContext) {
        return JbpmUtils.getJbpmContext(pageContext.getServletContext(),
            JbpmUtils.DEFAULT_NAME);
    }

    /**
     * 获得JbpmContext，从request中获得.
     *
     * @param request 请求
     * @param name String
     * @return JbpmContext
     */
    public static JbpmContext getJbpmContext(HttpServletRequest request,
        String name) {
        return JbpmUtils.getJbpmContext(request.getSession()
                                               .getServletContext(), name);
    }

    /**
     * 获得JbpmContext，使用默认名称从request中获得.
     *
     * @param request 请求
     * @return JbpmContext
     */
    public static JbpmContext getJbpmContext(HttpServletRequest request) {
        return JbpmUtils.getJbpmContext(request.getSession()
                                               .getServletContext(),
            JbpmUtils.DEFAULT_NAME);
    }
}
