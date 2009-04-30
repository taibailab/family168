package com.family168.core.struts2;

import com.family168.core.page.ExtjsPage;

import junit.framework.*;


public class MetaDataTest extends TestCase {
    protected MetaData data = new MetaData(new ExtjsPage());
    protected MetaData.Field f = new MetaData.Field("name", "header", 100,
            "{xtype:'textfield'}", "function(v){return v;}");

    public void testDefault() {
        assertNotNull(data);
    }

    public void testAddField() {
        data.addField("name", "value");
    }

    public void testAddField2() {
        data.addField("name", "value", 100, "{xtype:'textfield'}");
    }

    public void testAddField3() {
        data.addField("name", "value", 0, null);
    }

    public void testGetter() {
        assertNotNull(data.getMetaData());
        assertEquals(-1, data.getTotalCount());
        assertNull(data.getResult());
    }

    public void testField() {
        assertEquals("name", f.getName());
        assertEquals("header", f.getHeader());
        assertEquals(100, f.getWidth());
        assertNotNull(f.getEditor());
        assertEquals("function(v){return v;}", f.getRenderer());
    }
}
