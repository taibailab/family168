package com.family168.leona.web;

import java.util.ArrayList;
import java.util.List;

import com.family168.core.hibernate.HibernateEntityDao;
import com.family168.core.page.Page;
import com.family168.core.struts2.BaseAction;
import com.family168.core.utils.JsonUtils;
import com.family168.core.utils.MainUtils;

import com.family168.leona.domain.Department;
import com.family168.leona.manager.DepartmentManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import org.hibernate.Criteria;

import org.hibernate.criterion.Restrictions;


public class DepartmentAction extends AbstractGridAction<Department> {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * employee manager. */
    private transient DepartmentManager departmentManager = null;

    //
    public void setDepartmentManager(DepartmentManager departmentManager) {
        this.departmentManager = departmentManager;
    }

    public HibernateEntityDao<Department> getManager() {
        return departmentManager;
    }

    public String[] getExcludes() {
        return new String[] {
            "employees", "hibernateLazyInitializer", "handler"
        };
    }
}
