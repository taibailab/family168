package com.family168.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "FATHER_ID")
)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mother {
    private Long id;
    private String name;
    private Father father;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FATHER_ID")
    public Father getFather() {
        return father;
    }

    public void setFather(Father father) {
        this.father = father;
    }
}
