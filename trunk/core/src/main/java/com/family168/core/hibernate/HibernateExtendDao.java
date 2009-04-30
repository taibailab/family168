package com.family168.core.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Criteria;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;


/**
 * 支持bba96里的查询方式.
 *
 * @author Lingo
 * @param <T> 泛型
 */
public class HibernateExtendDao<T> extends HibernateEntityDao<T> {
    /** * logger. */
    private static Log logger = LogFactory.getLog(HibernateExtendDao.class);

    /**
     * 根据name,value进行查询，支持bba96里的查询方式.
     *
     * @param name String
     * @param value Object
     * @return List
     */
    @Override
    public List<T> findBy(String name, Object value) {
        return findBy(name, value, "eq");
    }

    /**
     * 根据name,value进行查询，支持eq,like等操作符.
     *
     * @param name String
     * @param value Object
     * @param operator String
     * @return List
     */
    public List<T> findBy(String name, Object value, String operator) {
        return new Helper().process(entityClass, name, value, operator);
    }

    /**
     * 帮助类.
     */
    class Helper {
        /** * 生成唯一id的种子. */
        //private int sed = 0;

        /** * 保存别名哈希表. */
        private Map<String, String> aliasesMap = new HashMap<String, String>();

        /**
         * 执行查询.
         *
         * @param clazz 类型
         * @param name 属性名
         * @param value 属性值
         * @param operator 默认操作方式
         * @return List
         */
        public List process(Class clazz, String name, Object value,
            String operator) {
            Criteria criteria = getSession().createCriteria(clazz);

            String truename = generateAlias(criteria, name, "sed-1");

            if ((truename != null) && operator.equals("eq")) {
                criteria.add(Expression.eq(truename, value));
            }

            return criteria.list();
        }

        /**
         * 生成别名.
         *
         * @param criteria 上级criteria
         * @param name 属性名
         * @param aliasKey 别名关键字
         * @return 生成的别名
         */
        private String generateAlias(Criteria criteria, String name,
            String aliasKey) {
            int aliasPos = StringUtils.indexOf(name, ":");

            if (aliasPos != -1) {
                int joinType = -1;

                // FIXME: 不知道为什么，全连接的时候，roles[:]r.name这种情况，hsqldb和mysql5都报sql错误。
                // 怎么才能支持全连接呢？bba96连个单元测试都没有，不知道怎么用哦。
                if (name.charAt(aliasPos - 1) == '[') {
                    if (name.charAt(aliasPos + 1) == ']') {
                        joinType = CriteriaSpecification.FULL_JOIN;
                    } else {
                        joinType = CriteriaSpecification.LEFT_JOIN;
                    }
                } else {
                    joinType = CriteriaSpecification.INNER_JOIN;
                }

                String trueName = null;

                if (joinType == CriteriaSpecification.FULL_JOIN) {
                    trueName = StringUtils.substring(name, aliasPos + 2);
                } else {
                    trueName = StringUtils.substring(name, aliasPos + 1);
                }

                if (StringUtils.indexOf(trueName, ".") == -1) {
                    logger.error("ERROR:" + name
                        + " -- the parameter name with alias should contain a . char!");

                    return null;
                }

                String alias = StringUtils.substringBefore(trueName, ".");

                //if (aliasesMap == null) {
                //    aliasesMap = new HashMap<String, String>();
                //}
                String aliases = aliasesMap.get(aliasKey);

                if (aliases == null) {
                    aliases = "";
                }

                if (StringUtils.indexOf(aliases, alias + '|') == -1) {
                    if (joinType == CriteriaSpecification.INNER_JOIN) {
                        criteria = criteria.createAlias(StringUtils
                                .substringBefore(name, ":"), alias,
                                joinType);
                    } else {
                        criteria = criteria.createAlias(StringUtils
                                .substringBefore(name, "[:"), alias,
                                joinType);
                    }

                    aliases = aliases + alias + '|';
                    aliasesMap.put(aliasKey, aliases);
                }

                name = trueName;

                if (StringUtils.lastIndexOf(name, ":") != -1) {
                    name = generateAlias(criteria, name, aliasKey);
                }
            }

            return name;
        }
    }
}
