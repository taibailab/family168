package com.family168.security.jcaptcha;

import java.awt.image.BufferedImage;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.family168.security.resource.*;

import com.octo.captcha.service.*;
import com.octo.captcha.service.image.ImageCaptchaService;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
import junit.framework.TestCase;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;

import org.springframework.web.context.WebApplicationContext;


public class JCaptchaServiceProxyImplTest extends TestCase {
    JCaptchaServiceProxyImpl impl = null;

    @Override
    protected void setUp() {
        impl = new JCaptchaServiceProxyImpl();
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testDefault() {
        CaptchaService service = createMock(CaptchaService.class);
        expect(service.validateResponseForID("test id", "response"))
            .andReturn(false);
        replay(service);

        impl.setJcaptchaService(service);

        boolean result = impl.validateReponseForId("test id", "response");
        verify();

        assertFalse(result);
    }

    public void testValidateTrue() {
        CaptchaService service = createMock(CaptchaService.class);
        expect(service.validateResponseForID("test id", "response"))
            .andReturn(true);
        replay(service);

        impl.setJcaptchaService(service);

        boolean result = impl.validateReponseForId("test id", "response");
        verify();

        assertTrue(result);
    }

    public void testException() {
        CaptchaService service = createMock(CaptchaService.class);
        expect(service.validateResponseForID("test id", "response"))
            .andThrow(new CaptchaServiceException());
        replay(service);

        impl.setJcaptchaService(service);

        boolean result = impl.validateReponseForId("test id", "response");
        verify();

        assertFalse(result);
    }
}
