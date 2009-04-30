package com.family168.security.web;

import java.util.ArrayList;
import java.util.List;

import com.family168.core.struts2.BaseAction;
import com.family168.core.utils.JsonUtils;
import com.family168.core.utils.MainUtils;

import com.family168.security.domain.Menu;
import com.family168.security.manager.MenuManager;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;


/**
 * menu action.
 *
 */
public class MenuAction extends BaseAction<Menu> {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * menu manager. */
    private transient MenuManager menuManager = null;

    /** * menu list. */
    private List<Menu> list;

    /** * id. */
    private long id;

    /** * parent id. */
    private long parentId;
    private long node;
    private String data;

    /** * @return success. */
    public String list() {
        this.list = menuManager.getAll();

        return SUCCESS;
    }

    /** * @return redirect. */
    public String save() {
        Menu menu;

        if (id == 0L) {
            menu = new Menu();
        } else {
            menu = menuManager.get(id);
        }

        MainUtils.copy(entity, menu);

        try {
            Menu parent = menuManager.get(parentId);
            menu.setParent(parent);
            menuManager.save(menu);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "redirect";
    }

    /** * @return forward. */
    public String edit() {
        this.entity = menuManager.get(id);

        return "forward";
    }

    /** * @return redirect. */
    public String remove() {
        menuManager.removeById(id);

        return "redirect";
    }

    //
    /** * @param menuManager menu manager. */
    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    /** * @return menu list. */
    public List<Menu> getList() {
        return list;
    }

    /** * @param id long. */
    public void setId(long id) {
        this.id = id;
    }

    /** * @param parentId long. */
    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public void setNode(long node) {
        this.node = node;
    }

    public void setData(String data) {
        this.data = data;
    }

    // ====================================================

    /**
     * 一次性获得整棵树的数据.
     *
     * @throws Exception 异常
     */
    public void getAllTree() throws Exception {
        String hql = "from Menu where parent is null order by theSort asc, id desc";

        List<Menu> list = menuManager.find(hql);

        JsonUtils.write(list,
            ServletActionContext.getResponse().getWriter(),
            getExcludesForAll(), getDatePattern());
    }

    /**
     * 根据传入参数，返回对应id分类的子分类，用于显示分类的树形结构.
     * 如果没有指定id的值，则返回所有根节点
     *
     * @throws Exception 异常
     */
    public void getChildren() throws Exception {
        long parentId = node;
        List<Menu> list = null;
        String hql = "from Menu";
        String orderBy = " order by theSort asc, id desc";

        if (parentId == -1L) {
            // 根节点
            list = menuManager.find(hql + " where parent is null"
                    + orderBy);
        } else {
            list = menuManager.find(hql + " where parent.id=?" + orderBy,
                    parentId);
        }

        JsonUtils.write(list,
            ServletActionContext.getResponse().getWriter(),
            getExcludesForChildren(), getDatePattern());
    }

    /**
     * 根据id获取一条记录.
     *
     * @throws Exception 写入json可能出现异常
     */
    public void loadData() throws Exception {
        Menu entity = menuManager.get(id);

        if (entity != null) {
            List<Menu> list = new ArrayList<Menu>();
            list.add(entity);
            JsonUtils.write(list,
                ServletActionContext.getResponse().getWriter(),
                getExcludesForChildren(), getDatePattern());
        }
    }

    /**
     * 添加一个分类.
     * 目前还不没有返回成功或者错误信息
     * 校验方式，当输入的data参数不存在的时候，直接返回
     *
     * @throws Exception 异常
     */
    public void insertTree() throws Exception {
        Menu node = JsonUtils.json2Bean(data, Menu.class,
                getExcludesForChildren(), getDatePattern());

        Menu entity = menuManager.get(node.getId());

        // 更新上级分类
        Long parentId = node.getParentId();

        if (parentId != null) {
            Menu parent = menuManager.get(parentId);
            node.setParent(parent);
        }

        if (entity == null) {
            // 添加
            // 只有在id = null的情况下，才执行insert，否则执行update
            node.setId(null);
            menuManager.save(node);
            entity = node;
        } else {
            // 修改
            entity.setName(node.getName());
            menuManager.save(entity);
        }

        ServletActionContext.getResponse().getWriter()
                            .print("{success:true,id:" + entity.getId()
            + "}");
    }

    /**
     * 根据id删除一个分类，由于级联关系，会同时删除所有子分类.
     *
     * @throws Exception 异常
     */
    public void removeTree() throws Exception {
        if (id != -1L) {
            menuManager.removeById(id);
        }
    }

    /**
     * 保存排序结果.
     * 参数是JSONArray，保存了id列表，排序时候根据id列表修改theSort字段
     *
     * @throws Exception 异常
     */
    public void sortTree() throws Exception {
        List<Menu> list = JsonUtils.json2List(data, Menu.class,
                getExcludesForChildren(), getDatePattern());

        for (int i = 0; i < list.size(); i++) {
            Menu node = list.get(i);
            Long id = node.getId();
            Long parentId = node.getParentId();

            Menu entity = menuManager.get(id);

            if (entity != null) {
                Menu parent = menuManager.get(parentId);
                entity.setParent(parent);
                entity.setTheSort(i);
                menuManager.save(entity);
            }
        }
    }

    /**
     * updateTree.
     * 修改信息信息
     *
     * @throws Exception 异常
     */
    public void updateTree() throws Exception {
        Menu menu = menuManager.get(id);
        MainUtils.copy(entity, menu);

        menuManager.save(menu);

        ServletActionContext.getResponse().getWriter()
                            .print("{success:true,info:\"success\"}");
    }

    /**
     * 迭代取得所有节点时候，使用的exclude设置.
     *
     * @return 不需要转化成json的属性列表，默认是空的
     */
    public String[] getExcludesForAll() {
        return new String[] {"class", "root", "parent", "checked", "roles"};
    }

    /**
     * 只取得直接子节点时，使用的exclude设置.
     *
     * @return 不需要转换json的属性数组
     */
    public String[] getExcludesForChildren() {
        return new String[] {
            "class", "root", "parent", "children", "checked", "roles"
        };
    }

    public String getDatePattern() {
        return "yyyy-MM-dd";
    }
}
