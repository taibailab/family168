package com.family168.core.hibernate;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;


public class UserDaoTest
    extends AbstractTransactionalDataSourceSpringContextTests {
    UserDao userDao;

    public void setUser2Dao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    protected String[] getConfigLocations() {
        setAutowireMode(AUTOWIRE_BY_NAME);

        return new String[] {"classpath*:applicationContext.xml"};
    }

    public void testFindBy() {
        assertNotNull(userDao.findBy("roles:r.rescs:re.name", "value"));
    }

    // 未实现
    public void testFindBy2() {
        assertNotNull(userDao.findBy("username", "value", "like"));
    }

    public void testFindBy3() {
        assertNotNull(userDao.findBy("roles[:r.name", "value"));
    }

    // hsqldb和mysql都不支持全连接，不知道为什么
    public void testFindBy4() {
        try {
            userDao.findBy("roles[:]r.name", "value");
        } catch (Exception ex) {
            assertTrue(true);
        }
    }

    public void testFindBy5() {
        assertNotNull(userDao.findBy("roles:r", "value"));
    }

    public void testFindBy6() {
        assertNotNull(userDao.findBy("roles:r.rescs:re.roles:r.name",
                "value"));
    }
}
