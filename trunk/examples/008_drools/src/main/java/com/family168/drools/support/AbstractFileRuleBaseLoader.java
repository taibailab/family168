package com.family168.drools.support;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.family168.drools.RuleBaseLoader;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;


/**
 * 抽象的Rule文件载入类
 *
 * @author Schweigen
 */
public abstract class AbstractFileRuleBaseLoader implements RuleBaseLoader {
    /**
     * RuleBase
     */
    RuleBase ruleBase = RuleBaseFactory.newRuleBase();

    /**
     * 支持通配符形式的规则文件定义
     */
    protected String[] ruleFiles;

    /**
     * Spring的Resource Loader
     */
    protected ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(this.getClass()
                                                                                             .getClassLoader());

    public void setRuleFiles(String[] ruleFiles) {
        this.ruleFiles = ruleFiles;
    }

    /**
     * 分割以，号分格的rule file定义,并用Spring的Resource resolver 匹配资源
     */
    protected Resource[] getRuleFiles(String[] files) throws IOException {
        List resourceList = new ArrayList();

        for (String file : files) {
            Resource[] resources = getResources(file);
            resourceList.addAll(Arrays.asList(resources));
        }

        return (Resource[]) resourceList.toArray(new Resource[] {});
    }

    /**
     * 使用Spring的Resource resolver 匹配资源
     */
    protected Resource[] getResources(String s) throws IOException {
        return resolver.getResources(s);
    }
}
