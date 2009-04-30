package com.family168.security.cache;

import java.util.*;

import com.family168.security.resource.Resource;
import com.family168.security.resource.ResourceDetails;

import junit.framework.TestCase;

import net.sf.ehcache.Cache;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.providers.dao.UserCache;
import org.springframework.security.providers.dao.cache.EhCacheBasedUserCache;
import org.springframework.security.userdetails.User;
import org.springframework.security.userdetails.UserDetails;


public class AcegiCacheManagerTest extends TestCase {
    AcegiCacheManager manager = null;
    UserCache mockUserCache = null;
    ResourceCache mockResourceCache = null;
    UserDetails mockUserDetails = null;
    ResourceDetails mockResourceDetails = null;
    List<String> resourceList = null;

    @Override
    protected void setUp() {
        mockUserCache = createMock(UserCache.class);
        mockResourceCache = createMock(ResourceCache.class);
        mockUserDetails = createMock(UserDetails.class);
        mockResourceDetails = createMock(ResourceDetails.class);

        resourceList = new ArrayList<String>();
        resourceList.add("1");
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testConstructor() {
        expect(mockResourceCache.getAllResources()).andReturn(resourceList);
        expect(mockResourceCache.getResourceFromCache("1"))
            .andReturn(mockResourceDetails);
        expect(mockResourceDetails.getResType()).andReturn("url").times(2);
        replay(mockResourceCache);
        replay(mockResourceDetails);

        manager = new AcegiCacheManager(mockUserCache, mockResourceCache);
        verify();
    }

    public void testGetUser() {
        expect(mockUserCache.getUserFromCache("user"))
            .andReturn(mockUserDetails);
        expect(mockResourceCache.getAllResources()).andReturn(resourceList);
        expect(mockResourceCache.getResourceFromCache("1"))
            .andReturn(mockResourceDetails);
        expect(mockResourceDetails.getResType()).andReturn("url").times(2);
        replay(mockUserCache);
        replay(mockResourceCache);
        replay(mockResourceDetails);

        manager = new AcegiCacheManager(mockUserCache, mockResourceCache);

        UserDetails ud = manager.getUser("user");
        verify();

        assertEquals(mockUserDetails, ud);
    }

    public void testGetResourceFromCache() {
        expect(mockUserCache.getUserFromCache("user"))
            .andReturn(mockUserDetails);
        expect(mockResourceCache.getAllResources()).andReturn(resourceList);
        expect(mockResourceCache.getResourceFromCache("1"))
            .andReturn(mockResourceDetails).times(2);
        expect(mockResourceDetails.getResType()).andReturn("url").times(2);
        replay(mockUserCache);
        replay(mockResourceCache);
        replay(mockResourceDetails);

        manager = new AcegiCacheManager(mockUserCache, mockResourceCache);

        ResourceDetails rd = manager.getResourceFromCache("1");
        verify();

        assertEquals(mockResourceDetails, rd);
    }

    public void testRemoveUser() {
        expect(mockUserCache.getUserFromCache("user"))
            .andReturn(mockUserDetails);
        mockUserCache.removeUserFromCache("user");
        expect(mockResourceCache.getAllResources()).andReturn(resourceList);
        expect(mockResourceCache.getResourceFromCache("1"))
            .andReturn(mockResourceDetails);
        expect(mockResourceDetails.getResType()).andReturn("url").times(2);
        replay(mockUserCache);
        replay(mockResourceCache);
        replay(mockResourceDetails);

        manager = new AcegiCacheManager(mockUserCache, mockResourceCache);

        manager.removeUser("user");
        verify();
    }

    public void testRemoveNullUsername() {
        expect(mockResourceCache.getAllResources()).andReturn(resourceList);
        expect(mockResourceCache.getResourceFromCache("1"))
            .andReturn(mockResourceDetails);
        expect(mockResourceDetails.getResType()).andReturn("url").times(2);
        replay(mockUserCache);
        replay(mockResourceCache);
        replay(mockResourceDetails);
        manager = new AcegiCacheManager(mockUserCache, mockResourceCache);

        manager.removeUser(null);
        verify();
    }

    public void testRemoveResource() {
        expect(mockUserCache.getUserFromCache("user"))
            .andReturn(mockUserDetails);
        expect(mockResourceCache.getAllResources()).andReturn(resourceList);
        expect(mockResourceCache.getResourceFromCache("1"))
            .andReturn(mockResourceDetails).times(2);
        mockResourceCache.removeResourceFromCache("1");
        expect(mockResourceDetails.getResType()).andReturn("url").times(3);
        replay(mockUserCache);
        replay(mockResourceCache);
        replay(mockResourceDetails);

        manager = new AcegiCacheManager(mockUserCache, mockResourceCache);
        manager.removeResource("1");
        verify();
    }

    public void testAddUser() {
        User user = new User("admin", "123456", true, true, true, true,
                new GrantedAuthority[0]);
        mockUserCache.putUserInCache(user);
        expect(mockResourceCache.getAllResources()).andReturn(resourceList);
        expect(mockResourceCache.getResourceFromCache("1"))
            .andReturn(mockResourceDetails).times(2);
        mockResourceCache.removeResourceFromCache("1");
        expect(mockResourceDetails.getResType()).andReturn("url").times(3);
        replay(mockUserCache);
        replay(mockResourceCache);
        replay(mockResourceDetails);

        manager = new AcegiCacheManager(mockUserCache, mockResourceCache);
        manager.addUser("admin", "123456", true, true, true, true,
            new GrantedAuthority[0]);
        verify();
    }

    public void testAddResource() {
        GrantedAuthority[] auths = new GrantedAuthority[0];
        Resource resource = new Resource("2", "url", auths);
        expect(mockResourceCache.getAllResources()).andReturn(resourceList);
        expect(mockResourceCache.getResourceFromCache("1"))
            .andReturn(mockResourceDetails).times(1);
        mockResourceCache.putResourceInCache(resource);
        expect(mockResourceDetails.getResType()).andReturn("url").times(3);
        replay(mockUserCache);
        replay(mockResourceCache);
        replay(mockResourceDetails);

        manager = new AcegiCacheManager(mockUserCache, mockResourceCache);
        manager.addResource("2", "url", auths);
        verify();
    }

    public void testRenovateUser() {
        User user = new User("admin", "123456", true, true, true, true,
                new GrantedAuthority[0]);
        mockUserCache.removeUserFromCache("user");
        mockUserCache.putUserInCache(user);
        expect(mockResourceCache.getAllResources()).andReturn(resourceList);
        expect(mockResourceCache.getResourceFromCache("1"))
            .andReturn(mockResourceDetails).times(1);
        expect(mockResourceDetails.getResType()).andReturn("url").times(3);
        replay(mockUserCache);
        replay(mockResourceCache);
        replay(mockResourceDetails);

        manager = new AcegiCacheManager(mockUserCache, mockResourceCache);
        manager.renovateUser("user", "admin", "123456", true, true, true,
            true, new GrantedAuthority[0]);
        verify();
    }

    public void testRenovateUser2() {
        User user = new User("admin", "123456", true, true, true, true,
                new GrantedAuthority[0]);
        mockUserCache.removeUserFromCache("user");
        mockUserCache.putUserInCache(user);
        expect(mockResourceCache.getAllResources()).andReturn(resourceList);
        expect(mockResourceCache.getResourceFromCache("1"))
            .andReturn(mockResourceDetails).times(1);
        expect(mockResourceDetails.getResType()).andReturn("url").times(3);
        replay(mockUserCache);
        replay(mockResourceCache);
        replay(mockResourceDetails);

        manager = new AcegiCacheManager(mockUserCache, mockResourceCache);
        manager.renovateUser("user", user);
        verify();
    }

    public void testRenovateResource() {
        GrantedAuthority[] auths = new GrantedAuthority[0];
        Resource resource = new Resource("2", "url", auths);
        expect(mockResourceCache.getAllResources()).andReturn(resourceList);
        expect(mockResourceCache.getResourceFromCache("1"))
            .andReturn(mockResourceDetails).times(2);
        expect(mockResourceDetails.getResType()).andReturn("url").times(3);
        mockResourceCache.removeResourceFromCache("1");
        mockResourceCache.putResourceInCache(resource);
        replay(mockUserCache);
        replay(mockResourceCache);
        replay(mockResourceDetails);

        manager = new AcegiCacheManager(mockUserCache, mockResourceCache);
        manager.renovateResource("1", "2", "url", auths);
        verify();
    }

    public void testRenovateResource2() {
        GrantedAuthority[] auths = new GrantedAuthority[0];
        Resource resource = new Resource("2", "url", auths);
        expect(mockResourceCache.getAllResources()).andReturn(resourceList);
        expect(mockResourceCache.getResourceFromCache("1"))
            .andReturn(mockResourceDetails).times(2);
        expect(mockResourceDetails.getResType()).andReturn("url").times(3);
        mockResourceCache.removeResourceFromCache("1");
        mockResourceCache.putResourceInCache(resource);
        replay(mockUserCache);
        replay(mockResourceCache);
        replay(mockResourceDetails);

        manager = new AcegiCacheManager(mockUserCache, mockResourceCache);
        manager.renovateResource("1", resource);
        verify();
    }

    public void testClearResource() {
        expect(mockResourceCache.getAllResources()).andReturn(resourceList);
        expect(mockResourceCache.getResourceFromCache("1"))
            .andReturn(mockResourceDetails);
        expect(mockResourceDetails.getResType()).andReturn("url").times(3);
        mockResourceCache.removeAllResources();
        replay(mockUserCache);
        replay(mockResourceCache);
        replay(mockResourceDetails);

        manager = new AcegiCacheManager(mockUserCache, mockResourceCache);
        manager.setUserCache(mockUserCache);
        manager.setResourceCache(mockResourceCache);
        manager.clearResources();
        verify();
    }

    public void testGetResourcesByType() {
        expect(mockResourceCache.getAllResources()).andReturn(resourceList);
        expect(mockResourceCache.getResourceFromCache("1"))
            .andReturn(mockResourceDetails);
        expect(mockResourceDetails.getResType()).andReturn("url").times(3);
        mockResourceCache.removeAllResources();
        replay(mockUserCache);
        replay(mockResourceCache);
        replay(mockResourceDetails);

        manager = new AcegiCacheManager(mockUserCache, mockResourceCache);

        List<String> list = manager.getResourcesByType("url");
        verify();

        assertEquals(1, list.size());
        assertEquals("1", list.get(0));
    }

    public void testGetAllResources() {
        List<String> resourceList = new ArrayList<String>();
        resourceList.add("1");

        expect(mockResourceCache.getAllResources()).andReturn(resourceList);
        expect(mockResourceCache.getResourceFromCache("1"))
            .andReturn(mockResourceDetails);
        expect(mockResourceDetails.getResType()).andReturn("url").times(3);
        expect(mockResourceCache.getAllResources()).andReturn(resourceList);
        replay(mockUserCache);
        replay(mockResourceCache);
        replay(mockResourceDetails);

        manager = new AcegiCacheManager(mockUserCache, mockResourceCache);

        List<String> list = manager.getAllResources();
        verify();

        assertEquals(1, list.size());
        assertEquals("1", list.get(0));
    }

    // 这个地方没有起到测试的效果
    public void testGetAllUsers() {
        EhCacheBasedUserCache mockEhCache = EasyMock.createMock(EhCacheBasedUserCache.class);
        Cache cache = new Cache("TestCache", 1, false, false, 0, 0);
        cache.initialise();

        List<String> userList = new ArrayList<String>();
        userList.add("user");

        EasyMock.expect(mockEhCache.getCache()).andReturn(cache);
        expect(mockResourceCache.getAllResources()).andReturn(resourceList);
        expect(mockResourceCache.getResourceFromCache("1"))
            .andReturn(mockResourceDetails);
        expect(mockResourceDetails.getResType()).andReturn("url").times(3);
        expect(mockResourceCache.getAllResources()).andReturn(resourceList);
        EasyMock.replay(mockEhCache);
        replay(mockResourceCache);
        replay(mockResourceDetails);

        manager = new AcegiCacheManager(mockEhCache, mockResourceCache);

        List<String> list = manager.getAllUsers();
        EasyMock.verify();
        verify();

        assertEquals(0, list.size());
    }
}
