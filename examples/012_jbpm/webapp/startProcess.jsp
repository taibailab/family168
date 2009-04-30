<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="java.util.List"%>
<%@page import="org.jbpm.*"%>
<%
    ApplicationContext ctx = null;
    ctx = WebApplicationContextUtils.getWebApplicationContext(application);
    ExecutionService executionService = (ExecutionService) ctx.getBean("executionService");
    String key = request.getParameter("key");
    Execution execution = executionService.startProcessInstanceByKey(key);
    String executionId = execution.getId();
    Boolean bingle = (Boolean) executionService.getVariable(executionId, "response");
    System.out.println("---------------------------------------------");
    System.out.println(execution);

    if (bingle != null && bingle) {
        System.out.println("you win!");
    } else {
        System.out.println("you lost!");
    }
    System.out.println("---------------------------------------------");
%>
<jsp:forward page="/processDefinitions.jsp"/>
