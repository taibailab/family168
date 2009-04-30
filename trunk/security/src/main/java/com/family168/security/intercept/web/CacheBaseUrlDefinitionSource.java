package com.family168.security.intercept.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.family168.security.AuthenticationHelper;
import com.family168.security.cache.AcegiCacheManager;
import com.family168.security.resource.Resource;
import com.family168.security.resource.ResourceDetails;

import org.apache.commons.collections.CollectionUtils;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.intercept.web.DefaultFilterInvocationDefinitionSource;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;


/**
 * 在resourceCache中获取当前调用方法相对应类型为 Url 的Resouce实例.
 * lookupAttributes(String url) 方法被{@link org.acegisecurity.intercept.web.FilterSecurityInterceptor} 调用
 * useAntPath 是否选用ant path的匹配模式
 * protectAllResource 是否默认情况下所有的资源都需要受保护
 * convertUrlToLowercaseBeforeComparison 是否需要把Url转为小写后再进行比较
 * 来自www.springside.org.cn
 *
 * @author cac
 * @author Lingo
 * @since 2007-03-22
 * @version 1.0
 */
public class CacheBaseUrlDefinitionSource
    extends DefaultFilterInvocationDefinitionSource {
    /**
     * 是否在比较前把url变成小写.
     */
    private boolean convertUrlToLowercaseBeforeComparison = false;

    /**
     * 是否使用ant的path格式.
     */
    private boolean useAntPath = true;

    /**
     * 是否保护所有资源.
     */
    private boolean protectAllResource = false;

    /**
     * ant路径匹配器.
     */
    private final PathMatcher pathMatcher = new AntPathMatcher();

    /**
     * perl5匹配器.
     */
    private final PatternMatcher matcher = new Perl5Matcher();

    /**
     * acegiCacheManager.
     */
    private AcegiCacheManager acegiCacheManager = null;

    /** * 构造方法. */
    public CacheBaseUrlDefinitionSource() {
        super(null, new LinkedHashMap());
    }

    /**
     * @param acegiCacheManager acegi cache manager.
     */
    public void setAcegiCacheManager(AcegiCacheManager acegiCacheManager) {
        this.acegiCacheManager = acegiCacheManager;
    }

    /**
     * 返回当前URL对应的权限.
     *
     * @see org.acegisecurity.intercept.web.AbstractFilterInvocationDefinitionSource#lookupAttributes(java.lang.String)
     * @param url url
     * @param method http方法
     * @return ConfigAttributeDefinition
     */
    @Override
    public ConfigAttributeDefinition lookupAttributes(String url,
        String method) {
        url = preDealUrl(url, isUseAntPath(),
                isConvertUrlToLowercaseBeforeComparison());

        List<String> urls = acegiCacheManager
            .getResourcesByType(Resource.RESOURCE_TYPE_URL);

        //倒叙排序所有获取的url
        if (urls == null) {
            urls = new ArrayList<String>(0);
        }

        orderUrls(urls);

        //logger.info(urls);
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

        for (Iterator iterator = urls.iterator(); iterator.hasNext();) {
            String resString = (String) iterator.next();

            //以首先匹配成功的资源的权限作为当前Url的权限
            if (isResourceMatch(isUseAntPath(), url, resString)) {
                ResourceDetails urlResource = acegiCacheManager
                    .getResourceFromCache(resString);

                if (urlResource != null) {
                    CollectionUtils.addAll(authorities,
                        urlResource.getAuthorities());

                    break;
                }
            }
        }

        return AuthenticationHelper.getCadByAuthorities(authorities,
            isProtectAllResource());
    }

    /**
     * 把url资源按倒序排序.
     * @param urls url list
     */
    private void orderUrls(List<String> urls) {
        Collections.sort(urls);
        Collections.reverse(urls);
    }

    /**
     * 根据是否使用UseAntPath和是否字符小写化来预先格式化url.
     *
     * @param url url
     * @param isUseAntPath boolean
     * @param isToLowercase boolean
     * @return String
     */
    private String preDealUrl(String url, boolean isUseAntPath,
        boolean isToLowercase) {
        if (isUseAntPath) {
            // Strip anything after a question mark symbol, as per SEC-161.
            int firstQuestionMarkIndex = url.lastIndexOf("?");

            if (firstQuestionMarkIndex != -1) {
                url = url.substring(0, firstQuestionMarkIndex);
            }
        }

        if (isToLowercase) {
            url = url.toLowerCase(Locale.CHINA);
        }

        return url;
    }

    /**
     * 查看当前url和资源中的url是否匹配.
     *
     * @param isUseAntPath Boolean
     * @param runningUrl String
     * @param rescUrl String
     * @return boolean
     */
    private boolean isResourceMatch(Boolean isUseAntPath,
        String runningUrl, String rescUrl) {
        if (isUseAntPath) {
            return pathMatcher.match(rescUrl, runningUrl);
        } else {
            Pattern compiledPattern;
            Perl5Compiler compiler = new Perl5Compiler();

            try {
                compiledPattern = compiler.compile(rescUrl,
                        Perl5Compiler.READ_ONLY_MASK);
            } catch (MalformedPatternException mpe) {
                throw new IllegalArgumentException(
                    "Malformed regular expression: " + rescUrl, mpe);
            }

            return matcher.matches(runningUrl, compiledPattern);
        }
    }

    /**
     * @return Iterator.
     */
    public Collection getConfigAttributeDefinitions() {
        return null;
    }

    //---------getters and setters---------------------
    /**
     * @param convertUrlToLowercaseBeforeComparison boolean.
     */
    public void setConvertUrlToLowercaseBeforeComparison(
        boolean convertUrlToLowercaseBeforeComparison) {
        this.convertUrlToLowercaseBeforeComparison = convertUrlToLowercaseBeforeComparison;
    }

    /**
     * @return convertUrlToLowercaseBeforeComparison.
     */
    public boolean isConvertUrlToLowercaseBeforeComparison() {
        return convertUrlToLowercaseBeforeComparison;
    }

    /**
     * @return useAntPath.
     */
    public boolean isUseAntPath() {
        return useAntPath;
    }

    /**
     * @param useAntPath boolean.
     */
    public void setUseAntPath(boolean useAntPath) {
        this.useAntPath = useAntPath;
    }

    /**
     * @param protectAllResource boolean.
     */
    public void setProtectAllResource(boolean protectAllResource) {
        this.protectAllResource = protectAllResource;
    }

    /**
     * @return boolean.
     */
    public boolean isProtectAllResource() {
        return protectAllResource;
    }
}
