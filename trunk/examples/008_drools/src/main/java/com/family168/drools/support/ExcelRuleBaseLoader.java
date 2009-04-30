/*
 * ExcelRuleBaseLoader.java
 *
 * Created on 2006-4-30
 * Original Author: Schweigen
 *
 * Changes
 * ---------
 * $Log$
 *
 */
package com.family168.drools.support;

import java.io.StringReader;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;

import org.drools.compiler.PackageBuilder;

import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;

import org.springframework.core.io.Resource;


/**
 * 从Excel 文件中读取规则，构建RuleBase
 *
 * @author Schweigen
 */
public class ExcelRuleBaseLoader extends AbstractFileRuleBaseLoader {
    public RuleBase buildRuleBase() throws Exception {
        RuleBase ruleBase = RuleBaseFactory.newRuleBase();
        SpreadsheetCompiler converter = new SpreadsheetCompiler();
        Resource[] resources = getRuleFiles(ruleFiles);

        for (Resource resource : resources) {
            PackageBuilder builder = new PackageBuilder();
            String drl = converter.compile(resource.getInputStream(),
                    InputType.XLS);
            builder.addPackageFromDrl(new StringReader(drl));

            org.drools.rule.Package pkg = builder.getPackage();
            ruleBase.addPackage(pkg);
        }

        return ruleBase;
    }
}
