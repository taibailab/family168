<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="java.util.List"%>
<%@page import="org.jbpm.*"%>
<table border="1" width="100%">
    <tr>
        <th>name</th>
        <th>key</th>
        <th>id</th>
        <th>version</th>
        <th>deploymentDbid</th>
        <th>operation</th>
    </tr>
<%
    ApplicationContext ctx = null;
    ctx = WebApplicationContextUtils.getWebApplicationContext(application);
    RepositoryService repositoryService = (RepositoryService) ctx.getBean("repositoryService");
    ProcessService processService = (ProcessService) ctx.getBean("processService");

    // List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().execute();
    // for (ProcessDefinition pd : list) {

    List<String> list = processService.findProcessDefinitionKeys();
    for (String key : list) {
        ProcessDefinition pd = processService.findLatestProcessDefinitionByKey(key);
%>
        <tr>
            <td><%=pd.getName()%></td>
            <td><%=pd.getKey()%></td>
            <td><%=pd.getId()%></td>
            <td><%=pd.getVersion()%></td>
            <td><%=pd.getDeploymentDbid()%></td>
            <td>
              <a href="processDefinitionPic.jsp?id=<%=pd.getDeploymentDbid()%>">pic</a>
              |
              <a href="startProcess.jsp?key=<%=pd.getKey()%>">start</a>
              |
              <a href="deleteProcessDefinition.jsp?id=<%=pd.getDeploymentDbid()%>">delete</a>
            </td>
        </tr>
<%
    }
%>
</table>
<a href="index.jsp">back</a>
