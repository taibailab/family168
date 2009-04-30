package com.family168.security;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.User;
import org.springframework.security.userdetails.UserDetails;


/**
 * 用户信息建造器.
 *
 * @author Lingo
 */
public class UserDetailsBuilder {
    /** protected constructor. */
    protected UserDetailsBuilder() {
    }

    /**
     * 使用参数生成一个用户对象.
     *
     * @param username String
     * @param password String
     * @param enabled boolean
     * @param auths Granted Authority
     * @return user details
     */
    public static UserDetails createUser(String username, String password,
        boolean enabled, GrantedAuthority[] auths) {
        return new User(username, password, enabled, true, true, true,
            auths);
    }
}
