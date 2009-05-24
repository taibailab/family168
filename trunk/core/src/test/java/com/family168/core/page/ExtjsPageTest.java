package com.family168.core.page;

import junit.framework.*;


public class ExtjsPageTest extends TestCase {
    ExtjsPage page = new ExtjsPage();

    public void testContructor() {
        page = new ExtjsPage(0, 10, "name", "ASC");
        assertEquals(0, page.getStart());
    }

    public void testContructorFull() {
        page = new ExtjsPage(0, 10, "name", "ASC", "name", "test");
        assertEquals(0, page.getStart());
    }

    public void testStart() {
        page.setStart(1);
        assertEquals(1, page.getStart());
    }

    public void testPageNo0() {
        page.setStart(-1);
        assertEquals(1, page.getPageNo());
    }

    public void testPageNoForPageSize() {
        page.setLimit(0);
        page.setStart(1);
        assertEquals(1, page.getPageNo());
    }

    public void testPageNoForRight() {
        page.setLimit(10);
        page.setStart(11);
        assertEquals(2, page.getPageNo());
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

    public void testFilterTxt() {
        page.setFilterTxt("name");
        assertEquals("name", page.getFilterTxt());
    }

    public void testFilterValue() {
        page.setFilterValue("value");
        assertEquals("value", page.getFilterValue());
    }
}
