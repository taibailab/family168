package com.family168.leona.web;

import java.util.ArrayList;
import java.util.List;

import com.family168.core.hibernate.HibernateEntityDao;
import com.family168.core.page.Page;
import com.family168.core.struts2.BaseAction;
import com.family168.core.utils.JsonUtils;
import com.family168.core.utils.MainUtils;

import com.family168.leona.domain.SignState;
import com.family168.leona.manager.SignStateManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import org.hibernate.Criteria;

import org.hibernate.criterion.Restrictions;


public class SignStateAction extends AbstractGridAction<SignState> {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * employee manager. */
    private transient SignStateManager signStateManager = null;

    //
    public void setSignStateManager(SignStateManager signStateManager) {
        this.signStateManager = signStateManager;
    }

    public HibernateEntityDao<SignState> getManager() {
        return signStateManager;
    }

    public String[] getExcludes() {
        return new String[] {"signs"};
    }
}
