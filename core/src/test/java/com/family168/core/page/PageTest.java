package com.family168.core.page;

import junit.framework.*;


public class PageTest extends TestCase {
    Page page = new Page();

    protected void setUp() {
        page = new Page();
    }

    protected void tearDown() {
        page = null;
    }

    public void testContructor() {
        page = new Page(1, 15);
        assertNotNull(page);
    }

    public void testContructor2() {
        page = new Page(1, 15, "id", "DESC");
        assertNotNull(page);
    }

    public void testContructor3() {
        try {
            page = new Page(1, 15, "id", null);
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
    }

    public void testPageNo() {
        page.setPageNo(1);
        assertEquals(1, page.getPageNo());
    }

    public void testPageSize() {
        page.setPageSize(15);
        assertEquals(15, page.getPageSize());
    }

    public void testOrderBy() {
        page.setOrderBy(null);
        assertNull(page.getOrderBy());
    }

    public void testOrder() {
        page.setOrder("DESC");
        assertEquals("DESC", page.getOrder());
        assertFalse(page.isAsc());
    }

    public void testOrder2() {
        page.setOrder("ASC");
        assertEquals("ASC", page.getOrder());
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

    public void testInverseOrder() {
        page.setOrder("DESC");
        assertEquals("ASC", page.getInverseOrder());
        page.setOrder("ASC");
        assertEquals("DESC", page.getInverseOrder());
    }

    public void testAutoCount() {
        page.setAutoCount(true);
        assertTrue(page.isAutoCount());
    }

    public void testStart() {
        assertEquals(0, page.getStart());
    }

    public void testPageSizeEnabled() {
        assertFalse(page.isPageSizeEnabled());
        page.setPageSize(15);
        assertTrue(page.isPageSizeEnabled());
    }

    public void testStartEnabled() {
        page.setPageNo(0);
        assertFalse(page.isStartEnabled());
        page.setPageNo(1);
        page.setPageSize(15);
        assertTrue(page.isStartEnabled());
    }

    public void testOrderEnabled() {
        assertFalse(page.isOrderEnabled());
        page.setOrderBy(" ");
        assertFalse(page.isOrderEnabled());
        page.setOrderBy("name");
        assertTrue(page.isOrderEnabled());
    }

    public void testCalculateStart() {
        page.setPageNo(0);
        page.setPageSize(0);
        assertFalse(page.isStartEnabled());
    }

    public void testPrevious() {
        assertFalse(page.isPreviousEnabled());
        page.setPageNo(2);
        assertTrue(page.isPreviousEnabled());
    }

    public void testNext() {
        assertFalse(page.isNextEnabled());
        page.setPageSize(10);
        page.setTotalCount(100);
        assertTrue(page.isNextEnabled());
    }

    public void testPageCount() {
        assertFalse(page.isPageCountEnabled());
        assertEquals(-1, page.getPageCount());
        page.setPageSize(15);
        page.setTotalCount(100);
        assertTrue(page.isPageCountEnabled());
        assertEquals(7, page.getPageCount());
    }
}
