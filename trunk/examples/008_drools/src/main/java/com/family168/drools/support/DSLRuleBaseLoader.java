package com.family168.drools.support;

import java.io.InputStreamReader;
import java.io.Reader;

import java.util.Properties;

import org.drools.RuleBase;

import org.drools.compiler.PackageBuilder;

import org.springframework.core.io.Resource;


/**
 * Drools Rule RuleBaseLoader from drl文件.
 *
 * @author anders
 * @author calvin
 * @author Schweigen
 */
public class DSLRuleBaseLoader extends AbstractFileRuleBaseLoader {
    Properties mappings = null;

    public void setRuleFiles(Properties mappings) {
        this.mappings = mappings;
    }

    /**
     * @see org.springside.plugins.jbossrules.RuleBaseLoader#buildRuleBase()
     */
    public RuleBase buildRuleBase() throws Exception {
        for (Object key : mappings.keySet()) {
            PackageBuilder builder = new PackageBuilder();
            Reader dsl = new InputStreamReader(getClass()
                                                   .getResourceAsStream((String) key),
                    "UTF-8");
            Resource[] resources = this.getResources(mappings.getProperty(
                        (String) key));

            for (Resource resource : resources) {
                Reader source = new InputStreamReader(resource
                        .getInputStream(), "UTF-8");
                builder.addPackageFromDrl(source, dsl);
            }

            ruleBase.addPackage(builder.getPackage());
        }

        return ruleBase;
    }
}
