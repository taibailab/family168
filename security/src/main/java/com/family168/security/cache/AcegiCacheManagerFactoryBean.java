package com.family168.security.cache;

import java.util.Iterator;
import java.util.List;

import com.family168.security.resource.Resource;
import com.family168.security.service.AuthenticationService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import org.springframework.security.providers.dao.UserCache;
import org.springframework.security.userdetails.UserDetails;

import org.springframework.util.Assert;


/**
 * AcegiCacheManagerFactoryBean 负责初始化缓存后生成AcegiCacheManager.
 * 调用 authenticationService 来获取资源和用户实例，并加入UserCache 和 ResourceCache 中
 * 来自www.springside.org.cn
 *
 * @author cac
 * @author Lingo
 * @since 2007-03-22
 * @version 1.0
 */
public class AcegiCacheManagerFactoryBean implements FactoryBean,
    InitializingBean {
    /**
     * logger.
     */
    private static Log logger = LogFactory.getLog(AcegiCacheManagerFactoryBean.class);

    /**
     * 缓存管理器.
     */
    private AcegiCacheManager acegiCacheManager;

    /**
     * 授权服务.
     */
    private AuthenticationService authenticationService = null;

    //-------resource caches---------------------
    /**
     * 用户缓存.
     */
    private UserCache userCache = null;

    /**
     * 资源缓存.
     */
    private ResourceCache resourceCache = null;

    //-------org.springframework.beans.factory.FactoryBean-----------
    /**
     * @see org.springframework.beans.factory.FactoryBean#getObject().
     * @return Object.
     * @throws Exception 异常
     */
    public Object getObject() throws Exception {
        return acegiCacheManager;
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObjectType().
     * @return Class
     */
    public Class getObjectType() {
        if (acegiCacheManager != null) {
            return acegiCacheManager.getClass();
        } else {
            return AcegiCacheManager.class;
        }
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#isSingleton().
     * @return boolean if it is a singleton
     */
    public boolean isSingleton() {
        return true;
    }

    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet().
     * @throws Exception 异常
     */
    public void afterPropertiesSet() throws Exception {
        logger.info("Initializing AcegiCacheManager");
        Assert.notNull(userCache, "userCache should not be null");
        Assert.notNull(resourceCache, "resourceCache should not be null");
        Assert.notNull(authenticationService,
            "Authentication Service should not be null");

        //初始化缓存
        List<UserDetails> users = authenticationService.getUsers();

        for (Iterator iter = users.iterator(); iter.hasNext();) {
            UserDetails user = (UserDetails) iter.next();
            userCache.putUserInCache(user);
        }

        List<Resource> rescs = authenticationService.getResources();

        for (Iterator iter = rescs.iterator(); iter.hasNext();) {
            Resource resc = (Resource) iter.next();
            resourceCache.putResourceInCache(resc);
        }

        //生成 acegiCacheManager
        this.acegiCacheManager = new AcegiCacheManager(userCache,
                resourceCache);
    }

    //-------------setters-----------
    /**
     * @param acegiCacheManager 缓存管理器.
     */
    public void setAcegiCacheManager(AcegiCacheManager acegiCacheManager) {
        this.acegiCacheManager = acegiCacheManager;
    }

    /**
     * @param authenticationService 授权服务.
     */
    public void setAuthenticationService(
        AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * @param userCache 用户缓存.
     */
    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }

    /**
     * @param resourceCache 资源缓存.
     */
    public void setResourceCache(ResourceCache resourceCache) {
        this.resourceCache = resourceCache;
    }
}
