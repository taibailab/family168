package com.family168;

import javax.jws.*;


@WebService(name = "HelloWorld", targetNamespace = "http://localhost:8080/cxf/services")
public interface HelloWorld {
    String sayHi(@WebParam(name = "text")
    String text);
}
