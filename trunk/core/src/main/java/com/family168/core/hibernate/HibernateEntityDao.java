package com.family168.core.hibernate;

import java.io.Serializable;

import java.util.List;

import com.family168.core.page.Page;
import com.family168.core.utils.GenericsUtils;

import org.hibernate.Criteria;

import org.hibernate.criterion.Criterion;


/**
 * 使用泛型的hibernate基类.
 *
 * @author Lingo
 * @param <T> 实体类型
 */
public class HibernateEntityDao<T> extends HibernateGenericDao {
    /** * 持久类的类型. */
    protected Class<T> entityClass;

    /** * 构造方法. */
    public HibernateEntityDao() {
        this.entityClass = GenericsUtils.getSuperClassGenricType(this
                .getClass());
    }

    /**
     * 子类可以获得泛型对应的实体类型.
     *
     * @return entityClass
     */
    protected Class<T> getEntityClass() {
        return entityClass;
    }

    /**
     * 获得一个实体类型的一条记录.
     *
     * @param id 主键
     * @return 实例
     */
    public T get(Serializable id) {
        return this.get(this.entityClass, id);
    }

    /**
     * 获得一个实体类型的所有记录.
     *
     * @return 所有实例列表
     */
    public List<T> getAll() {
        return this.getAll(this.entityClass);
    }

    /**
     * load一个实例，如果id不存在，会返回一个proxy，在调用proxy的时候出现问题.
     * 使用这个方法，可以利用缓存，但是如果实例不存在，会出现不容易预计的错误
     *
     * @param id 主键
     * @return 实例
     */
    public T load(Serializable id) {
        return this.load(this.entityClass, id);
    }

    /**
     * 根据主键删除记录.
     *
     * @param id 主键
     */
    public void removeById(Serializable id) {
        this.remove(this.get(this.entityClass, id));
    }

    /**
     * 删除所有记录.
     */
    public void removeAll() {
        this.removeAll(this.getAll(this.entityClass));
    }

    /**
     * 根据主键，获得一个不需要lazy load的实例.
     * 避免出现lazy load错误的一个方法
     *
     * @param id 主键
     * @return 实例
     */
    public T initialize(Serializable id) {
        return initialize(this.entityClass, id);
    }

    // query
    /**
     * 获得所有记录，带排序参数.
     *
     * @param orderBy 排序字段名
     * @param isAsc 是否正序排列
     * @return 返回结果列表
     */
    public List<T> getAll(String orderBy, boolean isAsc) {
        return this.getAll(this.entityClass, orderBy, isAsc);
    }

    /**
     * 根据entityClass生成对应类型的Criteria.
     *
     * @param criterions 条件
     * @return Criteria
     */
    public Criteria createCriteria(Criterion... criterions) {
        return this.createCriteria(this.entityClass, criterions);
    }

    /**
     * 根据entityClass，生成带排序的Criteria.
     *
     * @param orderBy 排序字段名
     * @param isAsc 是否正序
     * @param criterions 条件
     * @return Criteria
     */
    public Criteria createCriteria(String orderBy, boolean isAsc,
        Criterion... criterions) {
        return this.createCriteria(this.entityClass, orderBy, isAsc,
            criterions);
    }

    // find

    /**
     * 查询唯一记录.
     *
     * @param hql HQL字符串
     * @param values 参数
     * @return 实例
     */
    public T findUnique(String hql, Object... values) {
        return this.findUnique(this.entityClass, hql, values);
    }

    /**
     * 根据name，value进行查询.
     *
     * @param name 字段名
     * @param value 参数值
     * @return 查询结果
     */
    public List<T> findBy(String name, Object value) {
        return this.findBy(this.entityClass, name, value);
    }

    /**
     * 根据name,value进行模糊查询.
     *
     * @param name 字段名
     * @param value 用来做模糊查询的字段值
     * @return 查询结果
     */
    public List<T> findByLike(String name, Object value) {
        return this.findByLike(this.entityClass, name, value);
    }

    /**
     * 查询唯一记录.
     *
     * @param name 字段名
     * @param value 字段值
     * @return 实例
     */
    public T findUniqueBy(String name, Object value) {
        return this.findUniqueBy(this.entityClass, name, value);
    }

    /**
     * 获得总记录数.
     *
     * @return 总数
     */
    public Integer getCount() {
        return this.getCount(this.entityClass);
    }

    // ====================================================
    // 分页
    // ====================================================

    /**
     * 分页查询函数，根据entityClass和查询条件参数创建默认的<code>Criteria</code>.
     *
     * @param pageNo 当前页号
     * @param pageSize 每页最大记录数
     * @param criterions 条件
     * @return 含总记录数和当前页数据的Page对象.
     */
    public Page pagedQuery(int pageNo, int pageSize,
        Criterion... criterions) {
        return this.pagedQuery(this.entityClass, pageNo, pageSize,
            criterions);
    }

    /**
     * 分页查询函数，根据entityClass和查询条件参数,排序参数创建默认的<code>Criteria</code>.
     *
     * @param pageNo 当前页号
     * @param pageSize 每页最大记录数
     * @param orderBy 排序字段名
     * @param isAsc 是否正序
     * @param criterions 条件
     * @return 含总记录数和当前页数据的Page对象.
     */
    public Page pagedQuery(int pageNo, int pageSize, String orderBy,
        boolean isAsc, Criterion... criterions) {
        return this.pagedQuery(this.entityClass, pageNo, pageSize,
            orderBy, isAsc, criterions);
    }
}
