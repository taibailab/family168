package com.family168.core.hibernate;

import java.lang.reflect.Field;

import com.family168.core.page.ExtjsPage;
import com.family168.core.page.Page;
import com.family168.core.utils.BeanUtils;
import com.family168.core.utils.MainUtils;

import org.hibernate.Criteria;

import org.hibernate.criterion.Expression;


/**
 * 为extjs服务的dao.
 * 封装的是与extjs分页对应的分页方法
 *
 * @author Lingo
 * @param <T> 泛型
 */
public class HibernateExtjsDao<T> extends HibernateEntityDao<T> {
    /**
     * 分页查询.
     *
     * @param extjsPage 分页参数
     * @return 分页结果
     */
    public ExtjsPage pagedQuery(ExtjsPage extjsPage) {
        Criteria criteria = createCriteria(extjsPage.getSort(),
                extjsPage.isAsc());
        String filterTxt = extjsPage.getFilterTxt();
        String filterValue = extjsPage.getFilterValue();

        if (needFilter(filterTxt, filterValue)) {
            try {
                Field field = BeanUtils.getDeclaredField(entityClass,
                        filterTxt);

                Class type = field.getType();

                if (type == String.class) {
                    criteria.add(Expression.like(filterTxt,
                            "%" + filterValue + "%"));
                } else if ((type == Integer.class) || (type == int.class)) {
                    criteria.add(Expression.eq(filterTxt,
                            MainUtils.getInt(filterValue)));
                } else if ((type == Long.class) || (type == long.class)) {
                    criteria.add(Expression.eq(filterTxt,
                            MainUtils.getLong(filterValue)));
                } else if ((type == Double.class)
                        || (type == double.class)) {
                    criteria.add(Expression.eq(filterTxt,
                            MainUtils.getDouble(filterValue)));
                } else {
                    logger.warn("unimplemented");
                }
            } catch (Exception ex) {
                logger.warn(ex);
            }
        }

        //logger.info(criteria);
        Page page = pagedQuery(criteria, extjsPage.getPageNo(),
                extjsPage.getLimit());
        MainUtils.copy(page, extjsPage);

        return extjsPage;
    }

    /**
     * 判断是否需要进行条件搜索.
     *
     * @param filterTxt name
     * @param filterValue value
     * @return if need filter
     */
    protected boolean needFilter(String filterTxt, String filterValue) {
        return (filterTxt != null) && (filterValue != null)
        && (!filterTxt.trim().equals(""))
        && (!filterValue.trim().equals(""));
    }

    /**
     * 分页查询.
     *
     * @param start 本页第一条记录索引
     * @param limit 每页最大记录数
     * @param sort 排序字段
     * @param isAsc 是否正序
     * @return 分页结果
     */
    public ExtjsPage pagedQuery(int start, int limit, String sort,
        boolean isAsc) {
        ExtjsPage page = null;

        if (isAsc) {
            page = new ExtjsPage(start, limit, sort, "ASC");
        } else {
            page = new ExtjsPage(start, limit, sort, "DESC");
        }

        return pagedQuery(page);
    }

    /**
     * 分页查询.
     *
     * @param start 本页第一条记录索引
     * @param limit 每页最大记录数
     * @param sort 排序字段
     * @param dir 是否正序
     * @return 分页结果
     */
    public ExtjsPage pagedQuery(int start, int limit, String sort,
        String dir) {
        return pagedQuery(new ExtjsPage(start, limit, sort, dir));
    }

    /**
     * 分页查询.
     *
     * @param start 本页第一条记录索引
     * @param limit 每页最大记录数
     * @param sort 排序字段
     * @param dir 是否正序
     * @param filterTxt 搜索字段名
     * @param filterValue 搜索值
     * @return 分页结果
     */
    public ExtjsPage pagedQuery(int start, int limit, String sort,
        String dir, String filterTxt, String filterValue) {
        return pagedQuery(new ExtjsPage(start, limit, sort, dir,
                filterTxt, filterValue));
    }
}
