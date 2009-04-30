package com.family168.security.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class MenuTest extends TestCase {
    protected static Log logger = LogFactory.getLog(MenuTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        Menu entity = new Menu();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setParent(null);
        assertNull(entity.getParent());
        entity.setName(null);
        assertNull(entity.getName());
        entity.setTheSort(null);
        assertNull(entity.getTheSort());

        entity.setQtip(null);
        assertNull(entity.getQtip());
        entity.setIconCls(null);
        assertNull(entity.getIconCls());
        entity.setUrl(null);
        assertNull(entity.getUrl());

        entity.setDescn(null);
        assertNull(entity.getDescn());
        entity.setChildren(null);
        assertNull(entity.getChildren());
        entity.setRoles(null);
        assertNull(entity.getRoles());
    }
}
