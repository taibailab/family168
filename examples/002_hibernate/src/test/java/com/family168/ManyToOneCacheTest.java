package com.family168;

import com.family168.domain.*;

import org.hibernate.*;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;


public class ManyToOneCacheTest
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
        Parent parent = new Parent();
        Long parentId = null;
        session = sessionFactory.openSession();
        tx = session.beginTransaction();

        parent.setName("parent 1");
        session.save(parent);
        parentId = parent.getId();
        tx.commit();
        session.close();

        session = sessionFactory.openSession();
        parent = (Parent) session.get(Parent.class, parentId);
        assertEquals("parent 1", parent.getName());
        session.close();

        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        parent = (Parent) session.get(Parent.class, parentId);

        parent.setName("parent 2");
        session.update(parent);
        tx.commit();
        session.close();

        session = sessionFactory.openSession();
        parent = (Parent) session.get(Parent.class, parentId);
        assertEquals("parent 2", parent.getName());
        session.close();
    }

    public void testChild() {
        Session session = null;
        Transaction tx = null;
        Parent parent = new Parent();
        Child child = new Child();
        Long parentId = null;
        Long childId = null;
        session = sessionFactory.openSession();
        tx = session.beginTransaction();

        parent.setName("parent 1");
        child.setName("child 1");
        child.setParent(parent);
        session.save(parent);
        session.save(child);
        parentId = parent.getId();
        childId = child.getId();
        tx.commit();
        session.close();

        session = sessionFactory.openSession();
        parent = (Parent) session.get(Parent.class, parentId);
        assertEquals(1, parent.getChildren().size());
        session.close();

        session = sessionFactory.openSession();
        child = (Child) session.get(Child.class, childId);
        assertEquals("child 1", child.getName());
        session.close();

        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        child = (Child) session.get(Child.class, childId);

        child.setName("child 2");
        session.update(child);
        tx.commit();
        session.close();

        session = sessionFactory.openSession();
        parent = (Parent) session.get(Parent.class, parentId);
        assertEquals("parent 1", parent.getName());
        assertEquals("child 2",
            parent.getChildren().iterator().next().getName());
        session.close();
    }
}
