package com.family168.security;

import java.lang.reflect.InvocationTargetException;

import java.util.*;

import junit.framework.TestCase;

import org.apache.commons.beanutils.BeanUtils;

import org.springframework.security.*;


public class AuthenticationHelperTest extends TestCase {
    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testConvertToGrantedAuthority() {
        Collection<GrantedAuthorityImpl> auths = new HashSet<GrantedAuthorityImpl>();
        GrantedAuthorityImpl item = new GrantedAuthorityImpl("test");
        auths.add(item);

        GrantedAuthority[] arrays = AuthenticationHelper
            .convertToGrantedAuthority(auths);
        assertEquals(1, arrays.length);
        assertEquals(item, arrays[0]);
    }

    public void testConvertToGrantedAuthority2() {
        Collection<Object> auths = new HashSet<Object>();
        Object item = new Object();
        auths.add(item);

        GrantedAuthority[] arrays = AuthenticationHelper
            .convertToGrantedAuthority(auths, "class");
        assertEquals(1, arrays.length);
        assertEquals("class java.lang.Object", arrays[0].getAuthority());
    }

    public void testGetCadByAuthorities() {
        ConfigAttributeDefinition def = AuthenticationHelper
            .getCadByAuthorities(null, true);
        assertNotNull(def);

        def = AuthenticationHelper.getCadByAuthorities(null, false);
        assertNull(def);
    }

    public void testGetCadByAuthorities2() {
        Collection<GrantedAuthority> auths = new HashSet<GrantedAuthority>();
        GrantedAuthority item = new GrantedAuthorityImpl("test");
        auths.add(item);

        ConfigAttributeDefinition def = AuthenticationHelper
            .getCadByAuthorities(auths, true);
        assertNotNull(def);
    }
}
