package com.family168.security.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.family168.security.resource.Resource;
import com.family168.security.resource.ResourceDetails;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.providers.dao.UserCache;
import org.springframework.security.providers.dao.cache.EhCacheBasedUserCache;
import org.springframework.security.userdetails.User;
import org.springframework.security.userdetails.UserDetails;


/**
 * AcegiCacheManager是对缓存进行统一管理，以屏蔽其它类对缓存的直接操作.
 * 对缓存中的用户和资源进行初始化、增、删、改、清空等操作
 * 来自www.springside.org.cn
 *
 * @author cac
 * @author Lingo
 * @since 2007-03-22
 * @version 1.0
 */
public class AcegiCacheManager {
    /**
     * logger.
     */
    protected Log logger = LogFactory.getLog(AcegiCacheManager.class);

    /**
     * 用户缓存.
     */
    private UserCache userCache;

    /**
     * 资源缓存.
     */
    private ResourceCache resourceCache;

    /**
     * rescTypeMapp 映射资源类型对应的资源的一对多关系，以便快速查找.
     * 如method类型对应哪些资源实例，url资源类型对应哪些资源实例
     */
    private Map<String, List<String>> rescTypeMapping;

    //-----constructor using fields
    /**
     * 构造方法.
     *
     * @param userCache user cache
     * @param resourceCache resource cache
     */
    public AcegiCacheManager(UserCache userCache,
        ResourceCache resourceCache) {
        this.userCache = userCache;
        this.resourceCache = resourceCache;

        // 获取所有的资源,以初始化 rescTypeMapping
        rescTypeMapping = new HashMap<String, List<String>>();

        List resclist = resourceCache.getAllResources();

        for (Iterator iter = resclist.iterator(); iter.hasNext();) {
            String resString = (String) iter.next();
            ResourceDetails resc = resourceCache.getResourceFromCache(resString);
            List<String> typelist = rescTypeMapping.get(resc.getResType());

            if (typelist == null) {
                typelist = new ArrayList<String>();
                rescTypeMapping.put(resc.getResType(), typelist);
            }

            //logger.info(typelist);
            //logger.info(resc.getResType());
            typelist.add(resString);
        }
    }

    //-----get from cache methods
    /**
     * 根据用户名获得用户信息.
     *
     * @param username user name
     * @return user detail
     */
    public UserDetails getUser(String username) {
        return userCache.getUserFromCache(username);
    }

    /**
     * 根据资源内容获得资源信息.
     *
     * @param resString resource string
     * @return resource detail
     */
    public ResourceDetails getResourceFromCache(String resString) {
        return resourceCache.getResourceFromCache(resString);
    }

    //-----remove from cache methods
    /**
     * 根据用户名删除用户.
     *
     * @param username user name
     */
    public void removeUser(String username) {
        if (username != null) {
            userCache.removeUserFromCache(username);
        }
    }

    /**
     * 删除资源.
     *
     * @param resString resource string
     */
    public void removeResource(String resString) {
        ResourceDetails rd = resourceCache.getResourceFromCache(resString);
        List<String> typeList = rescTypeMapping.get(rd.getResType());
        typeList.remove(resString);
        resourceCache.removeResourceFromCache(resString);
    }

