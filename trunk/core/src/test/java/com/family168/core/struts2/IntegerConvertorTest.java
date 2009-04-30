package com.family168.core.struts2;

import java.util.Date;
import java.util.Map;

import junit.framework.*;

import org.apache.struts2.util.StrutsTypeConverter;


public class IntegerConvertorTest extends TestCase {
    public void testConvertFromString() {
        IntegerConvertor c = new IntegerConvertor();
        Object o = c.convertFromString(null, new String[] {"1"},
                Integer.class);
        assertNotNull(o);
    }

    public void testConvertFromStringError() {
        IntegerConvertor c = new IntegerConvertor();
        Object o = c.convertFromString(null, new String[] {"x"},
                Integer.class);
        assertNull(o);
    }

    public void testConvertFromStringError2() {
        IntegerConvertor c = new IntegerConvertor();
        Object o = c.convertFromString(null, new String[] {"x"}, Date.class);
        assertNull(o);
    }

    public void testConvertToString() {
        IntegerConvertor c = new IntegerConvertor();
        Object o = c.convertToString(null, new Integer(1));
        assertNotNull(o);
    }

    public void testConvertToStringNull() {
        IntegerConvertor c = new IntegerConvertor();
        Object o = c.convertToString(null, null);
        assertNull(o);
    }
}
