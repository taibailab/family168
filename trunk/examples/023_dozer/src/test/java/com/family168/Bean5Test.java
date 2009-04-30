package com.family168;

import junit.framework.*;

import net.sf.dozer.util.mapping.DozerBeanMapper;


public class Bean5Test extends TestCase {
    public void testBean5() {
        Bean5 bean5 = new Bean5();
        Bean6 bean6 = new Bean6();
        bean5.setBean6(bean6);
        bean6.setBean5(bean5);

        DozerBeanMapper mapper = new DozerBeanMapper();
        Bean5 bean = (Bean5) mapper.map(bean5, Bean5.class);
        assertTrue(bean5 != bean);
        assertTrue(bean6 != bean.getBean6());
        assertTrue(bean == bean.getBean6().getBean5());
    }
}
