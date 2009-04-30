package com.family168.security.domain;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class RescTest extends TestCase {
    protected static Log logger = LogFactory.getLog(RescTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testFields() {
        Resc entity = new Resc();
        entity.setId(null);
        assertNull(entity.getId());
        entity.setName(null);
        assertNull(entity.getName());
        entity.setResType(null);
        assertNull(entity.getResType());
        entity.setResString(null);
        assertNull(entity.getResString());
        entity.setDescn(null);
        assertNull(entity.getDescn());
        entity.setRoles(null);
        assertNull(entity.getRoles());
    }
}
