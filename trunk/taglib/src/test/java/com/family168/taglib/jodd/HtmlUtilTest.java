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


public class HtmlUtilTest extends TestCase {
    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testGetTagName() {
        String result = HtmlUtil.getTagName(null, 0);
        assertNull(result);

        result = HtmlUtil.getTagName(" <tag>", 0);
        assertNull(result);

        result = HtmlUtil.getTagName("<", 0);
        assertNull(result);

        result = HtmlUtil.getTagName("<>", 0);
        assertNull(result);

        result = HtmlUtil.getTagName("< >", 0);
        assertNull(result);

        result = HtmlUtil.getTagName("<tag", 0);
        assertEquals(null, result);
    }
}
