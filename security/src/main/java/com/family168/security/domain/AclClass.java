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
 * acl类.
 *
 *
 */
@Entity
@Table(name = "ACL_CLASS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AclClass implements Serializable {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * id. */
    private Long id;

    /** * 类名. */
    private String className;

    /** * 对象身份. */
    private Set<AclObjectIdentity> objectIdentities = new HashSet<AclObjectIdentity>(0);

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

    /** * @return className. */
    @Column(name = "SID", length = 100)
    public String getClassName() {
        return className;
    }

    /** * @param className String. */
    public void setClassName(String className) {
        this.className = className;
    }

    /** * @return objectIdentities. */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "objectIdClass")
    public Set<AclObjectIdentity> getObjectIdentities() {
        return objectIdentities;
    }

    /** * @param objectIdentities Set. */
    public void setObjectIdentities(
        Set<AclObjectIdentity> objectIdentities) {
        this.objectIdentities = objectIdentities;
    }
}
