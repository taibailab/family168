package com.family168.security.jcaptcha.engine;

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


public class SpringSideCaptchaEngineExTest extends TestCase {
    SpringSideCaptchaEngineEx engine = null;

    @Override
    protected void setUp() {
        engine = new SpringSideCaptchaEngineEx();
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
        engine.buildInitialFactories();
    }
}
