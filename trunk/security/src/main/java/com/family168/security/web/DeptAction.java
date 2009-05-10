package com.family168.security.web;

import java.util.ArrayList;
import java.util.List;

import com.family168.core.struts2.BaseAction;
import com.family168.core.utils.JsonUtils;
import com.family168.core.utils.MainUtils;

import com.family168.security.domain.Dept;
import com.family168.security.manager.DeptManager;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;


/**
 * dept action.
 *
 */
public class DeptAction extends BaseAction<Dept> {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * dept manager. */
    private transient DeptManager deptManager = null;

    /** * dept list. */
    private List<Dept> list;

    /** * id. */
    private long id;

    /** * parent id. */
    private long parentId;
    private long node;
    private String data;

    /** * @return success. */
    public String list() {
        this.list = deptManager.getAll();

        return SUCCESS;
    }

    /** * @return redirect. */
    public String save() {
        Dept dept;

        if (id == 0L) {
            dept = new Dept();
        } else {
            dept = deptManager.get(id);
        }

        MainUtils.copy(entity, dept);

        Dept parent = deptManager.get(parentId);
        dept.setParent(parent);
        deptManager.save(dept);

        return "redirect";
    }

    /** * @return forward. */
    public String edit() {
        this.entity = deptManager.get(id);

        return "forward";
    }

    /** * @return redirect. */
    public String remove() {
        deptManager.removeById(id);

        return "redirect";
    }

    //
    /** * @param deptManager dept manager. */
    public void setDeptManager(DeptManager deptManager) {
        this.deptManager = deptManager;
    }

    /** * @return dept list. */
    public List<Dept> getList() {
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
        String hql = "from Dept where parent is null order by theSort asc, id desc";

        List<Dept> list = deptManager.find(hql);

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
        List<Dept> list = null;
        String hql = "from Dept";
        String orderBy = " order by theSort asc, id desc";

        if (parentId == -1L) {
            // 根节点
            list = deptManager.find(hql + " where parent is null"
                    + orderBy);
        } else {
            list = deptManager.find(hql + " where parent.id=?" + orderBy,
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
        Dept entity = deptManager.get(id);

        if (entity != null) {
            List<Dept> list = new ArrayList<Dept>();
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
        Dept node = JsonUtils.json2Bean(data, Dept.class,
                getExcludesForChildren(), getDatePattern());

        Dept entity = deptManager.get(node.getId());

        // 更新上级分类
        Long parentId = node.getParentId();

        if (parentId != null) {
            Dept parent = deptManager.get(parentId);
            node.setParent(parent);
        }

        if (entity == null) {
            // 添加
            // 只有在id = null的情况下，才执行insert，否则执行update
            node.setId(null);
            deptManager.save(node);
            entity = node;
        } else {
            // 修改
            entity.setName(node.getName());
            deptManager.save(entity);
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
            deptManager.removeById(id);
        }
    }

    /**
     * 保存排序结果.
     * 参数是JSONArray，保存了id列表，排序时候根据id列表修改theSort字段
     *
     * @throws Exception 异常
     */
    public void sortTree() throws Exception {
        List<Dept> list = JsonUtils.json2List(data, Dept.class,
                getExcludesForChildren(), getDatePattern());

        for (int i = 0; i < list.size(); i++) {
            Dept node = list.get(i);
            Long id = node.getId();
            Long parentId = node.getParentId();

            Dept entity = deptManager.get(id);

            if (entity != null) {
                Dept parent = deptManager.get(parentId);
                entity.setParent(parent);
                entity.setTheSort(i);
                deptManager.save(entity);
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
        JSONObject jsonObject = JSONObject.fromObject(data);

        Dept entity = deptManager.get(jsonObject.getLong("id"));
        JsonUtils.json2Bean(jsonObject, entity, getExcludesForChildren(),
            getDatePattern());

        deptManager.save(entity);

        ServletActionContext.getResponse().getWriter()
                            .print("{success:true,info:\"success\"}");
    }
    /**
     * 组织结构.
     */
    public String orgMap() throws Exception {
        this.list=deptManager.loadTops();
        return "orgMap";
    }

    /**
     * 迭代取得所有节点时候，使用的exclude设置.
     *
     * @return 不需要转化成json的属性列表，默认是空的
     */
    public String[] getExcludesForAll() {
        return new String[] {
            "hibernateLazyInitializer", "class", "root", "parent","users"
        };
    }

    /**
     * 只取得直接子节点时，使用的exclude设置.
     *
     * @return 不需要转换json的属性数组
     */
    public String[] getExcludesForChildren() {
        return new String[] {
            "hibernateLazyInitializer", "class", "root", "parent",
            "children","users"
        };
    }

    public String getDatePattern() {
        return "yyyy-MM-dd";
    }
}
