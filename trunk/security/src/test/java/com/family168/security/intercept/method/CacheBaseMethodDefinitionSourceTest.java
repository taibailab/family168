package com.family168.security.intercept.method;

import java.lang.reflect.*;

import java.util.*;

import com.family168.security.AuthenticationHelper;
import com.family168.security.cache.*;
import com.family168.security.resource.*;
import com.family168.security.service.*;

import junit.framework.TestCase;

import net.sf.ehcache.*;

import org.aopalliance.intercept.MethodInvocation;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.aspectj.lang.*;
import org.aspectj.lang.reflect.CodeSignature;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;

import org.springframework.security.*;
import org.springframework.security.intercept.method.MethodDefinitionSource;
import org.springframework.security.providers.dao.UserCache;
import org.springframework.security.providers.dao.cache.EhCacheBasedUserCache;

import org.springframework.util.Assert;


public class CacheBaseMethodDefinitionSourceTest extends TestCase {
    CacheBaseMethodDefinitionSource source = null;
    EhCacheBasedResourceCache resourceCache = null;
    EhCacheBasedUserCache userCache = null;
    ResourceDetails resourceDetails = null;
    AcegiCacheManager manager = null;
    Cache userEhCache = null;
    Cache resourceEhCache = null;
    GrantedAuthority[] authorities = null;

