package com.family168;

import java.util.*;

import com.family168.domain.*;

import org.hibernate.*;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;


public class IteratorCacheTest
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

    public void testIterator() {
        Session session = null;
        Transaction tx = null;
        Father father = new Father();
        Mother mother = new Mother();
        Long fatherId = null;
        Long motherId = null;
        List list = null;
        Iterator iterator = null;

        session = sessionFactory.openSession();
        tx = session.beginTransaction();

        father = new Father();
        mother = new Mother();
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
        tx = session.beginTransaction();

        father = new Father();
        mother = new Mother();
        father.setName("father 2");
        mother.setName("mother 2");
        mother.setFather(father);
        session.save(father);
        session.save(mother);
        fatherId = father.getId();
        motherId = mother.getId();
        tx.commit();
        session.close();

        session = sessionFactory.openSession();
        list = session.createQuery("from Father").list();
        assertEquals(2, list.size());
        session.close();

        session = sessionFactory.openSession();
        iterator = session.createQuery("from Father").iterate();

        for (; iterator.hasNext();) {
            System.out.println(iterator.next());
        }

        session.close();

        session = sessionFactory.openSession();
        iterator = session.createQuery("from Father").iterate();

        for (; iterator.hasNext();) {
            System.out.println(iterator.next());
        }

        session.close();
    }
}
