package com.family168.core.utils;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;


public class HsqldbListenerTest extends TestCase {
    HsqldbListener listener = null;

    @Override
    protected void setUp() {
        listener = new HsqldbListener();
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testContextInitialized() {
        listener.contextInitialized(null);
        listener.contextDestroyed(null);
    }

    public void testContextDestroyedWrong() {
        listener.contextDestroyed(null);
    }
}
