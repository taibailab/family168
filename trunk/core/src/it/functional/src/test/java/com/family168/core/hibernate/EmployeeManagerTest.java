package com.family168.core.hibernate;

import java.util.*;

import com.family168.core.page.Page;

import org.hibernate.*;

import org.hibernate.criterion.Projections;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;


public class EmployeeManagerTest
    extends AbstractTransactionalDataSourceSpringContextTests {
    EmployeeManager dao;

    public void setEmployee2Manager(EmployeeManager dao) {
        this.dao = dao;
    }

    @Override
    protected String[] getConfigLocations() {
        setAutowireMode(AUTOWIRE_BY_NAME);

        return new String[] {"classpath*:applicationContext.xml"};
    }

    public void testDefault() {
        assertNotNull(dao);
    }

    public void testGet() {
        assertNull(dao.get(1));
    }

    public void testLoad() {
        assertNotNull(dao.load(-1));
    }

    public void testGetAll() {
        assertNotNull(dao.getAll());
        assertEquals(0, dao.getAll().size());
        assertEquals(0, dao.getAll("name", true).size());
        assertEquals(0, dao.getAll("name", false).size());
    }

    public void testRemove() {
        try {
            dao.removeById(-1);
            fail();
        } catch (Exception ex) {
            assertTrue(true);
        }
    }

    public void testRemove2() {
        Employee2 entity = new Employee2();
        dao.save(entity);
        assertNotNull(entity);
        dao.flush();
        dao.remove(entity);
    }

    public void testRemove3() {
        Employee2 entity = new Employee2();
        dao.save(entity);
        assertNotNull(entity);
        dao.flush();
        dao.removeById(entity.getId());
    }

    public void testRemoveAll() {
        dao.removeAll();
    }

    public void testCriteria() {
        Criteria criteria = dao.createCriteria();
        assertNotNull(criteria);
    }

    public void testCriteria2() {
        Criteria criteria = dao.createCriteria("name", true);
        assertNotNull(criteria);
    }

    public void testCriteria3() {
        Criteria criteria = dao.createCriteria("name", false);
        assertNotNull(criteria);
    }

    public void testFindBy() {
        List<Employee2> list = dao.findBy("name", "test");
        assertEquals(0, list.size());
    }

    //public void testFindBy2() {
    //    List<Employee2> list = dao.findBy("name", "test", "name", true);
    //    assertEquals(0, list.size());
    //}
    //public void testPagedQuery() throws Exception {
    //    Page page = dao.pagedQuery(1, 10);
    //    assertNotNull(page);
    //}
    public void testPagedQuery2() throws Exception {
        Page page = dao.pagedQuery("from Employee2", 1, 10);
        assertNotNull(page);
    }

    public void testPagedQuery3() throws Exception {
        try {
            Page page = dao.pagedQuery("from Employee2", -1, 10);
            fail();
        } catch (Exception ex) {
            assertTrue(true);
        }
    }

    public void testPagedQuery4() throws Exception {
        Employee2 entity = new Employee2();
        dao.save(entity);

        Employee2 entity2 = new Employee2();
        dao.save(entity2);
        dao.flush();

        Page page = dao.pagedQuery("from Employee2", 1, 1);
        assertNotNull(page);
    }

    public void testPagedQuery5() throws Exception {
        Page page = dao.pagedQuery("select id,name from Employee2 order by id desc",
                1, 10);
        assertNotNull(page);
    }

    public void testPagedQuery6() throws Exception {
        Page page = dao.pagedQuery(Employee2.class, 1, 10, "name", false);
        assertNotNull(page);
    }

    //public void testPagedQuery7() throws Exception {
    //    try {
    //        Page page = dao.pagedQuery(0, 10);
    //        fail();
    //    } catch (Exception ex) {
    //        assertTrue(true);
    //    }
    //}
    public void testPagedQuery8() throws Exception {
        Page page = dao.pagedQuery(Employee2.class, 1, 10);
        assertNotNull(page);
    }

    public void testPagedQuery9() throws Exception {
        try {
            Page page = dao.pagedQuery("select id", 1, 10);
            fail();
        } catch (Exception ex) {
            assertTrue(true);
        }
    }

    public void testPagedQuery10() throws Exception {
        Employee2 entity = new Employee2();
        dao.save(entity);

        Employee2 entity2 = new Employee2();
        dao.save(entity2);
        dao.flush();

        Criteria criteria = dao.createCriteria()
                               .setProjection(Projections.rowCount());
        Page page = dao.pagedQuery(criteria, 1, 1);
        assertNotNull(page);
    }

    public void testFindUniqueBy() {
        Employee2 entity = dao.findUniqueBy("name", "me");
        assertNull(entity);
    }

    //public void testIsUnique() {
    //    Employee2 entity = new Employee2();
    //    assertTrue(dao.isUnique(entity, "name"));
    //}

    //public void testIsUnique2() {
    //    Employee2 entity = new Employee2();
    //    entity.setName("user");
    //    dao.save(entity);
    //    Employee2 entity2 = new Employee2();
    //    entity2.setName("user");
    //    dao.save(entity2);
    //    dao.flush();
    //    assertFalse(dao.isUnique(entity, "name"));
    //}

    //public void testIsUnique3() {
    //    try {
    //        Employee2 entity = new Employee2();
    //        assertTrue(dao.isUnique(entity, "dd"));
    //        fail();
    //    } catch (Exception ex) {
    //        assertTrue(true);
    //    }
    //}
    public void testInitialize() {
        Employee2 entity = new Employee2();
        dao.save(entity);
        dao.flush();

        Employee2 entity2 = dao.initialize(entity.getId());
        assertEquals(entity, entity2);
    }

    public void testClear() {
        dao.clear();
    }

    public void testEvit() {
        Employee2 entity = new Employee2();
        dao.save(entity);

        //dao.evit(entity);
    }

    public void testCreateQuery() {
        Query query = dao.createQuery("from Employee2");
        assertNotNull(query);
    }

    public void testCreateQuery2() {
        Query query = dao.createQuery("from Employee2 where id=?", 1);
        assertNotNull(query);
    }

    public void testFind() {
        List<Employee2> list = dao.find("from Employee2");
        assertEquals(0, list.size());
    }

    public void testFind2() {
        List<Employee2> list = dao.find("from Employee2 where name like ?",
                "%test%");
        assertEquals(0, list.size());
    }
}
