<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.family168.*"%>
<%@page import="javax.management.*"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%
    ApplicationContext ctx = null;
    ctx = WebApplicationContextUtils.getWebApplicationContext(application);

    MBeanServerConnection clientConnector = (MBeanServerConnection) ctx.getBean("clientConnector");
    ObjectName objectName = new ObjectName("com.family168:type=User");
    out.println(objectName);

    UserMBean userMBean = (UserMBean) MBeanServerInvocationHandler.newProxyInstance(clientConnector, objectName, UserMBean.class, false);
    out.println(userMBean.getName());

    userMBean.setName("username");
    out.println(userMBean.getName());

	Object attr = clientConnector.getAttribute(objectName, "Name");
    out.println(attr);
    
	Attribute attribute = new Attribute("Name", "other name");
    clientConnector.setAttribute(objectName, attribute);
    attr = clientConnector.getAttribute(objectName, "Name");
    out.println(attr);
%>

