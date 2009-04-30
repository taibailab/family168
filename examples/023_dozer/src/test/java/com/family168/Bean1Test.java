package com.family168;

import java.util.*;

import junit.framework.*;

import net.sf.dozer.util.mapping.DozerBeanMapper;
import net.sf.dozer.util.mapping.MapperIF;


public class Bean1Test extends TestCase {
    public void test1() {
        Bean1 bean1 = new Bean1();
        bean1.setId(100L);
        bean1.setName("bean1");

        Bean1 bean2 = new Bean1();
        MapperIF mapper = new DozerBeanMapper();
        mapper.map(bean1, bean2);
        assertEquals(Long.valueOf(100L), bean1.getId());
        assertEquals("bean1", bean1.getName());
    }

    public void test2() {
        Bean1 bean1 = new Bean1();
        bean1.setId(100L);
        bean1.setName("bean1");

        DozerBeanMapper mapper = new DozerBeanMapper();
        List mappingFiles = new ArrayList();
        mappingFiles.add("dozerBeanMapping.xml");
        mapper.setMappingFiles(mappingFiles);

        Bean2 bean2 = (Bean2) mapper.map(bean1, Bean2.class);
        assertEquals(Long.valueOf(100L), bean2.getId2());
        assertEquals("bean1", bean2.getName2());
    }

    public void test3() {
        Bean1 bean1 = new Bean1();
        bean1.setId(100L);
        bean1.setName("bean1");

        Bean3 bean3 = new Bean3();
        bean3.setBean1(bean1);

        //
        DozerBeanMapper mapper = new DozerBeanMapper();
        Bean3 bean = (Bean3) mapper.map(bean3, Bean3.class);
        assertTrue(bean1 != bean.getBean1());
        assertEquals(bean1.getId(), bean.getBean1().getId());
        assertEquals(bean1.getName(), bean.getBean1().getName());
    }

    public void test4() {
        Bean1 bean1 = new Bean1();
        bean1.setId(100L);
        bean1.setName("bean1");

        Bean4 bean4 = new Bean4();
        bean4.getBeans().add(bean1);

        //
        DozerBeanMapper mapper = new DozerBeanMapper();
        Bean4 bean = (Bean4) mapper.map(bean4, Bean4.class);
        assertTrue(bean4.getBeans() != bean.getBeans());
        assertTrue(bean1 != bean.getBeans().iterator().next());
        assertEquals(bean1.getId(),
            bean.getBeans().iterator().next().getId());
        assertEquals(bean1.getName(),
            bean.getBeans().iterator().next().getName());
    }
}
