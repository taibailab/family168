package com.family168;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;


public class BaseAction<T> extends ActionSupport implements ModelDriven<T>,
    Preparable {
    public static final String RELOAD = "reload";
    public static final String SHOW = "show";
    protected Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String execute() throws Exception {
        return list();
    }

    @Override
    public String input() throws Exception {
        if (id == null) {
            return create();
        } else {
            return edit();
        }
    }

    // crud
    public String list() throws Exception {
        return SUCCESS;
    }

    public String create() throws Exception {
        return INPUT;
    }

    public String edit() throws Exception {
        return INPUT;
    }

    public String show() throws Exception {
        return SHOW;
    }

    public String save() throws Exception {
        addActionMessage("保存成功");

        return RELOAD;
    }

    public String update() throws Exception {
        return RELOAD;
    }

    public String remove() throws Exception {
        addActionMessage("删除成功");

        return RELOAD;
    }

    public String removeAll() throws Exception {
        addActionMessage("删除成功");

        return RELOAD;
    }

    public String query() throws Exception {
        return SUCCESS;
    }

    // prepare
    public void prepare() throws Exception {
        System.out.println("prepare");
    }

    public void prepareSave() throws Exception {
        System.out.println("prepare save");
        prepareModel();
    }

    public void prepareInput() throws Exception {
        System.out.println("prepare input");
        prepareModel();
    }

    protected void prepareModel() throws Exception {
    }

    // model
    public T getModel() {
        return null;
    }
}
