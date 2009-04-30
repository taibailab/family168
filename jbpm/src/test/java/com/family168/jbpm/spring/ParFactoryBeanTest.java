package com.family168.jbpm.spring;

import junit.framework.*;

import org.jbpm.graph.def.ProcessDefinition;


public class ParFactoryBeanTest extends TestCase {
    protected ParFactoryBean bean = null;

    protected void setUp() {
        bean = new ParFactoryBean();
    }

    protected void tearDown() {
        bean = null;
    }

    public void testGetter() throws Exception {
        assertNull(bean.getObject());
        assertEquals(ProcessDefinition.class, bean.getObjectType());
        assertTrue(bean.isSingleton());

        bean.setDefinitionLocation(null);
    }
}
