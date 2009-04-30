<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.family168.*"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.family168.*"%>
<%@page import="org.osgi.framework.*"%>
<p><a href="index.jsp">back</a></p>
<%
    ApplicationContext ctx = null;
    ctx = WebApplicationContextUtils.getWebApplicationContext(application);
    FelixService felixService = (FelixService) ctx.getBean("felixService");
    Helloworld helloworld = felixService.getService(Helloworld.class);
    out.println(helloworld.hello("lingo"));
	//System.out.println(com.family168.impl.HelloworldImpl.class);
%>
