package com.family168.core.struts2;

import com.family168.security.domain.User;

import junit.framework.*;


public class MetaActionTest extends TestCase {
    protected MetaAction action = new UserAction();

    public void testDefault() {
        assertNotNull(action);
    }

    public void testPagedQuery() throws Exception {
        try {
            action.setStart(0);
            action.setLimit(15);
            action.setMeta(true);
            action.pagedQuery();
        } catch (Exception ex) {
        }
    }

    public void testPagedQuery2() throws Exception {
        try {
            action.setStart(0);
            action.setLimit(15);
            action.setMeta(false);
            action.pagedQuery();
        } catch (Exception ex) {
        }
    }

    public void testConfigMeta() {
        action.configMeta(null);
    }

    class UserAction extends MetaAction<User> {
        public UserAction() {
            //entityDao = new UserDao();
        }
    }
}
