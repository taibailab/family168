package com.family168.leona.web;

import java.util.ArrayList;
import java.util.List;

import com.family168.core.hibernate.HibernateEntityDao;
import com.family168.core.page.Page;
import com.family168.core.struts2.BaseAction;
import com.family168.core.utils.JsonUtils;
import com.family168.core.utils.MainUtils;

import com.family168.leona.domain.*;
import com.family168.leona.domain.Bumf;
import com.family168.leona.manager.BumfManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import org.hibernate.Criteria;

import org.hibernate.criterion.Restrictions;


public class BumfAction extends AbstractGridAction<Bumf> {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * employee manager. */
    private transient BumfManager bumfManager = null;
    private Long senderId;
    private Long accepterId;

    //
    public void setBumfManager(BumfManager bumfManager) {
        this.bumfManager = bumfManager;
    }

    public HibernateEntityDao<Bumf> getManager() {
        return bumfManager;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setAccepterId(Long accepterId) {
        this.accepterId = accepterId;
    }

    public void beforeSave(Bumf model) {
        model.setSender(bumfManager.get(Employee.class, senderId));
        model.setAccepter(bumfManager.get(Employee.class, accepterId));
    }

    public String[] getExcludes() {
        return new String[] {
            "accepterMessages", "senderMessages",
            "hibernateLazyInitializer", "handler", "affices", "department",
            "senderBumfs", "signs", "accepterBumfs", "job", "state",
            "department"
        };
    }
}
