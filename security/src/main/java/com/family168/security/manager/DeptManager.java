package com.family168.security.manager;

import java.util.List;

import com.family168.core.hibernate.HibernateEntityDao;

import com.family168.security.domain.Dept;


/**
 * @author Lingo.
 * @since 2007年08月18日 下午 20时19分00秒578
 */
public class DeptManager extends HibernateEntityDao<Dept> {
    /**
     * 如果数据库中不存在指定name的记录，就将此条记录插入数据库，并返回对应的实体类.
     * 如果数据库中已经存在指定name的记录，就返回对应的实体类
     *
     * @param name 数据字典的name
     * @return T 返回对应的实体类
     */
    public Dept createOrGet(String name) {
        // 输入的名称不应该为空.
        if ((name == null) || "".equals(name.trim())) {
            return null;
        }

        String beanName = name.trim();

        List<Dept> list = findBy("name", beanName);

        // 如果找到记录，应该是只有一项目，就返回这条记录的实体类
        if (list.size() > 0) {
            return list.get(0);
        } else {
            // 如果没找到记录，需要把这个数据字典保存进数据库
            Dept entity = new Dept();
            entity.setName(beanName);
            save(entity);

            return entity;
        }
    }

    /**
     * 读取顶端的队列.
     *
     * @return List.
     */
    public List<Dept> loadTops() {
        return find("from Dept where parent is null");
    }

    /**
     * 读取顶级树节点.
     *
     * @param columnName 列名
     * @param sort asc/desc
     * @return List
     */
    public List<Dept> loadTops(String columnName, String sort) {
        return find("from Dept where parent is null order by "
            + columnName + " " + sort);
    }
}
