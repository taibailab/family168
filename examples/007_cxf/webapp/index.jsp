<%@page import="com.family168.HelloWorld"%>
<%@page import="org.apache.cxf.jaxws.JaxWsProxyFactoryBean"%>
<%
    JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    factory.setServiceClass(HelloWorld.class);
    factory.setAddress("http://localhost:8080/cxf/services/HelloWorld");
    HelloWorld client = (HelloWorld) factory.create();
    String result = client.sayHi("Hello");
    out.println(result);
%>
