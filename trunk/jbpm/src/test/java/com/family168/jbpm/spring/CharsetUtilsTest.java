package com.family168.jbpm.spring;

import java.io.*;

import java.util.*;

import junit.framework.*;

import org.apache.commons.logging.*;


public class CharsetUtilsTest extends TestCase {
    static Log logger = LogFactory.getLog(CharsetUtilsTest.class);

    public void testGbk() throws Exception {
        byte[] bytes = getBytes("/gbk.txt");

        assertTrue(bytes.length > 0);
        logger.info(bytes.length);
        assertTrue(bytes[0] < 0xEF);
    }

    public void testUtf8() throws Exception {
        byte[] bytes = getBytes("/utf8.txt");
        assertTrue(bytes.length > 0);
        logger.info(bytes.length);
    }

    public void testGbk2() throws Exception {
        assertEquals("GBK",
            CharsetUtils.getCharset(this.getClass().getResource("/gbk.txt")));
    }

    public void testUtf82() throws Exception {
        assertEquals("UTF-8",
            CharsetUtils.getCharset(this.getClass().getResource("/utf8.txt")));
    }

    public void testGbk3() throws Exception {
        assertEquals("GBK",
            CharsetUtils.getCharsetFromZip(this.getClass()
                                               .getResource("/gbk.zip")));
    }

    public void testUtf83() throws Exception {
        assertEquals("UTF-8",
            CharsetUtils.getCharsetFromZip(this.getClass()
                                               .getResource("/utf8.zip")));
    }

    //
    public byte[] getBytes(String fileName) throws Exception {
        InputStream is = this.getClass().getResourceAsStream(fileName);
        byte[] bb = new byte[1024];
        int len = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        while ((len = is.read(bb, 0, 1024)) != -1) {
            baos.write(bb, 0, len);
        }

        is.close();
        baos.flush();

        return baos.toByteArray();
    }
}
