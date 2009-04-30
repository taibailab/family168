<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.family168.*"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%
    ApplicationContext ctx = null;
    ctx = WebApplicationContextUtils.getWebApplicationContext(application);
    UserMessageProducer producer = (UserMessageProducer) ctx.getBean("userMessageProducer");
    User user = new User();
    producer.send(user);
%>
