package com.family168.security.web;

import java.util.ArrayList;
import java.util.List;

import com.family168.core.page.Page;
import com.family168.core.struts2.BaseAction;
import com.family168.core.utils.JsonUtils;
import com.family168.core.utils.MainUtils;

import com.family168.security.domain.Resc;
import com.family168.security.manager.RescManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import org.hibernate.Criteria;

import org.hibernate.criterion.Restrictions;


/**
 * resc action.
 *
 */
public class RescAction extends BaseAction<Resc> {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * resc manager. */
    private transient RescManager rescManager = null;

    /** * resc list. */
    private List<Resc> list;

    /** * id. */
    private long id;

    // ====================================================
    private int start;
    private int limit;
    private String sort;
    private String dir;
    private String filterTxt;
    private String filterValue;
    private String ids;
    private String data;

    /** * @return success. */
    public String list() {
        this.list = rescManager.getAll();

        return SUCCESS;
    }

    /** * @return redirect. */
    /*
    public String save() {
    Resc resc;
    
    if (id == 0L) {
    resc = new Resc();
    } else {
    resc = rescManager.get(id);
    }
    
    MainUtils.copy(entity, resc);
    rescManager.save(resc);
    
    return "redirect";
    }*/

    /** * @return forward. */
    public String edit() {
        this.entity = rescManager.get(id);

        return "forward";
    }

    /** * @return redirect. */
    /*
    public String remove() {
    rescManager.removeById(id);
    
    return "redirect";
    }*/

    //
    /** * @param rescManager resc manager. */
    public void setRescManager(RescManager rescManager) {
        this.rescManager = rescManager;
    }

    /** * @return resc list. */
    public List<Resc> getList() {
        return list;
    }

    /** * @param id long. */
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
            criteria = rescManager.createCriteria(sort, isAsc);
        } else {
            criteria = rescManager.createCriteria();
        }

        if ((filterTxt != null) && (filterValue != null)
                && (!filterTxt.equals("")) && (!filterValue.equals(""))) {
            criteria = criteria.add(Restrictions.like(filterTxt,
                        "%" + filterValue + "%"));
        }

        Page page = rescManager.pagedQuery(criteria, pageNo, limit);

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
    protected Resc bindObject() throws Exception {
        Resc resc = null;

        try {
            resc = rescManager.get(entity.getId());
        } catch (JSONException ex) {
            System.err.println(ex);
        }

        if (resc == null) {
            resc = entity;
        } else {
            MainUtils.copy(entity, resc);
        }

        if ("METHOD".equals(resc.getResType())) {
            String resString = resc.getResString();

            try {
                int lastDotIndex = resString.lastIndexOf('.');
                String className = resString.substring(0, lastDotIndex);
                //String methodName = resString.substring(lastDotIndex + 1);

                //Class rescClass = Class.forName(className);
                Class.forName(className);
            } catch (Exception ex) {
                System.err.println("resString内容不正确：" + ex.toString());
                throw ex;
            }
        }

        return resc;
    }

    /**
     * 保存，新增或修改.
     *
     * @throws Exception 异常
     */
    public void save() throws Exception {
        Resc entity = bindObject();

        rescManager.save(entity);
        ServletActionContext.getResponse().getWriter()
                            .print("{success:true}");
    }

    /**
     * 读取数据.
     *
     * @throws Exception 异常
     */
    public void loadData() throws Exception {
        Resc entity = rescManager.get(id);

        if (entity != null) {
            List<Resc> list = new ArrayList<Resc>();
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
                rescManager.removeById(id);
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
        return new String[] {"roles", "rescs", "users"};
    }

    public String getDatePattern() {
        return "yyyy-MM-dd";
    }
}
