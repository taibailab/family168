<%@page import="org.springframework.context.ApplicationContext"%><%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%><%@page import="com.family168.*"%><%
    ApplicationContext ctx = null;
    ctx = WebApplicationContextUtils.getWebApplicationContext(application);
    ReportBean reportBean = (ReportBean) ctx.getBean("reportBean");
    System.out.println(reportBean);
    BirtReportView view = reportBean.view(request, response);
    System.out.println(view);
    view.renderMergedOutputModel(null, request, response);
%>