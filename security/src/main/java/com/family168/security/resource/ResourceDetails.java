package com.family168.security.resource;

import java.io.Serializable;

import org.springframework.security.GrantedAuthority;


/**
 * 提供资源信息.
 * 来自www.springside.org.cn
 *
 * @author cac
 * @author Lingo
 * @since 2007-03-22
 * @version 1.0
 */
public interface ResourceDetails extends Serializable {
    /**
     * 资源串.
     *
     * @return resource string
     */
    String getResString();

    /**
     * 资源类型,如URL,FUNCTION.
     *
     * @return resource type
     */
    String getResType();

    /**
     * 返回属于该resource的authorities.
     *
     * @return array.
     */
    GrantedAuthority[] getAuthorities();

    /**
     * @param authorities array.
     */
    void setAuthorities(GrantedAuthority[] authorities);
}
