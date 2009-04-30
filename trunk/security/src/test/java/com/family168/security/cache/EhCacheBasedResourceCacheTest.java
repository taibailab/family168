package com.family168.security.cache;

import java.util.*;

import com.family168.security.resource.*;

import junit.framework.TestCase;

import net.sf.ehcache.*;

import org.springframework.security.GrantedAuthority;


public class EhCacheBasedResourceCacheTest extends TestCase {
    EhCacheBasedResourceCache baseCache = null;
    Cache cache = null;

    @Override
    protected void setUp() {
        baseCache = new EhCacheBasedResourceCache();
        cache = new Cache("TestCache", 1, false, false, 0, 0);
        cache.initialise();
        baseCache.setCache(cache);
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testBase() {
        baseCache.setCache(cache);

        Cache resultCache = baseCache.getCache();
        assertEquals(cache, resultCache);
    }

    public void testGetResourceFromCache() {
        assertNull(baseCache.getResourceFromCache("unexists user"));
    }

    public void testGetResourceFromCache2() {
        ResourceDetails details = new Resource("1", "url",
                new GrantedAuthority[0]);
        Element element = new Element("user", details);
        cache.put(element);

        assertNotNull(baseCache.getResourceFromCache("user"));
        assertEquals(details, baseCache.getResourceFromCache("user"));
    }

    public void testPutResourceInCache() {
        ResourceDetails details = new Resource("1", "url",
                new GrantedAuthority[0]);
        baseCache.putResourceInCache(details);

        assertEquals(details, cache.get("1").getValue());
    }

    public void testRemoveResourceFromCache() {
        baseCache.removeResourceFromCache("1");
    }

    public void testgetAllResources() {
        List list = baseCache.getAllResources();
        assertEquals(0, list.size());
    }

    public void testRemoveAllResources() {
        baseCache.removeAllResources();
    }
}
