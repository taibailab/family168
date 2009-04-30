<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="java.util.List"%>
<%@page import="org.jbpm.*"%>
<%@page import="org.jbpm.pvm.internal.util.ReflectUtil"%>
<%@page import="org.jbpm.session.RepositorySession"%>
<%
    String xml = request.getParameter("xml");
    if (xml != null) {

        ApplicationContext ctx = null;
        ctx = WebApplicationContextUtils.getWebApplicationContext(application);
        RepositoryService repositoryService = (RepositoryService) ctx.getBean("repositoryService");
        System.out.println(this.getClass().getResource("/" + xml));
        repositoryService.createDeployment()
            .addResourceFromInputStream("process.jpdl.xml", this.getClass().getResourceAsStream("/" + xml))
            .deploy();
    }
%>
<ol>
    <li><a href="?xml=jpdl/end/process.jpdl.xml">end</a></li>
    <li><a href="?xml=jpdl/guess/process.jpdl.xml">guest</a></li>
    <li><a href="?xml=jpdl/swimlane/process.jpdl.xml">swimlane</a></li>
</ol>
<a href="index.jsp">back</a>
