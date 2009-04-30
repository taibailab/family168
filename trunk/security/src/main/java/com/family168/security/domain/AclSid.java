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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;


/**
 * acl sid.
 *
 *
 */
@Entity
@Table(name = "ACL_SID")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AclSid implements Serializable {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * id. */
    private Long id;

    /** * principal. */
    private Integer principal;

    /** * sid. */
    private String sid;

    /** * objectIdentites. */
    private Set<AclObjectIdentity> objectIdentities = new HashSet<AclObjectIdentity>(0);

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

    /** * @return principal. */
    @Column(name = "PRINCIPAL")
    public Integer getPrincipal() {
        return principal;
    }

    /** * @param principal Integer. */
    public void setPrincipal(Integer principal) {
        this.principal = principal;
    }

    /** * @return sid. */
    @Column(name = "SID", length = 100)
    public String getSid() {
        return sid;
    }

    /** * @param sid String. */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /** * @return object identities. */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "objectIdClass")
    public Set<AclObjectIdentity> getObjectIdentities() {
        return objectIdentities;
    }

    /** * @param objectIdentities Set. */
    public void setObjectIdentities(
        Set<AclObjectIdentity> objectIdentities) {
        this.objectIdentities = objectIdentities;
    }

    /** * @return entries. */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sid")
    public Set<AclEntry> getEntries() {
        return entries;
    }

    /** * @param entries Set. */
    public void setEntries(Set<AclEntry> entries) {
        this.entries = entries;
    }
}
