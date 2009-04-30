<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.family168.drools.*"%>
<%@page import="com.family168.*"%>
<%
    ApplicationContext ctx = null;
    ctx = WebApplicationContextUtils.getWebApplicationContext(application);
    RuleTemplate ruleTemplate = (RuleTemplate) ctx.getBean("xlsRuleTemplate");
    Order order = new Order();
    order.setTotalPrice(200.0);
    System.out.println(ruleTemplate.executeRules(order));
    System.out.println(order.getDiscountPrice());
%>
