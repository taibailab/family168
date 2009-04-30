package com.family168.core.utils;

import java.lang.reflect.*;

import java.util.*;

import junit.framework.*;


public class MainUtils4Test extends TestCase {
    public void testSetter() throws Exception {
        Method method = Contract.class.getDeclaredMethod("setName",
                String.class);
        assertTrue(MainUtils.isSetter(method));
    }

    public void testNotSetter() throws Exception {
        Method method = Contract.class.getDeclaredMethod("setName",
                String.class, String.class);
        assertFalse(MainUtils.isSetter(method));
    }

    public void testNotSetter2() throws Exception {
        Method method = Contract.class.getDeclaredMethod("set");
        assertFalse(MainUtils.isSetter(method));
    }

    public void testNotSetter3() throws Exception {
        Method method = Contract.class.getDeclaredMethod("a");
        assertFalse(MainUtils.isSetter(method));
    }

    public void testNotSetter4() throws Exception {
        Method method = Contract.class.getDeclaredMethod("xxxx");
        assertFalse(MainUtils.isSetter(method));
    }

    public void testGetter2Setter() {
        assertEquals("setName", MainUtils.getter2Setter("getName"));
        assertEquals("setName", MainUtils.getter2Setter("isName"));
    }

    public void testGetter2SetterWrong() {
        try {
            MainUtils.getter2Setter("xxx");
            fail();
        } catch (IllegalArgumentException ex) {
            // expect here.
        }
    }

    public void testM2f() {
        assertEquals("name", MainUtils.m2f("getName"));
        assertEquals("name", MainUtils.m2f("setName"));
        assertEquals("name", MainUtils.m2f("isName"));
    }

    public void testM2fWrong() {
        try {
            MainUtils.m2f("xxx");
            fail();
        } catch (IllegalArgumentException ex) {
            // expect here.
        }
    }

    static class Contract {
        private String name;

        public void setName(String name) {
            this.name = name;
        }

        public void setName(String firstName, String lastName) {
            this.name = firstName + " " + lastName;
        }

        public void set() {
        }

        public void a() {
        }

        public void xxxx() {
        }
    }
}
