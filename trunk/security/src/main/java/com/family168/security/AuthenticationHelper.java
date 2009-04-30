package com.family168.security;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.ConfigAttributeEditor;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;


/**
 * 授权帮助类.
 * 来自www.springside.org.cn
 *
 * @author Lingo
 * @since 2007-03-22
 * @version 1.0
 */
public final class AuthenticationHelper {
    /** logger. */
    private static Log logger = LogFactory.getLog(AuthenticationHelper.class);

    /**
     * 工具类的保护构造方法.
     */
    protected AuthenticationHelper() {
    }

    /**
     * 由GrantedAuthority Collection 转为 GrantedAuthority 数组.
     *
     * @param auths 授权集合
     * @return 授权数组
     */
    public static GrantedAuthority[] convertToGrantedAuthority(
        Collection<GrantedAuthorityImpl> auths) {
        return (GrantedAuthority[]) auths.toArray(new GrantedAuthority[auths
            .size()]);
    }

    /**
     * 把Bean中的某个属性的值转化为GrantedAuthority.
     *
     * @param list Collection 一组Bean
     * @param propertyName 属性名
     * @return GrantedAuthority[] GrantedAuthority数组
     */
    public static GrantedAuthority[] convertToGrantedAuthority(
        Collection list, String propertyName) {
        Set<GrantedAuthorityImpl> authorities = new HashSet<GrantedAuthorityImpl>();

        try {
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                String authority = (String) BeanUtils.getProperty(iter.next(),
                        propertyName);
                authorities.add(new GrantedAuthorityImpl(authority));
            }
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }

        //logger.info(authorities);
        return convertToGrantedAuthority(authorities);
    }

    /**
     * 把权限组转为 ConfigAttributeDefinition.
     *
     * @param authorities 权限
     * @param isProtectAllResource 是否保护所有资源
     * true，则所有资源默认为受保护
     * false则只有声明了并且与权限挂钩了的资源才会受保护
     * @return ConfigAttributeDefinition
     */
    public static ConfigAttributeDefinition getCadByAuthorities(
        Collection<GrantedAuthority> authorities,
        boolean isProtectAllResource) {
        if ((authorities == null) || (authorities.size() == 0)) {
            if (isProtectAllResource) {
                return new ConfigAttributeDefinition(Collections.EMPTY_LIST);
            } else {
                return null;
            }
        }

        ConfigAttributeEditor configAttrEditor = new ConfigAttributeEditor();

        //String authoritiesStr = " ";
        StringBuffer buff = new StringBuffer(" ");

        for (Iterator iter = authorities.iterator(); iter.hasNext();) {
            GrantedAuthority authority = (GrantedAuthority) iter.next();
            //authoritiesStr += (authority.getAuthority() + ",");
            buff.append(authority.getAuthority()).append(",");
        }

        String authoritiesStr = buff.toString();

        String authStr = authoritiesStr.substring(0,
                authoritiesStr.length() - 1);
        configAttrEditor.setAsText(authStr);

        return (ConfigAttributeDefinition) configAttrEditor.getValue();
    }
}
