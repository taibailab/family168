package com.family168.core.utils;

import java.util.*;

import junit.framework.*;


public class MainUtils2Test extends TestCase {
    public void testGetByte() {
        Byte n1 = Byte.valueOf((byte) 1);
        Byte n2 = Byte.valueOf((byte) 2);
        Byte n3 = Byte.valueOf((byte) 3);
        assertEquals(n1, MainUtils.getByte("1", null));
        assertEquals(n2, MainUtils.getByte("2"));
        assertEquals(n3, MainUtils.getByte("xxxx", n3));
    }

    public void testGetShort() {
        Short n1 = Short.valueOf((short) 1);
        Short n2 = Short.valueOf((short) 2);
        Short n3 = Short.valueOf((short) 3);
        assertEquals(n1, MainUtils.getShort("1", null));
        assertEquals(n2, MainUtils.getShort("2"));
        assertEquals(n3, MainUtils.getShort("xxxx", n3));
    }

    public void testGetInt() {
        Integer n1 = Integer.valueOf(1);
        Integer n2 = Integer.valueOf(2);
        Integer n3 = Integer.valueOf(3);
        assertEquals(n1, MainUtils.getInt("1", null));
        assertEquals(n2, MainUtils.getInt("2"));
        assertEquals(n3, MainUtils.getInt("xxxx", n3));
    }

    public void testGetLong() {
        Long n1 = Long.valueOf(1L);
        Long n2 = Long.valueOf(2L);
        Long n3 = Long.valueOf(3L);
        assertEquals(n1, MainUtils.getLong("1", null));
        assertEquals(n2, MainUtils.getLong("2"));
        assertEquals(n3, MainUtils.getLong("xxxx", n3));
    }

    public void testGetFloat() {
        Float n1 = Float.valueOf(1F);
        Float n2 = Float.valueOf(2F);
        Float n3 = Float.valueOf(3F);
        assertEquals(n1, MainUtils.getFloat("1", null), 0.1F);
        assertEquals(n2, MainUtils.getFloat("2"), 0.1F);
        assertEquals(n3, MainUtils.getFloat("xxxx", n3), 0.1F);
    }

    public void testGetDouble() {
        Double n1 = Double.valueOf(1D);
        Double n2 = Double.valueOf(2D);
        Double n3 = Double.valueOf(3D);
        assertEquals(n1, MainUtils.getDouble("1", null), 0.1D);
        assertEquals(n2, MainUtils.getDouble("2"), 0.1D);
        assertEquals(n3, MainUtils.getDouble("xxxx", n3), 0.1D);
    }

    public void testGetChar() {
        Character n1 = Character.valueOf('1');
        Character n2 = Character.valueOf('2');
        Character n3 = Character.valueOf('3');
        assertEquals(n1, MainUtils.getChar("1", null));
        assertEquals(n2, MainUtils.getChar("2"));
        assertEquals(n3, MainUtils.getChar(null, n3));
    }

    public void testBoolean() {
        assertTrue(MainUtils.getBoolean("true"));
        assertTrue(MainUtils.getBoolean("TrUe"));
        assertFalse(MainUtils.getBoolean("xxx"));
        assertFalse(MainUtils.getBoolean("false"));
    }

    public void testDate() {
        assertNull(MainUtils.getDate("1111-11-11", null));
        assertNull(MainUtils.getDate("xxxx"));
    }

    public void testCurrency() {
        assertEquals(1000.0, MainUtils.getCurrency("￥1,000.00"));
        assertEquals(.1, MainUtils.getCurrency("xxx", .1));
    }
}
