package com.family168.web.user;

import com.family168.BaseAction;

import com.family168.domain.User;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;


@ParentPackage("default")
@Results({@Result(name = BaseAction.RELOAD, location = "user.do", type = "redirect")
})
public class UserAction extends BaseAction<User> {
    private User model;

    // private UserManager userManager;
    public User getModel() {
        return model;
    }

    protected void prepareModel() {
        if (id == null) {
            model = new User();
        } else {
            // model = userManager.get(id);
        }
    }

    public String list() throws Exception {
        // list = userManager.getAll();
        return SUCCESS;
    }

    public String create() throws Exception {
        return INPUT;
    }

    public String edit() throws Exception {
        // model = userManager.get(id);
        return INPUT;
    }

    public String show() throws Exception {
        // model = userManager.get(id);
        return SHOW;
    }

    public String save() throws Exception {
        // userManager.save(model);
        addActionMessage("保存成功");

        return RELOAD;
    }

    public String update() throws Exception {
        // userManager.save(model);
        addActionMessage("保存成功");

        return RELOAD;
    }

    public String remove() throws Exception {
        // userManager.removeById(id);
        addActionMessage("删除成功");

        return RELOAD;
    }

    public String removeAll() throws Exception {
        // userManager.removeAll();
        addActionMessage("删除成功");

        return RELOAD;
    }

    public String query() throws Exception {
        // list = userManager.query();
        return SUCCESS;
    }
}
