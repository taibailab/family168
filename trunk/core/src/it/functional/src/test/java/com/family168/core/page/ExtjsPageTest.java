package com.family168.core.page;

import junit.framework.*;


public class ExtjsPageTest extends TestCase {
    ExtjsPage page = new ExtjsPage();

    public void testStart() {
        page.setStart(1);
        assertEquals(1, page.getStart());
    }

    public void testLimit() {
        page.setLimit(15);
        assertEquals(15, page.getLimit());
    }

    public void testSort() {
        page.setSort(null);
        assertNull(page.getSort());
    }

    public void testDir() {
        page.setDir("DESC");
        assertEquals("DESC", page.getDir());
        assertFalse(page.isAsc());
    }

    public void testDir2() {
        page.setDir("ASC");
        assertEquals("ASC", page.getDir());
        assertTrue(page.isAsc());
    }

    public void testResult() {
        page.setResult(null);
        assertNull(page.getResult());
    }

    public void testTotalCount() {
        page.setTotalCount(1);
        assertEquals(1, page.getTotalCount());
    }
}
