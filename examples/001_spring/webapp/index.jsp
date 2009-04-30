<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%
    ApplicationContext ctx = null;

    ctx = new ClassPathXmlApplicationContext("/spring/applicationContext.xml");

    ctx = WebApplicationContextUtils.getWebApplicationContext(application);
%>
