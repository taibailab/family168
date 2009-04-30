package com.family168.security.service;

import java.sql.*;

import java.util.*;

import javax.sql.DataSource;

import com.family168.security.resource.*;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import org.springframework.security.providers.dao.UserCache;
import org.springframework.security.userdetails.User;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;


public class DaoAuthenticationServiceTest extends TestCase {
    DaoAuthenticationService service = null;
    String loadusersQuery = "select name,passwd,status from ss_users";
    String authoritiesByUsernameQuery = "select r.name from ss_users u,ss_roles r,ss_user_role ur where u.id = ur.user_id and r.id = ur.role_id and u.name = ?";
    String loadResourcesQuery = "select res_string, res_type from ss_resources";
    String authoritiesByResourceQuery = "select r.name from ss_resources c,ss_roles r,ss_role_resc rc where c.id = rc.resc_id and r.id = rc.role_id and c.res_string = ?";
    DataSource dataSource;
    Connection conn;
    Statement state;

    @Override
    protected void setUp() throws Exception {
        dataSource = new DriverManagerDataSource("org.hsqldb.jdbcDriver",
                "jdbc:hsqldb:.", "sa", "");

        service = new DaoAuthenticationService();
        service.setLoadUsersQuery(loadusersQuery);
        service.setAuthoritiesByUsernameQuery(authoritiesByUsernameQuery);
        service.setLoadResourcesQuery(loadResourcesQuery);
        service.setAuthoritiesByResourceQuery(authoritiesByResourceQuery);
        service.setRolePrefix("");
        service.setDataSource(dataSource);

        //
        conn = dataSource.getConnection();
        state = conn.createStatement();
        state.executeUpdate(
            "create table ss_users(id bigint,name varchar(100),passwd varchar(100),status integer)");
        state.executeUpdate(
            "create table ss_roles(id bigint,name varchar(100))");
        state.executeUpdate(
            "create table ss_resources(id bigint,res_type varchar(100),res_string varchar(100))");
    }

    @Override
    protected void tearDown() throws Exception {
        state.executeUpdate("drop schema PUBLIC cascade;");
        state.close();
        conn.close();
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testGetResources() throws Exception {
        List<Resource> resources = service.getResources();
    }

    public void testGetUsers() throws Exception {
        List<UserDetails> users = service.getUsers();
    }
}
