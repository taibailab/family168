package com.family168.core.tree;

import java.util.*;

import junit.framework.*;


public class LongTreeNodeTest extends TestCase {
    protected LongTreeNode node;

    protected void setUp() {
        node = new LongTreeNode();
    }

    protected void tearDown() {
        node = null;
    }

    public void testId() {
        node.setId(1L);
        assertEquals(Long.valueOf(1L), node.getId());
    }

    public void testName() {
        node.setName("name");

        assertEquals("name", node.getName());
    }

    public void testTheSort() {
        node.setTheSort(1);
        assertEquals(Integer.valueOf(1), node.getTheSort());
    }

    public void testParent() {
        LongTreeNode parent = new LongTreeNode();
        node.setParent(parent);
        assertEquals(parent, node.getParent());
    }

    public void testChildren() {
        Set<LongTreeNode> children = new HashSet<LongTreeNode>();
        node.setChildren(children);
        assertEquals(children, node.getChildren());
    }

    public void testChildren0() {
        node.setChildren(null);
        assertNotNull(node.getChildren());
    }

    public void testQtip() {
        node.setQtip("qtip");
        assertEquals("qtip", node.getQtip());
    }

    public void testText() {
        node.setName("text");
        assertEquals("text", node.getText());
    }

    public void testIconCls() {
        node.setIconCls("icon cls");
        assertEquals("icon cls", node.getIconCls());
    }

    public void testDraggable() {
        node.setDraggable(true);
        assertTrue(node.getDraggable());
    }

    public void testAllowEdit() {
        node.setAllowEdit(true);
        assertTrue(node.getAllowEdit());
    }

    public void testAllowDelete() {
        node.setAllowDelete(true);
        assertTrue(node.getAllowDelete());
    }

    public void testAllowChildren() {
        node.setAllowChildren(true);
        assertTrue(node.getAllowChildren());
    }

    public void testParentId() {
        node.setParentId(1L);
        assertEquals(Long.valueOf(1L), node.getParentId());
    }

    public void testRoot() {
        assertTrue(node.isRoot());
    }

    public void testRootFalse() {
        node.setParent(new LongTreeNode());
        assertFalse(node.isRoot());
    }

    public void testIsLeaf() {
        assertTrue(node.isLeaf());
    }

    public void testIsLeafFalse() {
        node.getChildren().add(new LongTreeNode());
        assertFalse(node.isLeaf());
    }

    public void testIsDeadLock() {
        assertFalse(node.isDeadLock(new LongTreeNode()));
    }
}
