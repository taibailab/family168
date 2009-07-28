package com.family168.security.web;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.List;

import com.family168.core.page.Page;
import com.family168.core.struts2.BaseAction;
import com.family168.core.utils.JsonUtils;
import com.family168.core.utils.MainUtils;

import com.family168.security.domain.Menu;
import com.family168.security.domain.Resc;
import com.family168.security.domain.Resc;
import com.family168.security.domain.Role;
import com.family168.security.manager.MenuManager;
import com.family168.security.manager.RescManager;
import com.family168.security.manager.RescManager;
import com.family168.security.manager.RoleManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import org.hibernate.Criteria;

import org.hibernate.criterion.Restrictions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * role action.
 *
 */
public class RoleAction extends BaseAction<Role> {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** logger. */
    private static Logger logger = LoggerFactory.getLogger(RoleAction.class);

    /** * role manager. */
    private transient RoleManager roleManager = null;

    /** * resc manager. */
    private transient RescManager rescManager = null;

    /** * menu manager. */
    private transient MenuManager menuManager = null;

    /** * role list. */
    private List<Role> list;

    /** * resc list. */
    private List<Resc> rescList;

    /** * menu list. */
    private List<Menu> menuList;

    /** * id. */
    private long id;

    /** * resc id. */
    private long rescId;

    /** * menu id. */
    private long menuId;

    // ====================================================
    private int start;
    private int limit;
    private String sort;
    private String dir;
    private String filterTxt;
    private String filterValue;
    private String ids;
    private String data;
    private Long roleId;
    private boolean auth;

    /** * @return success. */
    public String list() {
        this.list = roleManager.getAll();

        return SUCCESS;
    }

    /** * @return redirect. */
    /*public String save() {
        Role role;

        if (id == 0L) {
            role = new Role();
        } else {
            role = roleManager.get(id);
        }

        MainUtils.copy(entity, role);
        roleManager.save(role);

        return "redirect";
    }*/

    /** * @return forward. */
    public String edit() {
        this.entity = roleManager.get(id);

        return "forward";
    }

    /** * @return redirect. */
    /*public String remove() {
        roleManager.removeById(id);

        return "redirect";
    }*/

    /** * @return rescsByRole. */
    public String rescsByRole() {
        this.entity = roleManager.get(id);
        this.rescList = rescManager.getAll();

        for (Resc resc : rescList) {
            if (entity.getRescs().contains(resc)) {
                resc.setAuthorized(true);
            }
        }

        return "rescsByRole";
    }

    /** * @return auth resc end. */
    /*public String authResc() {
        Role role = roleManager.get(id);
        Resc resc = rescManager.get(rescId);

        if (!role.getRescs().contains(resc)) {
            role.getRescs().add(resc);
            roleManager.save(role);
        }

        return "auth-resc-end";
    }*/

    /** * @return auth resc end. */
    public String unauthResc() {
        Role role = roleManager.get(id);
        Resc resc = rescManager.get(rescId);

        if (role.getRescs().contains(resc)) {
            role.getRescs().remove(resc);
            roleManager.save(role);
        }

        return "auth-resc-end";
    }

    /** * @return menusByRole. */
    public String menusByRole() {
        this.entity = roleManager.get(id);
        this.menuList = menuManager.getAll();

        for (Menu menu : menuList) {
            if (entity.getMenus().contains(menu)) {
                menu.setChecked(true);
            }
        }

        return "menusByRole";
    }

    /** * @return auth menu end. */
    public String authMenu() {
        Role role = roleManager.get(id);
        Menu menu = menuManager.get(menuId);

        if (!role.getMenus().contains(menu)) {
            role.getMenus().add(menu);
            roleManager.save(role);
        }

        return "auth-menu-end";
    }

    /** * @return auth menu end. */
    public String unauthMenu() {
        Role role = roleManager.get(id);
        Menu menu = menuManager.get(menuId);

        if (role.getMenus().contains(menu)) {
            role.getMenus().remove(menu);
            roleManager.save(role);
        }

        return "auth-menu-end";
    }

    //
    /** * @param roleManager role manager. */
    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    /** * @param rescManager resc manager. */
    public void setRescManager(RescManager rescManager) {
        this.rescManager = rescManager;
    }

    /** * @param menuManager menu manager. */
    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    /** * @return role list. */
    public List<Role> getList() {
        return list;
    }

    /** * @return resc list. */
    public List<Resc> getRescList() {
        return rescList;
    }

    /** * @return menu list. */
    public List<Menu> getMenuList() {
        return menuList;
    }

    /** * @param id long. */
    public void setId(long id) {
        this.id = id;
    }

    /** * @param rescId long. */
    public void setRescId(long rescId) {
        this.rescId = rescId;
    }

