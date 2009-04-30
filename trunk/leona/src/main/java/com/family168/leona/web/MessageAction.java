package com.family168.leona.web;

import java.util.ArrayList;
import java.util.List;

import com.family168.core.hibernate.HibernateEntityDao;
import com.family168.core.page.Page;
import com.family168.core.struts2.BaseAction;
import com.family168.core.utils.JsonUtils;
import com.family168.core.utils.MainUtils;

import com.family168.leona.domain.*;
import com.family168.leona.domain.Message;
import com.family168.leona.manager.MessageManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import org.hibernate.Criteria;

import org.hibernate.criterion.Restrictions;


public class MessageAction extends AbstractGridAction<Message> {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * employee manager. */
    private transient MessageManager messageManager = null;
    private Long senderId;
    private Long accepterId;

    //
    public void setMessageManager(MessageManager messageManager) {
        this.messageManager = messageManager;
    }

    public HibernateEntityDao<Message> getManager() {
        return messageManager;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setAccepterId(Long accepterId) {
        this.accepterId = accepterId;
    }

    public void beforeSave(Message model) {
        model.setSender(messageManager.get(Employee.class, senderId));
        model.setAccepter(messageManager.get(Employee.class, accepterId));
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