    //------add to cache methods
    /**
     * 添加用户.
     *
     * @param username user name
     * @param password password
     * @param enabled is enable
     * @param accountNonExpired account not expired
     * @param credentialsNonExpired account not expired
     * @param accountNonLocked account not locked
     * @param authorities array
     */
    public void addUser(String username, String password, boolean enabled,
        boolean accountNonExpired, boolean credentialsNonExpired,
        boolean accountNonLocked, GrantedAuthority[] authorities) {
        User user = new User(username, password, enabled,
                accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
        addUser(user);
    }

    /**
     * 添加用户.
     *
     * @param user user detail
     */
    public void addUser(UserDetails user) {
        userCache.putUserInCache(user);
    }

    /**
     * 添加资源.
     *
     * @param resString resource string
     * @param resType resource type
     * @param authorities array
     */
    public void addResource(String resString, String resType,
        GrantedAuthority[] authorities) {
        Resource rd = new Resource(resString, resType, authorities);
        addResource(rd);
    }

    /**
     * 添加资源.
     *
     * @param rd resource detail
     */
    public void addResource(ResourceDetails rd) {
        List<String> typelist = rescTypeMapping.get(rd.getResType());

        if (typelist == null) {
            typelist = new ArrayList<String>();
            rescTypeMapping.put(rd.getResType(), typelist);
        }

        typelist.add(rd.getResString());
        resourceCache.putResourceInCache(rd);
    }

    //  ------renovate cache methods
    /**
     * 刷新用户.
     *
     * @param orgUsername original user name
     * @param username user name
     * @param password password
     * @param enabled is enable
     * @param accountNonExpired account not expired
     * @param credentialsNonExpired account not expired
     * @param accountNonLocked account not locked
     * @param authorities array
     */
    public void renovateUser(String orgUsername, String username,
        String password, boolean enabled, boolean accountNonExpired,
        boolean credentialsNonExpired, boolean accountNonLocked,
        GrantedAuthority[] authorities) {
        removeUser(orgUsername);
        addUser(username, password, enabled, accountNonExpired,
            credentialsNonExpired, accountNonLocked, authorities);
    }

    /**
     * 刷新用户.
     *
     * @param username 用户名
     * @param password 密码
     * @param enabled 可用
     * @param authorities 权限
     */
    public void renovateUser(String username, String password,
        boolean enabled, GrantedAuthority[] authorities) {
        this.renovateUser(username, username, password, enabled, true,
            true, true, authorities);
    }

    /**
     * 刷新用户.
     *
     * @param orgUsername original user name
     * @param user user detail
     */
    public void renovateUser(String orgUsername, UserDetails user) {
        removeUser(orgUsername);
        addUser(user);
    }

    /**
     * 刷新资源.
     *
     * @param orgResString original resource string
     * @param resString resource string
     * @param resType resource type
     * @param authorities array
     */
    public void renovateResource(String orgResString, String resString,
        String resType, GrantedAuthority[] authorities) {
        removeResource(orgResString);
        addResource(resString, resType, authorities);
    }

    /**
     * 刷新资源.
     *
     * @param orgResString original resource string
     * @param rd resource detail
     */
    public void renovateResource(String orgResString, ResourceDetails rd) {
        removeResource(orgResString);
        addResource(rd);
    }

    //-------getters and setters-------------------
    /**
     * 清除资源.
     */
    public void clearResources() {
        rescTypeMapping = new HashMap<String, List<String>>();
        resourceCache.removeAllResources();
    }

    /**
     * FIXME: 感觉这里有问题，重新设置新ResourceCache的时候，没有刷新rescTypeMapping.
     *
     * @param resourceCache resource cache
     */
    public void setResourceCache(ResourceCache resourceCache) {
        this.resourceCache = resourceCache;
    }

    /**
     * FIXME: 感觉这里有问题，重新设置新UserCache的时候，没有刷新rescTypeMapping.
     *
     * @param userCache user cache
     */
    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }

    /**
     * 根据资源类型,在rescTypeMapping职工获取所有该类型资源的对应的resource string.
     *
     * @param resType resource type
     * @return List
     */
    public List<String> getResourcesByType(String resType) {
        return rescTypeMapping.get(resType);
    }

    /**
     * 获取所有资源的对应的resource string.
     *
     * @return List
     */
    public List<String> getAllResources() {
        return resourceCache.getAllResources();
    }

    /**
     * 获取所有用户实例对应的user name.
     *
     * @return List
     */
    public List<String> getAllUsers() {
        EhCacheBasedUserCache ehUserCache = (EhCacheBasedUserCache) userCache;

        return ehUserCache.getCache().getKeys();
    }
}
