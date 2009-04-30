package com.family168.manager;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;


public class RoleManagerTest
    extends AbstractDependencyInjectionSpringContextTests {
    protected RoleManager roleManager;

    @Override
    protected String[] getConfigLocations() {
        return new String[] {"classpath*:/spring/applicationContext*.xml"};
    }

    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    public void testRoleManager() {
        System.out.println(roleManager.getUserManager());
    }
}
