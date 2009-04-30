package com.family168.manager;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;


public class UserManagerTest
    extends AbstractDependencyInjectionSpringContextTests {
    protected UserManager userManager;

    @Override
    protected String[] getConfigLocations() {
        return new String[] {"classpath*:/spring/applicationContext*.xml"};
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public void testUserManager() {
        System.out.println(userManager.getUsername());
    }
}
