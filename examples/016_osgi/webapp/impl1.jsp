<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.family168.*"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.family168.*"%>
<%@page import="org.osgi.framework.*"%>
<%
    ApplicationContext ctx = null;
    ctx = WebApplicationContextUtils.getWebApplicationContext(application);
    FelixService felixService = (FelixService) ctx.getBean("felixService");
    felixService.install("file:bundles/impl1.jar");
%>
<jsp:forward page="/"/>
