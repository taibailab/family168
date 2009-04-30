package com.family168.drools.support;

import java.io.InputStreamReader;
import java.io.Reader;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;

import org.drools.compiler.PackageBuilder;

import org.springframework.core.io.Resource;


/**
 * Drools  RuleBaseLoader from drl文件.
 *
 * @author anders
 * @author calvin
 * @author Schweigen
 */
public class DRLRuleBaseLoader extends AbstractFileRuleBaseLoader {
    /**
     * @see org.springside.plugins.jbossrules.RuleBaseLoader#buildRuleBase()
     */
    public RuleBase buildRuleBase() throws Exception {
        RuleBase ruleBase = RuleBaseFactory.newRuleBase();
        Resource[] resources = getRuleFiles(ruleFiles);

        for (Resource resource : resources) {
            PackageBuilder builder = new PackageBuilder();

            Reader source = new InputStreamReader(resource.getInputStream(),
                    "UTF-8");
            builder.addPackageFromDrl(source);
            ruleBase.addPackage(builder.getPackage());
        }

        return ruleBase;
    }
}
