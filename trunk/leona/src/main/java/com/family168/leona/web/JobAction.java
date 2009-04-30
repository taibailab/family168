package com.family168.leona.web;

import java.util.ArrayList;
import java.util.List;

import com.family168.core.hibernate.HibernateEntityDao;
import com.family168.core.page.Page;
import com.family168.core.struts2.BaseAction;
import com.family168.core.utils.JsonUtils;
import com.family168.core.utils.MainUtils;

import com.family168.leona.domain.Job;
import com.family168.leona.manager.JobManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import org.hibernate.Criteria;

import org.hibernate.criterion.Restrictions;


public class JobAction extends AbstractGridAction<Job> {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * employee manager. */
    private transient JobManager jobManager = null;

    //
    public void setJobManager(JobManager jobManager) {
        this.jobManager = jobManager;
    }

    public HibernateEntityDao<Job> getManager() {
        return jobManager;
    }

    public String[] getExcludes() {
        return new String[] {
            "employees", "hibernateLazyInitializer", "handler"
        };
    }
}
