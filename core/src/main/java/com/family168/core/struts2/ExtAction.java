package com.family168.core.struts2;

import com.family168.core.hibernate.HibernateExtjsDao;
import com.family168.core.page.Page;
import com.family168.core.utils.JsonUtils;
import com.family168.core.utils.MainUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 与extjs对应的action.
 * 实际上是与Ext.grid.GridPanel对应的action，提供分页，排序，模糊查询，保存等功能
 *
 * @author Lingo
 * @param <T> 泛型
 */
public class ExtAction<T> extends BaseAction<T> {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * logger. */
    private static Log logger = LogFactory.getLog(ExtAction.class);

    /** * 分页，当前页第一条记录的索引. */
    protected int start;

    /** * 分页，每页的最大记录数. */
    protected int limit;

    /** * 排序的字段名. */
    protected String sort;

    /** * 排序方式，ASC或DESC. */
    protected String dir;

    /** * 模糊查询的字段名. */
    protected String filterTxt;

    /** * 模糊查询的查询值. */
    protected String filterValue;

    /** * 显示记录的id. */
    protected long id;

    /** * 批量删除时记录的id，格式为1,2,3. */
    protected String ids;

    /** * dao. */
    protected transient HibernateExtjsDao<T> entityDao;

    /** * json-lib使用的excludes属性，使用,分隔，这里的属性不会被json-lib处理. */
    protected String excludes = "hibernateLazyInitializer";

    /** * 删除记录的id，格式为1,2,3. */
    protected String removedIds;

    /** * 批量保存使用的data，是格式为json的字符串. */
    protected String data;

    /**
     * 分页查询.
     *
     * @throws Exception 可能抛出任何异常
     */
    public void pagedQuery() throws Exception {
        Page page = entityDao.pagedQuery(start, limit, sort, dir,
                filterTxt, filterValue);

        JsonUtils.write(page, out, excludes, "yyyy-MM-dd");
    }

    /**
     * 添加或更新一条记录.
     *
     * @throws Exception 可能抛出任何异常
     */
    public void save() throws Exception {
        T stub = null;

        if (id == 0L) {
            stub = (T) entityClass.newInstance();
        } else {
            stub = entityDao.get(id);
        }

        MainUtils.copy(entity, stub);
        beforeSave(stub);
        entityDao.save(stub);
        out.write("{success:true,message:'操作成功'}");
    }

    /**
     * 根据id读取一条记录.
     *
     * @throws Exception 可能抛出任何异常
     */
    public void loadData() throws Exception {
        T stub = entityDao.get(id);
        JsonUtils.write(new Object[] {stub}, out, excludes, null);
    }

    /**
     * 根据ids删除多条记录.
     *
     * @throws Exception 可能抛出任何异常
     */
    public void remove() throws Exception {
        for (String str : ids.split(",")) {
            try {
                Long theId = Long.parseLong(str);
                entityDao.removeById(theId);
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }

        out.write("{success:true}");
    }

    /**
     * struts2提供的校验方法，测试用.
     */
    @Override
    public void validate() {
        logger.debug(getActionErrors());
        logger.debug(getErrors());
    }

    /**
     * 子类的回调方法，在添加或修改到数据库之前执行，为了搞定一些外键关联，但不能中止操作.
     *
     * @param stub 实体类
     */
    protected void beforeSave(T stub) {
    }

    /** * @param start int. */
    public void setStart(int start) {
        this.start = start;
    }

    /** * @param limit int. */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /** * @param sort String. */
    public void setSort(String sort) {
        this.sort = sort;
    }

    /** * @param dir String. */
    public void setDir(String dir) {
        this.dir = dir;
    }

    /** * @param filterTxt String. */
    public void setFilterTxt(String filterTxt) {
        this.filterTxt = filterTxt;
    }

    /** * @param filterValue String. */
    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    /** * @param id long. */
    public void setId(long id) {
        this.id = id;
    }

    /** * @param ids String. */
    public void setIds(String ids) {
        this.ids = ids;
    }

    /** * @param removedIds String. */
    public void setRemovedIds(String removedIds) {
        this.removedIds = removedIds;
    }

    /** * @param data String. */
    public void setData(String data) {
        this.data = data;
    }
}
