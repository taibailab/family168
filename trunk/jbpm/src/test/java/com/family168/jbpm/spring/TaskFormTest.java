package com.family168.jbpm.spring;

import junit.framework.*;


public class TaskFormTest extends TestCase {
    protected TaskForm form = null;

    protected void setUp() {
        form = new TaskForm(null, null);
    }

    protected void tearDown() {
        form = null;
    }

    public void testGetter() {
        assertNull(form.getTaskInstance());
        assertNull(form.getVariableFormList());
    }
}
