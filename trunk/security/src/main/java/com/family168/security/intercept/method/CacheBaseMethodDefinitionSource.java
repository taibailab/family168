package com.family168.security.intercept.method;

import java.lang.reflect.Method;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.family168.security.AuthenticationHelper;
import com.family168.security.cache.AcegiCacheManager;
import com.family168.security.resource.Resource;
import com.family168.security.resource.ResourceDetails;

import org.aopalliance.intercept.MethodInvocation;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;

import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.intercept.method.MethodDefinitionSource;

import org.springframework.util.Assert;


/**
 * 在resourceCache中获取当前调用方法相对应类型为 Method 的Resouce实例.
 * getAttributes(Object object)方法被
 * {!link org.acegisecurity.intercept.method.aopalliance.MethodSecurityInterceptor} 调用
 * 来自www.springside.org.cn
 *
 * @author cac
 * @author Lingo
 * @since 2007-03-22
 * @version 1.0
 */
public class CacheBaseMethodDefinitionSource
    implements MethodDefinitionSource {
    /**
     * logger.
     */
    private static Log logger = LogFactory.getLog(CacheBaseMethodDefinitionSource.class);

    /**
     * acegi cache manager.
     */
    private AcegiCacheManager acegiCacheManager = null;

    /**
     * protect all resource.
     */
    private boolean protectAllResource = false;

    /**
     * @param acegiCacheManagerIn acegi cache manager.
     */
    public void setAcegiCacheManager(AcegiCacheManager acegiCacheManagerIn) {
        acegiCacheManager = acegiCacheManagerIn;
    }

    // ~ Methods
    // ============================================================================================
    /**
     * @param object object.
     * @return ConfigAttributeDefinition
     */
    public ConfigAttributeDefinition getAttributes(Object object) {
        Assert.notNull(object, "Object cannot be null");

        if (object instanceof MethodInvocation) {
            MethodInvocation miv = (MethodInvocation) object;

            return this.lookupAttributes(miv.getThis().getClass(),
                miv.getMethod());
        }

        if (object instanceof JoinPoint) {
            JoinPoint jp = (JoinPoint) object;
            Class targetClazz = jp.getTarget().getClass();
            String targetMethodName = jp.getStaticPart().getSignature()
                                        .getName();
            Class[] types = ((CodeSignature) jp.getStaticPart()
                                               .getSignature())
                .getParameterTypes();

            if (logger.isDebugEnabled()) {
                logger.debug("Target Class: " + targetClazz);
                logger.debug("Target Method Name: " + targetMethodName);

                for (int i = 0; i < types.length; i++) {
                    logger.debug("Target Method Arg #" + i + ": "
                        + types[i]);
                }
            }

            try {
                return this.lookupAttributes(targetClazz,
                    targetClazz.getMethod(targetMethodName, types));
            } catch (NoSuchMethodException nsme) {
                throw new IllegalArgumentException(
                    "Could not obtain target method from JoinPoint: " + jp,
                    nsme);
            }
        }

        throw new IllegalArgumentException(
            "Object must be a MethodInvocation or JoinPoint");
    }

    /**
     * @param clazz Class.
     * @return boolean
     */
    public boolean supports(Class clazz) {
        return (MethodInvocation.class.isAssignableFrom(clazz)
        || JoinPoint.class.isAssignableFrom(clazz));
    }

    /**
     * 从resourceCache中获取当前方法对应的ResourceDetails{@link ResourceDetails} 最后返回由Role Name.
     * 组装成的ConfigAttributeDefinition(@link ConfigAttributeDefinition)
     *
     * @see org.acegisecurity.intercept.method.AbstractMethodDefinitionSource#lookupAttributes(java.lang.reflect.Method)
     * @param clszz Class
     * @param mi Method
     * @return ConfigAttributeDefinition
     */
    protected ConfigAttributeDefinition lookupAttributes(Class clszz,
        Method mi) {
        Assert.notNull(mi,
            "lookupAttrubutes in the DBMethodDefinitionSource is null");

        // 获取所有的method resources
        List<String> methodRescs = acegiCacheManager.getResourcesByType(Resource.RESOURCE_TYPE_METHOD);

        if (methodRescs == null) {
            return null;
        }

        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

        // 取权限的合集
        for (Iterator iter = methodRescs.iterator(); iter.hasNext();) {
            String methodString = (String) iter.next();

            if (isResourceMatch(clszz, mi, methodString)) {
                ResourceDetails methodResc = acegiCacheManager
                    .getResourceFromCache(methodString);

                CollectionUtils.addAll(authorities,
                    methodResc.getAuthorities());
            }
        }

        return AuthenticationHelper.getCadByAuthorities(authorities,
            isProtectAllResource());
    }

    /**
     * @return Collection = null.
     */
    public Collection getConfigAttributeDefinitions() {
        return null;
    }

    /**
     * 获得配置属性定义.
     *
     * @param method 方法.
     * @param targetClass 目标类
     * @return config attribute definition
     */
    public ConfigAttributeDefinition getAttributes(Method method,
        Class targetClass) {
        return null;
    }

    /**
     * Return if the given method name matches the mapped name.
     * matches.
     *
     * @param clszz Class
     * @param method Method
     * @param methodString method string
     * @return boolean
     */
    public boolean isResourceMatch(Class clszz, Method method,
        String methodString) {
        //logger.info("Class : " + clszz);
        //logger.info("Method : " + method);
        //logger.info("methodString : " + methodString);
        try {
            //split method string, get class name and method name
            int lastDotIndex = methodString.lastIndexOf('.');
            String className = methodString.substring(0, lastDotIndex);
            String methodName = methodString.substring(lastDotIndex + 1);

            Class rescClass = Class.forName(className);

            // 资源中申明的类是否与当前运行的类相同，或是否是当前运行类的父类或者接口
            if (!rescClass.isAssignableFrom(clszz)) {
                return false;
            }

            // 判断方法是否相等
            if (isMethodsNotMatch(method.getName(), methodName)) {
                return false;
            }
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

        return true;
    }

    /**
     * 判断方法是否相同.
     * 支持前后加* 号，如 method* = methodAbc, AbcMethod = *Method
     * 注意方法区分大小写
     *
     * @param runingMethod 当前运行类的方法
     * @param rescMethod 资源中定义的方法
     * @return boolean
     */
    private boolean isMethodsNotMatch(String runingMethod,
        String rescMethod) {
        if (runingMethod.equals(rescMethod)) {
            return false;
        }

        if (rescMethod.endsWith("*")
                && runingMethod.startsWith(rescMethod.substring(0,
                        rescMethod.length() - 1))) {
            return false;
        }

        if (rescMethod.startsWith("*")
                && runingMethod.endsWith(rescMethod.substring(1,
                        rescMethod.length()))) {
            return false;
        }

        return true;
    }

    /**
     * @return boolean protect all resource.
     */
    public boolean isProtectAllResource() {
        return protectAllResource;
    }

    /**
     * @param protectAllResourceIn protect all resource.
     */
    public void setProtectAllResource(boolean protectAllResourceIn) {
        protectAllResource = protectAllResourceIn;
    }
}
