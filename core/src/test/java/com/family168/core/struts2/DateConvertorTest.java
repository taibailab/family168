package com.family168.core.struts2;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;

import junit.framework.*;

import org.apache.struts2.util.StrutsTypeConverter;


public class DateConvertorTest extends TestCase {
    public void testConvertFromString() {
        DateConvertor c = new DateConvertor();
        Object o = c.convertFromString(null, new String[] {"08年06月27日"},
                Date.class);
        assertNotNull(o);
    }

    public void testConvertFromStringError() {
        DateConvertor c = new DateConvertor();
        Object o = c.convertFromString(null, new String[] {"08-06-27"},
                Date.class);
        assertNull(o);
    }

    public void testConvertFromStringError2() {
        DateConvertor c = new DateConvertor();
        Object o = c.convertFromString(null, new String[] {"08-06-27"},
                Integer.class);
        assertNull(o);
    }

    public void testConvertToString() {
        DateConvertor c = new DateConvertor();
        Object o = c.convertToString(null, new Date());
        assertNotNull(o);
    }

    public void testConvertToStringNull() {
        DateConvertor c = new DateConvertor();
        Object o = c.convertToString(null, null);
        assertNull(o);
    }
}
