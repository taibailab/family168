package com.family168.security;

import java.util.*;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;

import org.springframework.security.providers.dao.UserCache;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;


public class InCacheDaoImplTest extends TestCase {
    InCacheDaoImpl dao = null;
    UserCache mockCache = null;
    UserDetails mockDetails = null;

    @Override
    protected void setUp() {
        dao = new InCacheDaoImpl();
        mockCache = createMock(UserCache.class);
        mockDetails = createMock(UserDetails.class);
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testLoadUserByUsername() {
        expect(mockCache.getUserFromCache("user")).andReturn(mockDetails);
        replay(mockCache);

        dao.setUserCache(mockCache);

        UserDetails ud = dao.loadUserByUsername("user");
        verify();

        assertEquals(mockDetails, ud);
    }
}
