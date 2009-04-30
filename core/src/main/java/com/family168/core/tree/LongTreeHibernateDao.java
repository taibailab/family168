package com.family168.core.tree;

import java.util.List;

import com.family168.core.hibernate.HibernateEntityDao;


/**
 * 对tree进行dao操作的基类.
 *
 * @author Lingo
 * @since 2007-09-15
 * @param <T> TreeNode
 */
public class LongTreeHibernateDao<T extends TreeNode<T>>
    extends HibernateEntityDao<T> {
    /**
     * 如果数据库中不存在指定name的记录，就将此条记录插入数据库，并返回对应的实体类.
     * 如果数据库中已经存在指定name的记录，就返回对应的实体类
     *
     * @param name 数据字典的name
     * @return T 返回对应的实体类
     */
    public T createOrGet(String name) {
        // 输入的名称不应该为空.
        if ((name == null) || "".equals(name.trim())) {
            return null;
        }

        String beanName = name.trim();

        List<T> list = findBy("name", beanName);

        // 如果找到记录，应该是只有一项目，就返回这条记录的实体类
        if (list.size() > 0) {
            return list.get(0);
        } else {
            try {
                // 如果没找到记录，需要把这个数据字典保存进数据库
                T namedEntityBean = (T) getEntityClass().newInstance();
                namedEntityBean.setName(beanName);
                save(namedEntityBean);

                return namedEntityBean;
            } catch (InstantiationException ex) {
                // 如果T是抽象类，调用getEntityClass().newInstance()就会抛这个异常
                System.err.println(ex);
            } catch (IllegalAccessException ex) {
                // 没有访问权限的异常，现在还没想到达成的方法
                System.err.println(ex);
            }

            return null;
        }
    }

    /**
     * 读取顶端的队列.
     *
     * @return List.
     */
    public List<T> loadTops() {
        return find("from " + getEntityClass().getName()
            + " where parent is null");
    }

    /**
     * 读取顶级树节点.
     *
     * @param columnName 列名
     * @param sort asc/desc
     * @return List
     */
    public List<T> loadTops(String columnName, String sort) {
        return find("from " + getEntityClass().getName()
            + " where parent is null order by " + columnName + " " + sort);
    }
}
