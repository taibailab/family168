package com.family168.security.manager;

import java.util.List;

import com.family168.core.hibernate.HibernateEntityDao;

import com.family168.security.AuthenticationHelper;
import com.family168.security.cache.AcegiCacheManager;
import com.family168.security.domain.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.acls.AccessControlEntry;
import org.springframework.security.acls.MutableAcl;
import org.springframework.security.acls.MutableAclService;
import org.springframework.security.acls.NotFoundException;
import org.springframework.security.acls.Permission;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.objectidentity.ObjectIdentity;
import org.springframework.security.acls.objectidentity.ObjectIdentityImpl;
import org.springframework.security.acls.sid.PrincipalSid;
import org.springframework.security.acls.sid.Sid;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;


/**
 * @author Lingo.
 * @since 2007年08月18日 下午 20时19分00秒578
 */
public class UserManager extends HibernateEntityDao<User> {
    /** * logger. */
    private static Logger logger = LoggerFactory.getLogger(UserManager.class);

    /**
     * acegiCacheManager.
     */
    private AcegiCacheManager acegiCacheManager = null;

    /** * mutable acl service. */
    private MutableAclService mutableAclService = null;

    /**
     * 根据用户名和密码获得登陆的用户.
     *
     * @param loginId 用户名
     * @param password 密码
     * @return User 登陆用户
     */
    public User getUserByLoginidAndPasswd(String loginId, String password) {
        String hql = "from User u where u.username=? and u.password=?";

        List<User> list = getSession().createQuery(hql)
                              .setString(0, loginId).setString(1, password)
                              .list();

        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 删除用户时需在cache移除用户.
     *
     * @param o user
     */
    @Override
    public void remove(Object o) {
        super.remove(o);

        if (o instanceof User) {
            User user = (User) o;
            removeUserInCache(((User) o).getUsername());

            // Delete the ACL information as well
            ObjectIdentity oid = new ObjectIdentityImpl(user.getClass(),
                    user.getId());
            mutableAclService.deleteAcl(oid, false);

            if (logger.isDebugEnabled()) {
                logger.debug("Deleted user " + user
                    + " including ACL permissions");
            }
        }
    }

    /**
     * 保存User前需要检查当前用户名是否有更改，若有则需先在cache中去除再重新加入.
     *
     * @param o Object User type only.
     */
    @Override
    public void save(Object o) {
        User user = (User) o;
        boolean isNew = (user.getId() == null);
        String orginName = "";

        if (!isNew) {
            User orginUser = get(user.getId());
            orginName = orginUser.getUsername();
            getHibernateTemplate().evict(orginUser);
        }

        super.save(user);

        if (isNew) {
            saveUserInCache(user);

            // Grant the current principal administrative permission to the contact
            //addPermission(user, new PrincipalSid(getUsername()),
            //    BasePermission.ADMINISTRATION);
            //if (logger.isDebugEnabled()) {
            //    logger.debug("Created object " + user
            //        + " and granted admin permission to recipient "
            //        + getUsername());
            //}
        } else {
            user = get(user.getId());
            removeUserInCache(orginName);
            saveUserInCache(user);
        }
    }

    // ------------------------------------------------------------------------

    /**
     * 注意参数中的User需要已经与hibernate session关联，否则无法lazyload得到roles.
     *
     * @param user 用户
     */
    private void saveUserInCache(User user) {
        GrantedAuthority[] authorities = AuthenticationHelper
            .convertToGrantedAuthority(user.getRoles(), "name");

        //logger.info(java.util.Arrays.asList());
        if (user.getUsername() != null) {
            acegiCacheManager.addUser(user.getUsername(),
                user.getPassword(), user.isEnabled(), true, true, true,
                authorities);
        }
    }

    /**
     * 根据用户名，从缓存中删除用户.
     *
     * @param username 用户名
     */
    public void removeUserInCache(String username) {
        acegiCacheManager.removeUser(username);
    }

    /**
     * 添加权限.
     *
     * @param user User
     * @param recipient Sid
     * @param permission Permission
     */
    public void addPermission(User user, Sid recipient,
        Permission permission) {
        MutableAcl acl;
        ObjectIdentity oid = new ObjectIdentityImpl(user.getClass(),
                user.getId());

        try {
            acl = (MutableAcl) mutableAclService.readAclById(oid);
        } catch (NotFoundException nfe) {
            acl = mutableAclService.createAcl(oid);
        }

        acl.insertAce(acl.getEntries().length, permission, recipient, true);
        mutableAclService.updateAcl(acl);

        if (logger.isDebugEnabled()) {
            logger.debug("Added permission " + permission + " for Sid "
                + recipient + " object " + user);
        }
    }

    /**
     * 删除权限.
     *
     * @param user User
     * @param recipient Sid
     * @param permission Permission
     */
    public void deletePermission(User user, Sid recipient,
        Permission permission) {
        ObjectIdentity oid = new ObjectIdentityImpl(user.getClass(),
                user.getId());
        MutableAcl acl = (MutableAcl) mutableAclService.readAclById(oid);

        // Remove all permissions associated with this particular recipient (string equality to KISS)
        AccessControlEntry[] entries = acl.getEntries();

        for (int i = 0; i < entries.length; i++) {
            if (entries[i].getSid().equals(recipient)
                    && entries[i].getPermission().equals(permission)) {
                acl.deleteAce(i);
            }
        }

        mutableAclService.updateAcl(acl);

        if (logger.isDebugEnabled()) {
            logger.debug("Deleted object " + user
                + " ACL permissions for recipient " + recipient);
        }
    }

    /** * @return user name. */
    protected String getUsername() {
        Authentication auth = SecurityContextHolder.getContext()
                                                   .getAuthentication();

        if (auth.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) auth.getPrincipal()).getUsername();
        } else {
            return auth.getPrincipal().toString();
        }
    }

    /**
     * @param acegiCacheManager AcegiCacheManager.
     */
    public void setAcegiCacheManager(AcegiCacheManager acegiCacheManager) {
        this.acegiCacheManager = acegiCacheManager;
    }

    /** * @param mutableAclService mutable acl service. */
    public void setMutableAclService(MutableAclService mutableAclService) {
        this.mutableAclService = mutableAclService;
    }
}
