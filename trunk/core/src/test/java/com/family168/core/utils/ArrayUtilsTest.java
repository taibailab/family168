package com.family168.core.utils;

import java.util.*;

import junit.framework.*;


public class ArrayUtilsTest extends TestCase {
    public void testDefault() {
        ArrayUtils a = new ArrayUtils();
        assertNotNull(a);
    }

    public void testNull() {
        assertNull(ArrayUtils.copyArray(null));
    }

    public void testIntArray() {
        int[] orgin = new int[] {1, 2, 3};
        int[] array = ArrayUtils.copyArray(orgin);
        assertEquals(3, array.length);
    }

    public void testIntegerArray() {
        Integer[] orgin = new Integer[] {1, 2, 3};
        Integer[] array = ArrayUtils.copyArray(orgin);
        assertEquals(3, array.length);
    }

    public void testLongArray() {
        long[] orgin = new long[] {1L, 2L, 3L};
        long[] array = ArrayUtils.copyArray(orgin);
        assertEquals(3, array.length);
    }

    public void testLongArray2() {
        Long[] orgin = new Long[] {1L, 2L, 3L};
        Long[] array = ArrayUtils.copyArray(orgin);
        assertEquals(3, array.length);
    }

    public void testDoubleArray() {
        double[] orgin = new double[] {1D, 2D, 3D};
        double[] array = ArrayUtils.copyArray(orgin);
        assertEquals(3, array.length);
    }

    public void testDoubleArray2() {
        Double[] orgin = new Double[] {1D, 2D, 3D};
        Double[] array = ArrayUtils.copyArray(orgin);
        assertEquals(3, array.length);
    }

    public void testStringArray() {
        String[] orgin = new String[] {"1", "2", "3"};
        String[] array = ArrayUtils.copyArray(orgin);
        assertEquals(3, array.length);
    }

    public void testDateArray() {
        Date[] orgin = new Date[] {new Date(), new Date(), new Date()};
        Date[] array = ArrayUtils.copyArray(orgin);
        assertEquals(3, array.length);
    }

    public void testObjectArray() {
        try {
            Object[] orgin = new Object[] {
                    new Object(), new Object(), new Object()
                };
            Object[] array = ArrayUtils.copyArray(orgin);
            fail();
        } catch (Exception ex) {
            // expect here.
        }
    }
}
