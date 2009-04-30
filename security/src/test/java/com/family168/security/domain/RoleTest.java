package com.family168.security.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class RoleTest extends TestCase {
    protected static Log logger = LogFactory.getLog(RoleTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        Role entity = new Role();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setName(null);
        assertNull(entity.getName());
        entity.setDescn(null);
        assertNull(entity.getDescn());
        entity.setUsers(null);
        assertNull(entity.getUsers());
        entity.setRescs(null);
        assertNull(entity.getRescs());
        entity.setMenus(null);
        assertNull(entity.getMenus());
    }
}
