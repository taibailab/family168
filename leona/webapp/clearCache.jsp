<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.hibernate.SessionFactory"%>
<%
    ApplicationContext ctx =  WebApplicationContextUtils.getWebApplicationContext(application);
    SessionFactory sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
    sessionFactory.evictEntity("com.family168.security.domain.Menu");
    sessionFactory.evictQueries();
%>
clear cache
