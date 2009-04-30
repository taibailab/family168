package com.family168.manager;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;


public class RescManagerTest
    extends AbstractDependencyInjectionSpringContextTests {
    protected RescManager rescManager;

    @Override
    protected String[] getConfigLocations() {
        return new String[] {"classpath*:/spring/applicationContext*.xml"};
    }

    public void setRescManagerr(RescManager rescManager) {
        this.rescManager = rescManager;
    }

    public void testRescManager() throws Exception {
        rescManager.demo();
    }
}
