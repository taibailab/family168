package com.family168.taglib.jodd;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.BodyContent;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;

import org.springframework.dao.DataIntegrityViolationException;


public class HtmlEncoderTest extends TestCase {
    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testEncode() {
        String result = HtmlEncoder.encode("汉字");
        assertNotNull(result);
        assertEquals("&#27721;&#23383;", result);
    }

    public void testEncodeTextStrict() {
        String result = HtmlEncoder.encodeTextStrict(null);
        assertEquals("", result);

        result = HtmlEncoder.encodeTextStrict("");
        assertEquals("", result);

        result = HtmlEncoder.encodeTextStrict("test\r\n ");
        assertEquals("test<br>&nbsp;", result);

        result = HtmlEncoder.encodeTextStrict("汉字");
        assertEquals("&#27721;&#23383;", result);
    }

    public void testEncodeText() {
        String result = HtmlEncoder.encodeText(null);
        assertEquals("", result);

        result = HtmlEncoder.encodeText("");
        assertEquals("", result);

        result = HtmlEncoder.encodeText("test\r\n ");
        assertEquals("test<br> ", result);

        result = HtmlEncoder.encodeText("汉字");
        assertEquals("&#27721;&#23383;", result);
    }

    public void testEncodeTextSmart() {
        String result = HtmlEncoder.encodeTextSmart(null);
        assertEquals("", result);

        result = HtmlEncoder.encodeTextSmart("");
        assertEquals("", result);

        result = HtmlEncoder.encodeTextSmart("test\r\n");
        assertEquals("test<br>", result);

        result = HtmlEncoder.encodeTextSmart("    ");
        assertEquals(" &nbsp; &nbsp;", result);

        result = HtmlEncoder.encodeTextSmart("汉字");
        assertEquals("&#27721;&#23383;", result);
    }

    public void testUrl() {
        String url = "http://www.163.com/test.jsp?aaa=b&bbb=c";
        String result = HtmlEncoder.encodeUrl(url);
        assertEquals("http%3A%2F%2Fwww.163.com%2Ftest.jsp%3Faaa%3Db%26bbb%3Dc",
            result);
        assertEquals(url, HtmlEncoder.decodeUrl(result));
    }
}
