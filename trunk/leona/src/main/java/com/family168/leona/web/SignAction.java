package com.family168.leona.web;

import java.util.ArrayList;
import java.util.List;

import com.family168.core.hibernate.HibernateEntityDao;
import com.family168.core.page.Page;
import com.family168.core.struts2.BaseAction;
import com.family168.core.utils.JsonUtils;
import com.family168.core.utils.MainUtils;

import com.family168.leona.domain.*;
import com.family168.leona.domain.Sign;
import com.family168.leona.manager.SignManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import org.hibernate.Criteria;

import org.hibernate.criterion.Restrictions;


public class SignAction extends AbstractGridAction<Sign> {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * employee manager. */
    private transient SignManager signManager = null;
    private Long employeeId;
    private Long signStateId;

    //
    public void setSignManager(SignManager signManager) {
        this.signManager = signManager;
    }

    public HibernateEntityDao<Sign> getManager() {
        return signManager;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setSignStateId(Long signStateId) {
        this.signStateId = signStateId;
    }

    public void beforeSave(Sign model) {
        model.setEmployee(signManager.get(Employee.class, employeeId));
        model.setSignState(signManager.get(SignState.class, signStateId));
    }

    public String[] getExcludes() {
        return new String[] {
            "signs", "hibernateLazyInitializer", "handler", "employees",
            "accepterBumfs", "accepterMessages", "senderMessages",
            "senderBumfs", "affices", "department", "job", "state"
        };
    }
}
