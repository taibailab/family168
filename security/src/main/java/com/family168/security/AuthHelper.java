package com.family168.security;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;


/**
 * 授权管理类.
 *
 * @author cac
 * @author Lingo
 * @since 2007-03-25
 * @version 1.0
 */
public final class AuthHelper {
    /**
     * 是否已授权.
     */
    public static final String AUTHORIZED = "authorized";

    /**
     * 授权映射.
     */
    public static final Map<String, String> AUTH_MAP;

    static {
        AUTH_MAP = new HashMap<String, String>();
        AUTH_MAP.put("true", "Authorized");
        AUTH_MAP.put("false", "Unauthorized");
    }

    /**
     * 工具类的私有构造方法.
     */
    private AuthHelper() {
    }

    /**
     * 新增或删除多对多关系.
     *
     * @param authSet 授权集合
     * @param authObj 授权对象
     * @param isAuthorized 是否授权
     */
    protected static void saveAuth(Collection authSet, Object authObj,
        boolean isAuthorized) {
        if (isAuthorized) {
            if (authSet.contains(authObj)) {
                authSet.remove(authObj);
            }
        } else {
            if (!authSet.contains(authObj)) {
                authSet.add(authObj);
            }
        }
    }

    /**
     * 调整授权.
     * 从第一个集合参数里依次取出每个元素，如果第二个集合参数里也包含这个元素
     * 就把这个元素的authorized字段设置为true，否则设置为false
     *
     * @param auths 授权集合
     * @param autheds 授权方式
     */
    protected static void judgeAuthorized(Collection auths,
        Collection autheds) {
        Iterator iter = auths.iterator();

        try {
            while (iter.hasNext()) {
                Object resource = iter.next();

                if (autheds.contains(resource)) {
                    BeanUtils.setProperty(resource, "authorized",
                        Boolean.TRUE);
                } else {
                    BeanUtils.setProperty(resource, "authorized",
                        Boolean.FALSE);
                }
            }
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }
}
