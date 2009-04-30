package com.family168.security.web;

import com.family168.core.struts2.BaseAction;

import com.family168.security.domain.AclObjectIdentity;
import com.family168.security.manager.AclObjectIdentityManager;


/**
 * acl object identity action.
 *
 */
public class AclObjectIdentityAction extends BaseAction<AclObjectIdentity> {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * acl object identity manager. */
    private transient AclObjectIdentityManager aclObjectIdentityManager;

    /** * @param aclObjectIdentityManager AclObjectIdentityManager. */
    public void setAclObjectIdentityManager(
        AclObjectIdentityManager aclObjectIdentityManager) {
        this.aclObjectIdentityManager = aclObjectIdentityManager;
    }
}
