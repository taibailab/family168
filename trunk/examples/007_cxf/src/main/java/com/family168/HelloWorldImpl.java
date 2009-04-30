package com.family168;

import javax.jws.*;


@WebService(endpointInterface = "com.family168.HelloWorld", targetNamespace = "http://localhost:8080/cxf/services", portName = "HelloWorldPort", serviceName = "HelloWorld")
public class HelloWorldImpl implements HelloWorld {
    public String sayHi(String text) {
        System.out.println(text);

        return "Hello " + text;
    }
}
