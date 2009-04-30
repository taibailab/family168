package com.family168;

import com.family168.domain.*;

import org.hibernate.*;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;


public class OneToOneCacheTest
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

    public void testFather() {
        Session session = null;
        Transaction tx = null;
        Father father = new Father();
        Mother mother = new Mother();
        Long fatherId = null;
        Long motherId = null;
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
        father = (Father) session.get(Father.class, fatherId);
        assertEquals("father 1", father.getName());
        mother = (Mother) session.get(Mother.class, motherId);
        assertEquals("mother 1", mother.getName());
        assertEquals("mother 1", father.getMother().getName());
        session.close();

        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        father = (Father) session.get(Father.class, fatherId);

        father.setName("father 2");
        session.update(father);
        tx.commit();
        session.close();

        session = sessionFactory.openSession();
        mother = (Mother) session.get(Mother.class, motherId);
        assertEquals("father 2", mother.getFather().getName());
        session.close();

        session = sessionFactory.openSession();
        father = (Father) session.get(Father.class, fatherId);
        assertEquals("mother 1", father.getMother().getName());
        session.close();
    }
}
