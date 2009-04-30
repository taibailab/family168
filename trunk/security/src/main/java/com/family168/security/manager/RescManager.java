package com.family168.security.manager;

import com.family168.core.hibernate.HibernateEntityDao;

import com.family168.security.AuthenticationHelper;
import com.family168.security.cache.AcegiCacheManager;
import com.family168.security.domain.Resc;
import com.family168.security.resource.Resource;
import com.family168.security.resource.ResourceDetails;

import org.apache.commons.lang.StringUtils;

import org.springframework.security.GrantedAuthority;


/**
 * @author Lingo.
 * @since 2007年08月18日 下午 20时19分00秒578
 */
public class RescManager extends HibernateEntityDao<Resc> {
    /**
     * AcegiCacheManager.
     */
    private AcegiCacheManager acegiCacheManager = null;

    /**
     * @param acegiCacheManager AcegiCacheManager.
     */
    public void setAcegiCacheManager(AcegiCacheManager acegiCacheManager) {
        this.acegiCacheManager = acegiCacheManager;
    }

    /**
     * @param o resource.
     */
    @Override
    public void save(Object o) {
        Resc resc = (Resc) o;
        boolean isNew = (resc.getId() == null);
        String orginString = "";

        if (!isNew) {
            Resc orginResc = get(resc.getId());
            orginString = orginResc.getResString();
            getHibernateTemplate().evict(orginResc);
        }

        super.save(o);

        if (!isNew
                && !StringUtils.equals(resc.getResString(), orginString)) {
            removeRescInCache(orginString);
        }

        saveRescInCache(resc);
    }

    /**
     * @param o resource.
     */
    @Override
    public void remove(Object o) {
        super.remove(o);

        Resc resc = (Resc) o;
        removeRescInCache(resc.getResString());
    }

    /**
     * 把资源保存里缓存.
     *
     * @param resc 资源
     */
    private void saveRescInCache(Resc resc) {
        GrantedAuthority[] authorities = AuthenticationHelper
            .convertToGrantedAuthority(resc.getRoles(), "name");
        ResourceDetails rd = new Resource(resc.getResString(),
                resc.getResType(), authorities);
        acegiCacheManager.addResource(rd);
    }

    /**
     * 从缓存里删除资源.
     *
     * @param resString 资源内容
     */
    public void removeRescInCache(String resString) {
        acegiCacheManager.removeResource(resString);
    }
}
