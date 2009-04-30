package com.family168.jbpm.spring;

import junit.framework.*;


public class VariableFormTest extends TestCase {
    protected VariableForm form = null;

    protected void setUp() {
        form = new VariableForm();
    }

    protected void tearDown() {
        form = null;
    }

    public void testGetter() {
        assertNull(form.getName());
        assertEquals("", form.getValue());
    }

    public void testGetValue() {
        form = new VariableForm("name", "value", true);
        assertEquals("value", form.getValue());
    }

    public void testIsReadonly() {
        assertFalse(form.isReadonly());
        form = new VariableForm("name", "value", true);
        assertTrue(form.isReadonly());
    }

    public void testToString() {
        assertEquals("[name:null,value:null,readonly:false]",
            form.toString());
    }
}
