package com.family168.security.web;

import com.family168.core.struts2.BaseAction;

import com.family168.security.domain.AclEntry;
import com.family168.security.manager.AclEntryManager;


/**
 * acl entry action.
 *
 */
public class AclEntryAction extends BaseAction<AclEntry> {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * acl entry manager. */
    private transient AclEntryManager aclEntryManager;

    /** * @param aclEntryManager acl entry manager. */
    public void setAclEntryManager(AclEntryManager aclEntryManager) {
        this.aclEntryManager = aclEntryManager;
    }
}
