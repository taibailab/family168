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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;


/**
 * Resource generated by Lingo.
 *
 * @author Lingo
 * @since 2007年08月18日 下午 20时18分45秒0
 */
@Entity
@Table(name = "RESC")
public class Resc implements Serializable {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * id. */
    private Long id;

    /** * name. */
    private String name;

    /** * resType. */
    private String resType;

    /** * resString. */
    private String resString;

    /** * descn. */
    private String descn;

    /** * roles. */
    private Set<Role> roles = new HashSet<Role>(0);

    // ----------------------------------------------------
    // transient fields
    // ----------------------------------------------------
    /** * 是否授权. */
    private Boolean authorized;

    // ----------------------------------------------------
    // constructors
    // ----------------------------------------------------
    /** * empty contructor. */
    public Resc() {
    }

    /**
     * full contructor.
     * @param name String
     * @param resType String
     * @param resString String
     * @param descn String
     */
    public Resc(String name, String resType, String resString, String descn) {
        this.name = name;
        this.resType = resType;
        this.resString = resString;
        this.descn = descn;
    }

    /** * @return id. */
    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    /** * @param id id. */
    public void setId(Long id) {
        this.id = id;
    }

    /** * @return name. */
    @Column(name = "NAME", length = 50)
    public String getName() {
        return name;
    }

    /** * @param name name. */
    public void setName(String name) {
        this.name = name;
    }

    /** * @return resType. */
    @Column(name = "RES_TYPE", length = 50)
    public String getResType() {
        return resType;
    }

    /** * @param resType resType. */
    public void setResType(String resType) {
        this.resType = resType;
    }

    /** * @return resString. */
    @Column(name = "RES_STRING", length = 200)
    public String getResString() {
        return resString;
    }

    /** * @param resString resString. */
    public void setResString(String resString) {
        this.resString = resString;
    }

    /** * @return descn. */
    @Column(name = "DESCN", length = 200)
    public String getDescn() {
        return descn;
    }

    /** * @param descn descn. */
    public void setDescn(String descn) {
        this.descn = descn;
    }

    /** * @return roles. */
    @ManyToMany(cascade =  {
        CascadeType.PERSIST, CascadeType.MERGE}
    , fetch = FetchType.LAZY)
    @JoinTable(name = "RESC_ROLE", joinColumns =  {
        @JoinColumn(name = "RESC_ID")
    }
    , inverseJoinColumns =  {
        @JoinColumn(name = "ROLE_ID")
    }
    )
    public Set<Role> getRoles() {
        return roles;
    }

    /** * @param roles roles. */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    // ----------------------------------------------------
    // transient methods
    // ----------------------------------------------------
    /** * @return is authorized. */
    @Transient
    public Boolean getAuthorized() {
        return authorized;
    }

    /** * @param authorized is authorized. */
    public void setAuthorized(Boolean authorized) {
        this.authorized = authorized;
    }
}
