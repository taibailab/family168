package com.family168.core.utils;

import junit.framework.*;


public class BeanUtilsTest extends TestCase {
    public void testDefault() {
        BeanUtils b = new BeanUtils();
        assertNotNull(b);
    }

    public void testName() throws Exception {
        assertEquals("name",
            BeanUtils.getDeclaredField(new Bean(), "name").getName());
    }

    public void testName2() throws Exception {
        assertEquals("name",
            BeanUtils.getDeclaredField(Sub.class, "name").getName());
    }

    public void testName3() {
        try {
            BeanUtils.getDeclaredField(Sub.class, "xxx");
        } catch (Exception ex) {
            assertTrue(true);
        }
    }

    public void testGetProperty() throws Exception {
        assertNull(BeanUtils.forceGetProperty(new Sub(), "name"));
    }

    public void testSetProperty() throws Exception {
        BeanUtils.forceSetProperty(new Sub(), "name", "xxx");
    }

    public void testMethod() throws Exception {
        assertNull(BeanUtils.invokePrivateMethod(new Sub(), "invokeName"));
    }

    public void testGetFieldsByType() throws Exception {
        assertEquals(1,
            BeanUtils.getFieldsByType(new Bean(), String.class).size());
    }

    public void testGetPropertyType() throws Exception {
        assertEquals(String.class,
            BeanUtils.getPropertyType(Bean.class, "name"));
    }

    public void testGetGetterName() throws Exception {
        assertEquals("isField1",
            BeanUtils.getGetterName(Sub.class, "field1"));
        assertEquals("isField2",
            BeanUtils.getGetterName(Sub.class, "field2"));
        assertEquals("getField3",
            BeanUtils.getGetterName(Sub.class, "field3"));
    }

    public void testGetGetterMethod() throws Exception {
        assertEquals("getType",
            BeanUtils.getGetterMethod(Sub.class, "type").getName());
    }

    public void testGetGetterMethod2() throws Exception {
        assertNull(BeanUtils.getGetterMethod(Bean.class, "type"));
    }

    public void testGetGetterMethod3() throws Exception {
        assertNull(BeanUtils.getGetterMethod(Sub.class, "field1"));
    }

    class Bean {
        private String name;

        private String invokeName() {
            return name;
        }
    }

    class Sub extends Bean {
        //
        private boolean field1;
        private Boolean field2;
        private String field3;

        //
        private String type;

        //
        public String getType() {
            return type;
        }
    }
}
