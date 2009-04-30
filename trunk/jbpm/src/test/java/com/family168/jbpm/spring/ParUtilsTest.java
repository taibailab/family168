package com.family168.jbpm.spring;

import junit.framework.*;


public class ParUtilsTest extends TestCase {
    public void testConstructor() {
        ParUtils parUtils = new ParUtils();
    }

    public void testParseParGbk() throws Exception {
        assertNotNull(ParUtils.parsePar(this.getClass()
                                            .getResource("/gbk.zip")));
    }

    public void testParseParUtf8() throws Exception {
        assertNotNull(ParUtils.parsePar(this.getClass()
                                            .getResource("/utf8.zip")));
    }
}
