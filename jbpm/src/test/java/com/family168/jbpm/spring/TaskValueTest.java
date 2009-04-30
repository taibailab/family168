package com.family168.jbpm.spring;

import junit.framework.*;


public class TaskValueTest extends TestCase {
    protected TaskValue value = null;

    protected void setUp() {
        value = new TaskValue(0L, null);
    }

    protected void tearDown() {
        value = null;
    }

    public void testGetter() {
        assertEquals(0L, value.getTaskInstanceId());
        assertEquals(0, value.getVariableFormList().size());
        assertNull(value.getTransition());
    }

    public void testIs() {
        assertFalse(value.isDefaultEnd());
        assertFalse(value.isSave());
        assertFalse(value.isCancel());
    }

    public void testVariables() {
        //
        value.setVariables(new String[] {"name"}, new String[] {"value"},
            new String[] {"true"});
        //
        value.setVariables(new String[] {"name"}, new String[] {"value"},
            new String[] {"false"});
        value.setVariables(new String[] {"name1", "name2"},
            new String[] {"value"}, new String[] {"false"});
    }

    public void testToString() {
        assertEquals("{taskInstanceId:0,transition:null,variableFormList:[]}",
            value.toString());
    }
}
