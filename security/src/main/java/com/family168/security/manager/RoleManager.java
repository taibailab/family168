package com.family168.security.manager;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.family168.core.hibernate.HibernateEntityDao;

import com.family168.security.AuthenticationHelper;
import com.family168.security.UserDetailsBuilder;
import com.family168.security.cache.AcegiCacheManager;
import com.family168.security.domain.Resc;
import com.family168.security.domain.Role;
import com.family168.security.domain.User;
import com.family168.security.resource.ResourceDetails;

import org.apache.commons.lang.ArrayUtils;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;


/**
 * @author Lingo.
 * @since 2007年08月18日 下午 20时19分00秒578
 */
public class RoleManager extends HibernateEntityDao<Role> {
    /**
     * AcegiCacheManager.
     */
    private AcegiCacheManager acegiCacheManager = null;

    /**
     * @param acegiCacheManagerIn acegiCacheManager.
     */
    public void setAcegiCacheManager(AcegiCacheManager acegiCacheManagerIn) {
        acegiCacheManager = acegiCacheManagerIn;
    }

    /**
     * @param o role.
     */
    @Override
    public void save(Object o) {
        super.save(o);

        if (o instanceof Role) {
            flush();

            Role role = (Role) o;

            Set<Resc> rescs = role.getRescs();

            for (Resc resc : rescs) {
                saveRoleInCache(resc);
            }

            Set<User> users = role.getUsers();

            for (User user : users) {
                flushUserAuth(user);
            }
        }
    }

    /**
     * @param o role.
     */
    @Override
    public void remove(Object o) {
        super.remove(o);

        Role role = (Role) o;
        removeAuthoritiesInCache(role.getName());
    }

    /**
     * 将资源保存到缓存里.
     *
     * @param resc 资源
     */
    public void saveRoleInCache(Resc resc) {
        GrantedAuthority[] authorities = AuthenticationHelper
            .convertToGrantedAuthority(resc.getRoles(), "name");

        ResourceDetails rd = acegiCacheManager.getResourceFromCache(resc
                .getResString());
        rd.setAuthorities(authorities);
    }

    /**
     * 刷新用户权限.
     *
     * @param user User
     */
    public void flushUserAuth(User user) {
        GrantedAuthority[] authorities = AuthenticationHelper
            .convertToGrantedAuthority(user.getRoles(), "name");

        acegiCacheManager.renovateUser(user.getUsername(),
            user.getPassword(), user.isEnabled(), authorities);
    }

    /**
     * 删除缓存中的授权.
     *
     * @param authority 授权
     */
    private void removeAuthoritiesInCache(String authority) {
        GrantedAuthorityImpl auth = new GrantedAuthorityImpl(authority);
        List<String> rescs = acegiCacheManager.getAllResources();

        for (Iterator iter = rescs.iterator(); iter.hasNext();) {
            String str = (String) iter.next();
            ResourceDetails resc = acegiCacheManager.getResourceFromCache(str);
            GrantedAuthority[] auths = resc.getAuthorities();
            int idx = ArrayUtils.indexOf(auths, auth);

            if (idx >= 0) {
                auths = (GrantedAuthority[]) ArrayUtils.remove(auths, idx);
                resc.setAuthorities(auths);
            }
        }

        List<String> users = acegiCacheManager.getAllUsers();

        for (Iterator iter = users.iterator(); iter.hasNext();) {
            String username = (String) iter.next();
            UserDetails user = acegiCacheManager.getUser(username);
            GrantedAuthority[] auths = user.getAuthorities();
            int idx = ArrayUtils.indexOf(auths, auth);

            if (idx >= 0) {
                auths = (GrantedAuthority[]) ArrayUtils.remove(auths, idx);
                user = UserDetailsBuilder.createUser(user.getUsername(),
                        user.getPassword(), user.isEnabled(), auths);
                acegiCacheManager.addUser(user);
            }
        }
    }
}
