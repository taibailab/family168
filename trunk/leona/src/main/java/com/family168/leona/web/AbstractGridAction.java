package com.family168.leona.web;

import java.util.ArrayList;
import java.util.List;

import com.family168.core.hibernate.HibernateEntityDao;
import com.family168.core.page.Page;
import com.family168.core.struts2.BaseAction;
import com.family168.core.utils.GenericsUtils;
import com.family168.core.utils.JsonUtils;
import com.family168.core.utils.MainUtils;

import com.family168.leona.domain.Affice;
import com.family168.leona.manager.AfficeManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import org.hibernate.Criteria;

import org.hibernate.criterion.Restrictions;


/**
 * affice action.
 *
 */
public abstract class AbstractGridAction<T> extends BaseAction<T> {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * affice manager. */
    //private transient AfficeManager afficeManager = null;
    /** * 持久类的类型. */
    protected Class<T> entityClass;

    /** * id. */
    protected Long id;

    // ====================================================
    protected int start;
    protected int limit;
    protected String sort;
    protected String dir;
    protected String filterTxt;
    protected String filterValue;
    protected String ids;
    protected String data;

    /** * 构造方法. */
    public AbstractGridAction() {
        this.entityClass = GenericsUtils.getSuperClassGenricType(this
                .getClass());
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    //
    //public void setAfficeManager(AfficeManager afficeManager) {
    //    this.afficeManager = afficeManager;
    //}
    public void setId(long id) {
        this.id = id;
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
            criteria = getManager().createCriteria(sort, isAsc);
        } else {
            criteria = getManager().createCriteria();
        }

        if ((filterTxt != null) && (filterValue != null)
                && (!filterTxt.equals("")) && (!filterValue.equals(""))) {
            criteria = criteria.add(Restrictions.like(filterTxt,
                        "%" + filterValue + "%"));
        }

        Page page = getManager().pagedQuery(criteria, pageNo, limit);

        JsonUtils.write(page,
            ServletActionContext.getResponse().getWriter(), getExcludes(),
            getDatePattern());
    }

    /**
     * 保存，新增或修改.
     *
     * @throws Exception 异常
     */
    public void save() throws Exception {
        T model = null;

        if (id != null) {
            model = getManager().get(id);
        } else {
            model = getEntityClass().newInstance();
        }

        MainUtils.copy(entity, model);
        beforeSave(model);
        getManager().save(model);
        ServletActionContext.getResponse().getWriter()
                            .print("{success:true}");
    }

    /**
     * 读取数据.
     *
     * @throws Exception 异常
     */
    public void loadData() throws Exception {
        T model = getManager().get(id);

        if (model != null) {
            List<T> list = new ArrayList<T>();
            list.add(model);
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
                getManager().removeById(id);
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
        return new String[] {};
    }

    public String getDatePattern() {
        return "yyyy-MM-dd";
    }

    public abstract HibernateEntityDao<T> getManager();

    public void beforeSave(T model) {
        //
    }
}
