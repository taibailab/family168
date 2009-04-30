package com.family168.leona.domain;


// Generated by Hibernate Tools
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * Employee .
 *
 * @author Lingo
 */
@Entity
@Table(name = "EMPLOYEE")
public class Employee implements java.io.Serializable {
    /** null. */
    private Long id;

    /** null. */
    private Job job;

    /** null. */
    private Department department;

    /** null. */
    private State state;

    /** null. */
    private String name;

    /** null. */
    private Integer sex;

    /** null. */
    private Date birthday;

    /** null. */
    private String learn;

    /** null. */
    private String post;

    /** null. */
    private String tel;

    /** null. */
    private String addr;

    /** . */
    private Set<Message> accepterMessages = new HashSet<Message>(0);

    /** . */
    private Set<Bumf> senderBumfs = new HashSet<Bumf>(0);

    /** . */
    private Set<Message> senderMessages = new HashSet<Message>(0);

    /** . */
    private Set<Bumf> accepterBumfs = new HashSet<Bumf>(0);

    /** . */
    private Set<Affice> affices = new HashSet<Affice>(0);

    /** . */
    private Set<Sign> signs = new HashSet<Sign>(0);

    public Employee() {
    }

    public Employee(Job job, Department department, State state,
        String name, Integer sex, Date birthday, String learn,
        String post, String tel, String addr,
        Set<Message> accepterMessages, Set<Bumf> senderBumfs,
        Set<Message> senderMessages, Set<Bumf> accepterBumfs,
        Set<Affice> affices, Set<Sign> signs) {
        this.job = job;
        this.department = department;
        this.state = state;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.learn = learn;
        this.post = post;
        this.tel = tel;
        this.addr = addr;
        this.accepterMessages = accepterMessages;
        this.senderBumfs = senderBumfs;
        this.senderMessages = senderMessages;
        this.accepterBumfs = accepterBumfs;
        this.affices = affices;
        this.signs = signs;
    }

    /** @return null. */
    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    /** @param id null. */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return null. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JOB_ID")
    public Job getJob() {
        return this.job;
    }

    /** @param job null. */
    public void setJob(Job job) {
        this.job = job;
    }

    /** @return null. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT_ID")
    public Department getDepartment() {
        return this.department;
    }

    /** @param department null. */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /** @return null. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATE_ID")
    public State getState() {
        return this.state;
    }

    /** @param state null. */
    public void setState(State state) {
        this.state = state;
    }

    /** @return null. */
    @Column(name = "NAME", length = 20)
    public String getName() {
        return this.name;
    }

    /** @param name null. */
    public void setName(String name) {
        this.name = name;
    }

    /** @return null. */
    @Column(name = "SEX")
    public Integer getSex() {
        return this.sex;
    }

    /** @param sex null. */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /** @return null. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BIRTHDAY", length = 6)
    public Date getBirthday() {
        return this.birthday;
    }

    /** @param birthday null. */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /** @return null. */
    @Column(name = "LEARN", length = 10)
    public String getLearn() {
        return this.learn;
    }

    /** @param learn null. */
    public void setLearn(String learn) {
        this.learn = learn;
    }

    /** @return null. */
    @Column(name = "POST", length = 10)
    public String getPost() {
        return this.post;
    }

    /** @param post null. */
    public void setPost(String post) {
        this.post = post;
    }

    /** @return null. */
    @Column(name = "TEL", length = 20)
    public String getTel() {
        return this.tel;
    }

    /** @param tel null. */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /** @return null. */
    @Column(name = "ADDR", length = 200)
    public String getAddr() {
        return this.addr;
    }

    /** @param addr null. */
    public void setAddr(String addr) {
        this.addr = addr;
    }

    /** @return . */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "accepter")
    public Set<Message> getAccepterMessages() {
        return this.accepterMessages;
    }

    /** @param accepterMessages . */
    public void setAccepterMessages(Set<Message> accepterMessages) {
        this.accepterMessages = accepterMessages;
    }

    /** @return . */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sender")
    public Set<Bumf> getSenderBumfs() {
        return this.senderBumfs;
    }

    /** @param senderBumfs . */
    public void setSenderBumfs(Set<Bumf> senderBumfs) {
        this.senderBumfs = senderBumfs;
    }

    /** @return . */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sender")
    public Set<Message> getSenderMessages() {
        return this.senderMessages;
    }

    /** @param senderMessages . */
    public void setSenderMessages(Set<Message> senderMessages) {
        this.senderMessages = senderMessages;
    }

    /** @return . */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "accepter")
    public Set<Bumf> getAccepterBumfs() {
        return this.accepterBumfs;
    }

    /** @param accepterBumfs . */
    public void setAccepterBumfs(Set<Bumf> accepterBumfs) {
        this.accepterBumfs = accepterBumfs;
    }

    /** @return . */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "employee")
    public Set<Affice> getAffices() {
        return this.affices;
    }

    /** @param affices . */
    public void setAffices(Set<Affice> affices) {
        this.affices = affices;
    }

    /** @return . */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "employee")
    public Set<Sign> getSigns() {
        return this.signs;
    }

    /** @param signs . */
    public void setSigns(Set<Sign> signs) {
        this.signs = signs;
    }
}
