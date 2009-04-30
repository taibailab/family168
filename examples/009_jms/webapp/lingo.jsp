<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.family168.*"%>
<%@page import="com.family168.lingo.*"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%
    ApplicationContext ctx = null;
    ctx = new ClassPathXmlApplicationContext("/spring/lingo/client.xml");
    User user = new User();
    UserCheck userCheck = null;
    userCheck = (UserCheck) ctx.getBean("client");

    long startTime = System.currentTimeMillis();
    // sync
    System.out.println(" - [syn result]: " + userCheck.synGetResidual(user));
    System.out.println(" - [syn cost]: " + (System.currentTimeMillis() - startTime));

    // callback
    UserListenerImpl callBack = new UserListenerImpl();
    callBack.setWaitTime(6000);

    startTime = System.currentTimeMillis();
    // async
    userCheck.asynGetResidual(user, callBack);
    // no block, time should be zero
    System.out.println(" - [async cost]: " + (System.currentTimeMillis() - startTime));

    // callBack block to wait message
    callBack.waitForAsyncResponses(1);
    System.out.println(" - [async callback]: " + callBack.getResults());
    System.out.println(" - [async wait cost]: " + (System.currentTimeMillis() - startTime));

%>
