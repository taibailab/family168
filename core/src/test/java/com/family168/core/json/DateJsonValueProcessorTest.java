package com.family168.core.json;

import java.util.Date;

import junit.framework.*;


public class DateJsonValueProcessorTest extends TestCase {
    public void testContructor() {
        DateJsonValueProcessor p = new DateJsonValueProcessor(null);
        assertNotNull(p);
    }

    public void testContructor2() {
        DateJsonValueProcessor p = new DateJsonValueProcessor("yyyy");
        assertNotNull(p);
    }

    public void testProcessArrayValue() {
        DateJsonValueProcessor p = new DateJsonValueProcessor("yyyy");
        Object o = p.processArrayValue(new Date(), null);
        assertNotNull(o);
    }

    public void testProcessObjectValue() {
        DateJsonValueProcessor p = new DateJsonValueProcessor("yyyy");
        Object o = p.processObjectValue(null, new Date(), null);
        assertNotNull(o);
    }

    public void testProcessObjectValueError() {
        DateJsonValueProcessor p = new DateJsonValueProcessor("yyyy");
        Object o = p.processObjectValue(null, null, null);
        assertNull(o);
    }
}
