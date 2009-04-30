package com.family168.leona.web;

import java.util.ArrayList;
import java.util.List;

import com.family168.core.hibernate.HibernateEntityDao;
import com.family168.core.page.Page;
import com.family168.core.struts2.BaseAction;
import com.family168.core.utils.JsonUtils;
import com.family168.core.utils.MainUtils;

import com.family168.leona.domain.Affice;
import com.family168.leona.domain.Employee;
import com.family168.leona.manager.AfficeManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import org.hibernate.Criteria;

import org.hibernate.criterion.Restrictions;


/**
 * affice action.
 *
 */
public class AfficeAction extends AbstractGridAction<Affice> {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * affice manager. */
    private transient AfficeManager afficeManager = null;
    private Long employeeId;

    //
    public void setAfficeManager(AfficeManager afficeManager) {
        this.afficeManager = afficeManager;
    }

    public HibernateEntityDao<Affice> getManager() {
        return afficeManager;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void beforeSave(Affice model) {
        model.setEmployee(afficeManager.get(Employee.class, employeeId));
    }

    public String[] getExcludes() {
        return new String[] {
            "affices", "hibernateLazyInitializer", "handler", "job",
            "state", "department", "accepterBumfs", "senderBumfs",
            "accepterMessages", "senderMessages", "signs"
        };
    }
}
