package com.family168.core.hibernate.extend;

import java.util.List;

import org.hibernate.criterion.Criterion;


/**
 * 定义如果支持Entity不被直接删除必须支持的Operation.
 *
 * @author calvin
 * @param <T> 泛型
 */
public interface UndeleteableEntityOperation<T> {
    // Undelete Entity用到的几个常量,因为要同时兼顾Interface与Annotation,所以集中放此.
    /** 无效值. */
    String UNVALID_VALUE = "-1";

    /** 有效值. */
    String NORMAL_VALUE = "0";

    /** 状态字段名. */
    String STATUS = "status";

    /**
     * 取得所有状态为有效的对象.
     *
     * @return 结果列表
     */
    List<T> getAllValid();

    /**
     * 删除对象，但如果是Undeleteable的entity,设置对象的状态而不是直接删除.
     *
     * @param entity 需要删除的实例
     */
    void remove(Object entity);

    /**
     * 获取过滤已删除对象的hql条件语句.
     *
     * @return hql
     */
    String getUnDeletableHQL();

    /**
     * 获取过滤已删除对象的Criterion条件语句.
     *
     * @return criteria
     */
    Criterion getUnDeletableCriterion();
}
