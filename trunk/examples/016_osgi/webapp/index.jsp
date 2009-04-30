<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.family168.*"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.family168.*"%>
<%@page import="org.osgi.framework.*"%>
<%!
    public String showState(int state) {
        if (state == Bundle.UNINSTALLED) {
            return "UNINSTALLED";
        } else if (state == Bundle.INSTALLED) {
            return "INSTALLED";
        } else if (state == Bundle.RESOLVED) {
            return "RESOLVED";
        } else if (state == Bundle.STARTING) {
            return "STARTING";
        } else if (state == Bundle.STOPPING) {
            return "STOPPING";
        } else if (state == Bundle.ACTIVE) {
            return "ACTIVE";
        } else {
            return "UNKNOWN";
        }
    }
%>
<a href="impl1.jsp">install impl1</a>
|
<a href="impl2.jsp">install impl2</a>
<table border="1" width="100%">
  <tr>
    <th>ID</th>
    <th>State</th>
    <th>Bundle</th>
    <th>&nbsp;</th>
  </tr>
<%
    ApplicationContext ctx = null;
    ctx = WebApplicationContextUtils.getWebApplicationContext(application);
    FelixService felixService = (FelixService) ctx.getBean("felixService");
    Bundle[] bundles = felixService.getBundles();
    for (Bundle bundle : bundles) {
%>
  <tr>
    <td>&nbsp;<%=bundle.getBundleId()%></td>
    <td>&nbsp;<%=showState(bundle.getState())%></td>
    <td>&nbsp;<%=bundle.getSymbolicName()%></td>
    <td>
      &nbsp;
      <a href="start.jsp?id=<%=bundle.getBundleId()%>">start</a>
      |
      <a href="stop.jsp?id=<%=bundle.getBundleId()%>">stop</a>
      |
      <a href="uninstall.jsp?id=<%=bundle.getBundleId()%>">uninstall</a>
    </td>
  </tr>
<%
    }
%>
</table>
<a href="test.jsp">test hello world</a>
