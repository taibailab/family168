package com.family168.security.web;

import com.family168.core.struts2.BaseAction;

import com.family168.security.domain.AclSid;
import com.family168.security.manager.AclSidManager;


/**
 * acl sid action.
 *
 */
public class AclSidAction extends BaseAction<AclSid> {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * acl sid manager. */
    private transient AclSidManager aclSidManager;

    /** * @param aclSidManager acl sid manager. */
    public void setAclSidManager(AclSidManager aclSidManager) {
        this.aclSidManager = aclSidManager;
    }
}
