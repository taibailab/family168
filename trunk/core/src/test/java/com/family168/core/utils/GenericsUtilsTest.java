package com.family168.core.utils;

import java.lang.reflect.*;

import junit.framework.TestCase;


/**
 * @author calvin
 */
public class GenericsUtilsTest extends TestCase {
    public static final String STR = "test";

    public void testConstructor() {
        assertNotNull(new GenericsUtils());
    }

    /**
     * 测试GenericsUtils的各种情况
     */
    public void testGetGenericClass() {
        // 测试取出第1,2个范型类定义
        assertEquals(TestBean.class,
            GenericsUtils.getSuperClassGenricType(
                TestActualGenericsBean.class));
        assertEquals(TestBean2.class,
            GenericsUtils.getSuperClassGenricType(
                TestActualGenericsBean.class, 1));

        // 数组越界的时候返回Object.class
        assertEquals(Object.class,
            GenericsUtils.getSuperClassGenricType(
                TestActualGenericsBean.class, 2));

        // 测试无范型定义时返回Object.class
        assertEquals(Object.class,
            GenericsUtils.getSuperClassGenricType(
                TestActualGenericsBean2.class));

        // enum
        assertEquals(TestEnumEnum.class,
            GenericsUtils.getSuperClassGenricType(TestEnum.class));

        // interface
        assertEquals(Runnable.class,
            GenericsUtils.getSuperClassGenricType(TestInterface.class));
    }

    public void testOutRange() {
        // ?
        assertEquals(Object.class,
            GenericsUtils.getSuperClassGenricType(
                TestActualGenericsBean.class, 3));
        // ?
        assertEquals(Object.class,
            GenericsUtils.getSuperClassGenricType(
                TestActualGenericsBean.class, -1));
    }

    /**
     * 测试超类参数可能不是class的情况
     */
    public void testNotClass() {
        GenericsUtils.getSuperClassGenricType(MyClass2.class);
    }

    /**
     * 带范型定义的父类
     */
    public class TestGenericsBean<T, T2> {
    }

    /**
     * T1用到的类
     */
    public class TestBean {
    }

    /**
     * T2用到的类
     */
    public class TestBean2 {
    }

    /**
     * 定义了父类范型的子类
     */
    public class TestActualGenericsBean extends TestGenericsBean<TestBean, TestBean2> {
    }

    /**
     * 没有定义父类范型的子类
     */
    public class TestActualGenericsBean2 extends TestGenericsBean {
    }

    public class TestEnum extends TestGenericsBean<TestEnumEnum, TestEnumEnum> {
    }

    public class TestInterface extends TestGenericsBean<Runnable, Runnable> {
    }

    public class TextNoClass<T, T2> extends TestGenericsBean {
    }

    // 帮助我解开最后那个if not instance of Class的谜题
    static class MyClass<T> {
    }

    static class MyClass2<T> extends MyClass<T> {
    }
    enum TestEnumEnum {
    }
}
