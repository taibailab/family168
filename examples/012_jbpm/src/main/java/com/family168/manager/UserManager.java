package com.family168.manager;

import java.util.List;

import com.family168.domain.User;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public class UserManager extends HibernateDaoSupport {
    public List query() {
        return getSession().createQuery("from User").list();
    }
}
