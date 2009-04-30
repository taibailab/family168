package com.family168.core.hibernate.extend;


/**
 * 标识商业对象不能被删除,只能被设为无效的接口.
 *
 * @author calvin
 */
public interface UndeletableEntity {
    /**
     * 设置状态.
     *
     * @param status 状态
     */
    void setStatus(String status);
}
