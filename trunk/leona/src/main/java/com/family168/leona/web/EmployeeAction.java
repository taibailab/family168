package com.family168.leona.web;

import java.util.ArrayList;
import java.util.List;

import com.family168.core.hibernate.HibernateEntityDao;
import com.family168.core.page.Page;
import com.family168.core.struts2.BaseAction;
import com.family168.core.utils.JsonUtils;
import com.family168.core.utils.MainUtils;

import com.family168.leona.domain.*;
import com.family168.leona.domain.Employee;
import com.family168.leona.manager.EmployeeManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import org.hibernate.Criteria;

import org.hibernate.criterion.Restrictions;


public class EmployeeAction extends AbstractGridAction<Employee> {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * employee manager. */
    private transient EmployeeManager employeeManager = null;
    private Long departmentId;
    private Long jobId;
    private Long stateId;

    //
    public void setEmployeeManager(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
    }

    public HibernateEntityDao<Employee> getManager() {
        return employeeManager;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public void setStateId(Long stateId) {
        System.out.println(stateId);
        this.stateId = stateId;
    }

    //
    public void beforeSave(Employee model) {
        model.setDepartment(employeeManager.get(Department.class,
                departmentId));
        model.setJob(employeeManager.get(Job.class, jobId));
        model.setState(employeeManager.get(State.class, stateId));
    }

    public String[] getExcludes() {
        return new String[] {
            "signs", "hibernateLazyInitializer", "handler", "employees",
            "accepterBumfs", "accepterMessages", "affices", "senderBumfs",
            "senderMessages"
        };
    }
}
