package com.family168.security.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;
import static org.easymock.classextension.EasyMock.*;

import org.springframework.security.Authentication;


public class ProtectedRememberMeServicesTest extends TestCase {
    protected ProtectedRememberMeServices service = null;
    HttpServletRequest request = null;
    HttpServletResponse response = null;
    Authentication auth = null;

    @Override
    protected void setUp() {
        service = new ProtectedRememberMeServices();
        request = createMock(HttpServletRequest.class);
        response = createMock(HttpServletResponse.class);
        auth = createMock(Authentication.class);
    }

    @Override
    protected void tearDown() {
    }

    public void testLogout() {
        expect(request.getContextPath()).andReturn("/").anyTimes();
        response.addCookie(isA(Cookie.class));

        replay(request);
        replay(response);
        replay(auth);
        service.logout(request, response, auth);
        verify();
    }
}
