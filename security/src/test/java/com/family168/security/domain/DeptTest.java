package com.family168.security.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class DeptTest extends TestCase {
    protected static Log logger = LogFactory.getLog(DeptTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        Dept entity = new Dept();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setParent(null);
        assertNull(entity.getParent());
        entity.setName(null);
        assertNull(entity.getName());
        entity.setDescn(null);
        assertNull(entity.getDescn());
        entity.setChildren(null);
        assertNull(entity.getChildren());
        entity.setUsers(null);
        assertNull(entity.getUsers());
    }
}
