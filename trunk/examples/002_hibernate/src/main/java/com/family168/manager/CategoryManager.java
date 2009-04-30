package com.family168.manager;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public class CategoryManager extends HibernateDaoSupport {
    public List query() {
        return getSession().createQuery("from Category").setCacheable(true)
                   .setCacheRegion("categoryCacheRegion").list();
    }

    public List findIn() {
        return getSession().createQuery("from Category where id in (:list)")
                   .setParameterList("list", new Long[] {1L}).list();
    }
}
