<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="java.util.List"%>
<%@page import="org.jbpm.*"%>
<%
    ApplicationContext ctx = null;
    ctx = WebApplicationContextUtils.getWebApplicationContext(application);
    RepositoryService repositoryService = (RepositoryService) ctx.getBean("repositoryService");
    long id = Long.parseLong(request.getParameter("id"));

    repositoryService.deleteDeploymentCascade(id);
%>
<jsp:forward page="/processDefinitions.jsp"/>
