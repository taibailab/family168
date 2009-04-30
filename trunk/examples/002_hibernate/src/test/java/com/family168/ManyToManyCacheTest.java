package com.family168;

import com.family168.domain.*;

import org.hibernate.*;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;


public class ManyToManyCacheTest
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
        Role role = new Role();
        Resc resc = new Resc();
        Long roleId = null;
        Long rescId = null;
        session = sessionFactory.openSession();
        tx = session.beginTransaction();

        role.setName("role 1");
        resc.setName("resc 1");
        resc.getRoles().add(role);
        session.save(role);
        session.save(resc);
        roleId = role.getId();
        rescId = resc.getId();
        tx.commit();
        session.close();

        session = sessionFactory.openSession();
        role = (Role) session.get(Role.class, roleId);
        assertEquals("role 1", role.getName());
        resc = role.getRescs().iterator().next();
        assertEquals("resc 1", resc.getName());
        session.close();

        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        resc = (Resc) session.get(Resc.class, rescId);
        resc.setName("resc 2");
        session.update(resc);
        tx.commit();
        session.close();

        session = sessionFactory.openSession();
        role = (Role) session.get(Role.class, roleId);
        assertEquals("resc 2", role.getRescs().iterator().next().getName());
        session.close();
    }
}