    @Override
    protected void setUp() {
        source = new CacheBaseMethodDefinitionSource();
        resourceCache = new EhCacheBasedResourceCache();
        userCache = new EhCacheBasedUserCache();

        authorities = new GrantedAuthority[] {
                new GrantedAuthorityImpl("all")
            };
        resourceDetails = new Resource("java.lang.Object.toString",
                "METHOD", authorities);
        userEhCache = new Cache("test user", 20, false, false, 0, 0);
        resourceEhCache = new Cache("test resource", 20, false, false, 0, 0);
        userEhCache.initialise();
        resourceEhCache.initialise();

        userCache.setCache(userEhCache);
        resourceCache.setCache(resourceEhCache);
        resourceCache.putResourceInCache(resourceDetails);
        manager = new AcegiCacheManager(userCache, resourceCache);
        source.setAcegiCacheManager(manager);
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testDefault() {
        assertNull(source.getConfigAttributeDefinitions());
    }

    public void testSupports() {
        assertTrue(source.supports(MethodInvocation.class));
        assertTrue(source.supports(JoinPoint.class));
    }

    public void testProtectAllResource() {
        source.setProtectAllResource(true);
        assertTrue(source.isProtectAllResource());
    }

    public void testGetAttributes() {
        try {
            ConfigAttributeDefinition def = source.getAttributes(new Object());
            fail();
        } catch (IllegalArgumentException ex) {
            assertEquals("Object must be a MethodInvocation or JoinPoint",
                ex.getMessage());
        }
    }

    public void testGetAttributes2() throws Exception {
        Object target = new Object();
        Method method = target.getClass().getDeclaredMethod("toString");

        MethodInvocation mockMI = createMock(MethodInvocation.class);
        expect(mockMI.getThis()).andReturn(target);
        expect(mockMI.getMethod()).andReturn(method);
        replay(mockMI);

        resourceCache.removeAllResources();
        resourceCache.putResourceInCache(new Resource("/", "URL",
                new GrantedAuthority[0]));
        manager = new AcegiCacheManager(userCache, resourceCache);
        source.setAcegiCacheManager(manager);

        ConfigAttributeDefinition def = source.getAttributes(mockMI);
        verify();

        assertNull(def);
    }

    public void testGetAttributes3() throws Exception {
        Object target = new Object();
        Method method = target.getClass().getDeclaredMethod("toString");

        JoinPoint mockJP = createMock(JoinPoint.class);
        JoinPoint.StaticPart mockSP = createMock(JoinPoint.StaticPart.class);

        CodeSignature mockCS = createMock(CodeSignature.class);
        expect(mockJP.getTarget()).andReturn(target);
        expect(mockJP.getStaticPart()).andReturn(mockSP).times(2);
        expect(mockSP.getSignature()).andReturn(mockCS).times(2);
        expect(mockCS.getName()).andReturn("toString");
        expect(mockCS.getParameterTypes()).andReturn(new Class[0]);
        replay(mockJP);
        replay(mockSP);
        replay(mockCS);

        ConfigAttributeDefinition def = source.getAttributes(mockJP);
        verify();

        assertNotNull(def);
    }

    public void testGetAttributes4() throws Exception {
        Object target = new String();
        Method method = target.getClass()
                              .getDeclaredMethod("split", String.class);

        JoinPoint mockJP = createMock(JoinPoint.class);
        JoinPoint.StaticPart mockSP = createMock(JoinPoint.StaticPart.class);

        CodeSignature mockCS = createMock(CodeSignature.class);
        expect(mockJP.getTarget()).andReturn(target);
        expect(mockJP.getStaticPart()).andReturn(mockSP).times(2);
        expect(mockSP.getSignature()).andReturn(mockCS).times(2);
        expect(mockCS.getName()).andReturn("split");
        expect(mockCS.getParameterTypes())
            .andReturn(new Class[] {String.class});
        replay(mockJP);
        replay(mockSP);
        replay(mockCS);

        resourceCache.removeAllResources();
        resourceDetails = new Resource("java.lang.Object.toString",
                "METHOD", authorities);
        resourceCache.putResourceInCache(resourceDetails);
        resourceDetails = new Resource("java.lang.Runnable.*", "METHOD",
                authorities);
        resourceCache.putResourceInCache(resourceDetails);
        resourceDetails = new Resource("java.lang.String.get*", "METHOD",
                authorities);
        resourceCache.putResourceInCache(resourceDetails);
        resourceDetails = new Resource("java.lang.String.*string",
                "METHOD", authorities);
        resourceCache.putResourceInCache(resourceDetails);
        manager = new AcegiCacheManager(userCache, resourceCache);
        source.setAcegiCacheManager(manager);

        ConfigAttributeDefinition def = source.getAttributes(mockJP);
        verify();

        assertNull(def);
    }

    public void testGetAttributes5() throws Exception {
        Object target = new String();
        Method method = target.getClass()
                              .getDeclaredMethod("split", String.class);

        JoinPoint mockJP = createMock(JoinPoint.class);
        JoinPoint.StaticPart mockSP = createMock(JoinPoint.StaticPart.class);

        CodeSignature mockCS = createMock(CodeSignature.class);
        expect(mockJP.getTarget()).andReturn(target);
        expect(mockJP.getStaticPart()).andReturn(mockSP).times(2);
        expect(mockSP.getSignature()).andReturn(mockCS).times(2);
        expect(mockCS.getName()).andReturn("toString");
        expect(mockCS.getParameterTypes()).andReturn(new Class[0]);
        replay(mockJP);
        replay(mockSP);
        replay(mockCS);

        resourceCache.removeAllResources();
        resourceDetails = new Resource("java.lang.String.to*", "METHOD",
                authorities);
        resourceCache.putResourceInCache(resourceDetails);
        manager = new AcegiCacheManager(userCache, resourceCache);
        source.setAcegiCacheManager(manager);

        ConfigAttributeDefinition def = source.getAttributes(mockJP);
        verify();

        assertNotNull(def);
    }

    public void testGetAttributes6() throws Exception {
        Object target = new String();
        Method method = target.getClass()
                              .getDeclaredMethod("split", String.class);

        JoinPoint mockJP = createMock(JoinPoint.class);
        JoinPoint.StaticPart mockSP = createMock(JoinPoint.StaticPart.class);

        CodeSignature mockCS = createMock(CodeSignature.class);
        expect(mockJP.getTarget()).andReturn(target);
        expect(mockJP.getStaticPart()).andReturn(mockSP).times(2);
        expect(mockSP.getSignature()).andReturn(mockCS).times(2);
        expect(mockCS.getName()).andReturn("toString");
        expect(mockCS.getParameterTypes()).andReturn(new Class[0]);
        replay(mockJP);
        replay(mockSP);
        replay(mockCS);

        resourceCache.removeAllResources();
        resourceDetails = new Resource("java.lang.String.*String",
                "METHOD", authorities);
        resourceCache.putResourceInCache(resourceDetails);
        manager = new AcegiCacheManager(userCache, resourceCache);
        source.setAcegiCacheManager(manager);

        ConfigAttributeDefinition def = source.getAttributes(mockJP);
        verify();

        assertNotNull(def);
    }
}
