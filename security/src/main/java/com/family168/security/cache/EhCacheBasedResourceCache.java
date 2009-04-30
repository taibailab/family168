package com.family168.security.cache;

import java.util.List;

import com.family168.security.resource.ResourceDetails;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.dao.DataRetrievalFailureException;


/**
 * ehcache实现的资源缓存.
 * 来自www.springside.org.cn
 *
 * @author Lingo
 * @since 2007-03-22
 * @version 1.0
 */
public class EhCacheBasedResourceCache implements ResourceCache {
    // ~ Static fields/initializers
    // =============================================
    /**
     * logger.
     */
    private static Log logger = LogFactory.getLog(EhCacheBasedResourceCache.class);

    // ~ Instance fields
    // ========================================================
    /**
     * 缓存.
     */
    private Cache cache = null;

    // ~ Methods
    // ================================================================
    /**
     * @param cacheIn cache.
     */
    public void setCache(Cache cacheIn) {
        cache = cacheIn;
    }

    /**
     * @return cache.
     */
    public Cache getCache() {
        return cache;
    }

    /**
     * 根据资源内容，从缓存获得资源细节.
     * @see lg.lag.security.cache.ResourceCache#getResourceFromCache(java.lang.String)
     *
     * @param resString resource string
     * @return ResourceDetails
     */
    public ResourceDetails getResourceFromCache(String resString) {
        Element element = null;

        try {
            element = cache.get(resString);
        } catch (CacheException cacheException) {
            throw new DataRetrievalFailureException("Cache failure: "
                + cacheException.getMessage(), cacheException);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Cache hit: " + (element != null)
                + "; resString: " + resString);
        }

        if (element == null) {
            return null;
        } else {
            return (ResourceDetails) element.getValue();
        }
    }

    /**
     * 添加一个资源信息.
     * @see lg.lag.security.cache.ResourceCache#putResourceInCache(lg.lag.security.model.ResourceDetails)
     *
     * @param resourceDetails 资源信息.
     */
    public void putResourceInCache(ResourceDetails resourceDetails) {
        Element element = new Element(resourceDetails.getResString(),
                resourceDetails);

        if (logger.isDebugEnabled()) {
            logger.debug("Cache put: " + element.getKey());
        }

        this.cache.put(element);
    }

    /**
     * 根据资源内容，删除一个资源.
     * @see lg.lag.security.cache.ResourceCache#removeResourceFromCache(java.lang.String)
     *
     * @param resString resource string
     */
    public void removeResourceFromCache(String resString) {
        if (logger.isDebugEnabled()) {
            logger.debug("Cache remove: " + resString);
        }

        this.cache.remove(resString);
    }

    /**
     * 获得所有资源.
     *
     * @return List
     */
    public List getAllResources() {
        return cache.getKeys();
    }

    /**
     * 删除所有资源.
     */
    public void removeAllResources() {
        cache.removeAll();
    }
}
