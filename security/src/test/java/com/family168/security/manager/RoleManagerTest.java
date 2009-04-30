package com.family168.security.manager;

import com.family168.security.domain.*;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;


public class RoleManagerTest
    extends AbstractTransactionalDataSourceSpringContextTests {
    private RoleManager roleManager;

    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    @Override
    protected String[] getConfigLocations() {
        setAutowireMode(AUTOWIRE_BY_NAME);

        return new String[] {
            "classpath*:spring/*.xml", "classpath*:test/*.xml"
        };
    }

    public void testEdit() throws Exception {
        //
        Role role = new Role();
        role.setName("role1");
        roleManager.save(role);

        //
        Menu menu = new Menu();
        roleManager.save(menu);
        //
        role.getMenus().add(menu);
        roleManager.save(role);
        roleManager.flush();
        setComplete();

        role = roleManager.get(role.getId());
        //
        role.setName("role2");
        roleManager.save(role);

        setComplete();

        //
        //roleManager.remove(role);
        //roleManager.flush();
    }
}
