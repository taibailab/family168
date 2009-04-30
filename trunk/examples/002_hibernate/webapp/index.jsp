<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.family168.manager.CategoryManager"%>
<%@page import="org.springframework.jdbc.core.JdbcTemplate"%>
<%@page import="org.springframework.jdbc.support.rowset.SqlRowSet"%>
<%
    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(application);
    CategoryManager categoryManager = (CategoryManager) ctx.getBean("categoryManager");
    out.println(categoryManager.query());
    out.println(categoryManager.findIn());

    JdbcTemplate jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
    SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from Category where id=?", new Object[]{1L});
    out.println(rs);
%>
