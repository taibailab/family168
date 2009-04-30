package com.family168.security.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;


/**
 * acl入口.
 *
 *
 */
@Entity
@Table(name = "ACL_ENTRY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AclEntry implements Serializable {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * id. */
    private Long id;

    /** * ace order. */
    private Integer aceOrder;

    /** * mask. */
    private Integer mask;

    /** * granting. */
    private Integer granting;

    /** * auditSuccess. */
    private Integer auditSuccess;

    /** * auditFailure. */
    private Integer auditFailure;

    /** * acl object identity. */
    private AclObjectIdentity aclObjectIdentity;

    /** * sid. */
    private AclSid sid;

    /** * @return id. */
    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    /** * @param id Long. */
    public void setId(Long id) {
        this.id = id;
    }

    /** * @return ace order. */
    @Column(name = "ACE_ORDER")
    public Integer getAceOrder() {
        return aceOrder;
    }

    /** * @param aceOrder Integer. */
    public void setAceOrder(Integer aceOrder) {
        this.aceOrder = aceOrder;
    }

    /** * @return mask. */
    @Column(name = "MASK")
    public Integer getMask() {
        return mask;
    }

    /** * @param mask Integer. */
    public void setMask(Integer mask) {
        this.mask = mask;
    }

    /** * @return granting. */
    @Column(name = "GRANTING")
    public Integer getGranting() {
        return granting;
    }

    /** * @param granting Integer. */
    public void setGranting(Integer granting) {
        this.granting = granting;
    }

    /** * @return auditSuccess. */
    @Column(name = "AUDIT_SUCCESS")
    public Integer getAuditSuccess() {
        return auditSuccess;
    }

    /** * @param auditSuccess Integer. */
    public void setAuditSuccess(Integer auditSuccess) {
        this.auditSuccess = auditSuccess;
    }

    /** * @return auditFaliure. */
    @Column(name = "AUDIT_FAILURE")
    public Integer getAuditFailure() {
        return auditFailure;
    }

    /** * @param auditFailure Integer. */
    public void setAuditFailure(Integer auditFailure) {
        this.auditFailure = auditFailure;
    }

    /** * @return acl object identity. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACL_OBJECT_IDENTITY")
    public AclObjectIdentity getAclObjectIdentity() {
        return aclObjectIdentity;
    }

    /** * @param aclObjectIdentity AclObjectIdentity. */
    public void setAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
        this.aclObjectIdentity = aclObjectIdentity;
    }

    /** * @return acl sid. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SID")
    public AclSid getSid() {
        return sid;
    }

    /** * @param sid AclSid. */
    public void setSid(AclSid sid) {
        this.sid = sid;
    }
}
