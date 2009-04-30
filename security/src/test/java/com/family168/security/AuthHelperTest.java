package com.family168.security;

import java.util.*;

import junit.framework.TestCase;


public class AuthHelperTest extends TestCase {
    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testSaveAuth() {
        Collection authSet = new HashSet();
        Object authObject = new Object();

        // 以前没有这个权限，就添加
        AuthHelper.saveAuth(authSet, authObject, false);
        assertTrue(authSet.contains(authObject));

        // 以前有了这个权限，就删除
        AuthHelper.saveAuth(authSet, authObject, true);
        assertFalse(authSet.contains(authObject));

        // 想添加，但以前就有这个权限了
        authSet.add(authObject);
        AuthHelper.saveAuth(authSet, authObject, false);
        assertTrue(authSet.contains(authObject));

        // 想删除，但以前没有这个权限
        authSet.remove(authObject);
        AuthHelper.saveAuth(authSet, authObject, true);
        assertFalse(authSet.contains(authObject));
    }

    public void testJudgeAuthorized() {
        TestResource res1 = new TestResource();
        TestResource res2 = new TestResource();

        Collection auths = new HashSet();
        Collection autheds = new HashSet();
        auths.add(res1);
        auths.add(res2);
        autheds.add(res2);

        AuthHelper.judgeAuthorized(auths, autheds);

        assertTrue(res2.authorized);
        assertFalse(res1.authorized);
    }

    public void testCannotJudgeAuthorized() {
        Collection auths = new HashSet();
        Collection autheds = new HashSet();
        auths.add(new TestResource2());

        try {
            AuthHelper.judgeAuthorized(auths, autheds);
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
    }

    public class TestResource {
        boolean authorized = false;

        public void setAuthorized(boolean authorizedIn) {
            authorized = authorizedIn;
        }
    }

    // 只有在方法，或类，不是public的情况下才抛
    class TestResource2 {
        boolean authorized = false;

        public void setAuthorized(boolean authorizedIn) {
            authorized = authorizedIn;
        }
    }
}
