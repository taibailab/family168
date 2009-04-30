package com.family168.security.web;

import java.util.ArrayList;
import java.util.List;

import com.family168.core.page.Page;
import com.family168.core.struts2.BaseAction;
import com.family168.core.utils.JsonUtils;
import com.family168.core.utils.MainUtils;

import com.family168.security.domain.Dept;
import com.family168.security.domain.Menu;
import com.family168.security.domain.Resc;
import com.family168.security.domain.Role;
import com.family168.security.domain.User;
import com.family168.security.manager.DeptManager;
import com.family168.security.manager.MenuManager;
import com.family168.security.manager.RescManager;
import com.family168.security.manager.RoleManager;
import com.family168.security.manager.UserManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import org.hibernate.Criteria;

import org.hibernate.criterion.Restrictions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.providers.encoding.PasswordEncoder;


/**
 * user action.
 *
 */
public class UserAction extends BaseAction<User> {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** logger. */
    private static Logger logger = LoggerFactory.getLogger(UserAction.class);

    /** * user manager. */
    private transient UserManager userManager = null;

    /** * dept manager. */
    private transient DeptManager deptManager = null;

    /** * role manager. */
    private transient RoleManager roleManager = null;

    /** * password encoder. */
    private transient PasswordEncoder passwordEncoder = null;

    /** * user list. */
    private List<User> list;

    /** * dept list. */
    private List<Dept> deptList;

    /** * role list. */
    private List<Role> roleList;

    /** * id. */
    private long id;

    /** * dept id. */
    private long deptId;

    /** * message. */
    private String message;

    // ====================================================
    private int start;
    private int limit;
    private String sort;
    private String dir;
    private String filterTxt;
    private String filterValue;
    private String ids;
    private String data;
    private Long userId;
    private Long roleId;
    private boolean auth;

    /** * @return success. */
    public String list() {
        this.list = userManager.getAll();
        this.deptList = deptManager.getAll();

        return SUCCESS;
    }

    /** * @return redirect. */
    /*public String save() {
        User user;
    
        if (id == 0L) {
            if (userManager.findBy("username", entity.getUsername()).size() > 0) {
                message = "User already exists.";
    
                return "forward";
            }
    
            user = new User();
        } else {
            user = userManager.get(id);
        }
    
        MainUtils.copy(entity, user);
        user.setPassword(passwordEncoder.encodePassword(
                user.getPassword(), null));
    
        Dept dept = deptManager.get(deptId);
        user.setDept(dept);
        userManager.save(user);
    
        return "redirect";
    }*/

    /** * @return forward. */
    public String edit() {
        this.entity = userManager.get(id);

        return "forward";
    }

    /** * @return redirect. */
    /*public String remove() {
        userManager.removeById(id);
    
        return "redirect";
    }*/

    /** * @return rolesByUser. */
    public String rolesByUser() {
        this.entity = userManager.get(id);
        this.roleList = roleManager.getAll();

        for (Role role : roleList) {
            if (entity.getRoles().contains(role)) {
                role.setAuthorized(true);
            }
        }

        return "rolesByUser";
    }

    /** * @return auth end. */
    public String auth() {
        User user = userManager.get(id);
        Role role = roleManager.get(roleId);

        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
            userManager.save(user);
        }

