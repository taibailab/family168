<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.family168.RuleRunner"%>
<%
    new RuleRunner().runRules( new String[] { "Example1.drl" },
                                   new Object[0] );
%>
