package com.family168.core.struts2;

import junit.framework.*;

import org.springframework.mock.web.*;


public class BaseActionTest extends TestCase {
    BaseAction action = new BaseAction();

    public void testRequest() {
        action.setServletRequest(new MockHttpServletRequest());
        assertNotNull(action);
    }

    public void testResponse() {
        action.setServletResponse(new MockHttpServletResponse());
        assertNotNull(action);
    }

    public void testResponse2() {
        action.setServletResponse(null);
        assertNotNull(action);
    }

    public void testModel() {
        assertNotNull(action.getModel());
    }

    public void testModel2() {
        assertNotNull(action.getModel());
        assertNotNull(action.getModel());
    }

    public void testModel3() {
        assertNull(new TestAction().getModel());
    }

    class Bean {
        public Bean() throws Exception {
            throw new Exception("test");
        }
    }

    class TestAction extends BaseAction<Bean> {
    }
}
