package com.family168.security.jcaptcha;

import java.awt.image.BufferedImage;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.family168.security.resource.*;

import com.octo.captcha.service.image.ImageCaptchaService;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
import junit.framework.TestCase;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;

import org.springframework.web.context.WebApplicationContext;


public class ImageCaptchaServletTest extends TestCase {
    ImageCaptchaServlet servlet = null;

    @Override
    protected void setUp() {
        servlet = new ImageCaptchaServlet();
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testInit() throws Exception {
        ServletConfig mockConfig = createMock(ServletConfig.class);

        expect(mockConfig.getInitParameter("captchaServiceName"))
            .andReturn("");
        replay(mockConfig);

        servlet.init(mockConfig);
        verify();
    }

    public void testInit2() throws Exception {
        ServletConfig mockConfig = createMock(ServletConfig.class);

        expect(mockConfig.getInitParameter("captchaServiceName"))
            .andReturn("captchaService").times(2);
        replay(mockConfig);

        servlet.init(mockConfig);
        verify();
    }

    public void testDoGet() throws Exception {
        ServletConfig mockConfig = createMock(ServletConfig.class);
        ServletContext mockContext = createMock(ServletContext.class);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        WebApplicationContext wac = createMock(WebApplicationContext.class);
        HttpSession mockSession = createMock(HttpSession.class);
        ImageCaptchaService mockImageCaptchaService = createMock(ImageCaptchaService.class);

        BufferedImage bi = new BufferedImage(100, 100,
                BufferedImage.TYPE_INT_RGB);
        TestServletOutputStream tsos = new TestServletOutputStream();

        expect(mockConfig.getServletContext()).andReturn(mockContext)
            .anyTimes();
        expect(mockConfig.getInitParameter("captchaServiceName"))
            .andReturn("captchaService").anyTimes();
        expect(mockContext.getAttribute(
                "org.springframework.web.context.WebApplicationContext.ROOT"))
            .andReturn(wac);
        expect(request.getSession()).andReturn(mockSession);
        expect(wac.getBean("captchaService"))
            .andReturn(mockImageCaptchaService);
        expect(mockSession.getId()).andReturn("mock session id");
        expect(request.getLocale()).andReturn(Locale.CHINA);
        expect(mockImageCaptchaService.getImageChallengeForID(
                "mock session id", Locale.CHINA)).andReturn(bi);
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        expect(response.getOutputStream()).andReturn(tsos);

        replay(mockConfig);
        replay(mockContext);
        replay(request);
        replay(response);
        replay(wac);
        replay(mockSession);
        replay(mockImageCaptchaService);

        servlet.init(mockConfig);
        servlet.doGet(request, response);
        verify();
    }

    class TestServletOutputStream extends ServletOutputStream {
        public void write(int b) {
        }
    }
}
