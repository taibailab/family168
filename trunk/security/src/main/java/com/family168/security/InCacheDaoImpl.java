package com.family168.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.support.MessageSourceAccessor;

import org.springframework.security.SpringSecurityMessageSource;
import org.springframework.security.providers.dao.UserCache;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;


/**
 * 登陆时从缓存里获取用户.
 * 而不是像@link org.acegisecurity.acl.basic.jdbc.JdbcDaoImpl 那样从数据库中获取用户实例
 * 实现loadUserByUsername(String username) 方法
 * 来自www.springside.org.cn
 *
 * @author cac
 * @author Lingo
 * @since 2007-03-22
 * @version 1.0
 */
public class InCacheDaoImpl implements UserDetailsService {
    /** * logger. */
    private static Log logger = LogFactory.getLog(InCacheDaoImpl.class);

    /** * 国际化. */
    protected MessageSourceAccessor messages = SpringSecurityMessageSource
        .getAccessor();

    /**
     * 用户缓存.
     */
    private UserCache userCache;

    /**
     * @return UserCase.
     */
    public UserCache getUserCache() {
        return userCache;
    }

    /**
     * @param userCache userCache.
     */
    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }

    /**
     * 根据用户名读取用户信息.
     * 可能抛出异常：
     * throws org.acegisecurity.userdetails.UsernameNotFoundException 找不到用户
     * throws org.springframework.dao.DataAccessException 数据无法访问
     *
     * @param username 用户名
     * @return UserDetails
     */
    public UserDetails loadUserByUsername(String username) {
        logger.debug(username);

        UserDetails ud = getUserCache().getUserFromCache(username);

        logger.debug(ud);

        if (ud != null) {
            return ud;
        } else {
            throw new UsernameNotFoundException(messages.getMessage(
                    "JdbcDaoImpl.notFound", new Object[] {username},
                    "Username {0} not found"), username);
        }
    }
}
