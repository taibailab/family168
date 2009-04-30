package com.family168.core.struts2;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.family168.core.utils.GenericsUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;


/**
 * 支持ModelDriven的基类.
 *
 * @author Lingo
 * @param <T> 泛型
 */
public class BaseAction<T> extends ActionSupport
    implements ServletRequestAware, ServletResponseAware, ModelDriven<T> {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * 日志. */
    private static Log logger = LogFactory.getLog(BaseAction.class);

    /** * request. */
    protected transient HttpServletRequest request;

    /** * response. */
    protected transient HttpServletResponse response;

    /** * session. */
    protected transient HttpSession session;

    /** * out. */
    protected transient PrintWriter out;

    /** * 持久类. */
    protected T entity;

    /** * 持久类类型. */
    protected Class entityClass;

    /** * 构造方法. */
    public BaseAction() {
        this.entityClass = GenericsUtils.getSuperClassGenricType(this
                .getClass());
    }

    /** * @param servletRequest HttpServletRequest. */
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.request = servletRequest;
        this.session = this.request.getSession();
    }

    /** * @param servletResponse HttpServletResponse. */
    public void setServletResponse(HttpServletResponse servletResponse) {
        try {
            this.response = servletResponse;

            this.response.setCharacterEncoding("UTF-8");
            this.out = this.response.getWriter();
        } catch (Exception ex) {
            logger.error(ex, ex);
        }
    }

    /** * @return T. */
    public T getModel() {
        try {
            if (entity == null) {
                entity = (T) entityClass.newInstance();
            }
        } catch (Exception ex) {
            logger.error(ex, ex);
        }

        return entity;
    }
}
