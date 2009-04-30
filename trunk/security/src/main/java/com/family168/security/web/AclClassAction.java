package com.family168.security.web;

import com.family168.core.struts2.BaseAction;

import com.family168.security.domain.AclClass;
import com.family168.security.manager.AclClassManager;


/**
 * acl class action.
 *
 */
public class AclClassAction extends BaseAction<AclClass> {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * acl class manager. */
    private transient AclClassManager aclClassManager;

    /** * @param aclClassManager AclClassManager. */
    public void setAclClassManager(AclClassManager aclClassManager) {
        this.aclClassManager = aclClassManager;
    }
}
