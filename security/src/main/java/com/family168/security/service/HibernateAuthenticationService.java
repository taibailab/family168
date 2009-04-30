package com.family168.security.service;

import java.util.ArrayList;
import java.util.List;

import com.family168.security.AuthenticationHelper;
import com.family168.security.UserDetailsBuilder;
import com.family168.security.domain.Resc;
import com.family168.security.domain.Role;
import com.family168.security.domain.User;
import com.family168.security.resource.Resource;

import org.hibernate.Session;

import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;


/**
 * 借用hibernate获得权限数据.
 * 来自www.springside.org.cn
 *
 * @author Lingo
 * @since 2008-04-22
 * @version 1.0
 */
public class HibernateAuthenticationService extends HibernateDaoSupport
    implements AuthenticationService {
    /**
     * 角色前缀.
     */
    private String rolePrefix = "";

    /**
     * @param rolePrefix 角色前缀.
     */
    public void setRolePrefix(String rolePrefix) {
        this.rolePrefix = rolePrefix;
    }

    /**
     * 获取所有用户实例.
     *
     * @see com.family168.security.service.AuthenticationService#getUsers()
     * @return List
     */
    public List<UserDetails> getUsers() {
        return (List<UserDetails>) this.getHibernateTemplate()
                                       .execute(new UserCallback(
                rolePrefix));
    }

    /**
     * 获取所有资源实例.
     *
     * @see com.family168.security.service.AuthenticationService#getResources()
     * @return List
     */
    public List<Resource> getResources() {
        return (List<Resource>) this.getHibernateTemplate()
                                    .execute(new RescCallback(rolePrefix));
    }

    /**
     * 写到callback里，避免出现延迟加载问题.
     */
    public static class RescCallback implements HibernateCallback {
        /** * 角色前缀. */
        private String rolePrefix = "";

        /**
         * 构造方法.
         *
         * @param rolePrefix 角色前缀
         */
        public RescCallback(String rolePrefix) {
            this.rolePrefix = rolePrefix;
        }

        /**
         * 保存资源.
         *
         * @param session Session
         * @return 资源
         */
        public Object doInHibernate(Session session) {
            List<Resc> rescs = session.createCriteria(Resc.class).list();

            List<Resource> authResources = new ArrayList<Resource>();

            for (Resc resc : rescs) {
                GrantedAuthority[] arrayAuths = AuthenticationHelper
                    .convertToGrantedAuthority(getAuthsByResource(resc));
                authResources.add(new Resource(resc.getResString(),
                        resc.getResType(), arrayAuths));
            }

            return authResources;
        }

        /**
         * 使用资源获得权限.
         *
         * @param resc Resc
         * @return 权限列表
         */
        private List<GrantedAuthorityImpl> getAuthsByResource(Resc resc) {
            List<GrantedAuthorityImpl> list = new ArrayList<GrantedAuthorityImpl>();

            for (Role role : resc.getRoles()) {
                String roleName = rolePrefix + role.getName();
                GrantedAuthorityImpl authority = new GrantedAuthorityImpl(roleName);
                list.add(authority);
            }

            return list;
        }
    }

    /**
     * 写到callback里，可以避免出现延迟加载的问题.
     */
    public static class UserCallback implements HibernateCallback {
        /** * 角色前缀. */
        private String rolePrefix = "";

        /**
                 * 构造方法.
                 *
                 * @param rolePrefix String
                 */
        public UserCallback(String rolePrefix) {
            this.rolePrefix = rolePrefix;
        }

        /**
         * 添加用户.
         *
         * @param session 会话
         * @return Object
         */
        public Object doInHibernate(Session session) {
            List<User> users = session.createCriteria(User.class).list();

            List<UserDetails> authUsers = new ArrayList<UserDetails>();

            for (User user : users) {
                GrantedAuthority[] arrayAuths = AuthenticationHelper
                    .convertToGrantedAuthority(getAuthsByUser(user));
                authUsers.add(UserDetailsBuilder.createUser(
                        user.getUsername(), user.getPassword(),
                        user.isEnabled(), arrayAuths));
            }

            return authUsers;
        }

        /**
         * 通过用户获得授权.
         *
         * @param user User
         * @return List
         */
        private List<GrantedAuthorityImpl> getAuthsByUser(User user) {
            List<GrantedAuthorityImpl> list = new ArrayList<GrantedAuthorityImpl>();

            for (Role role : user.getRoles()) {
                String roleName = this.rolePrefix + role.getName();
                GrantedAuthorityImpl authority = new GrantedAuthorityImpl(roleName);
                list.add(authority);
            }

            return list;
        }
    }
}
