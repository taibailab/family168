package com.family168.jbpm.spring;

import junit.framework.*;


public class JbpmManagerTest extends TestCase {
    public void testJbpmTemplate() {
        JbpmManager jbpmManager = new JbpmManager();
        jbpmManager.setJbpmTemplate(null);
    }
}
