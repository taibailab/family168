package com.family168;

import org.slf4j.bridge.SLF4JBridgeHandler;

import org.springframework.beans.factory.InitializingBean;


public class JulOverSlf4jProcessor implements InitializingBean {
    public void afterPropertiesSet() {
        SLF4JBridgeHandler.install();
    }
}
