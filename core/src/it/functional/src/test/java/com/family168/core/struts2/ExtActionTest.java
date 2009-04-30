package com.family168.core.struts2;

import com.family168.security.domain.User;

import junit.framework.*;


public class ExtActionTest extends TestCase {
    protected ExtAction action = new UserAction();

    public void testDefault() {
        assertNotNull(action);
    }

    public void testPagedQuery() throws Exception {
        try {
            action.setStart(0);
            action.setLimit(15);
            action.setSort(null);
            action.setDir(null);
            action.setFilterTxt(null);
            action.setFilterValue(null);
            action.pagedQuery();
        } catch (Exception ex) {
        }
    }

    public void testSave() throws Exception {
        try {
            action.save();
        } catch (Exception ex) {
        }
    }

    public void testLoadData() throws Exception {
        try {
            action.setId(1L);
            action.loadData();
        } catch (Exception ex) {
        }
    }

    public void testRemove() throws Exception {
        try {
            action.setIds("1,2");
            action.remove();
        } catch (Exception ex) {
        }
    }

    public void testValidate() throws Exception {
        action.beforeSave(null);
        action.validate();
    }

    public void testEtc() throws Exception {
        action.setRemovedIds(null);
        action.setData(null);
    }

    class UserAction extends ExtAction<User> {
        public UserAction() {
            //entityDao = new UserDao();
        }
    }
}
