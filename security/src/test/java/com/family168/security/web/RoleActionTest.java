package com.family168.security.web;

import com.family168.security.domain.*;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;


public class RoleActionTest
    extends AbstractTransactionalDataSourceSpringContextTests {
    private RoleAction roleAction;

    public void setRoleAction(RoleAction roleAction) {
        this.roleAction = roleAction;
    }

    @Override
    protected String[] getConfigLocations() {
        setAutowireMode(AUTOWIRE_BY_NAME);

        return new String[] {
            "classpath*:spring/*.xml", "classpath*:test/*.xml"
        };
    }

    public void testSave() throws Exception {
        //roleAction.setId(1L);
        //Role model = roleAction.getModel();
        //model.setId(null);
        //model.setName("role1");
        //model.setDescn("descn1");
        //roleAction.save();
    }
}
