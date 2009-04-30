package com.family168.jbpm.spring;

import junit.framework.*;


public class ProcessImageTagTest extends TestCase {
    protected ProcessImageTag tag = null;

    protected void setUp() {
        tag = new ProcessImageTag();
    }

    protected void tearDown() {
        tag = null;
    }

    public void testTag() {
        assertNotNull(tag);
    }
}
