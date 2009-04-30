package com.family168.core.utils;

import java.io.*;

import java.util.*;

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class JsonUtilsTest extends TestCase {
    protected static Log logger = LogFactory.getLog(JsonUtilsTest.class);

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testConstructor() {
        JsonUtils utils = new JsonUtils();
        assertNotNull(utils);
    }

    public void testWrite() throws Exception {
        LongTreeNode node = new LongTreeNode();
        StringWriter writer = new StringWriter();
        String[] excludes = new String[] {"class", "root", "parent"};
        JsonUtils.write(node, writer, excludes, null);

        assertEquals("{\"allowChildren\":true,\"allowDelete\":true,\"allowEdit\":true,\"children\":[],\"cls\":\"\",\"draggable\":true,\"id\":0,\"leaf\":true,\"name\":\"\",\"parentId\":0,\"qtip\":\"\",\"text\":\"\",\"theSort\":0}",
            writer.toString());
    }

    public void testJson2Bean2() throws Exception {
        String json = "{\"id\":1,\"name\":\"name\",\"theSort\":0}";
        String[] excludes = new String[] {"class", "root", "parent"};
        LongTreeNode node = JsonUtils.json2Bean(json, LongTreeNode.class,
                excludes, null);
        assertEquals(new Long(1L), node.getId());
        assertEquals("name", node.getName());
    }

    public void testJson2Bean() throws Exception {
        String json = "{theByte:1,theShort:2,theInteger:3,theLong:4,theFloat:5,theDouble:6,theDate:'2007年09月23日',theBoolean:true}";
        String[] excludes = new String[] {"class", "root", "parent"};
        TestNode node = JsonUtils.json2Bean(json, TestNode.class,
                excludes, "yyyy年MM月dd日");
        assertEquals(new Byte((byte) 1), node.getTheByte());
        assertEquals(new Short((short) 2), node.getTheShort());
        assertEquals(new Integer(3), node.getTheInteger());
        assertEquals(new Long(4), node.getTheLong());
        assertEquals(new Float(5), node.getTheFloat());
        assertEquals(new Double(6), node.getTheDouble());
    }

    public void testJson2List() throws Exception {
        String json = "[{\"id\":1},{\"id\":2}]";
        String[] excludes = new String[] {"class", "root", "parent"};
        List<LongTreeNode> list = JsonUtils.json2List(json,
                LongTreeNode.class, excludes, null);
        assertEquals(2, list.size());
    }

    public void testJson2List2() throws Exception {
        String json = "[]";
        String[] excludes = new String[] {"class", "root", "parent"};
        List<LongTreeNode> list = JsonUtils.json2List(json,
                LongTreeNode.class, excludes, null);
        assertEquals(0, list.size());
    }

    public void testJson2List3() throws Exception {
        String json = "[{\"id\":1,name:'name1'},{\"id\":2,name:'name2'}]";
        String[] excludes = new String[] {"id", "class", "root", "parent"};
        List<LongTreeNode> list = JsonUtils.json2List(json,
                LongTreeNode.class, excludes, null);
        assertEquals(2, list.size());

        for (LongTreeNode node : list) {
            assertNull(node.getId());
        }
    }

    public void testPrimitive() throws Exception {
        String json;
        String[] excludes = new String[0];
        TestPrimitive bean;
        // byte
        json = "{byteField:1}";

        bean = JsonUtils.json2Bean(json, TestPrimitive.class, excludes,
                null);
        assertEquals((byte) 1, bean.getByteField());
        // short
        json = "{shortField:1}";

        bean = JsonUtils.json2Bean(json, TestPrimitive.class, excludes,
                null);
        assertEquals((short) 1, bean.getShortField());
        // int
        json = "{intField:1}";

        bean = JsonUtils.json2Bean(json, TestPrimitive.class, excludes,
                null);
        assertEquals(1, bean.getIntField());
        // long
        json = "{longField:1}";

        bean = JsonUtils.json2Bean(json, TestPrimitive.class, excludes,
                null);
        assertEquals(1L, bean.getLongField());
        // float
        json = "{floatField:1}";

        bean = JsonUtils.json2Bean(json, TestPrimitive.class, excludes,
                null);
        assertEquals(1.0F, bean.getFloatField());
        // double
        json = "{doubleField:1}";

        bean = JsonUtils.json2Bean(json, TestPrimitive.class, excludes,
                null);
        assertEquals(1.0D, bean.getDoubleField());
        // boolean
        json = "{booleanField:true}";

        bean = JsonUtils.json2Bean(json, TestPrimitive.class, excludes,
                null);
        assertTrue(bean.getBooleanField());
        // char
        json = "{charField:'a'}";

        bean = JsonUtils.json2Bean(json, TestPrimitive.class, excludes,
                null);
        assertEquals('a', bean.getCharField());
    }

    public static class TestNode {
        private Byte theByte;
        private Short theShort;
        private Integer theInteger;
        private Long theLong;
        private Float theFloat;
        private Double theDouble;
        private Date theDate;

        // theBoolean
        private Boolean theBoolean;

        public Byte getTheByte() {
            return theByte;
        }

        public void setTheByte(Byte theByte) {
            this.theByte = theByte;
        }

        public Short getTheShort() {
            return theShort;
        }

        public void setTheShort(Short theShort) {
            this.theShort = theShort;
        }

        public Integer getTheInteger() {
            return theInteger;
        }

        public void setTheInteger(Integer theInteger) {
            this.theInteger = theInteger;
        }

        public Long getTheLong() {
            return theLong;
        }

        public void setTheLong(Long theLong) {
            this.theLong = theLong;
        }

        public Float getTheFloat() {
            return theFloat;
        }

        public void setTheFloat(Float theFloat) {
            this.theFloat = theFloat;
        }

        public Double getTheDouble() {
            return theDouble;
        }

        public void setTheDouble(Double theDouble) {
            this.theDouble = theDouble;
        }

        public Date getTheDate() {
            return theDate;
        }

        public void setTheDate(Date theDate) {
            this.theDate = theDate;
        }

        public Boolean getTheBoolean() {
            return theBoolean;
        }

        public void setTheBoolean(Boolean theBoolean) {
            this.theBoolean = theBoolean;
        }
    }

    public static class TestPrimitive {
        private byte byteField;
        private short shortField;
        private int intField;
        private long longField;
        private float floatField;
        private double doubleField;
        private boolean booleanField;
        private char charField;

        public byte getByteField() {
            return byteField;
        }

        public void setByteField(byte byteField) {
            this.byteField = byteField;
        }

        public short getShortField() {
            return shortField;
        }

        public void setShortField(short shortField) {
            this.shortField = shortField;
        }

        public int getIntField() {
            return intField;
        }

        public void setIntField(int intField) {
            this.intField = intField;
        }

        public long getLongField() {
            return longField;
        }

        public void setLongField(long longField) {
            this.longField = longField;
        }

        public float getFloatField() {
            return floatField;
        }

        public void setFloatField(float floatField) {
            this.floatField = floatField;
        }

        public double getDoubleField() {
            return doubleField;
        }

        public void setDoubleField(double doubleField) {
            this.doubleField = doubleField;
        }

        public boolean getBooleanField() {
            return booleanField;
        }

        public void setBooleanField(boolean booleanField) {
            this.booleanField = booleanField;
        }

        public char getCharField() {
            return charField;
        }

        public void setCharField(char charField) {
            this.charField = charField;
        }
    }
}