    /** * @param menuId long. */
    public void setMenuId(long menuId) {
        this.menuId = menuId;
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
            criteria = roleManager.createCriteria(sort, isAsc);
        } else {
            criteria = roleManager.createCriteria();
        }

        if ((filterTxt != null) && (filterValue != null)
                && (!filterTxt.equals("")) && (!filterValue.equals(""))) {
            criteria = criteria.add(Restrictions.like(filterTxt,
                        "%" + filterValue + "%"));
        }

        Page page = roleManager.pagedQuery(criteria, pageNo, limit);

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
    protected Role bindObject() throws Exception {
        Role role = null;

        try {
            role = roleManager.get(entity.getId());
        } catch (JSONException ex) {
            System.err.println(ex);
        }

        if (role == null) {
            role = entity;
        } else {
            MainUtils.copy(entity, role);
        }

        return role;
    }

    /**
     * 保存，新增或修改.
     *
     * @throws Exception 异常
     */
    public void save() throws Exception {
        Role entity = bindObject();

        roleManager.save(entity);
        ServletActionContext.getResponse().getWriter()
                            .print("{success:true}");
    }

    /**
     * 读取数据.
     *
     * @throws Exception 异常
     */
    public void loadData() throws Exception {
        Role entity = roleManager.get(id);

        if (entity != null) {
            List<Role> list = new ArrayList<Role>();
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
                roleManager.removeById(id);
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
        return new String[] {"roles", "users", "menus", "rescs"};
    }

    public String getDatePattern() {
        return "yyyy-MM-dd";
    }

    /**
     * 显示角色对应的资源列表.
     *
     * @throws Exception 异常
     */
    public void getRescs() throws Exception {
        Role role = roleManager.get(roleId);

        List<Resc> rescs = rescManager.getAll();

        if (role != null) {
            for (Resc resc : rescs) {
                if (role.getRescs().contains(resc)) {
                    resc.setAuthorized(true);
                }
            }
        }

        JsonUtils.write(rescs,
            ServletActionContext.getResponse().getWriter(), getExcludes(),
            getDatePattern());
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    /**
     * 授权与撤消授权.
     *
     * @throws Exception 异常
     */
    public void authResc() throws Exception {
        Role role = roleManager.get(roleId);
        Resc resc = rescManager.get(rescId);

        if (auth) {
            if (!role.getRescs().contains(resc)) {
                role.getRescs().add(resc);
            }
        } else {
            if (role.getRescs().contains(resc)) {
                role.getRescs().remove(resc);
            }
        }

        roleManager.save(role);
        ServletActionContext.getResponse().getWriter()
                            .print("{success:true}");
    }

    /**
     * 显示指定角色下可显示的菜单.
     *
     * @throws Exception 写入writer的时候，抛出异常
     */
    public void getMenus() throws Exception {
        Role role = roleManager.get(roleId);
        List<Menu> list = menuManager.find(
                "from Menu where parent is null order by theSort asc,id desc");

        // 因为只有两级菜单，所以这里只需要写两个循环就可以判断哪些菜单被选中了
        // 不考虑多级情况，只从最直接的角度考虑
        for (Menu menu : list) {
            if (role.getMenus().contains(menu)) {
                menu.setChecked(true);

                for (Menu subMenu : menu.getChildren()) {
                    if (role.getMenus().contains(subMenu)) {
                        subMenu.setChecked(true);
                    }
                }
            }
        }

        // 现在checkbox tree的问题是无法在js里设置根节点，必须在json里做一个根节点
        // 如果不设置根节点，getChecked()方法返回的只有第一棵树的数据，疑惑中。
        // 为了他的限制，多写了下面这么多代码，真郁闷
        Menu root = new Menu();
        root.setId(0L);
        root.setName("选择菜单");
        root.setChildren(new LinkedHashSet<Menu>(list));
        root.setChecked(true);

        Menu[] menus = new Menu[] {root};

        JsonUtils.write(menus,
            ServletActionContext.getResponse().getWriter(), getExcludes(),
            getDatePattern());
    }

    /**
     * 选择角色对应的菜单.
     *
     * @throws Exception 写入response可能抛出异常吧？
     */
    public void selectMenu() throws Exception {
        Role role = roleManager.get(roleId);

        if (role == null) {
            ServletActionContext.getResponse().getWriter()
                                .print("{success:false}");

            return;
        }

        role.getMenus().clear();

        for (String str : ids.split(",")) {
            try {
                long id = Long.parseLong(str);
                logger.info("{}", id);

                Menu menu = menuManager.get(id);
                logger.info("{}", menu);

                if (menu != null) {
                    role.getMenus().add(menu);
                }
            } catch (Exception ex) {
                logger.info("", ex);
            }
        }

        roleManager.save(role);

        ServletActionContext.getResponse().getWriter()
                            .print("{success:true}");
    }
}
