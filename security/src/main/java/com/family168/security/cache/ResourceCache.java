package com.family168.security.cache;

import java.util.List;

import com.family168.security.resource.ResourceDetails;


/**
 * 为 {@link org.springside.components.acegi.resource.Resource} 实例提供缓存.
 * 来自www.springside.org.cn
 *
 * @author cac
 * @author Lingo
 * @since 2007-03-22
 * @version 1.0
 */
public interface ResourceCache {
    /**
     * 根据资源内容，从缓存获得资源细节.
     *
     * @param resString resource string
     * @return ResourceDetails
     */
    ResourceDetails getResourceFromCache(String resString);

    /**
     * 添加一个资源信息.
     *
     * @param resourceDetails 资源信息.
     */
    void putResourceInCache(ResourceDetails resourceDetails);

    /**
     * 根据资源内容，删除一个资源.
     *
     * @param resString resource string
     */
    void removeResourceFromCache(String resString);

    /**
     * 获得所有资源.
     *
     * @return List
     */
    List getAllResources();

    /**
     * 删除所有资源.
     */
    void removeAllResources();
}
