package com.family168.security.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class UserTest extends TestCase {
    protected static Log logger = LogFactory.getLog(UserTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        User entity = new User();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setDept(null);
        assertNull(entity.getDept());
        entity.setUsername(null);
        assertNull(entity.getUsername());
        entity.setPassword(null);
        assertNull(entity.getPassword());
        entity.setStatus(null);
        assertNull(entity.getStatus());
        entity.setDescn(null);
        assertNull(entity.getDescn());
        entity.setRoles(null);
        assertNull(entity.getRoles());
    }
}
