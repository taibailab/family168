package com.family168;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;


public class ReportService {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(final JdbcTemplate jdbcTemplateIn) {
        jdbcTemplate = jdbcTemplateIn;
    }

    public List findUsersByReportParam(final Integer status) {
        List list = new ArrayList();
        String sql = "select * from ss_users";

        if (status != null) {
            sql += ("where status=" + status);
        }

        System.out.println(sql);

        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, new Object[0]);

        while (rs.next()) {
            DynaBean userBean = new LazyDynaBean();
            userBean.set("id", rs.getString("id"));
            userBean.set("passwd", rs.getString("passwd"));
            userBean.set("name", rs.getString("name"));
            userBean.set("email", rs.getString("email"));
            userBean.set("status", rs.getString("status"));
            userBean.set("descn", rs.getString("descn"));
            list.add(userBean);
        }

        return list;
    }
}
