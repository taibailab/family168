package com.family168.security.domain;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;


/**
 * acl object identity.
 *
 *
 */
@Entity
@Table(name = "ACL_OBJECT_IDENTITY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AclObjectIdentity implements Serializable {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * id. */
    private Long id;

    /** * object id identity. */
    private Long objectIdIdentity;

    /** * entries inheriting. */
    private Integer entriesInheriting;

    /** * object id class. */
    private AclClass objectIdClass;

    /** * parent object. */
    private AclObjectIdentity parentObject;

    /** * owner sid. */
    private AclSid ownerSid;

    /** * children. */
    private Set<AclObjectIdentity> children = new HashSet<AclObjectIdentity>(0);

    /** * entries. */
    private Set<AclEntry> entries = new HashSet<AclEntry>(0);

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

    /** * @return object id identity. */
    @Column(name = "OBJECT_ID_IDENTITY")
    public Long getObjectIdIdentity() {
        return objectIdIdentity;
    }

    /** * @param objectIdIdentity Long. */
    public void setObjectIdIdentity(Long objectIdIdentity) {
        this.objectIdIdentity = objectIdIdentity;
    }

    /** * @return entries inheriting. */
    @Column(name = "entries_inheriting")
    public Integer getEntriesInheriting() {
        return entriesInheriting;
    }

    /** * @param entriesInheriting Integer. */
    public void setEntriesInheriting(Integer entriesInheriting) {
        this.entriesInheriting = entriesInheriting;
    }

    /** * @return acl class. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OBJECT_ID_CLASS")
    public AclClass getObjectIdClass() {
        return objectIdClass;
    }

    /** * @param objectIdClass AclClass. */
    public void setObjectIdClass(AclClass objectIdClass) {
        this.objectIdClass = objectIdClass;
    }

    /** * @return acl object identity. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_OBJECT")
    public AclObjectIdentity getParentObject() {
        return parentObject;
    }

    /** * @param parentObject AclobjectIdentity. */
    public void setParentObject(AclObjectIdentity parentObject) {
        this.parentObject = parentObject;
    }

    /** * @return owner sid. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER_SID")
    public AclSid getOwnerSid() {
        return ownerSid;
    }

    /** * @param ownerSid AclSid. */
    public void setOwnerSid(AclSid ownerSid) {
        this.ownerSid = ownerSid;
    }

    /** * @return children. */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentObject")
    public Set<AclObjectIdentity> getChildren() {
        return children;
    }

    /** * @param children Set. */
    public void setChildren(Set<AclObjectIdentity> children) {
        this.children = children;
    }

    /** * @return entries. */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "aclObjectIdentity")
    public Set<AclEntry> getEntries() {
        return entries;
    }

    /** * @param entries Set. */
    public void setEntries(Set<AclEntry> entries) {
        this.entries = entries;
    }
}