        return "auth-end";
    }

    /** * @return auth end. */
    public String unauth() {
        User user = userManager.get(id);
        Role role = roleManager.get(roleId);

        if (user.getRoles().contains(role)) {
            user.getRoles().remove(role);
            userManager.save(user);
        }

        return "auth-end";
    }

    //
    /** * @param userManager user manager. */
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    /** * @param deptManager dept manager. */
    public void setDeptManager(DeptManager deptManager) {
        this.deptManager = deptManager;
    }

    /** * @param roleManager role manager. */
    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    /** * @param passwordEncoder password encoder. */
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /** * @return user list. */
    public List<User> getList() {
        return list;
    }

    /** * @return dept list. */
    public List<Dept> getDeptList() {
        return deptList;
    }

    /** * @return role list. */
    public List<Role> getRoleList() {
        return roleList;
    }

    /** * @param id long. */
    public void setId(long id) {
        this.id = id;
    }

    /** * @param deptId long. */
    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    /** * @param roleId long. */
    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    /** * @return message. */
    public String getMessage() {
        return message;
    }

    // ====================================================
    public void setStart(int start) {
        this.start = start;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public void setFilterTxt(String filterTxt) {
        this.filterTxt = filterTxt;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    /**
     * 分页浏览记录.
     *
     * @throws Exception 异常
     */
    public void pagedQuery() throws Exception {
        // 分页
        int pageNo = (start / limit) + 1;

        Criteria criteria;

        if (sort != null) {
            boolean isAsc = dir.equalsIgnoreCase("asc");
            criteria = userManager.createCriteria(sort, isAsc);
        } else {
            criteria = userManager.createCriteria();
        }

        if ((filterTxt != null) && (filterValue != null)
                && (!filterTxt.equals("")) && (!filterValue.equals(""))) {
            criteria = criteria.add(Restrictions.like(filterTxt,
                        "%" + filterValue + "%"));
        }

        Page page = userManager.pagedQuery(criteria, pageNo, limit);

        JsonUtils.write(page,
            ServletActionContext.getResponse().getWriter(), getExcludes(),
            getDatePattern());
    }

    /**
     * 使用json绑定pojo，再将绑定的pojo返回.
     *
     * @return T entity
     * @throws Exception 解析时，可能抛出异常
     */
    protected User bindObject() throws Exception {
        User user = null;

        try {
            user = userManager.get(entity.getId());
        } catch (JSONException ex) {
            System.err.println(ex);
        }

        if (user == null) {
            if (userManager.findBy("username", entity.getUsername()).size() > 0) {
                throw new Exception("User already exists.");
            }

            user = entity;
            logger.info("{}", user);
            logger.info("{}", passwordEncoder);
            user.setPassword(passwordEncoder.encodePassword(
                    user.getPassword(), null));
        } else {
            MainUtils.copy(entity, user);
        }

        return user;
    }

    /**
     * 保存，新增或修改.
     *
     * @throws Exception 异常
     */
    public void save() throws Exception {
        User entity = bindObject();
        Dept dept = deptManager.get(deptId);
        entity.setDept(dept);
        userManager.save(entity);
        ServletActionContext.getResponse().getWriter()
                            .print("{success:true}");
    }

    /**
     * 读取数据.
     *
     * @throws Exception 异常
     */
    public void loadData() throws Exception {
        User entity = userManager.get(id);

        if (entity != null) {
            List<User> list = new ArrayList<User>();
            list.add(entity);
            JsonUtils.write(list,
                ServletActionContext.getResponse().getWriter(),
                getExcludes(), getDatePattern());
        }
    }

    /**
     * 删除记录.
     *
     * @throws Exception 异常
     */
    public void remove() throws Exception {
        for (String str : ids.split(",")) {
            try {
                long id = Long.parseLong(str);
                userManager.removeById(id);
            } catch (NumberFormatException ex) {
                continue;
            }
        }

        ServletActionContext.getResponse().getWriter()
                            .print("{success:true}");
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public void setData(String data) {
        this.data = data;
    }

    /** * @return excludes. */
    public String[] getExcludes() {
        return new String[] {
            "hibernateLazyInitializer", "roles", "menus", "rescs", "users"
        };
    }

    public String getDatePattern() {
        return "yyyy-MM-dd";
    }

    /**
     * 显示用户对应的角色列表.
     *
     * @throws Exception 异常
     */
    public void getRoles() throws Exception {
        // mv.setViewName("/admin/selectRoles");
        User user = userManager.get(userId);
        List<Role> roles = roleManager.getAll();

        if (user != null) {
            for (Role role : roles) {
                if (user.getRoles().contains(role)) {
                    role.setAuthorized(true);
                }
            }
        }

        JsonUtils.write(roles,
            ServletActionContext.getResponse().getWriter(), getExcludes(),
            getDatePattern());
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    /**
     * 授权与撤消授权.
     *
     * @throws Exception 异常
     */
    public void authRole() throws Exception {
        User user = userManager.get(userId);
        Role role = roleManager.get(roleId);

        if (auth) {
            if (!user.getRoles().contains(role)) {
                user.getRoles().add(role);
            }
        } else {
            if (user.getRoles().contains(role)) {
                user.getRoles().remove(role);
            }
        }

        userManager.save(user);
        ServletActionContext.getResponse().getWriter()
                            .print("{success:true}");
    }
}
