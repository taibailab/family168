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
    long id = Integer.parseInt(request.getParameter("id"));
    felixService.stop(id);
%>
<jsp:forward page="/"/>
