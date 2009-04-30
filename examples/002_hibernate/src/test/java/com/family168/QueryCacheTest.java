package com.family168;

import java.util.*;

import com.family168.domain.*;

import org.hibernate.*;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;


public class QueryCacheTest
    extends AbstractDependencyInjectionSpringContextTests {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected String[] getConfigLocations() {
        return new String[] {
            "classpath*:spring/*.xml", "classpath*:spring/test/*.xml"
        };
    }

    public void testParent() {
        Session session = null;
        Transaction tx = null;
        Father father = new Father();
        Mother mother = new Mother();
        Long fatherId = null;
        Long motherId = null;
        List list = null;
        session = sessionFactory.openSession();
        tx = session.beginTransaction();

        father.setName("father 1");
        mother.setName("mother 1");
        mother.setFather(father);
        session.save(father);
        session.save(mother);
        fatherId = father.getId();
        motherId = mother.getId();
        tx.commit();
        session.close();

        session = sessionFactory.openSession();
        list = session.createQuery(
                "select new Map(f.name,m.name) from Father f, Mother m")
                      .setCacheable(true).list();
        assertEquals(1, list.size());
        session.close();

        session = sessionFactory.openSession();
        list = session.createQuery(
                "select new Map(f.name,m.name) from Father f, Mother m")
                      .setCacheable(true).list();
        assertEquals(1, list.size());
        session.close();

        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        father = (Father) session.get(Father.class, fatherId);
        father.setName("father 2");
        session.update(father);
        tx.commit();
        session.close();

        session = sessionFactory.openSession();
        list = session.createQuery(
                "select new Map(f.name,m.name) from Father f, Mother m")
                      .setCacheable(true).list();
        assertEquals(1, list.size());
        session.close();

        session = sessionFactory.openSession();
        list = session.createQuery(
                "select new Map(f.name,m.name) from Father f, Mother m")
                      .setCacheable(true).list();
        assertEquals(1, list.size());
        session.close();
    }
}
