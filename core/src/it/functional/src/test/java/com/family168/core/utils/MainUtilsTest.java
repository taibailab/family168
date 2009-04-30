package com.family168.core.utils;

import java.util.*;

import junit.framework.*;


public class MainUtilsTest extends TestCase {
    public void testDefault() {
        MainUtils m = new MainUtils();
        assertNotNull(m);
    }

    public void testCopyForEmpty() {
        Bean1 a = new Bean1();
        Bean1 b = new Bean1();
        a.setIntArray(new int[] {1, 2, 3});
        MainUtils.copy(b, a);
        assertEquals(3, a.getIntArray().length);
    }

    public void testCopyForNotEmpty() {
        Bean1 a = new Bean1();
        Bean1 b = new Bean1();
        b.setCollection(Arrays.asList(new Object[] {1, 2, 3}));
        b.setObjectArray(new Object[] {1, 2, 3});
        b.setShortArray(new short[] {1, 2, 3});
        b.setByteArray(new byte[] {1, 2, 3});
        b.setIntArray(new int[] {1, 2, 3});
        b.setLongArray(new long[] {1L, 2L, 3L});
        b.setFloatArray(new float[] {1F, 2F, 3F});
        b.setDoubleArray(new double[] {1D, 2D, 3D});
        b.setCharArray(new char[] {'1', '2', '3'});
        b.setBooleanArray(new boolean[] {true, true, true});
        MainUtils.copy(b, a);
        assertEquals(0, a.getIntArray().length);
    }

    // copy
    public void testCopy2() {
        Bean src = new Bean();
        src.setNum(10);
        src.setStr("test");

        Bean dest = new Bean();
        MainUtils.copy(src, dest);
        assertEquals(10, dest.getNum());
        assertEquals("test", dest.getStr());
    }

    public void testCopy3() {
        Bean src = new Bean();
        src.setNum(10);
        src.setStr(null);

        Bean dest = new Bean();
        dest.setStr("test");
        MainUtils.copy(src, dest);
        assertEquals(10, dest.getNum());
        assertEquals("test", dest.getStr());
    }

    public void testCopy4() {
        Bean src = new Bean();
        src.setNum(10);
        src.setStr(null);

        Bean1 dest = new Bean1();
        MainUtils.copy(src, dest);
        assertEquals(0, dest.getIntArray().length);
    }

    static class Bean {
        private int num = 0;
        private String str = null;
        private boolean tag = false;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public boolean isTag() {
            return tag;
        }

        public void setTag(boolean tag) {
            this.tag = tag;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        public void get() {
        }

        public void is() {
        }
    }

    static class Bean1 {
        private Collection collection = Collections.EMPTY_LIST;
        private Object[] objectArray = new Object[0];
        private byte[] byteArray = new byte[0];
        private short[] shortArray = new short[0];
        private int[] intArray = new int[0];
        private long[] longArray = new long[0];
        private float[] floatArray = new float[0];
        private double[] doubleArray = new double[0];
        private char[] charArray = new char[0];
        private boolean[] booleanArray = new boolean[0];

        public Collection getCollection() {
            return collection;
        }

        public void setCollection(Collection collection) {
            this.collection = collection;
        }

        public Object[] getObjectArray() {
            return objectArray;
        }

        public void setObjectArray(Object[] objectArray) {
            this.objectArray = objectArray;
        }

        public byte[] getByteArray() {
            return byteArray;
        }

        public void setByteArray(byte[] byteArray) {
            this.byteArray = byteArray;
        }

        public short[] getShortArray() {
            return shortArray;
        }

        public void setShortArray(short[] shortArray) {
            this.shortArray = shortArray;
        }

        public int[] getIntArray() {
            return intArray;
        }

        public void setIntArray(int[] intArray) {
            this.intArray = intArray;
        }

        public long[] getLongArray() {
            return longArray;
        }

        public void setLongArray(long[] longArray) {
            this.longArray = longArray;
        }

        public float[] getFloatArray() {
            return floatArray;
        }

        public void setFloatArray(float[] floatArray) {
            this.floatArray = floatArray;
        }

        public double[] getDoubleArray() {
            return doubleArray;
        }

        public void setDoubleArray(double[] doubleArray) {
            this.doubleArray = doubleArray;
        }

        public char[] getCharArray() {
            return charArray;
        }

        public void setCharArray(char[] charArray) {
            this.charArray = charArray;
        }

        public boolean[] getBooleanArray() {
            return booleanArray;
        }

        public void setBooleanArray(boolean[] booleanArray) {
            this.booleanArray = booleanArray;
        }
    }
}
