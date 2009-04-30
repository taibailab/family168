package com.family168.manager;

import org.springframework.test.AbstractTransactionalSpringContextTests;


public class UserManagerTest
    extends AbstractTransactionalSpringContextTests {
    private UserManager userManager;

    public void setUserManager(UserManager userManager) {
        System.out.println(userManager);
        this.userManager = userManager;
    }

    protected String[] getConfigLocations() {
        return new String[] {"classpath*:spring/*.xml"};
    }

    public void testDefault() throws Exception {
        userManager.index();
        setComplete();
        endTransaction();
        startNewTransaction();
        userManager.search();
    }
}
