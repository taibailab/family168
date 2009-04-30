package com.family168.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;


@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Indexed(index = "/text_demo_user")
public class User {
    private Long id;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @DocumentId
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    @Field(name = "user_name", index = Index.TOKENIZED)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
