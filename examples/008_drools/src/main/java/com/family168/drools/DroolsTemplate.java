package com.family168;

import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.drools.FactHandle;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;

import org.drools.compiler.DrlParser;
import org.drools.compiler.PackageBuilder;

import org.drools.lang.descr.PackageDescr;

import org.springframework.beans.factory.BeanNameAware;

import org.springframework.context.ResourceLoaderAware;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;


/**
 * Drools的封装便利函数,返回WorkingMemory.
 * <p>移植自JBoss Seam的org.jboss.seam.drools.DroolsHandler. 根据注入的RuleBase或规则文件,生成WorkingMemory,并注入事实.</p>
 * <pre>
 * WorkingMemory wm = droolsTemplate.getNewWorkingMemory(order);
 * wm.fireAllRules();
 * </pre>
 *
 * @author calvin
 */
public class DroolsTemplate implements BeanNameAware, ResourceLoaderAware {
    private static final Log log = LogFactory.getLog(DroolsTemplate.class);
    private String beanName;
    private WorkingMemory currentWorkingMemory;
    private Resource dslFile;
    private ResourceLoader resourceLoader;
    private RuleBase ruleBase;
    private String[] ruleFiles;

    /**
     * 返回RuleBase当前的WorkingMemory,并注入相关事实.
     *
     * @param assertObjects 注入的事实,变长参数支持多种输入方式.
     */
    public WorkingMemory getWorkingMemory(Object... assertObjects) {
        if (currentWorkingMemory == null) {
            currentWorkingMemory = getRuleBase().newStatefulSession();
        }

        for (Object object : assertObjects) {
            assertObject(currentWorkingMemory, object);
        }

        return currentWorkingMemory;
    }

    /**
     * 从RuleBase生成新的WorkingMemory,并注入相关事实. 生成新的WorkingMemory,而不是当前WorkingMemory,主要用于动态增减规则.
     *
     * @param assertObjects 注入的事实,变长参数支持多种输入方式.
     */
    public WorkingMemory getNewWorkingMemory(Object... assertObjects) {
        WorkingMemory newWorkingMemory = getRuleBase().newStatefulSession();

        for (Object object : assertObjects) {
            assertObject(newWorkingMemory, object);
        }

        return newWorkingMemory;
    }

    /**
     * 往workingMemory中注入事实的函数,如果workingMemory中已存在该事实,进行更新.
     */
    public void assertObject(WorkingMemory workingMemory, Object element) {
        if (element == null) {
            return;
        }

        FactHandle fact = workingMemory.getFactHandle(element);

        if (fact == null) {
            workingMemory.insert(element);
        } else {
            workingMemory.update(fact, element);
        }
    }

    private RuleBase getRuleBase() {
        if (ruleBase == null) {
            try {
                compileRuleBase();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return ruleBase;
    }

    /**
     * 根据dsl,ruleFiles编译RuleBase.
     */
    private void compileRuleBase() throws Exception {
        PackageBuilder builder = new PackageBuilder();
        // add the package to a rulebase
        ruleBase = RuleBaseFactory.newRuleBase();

        if (ruleFiles != null) {
            // 读入DSL
            Reader dslReader = null;

            if (dslFile != null) {
                dslReader = new InputStreamReader(dslFile.getInputStream(),
                        "UTF-8");
            }

            // 读入DRL
            ResourcePatternResolver resolver = ResourcePatternUtils
                .getResourcePatternResolver(resourceLoader);

            for (String rulePattern : ruleFiles) {
                Resource[] rules = resolver.getResources(rulePattern);

                for (Resource ruleFile : rules) {
                    PackageDescr packageDescr;
                    Reader drlReader = new InputStreamReader(ruleFile
                            .getInputStream(), "UTF-8");

                    // 如果有dsl定义文件,同时编译dsl
                    if (dslFile != null) {
                        packageDescr = new DrlParser().parse(drlReader,
                                dslReader);
                    } else {
                        packageDescr = new DrlParser().parse(drlReader);
                    }

                    // pre build the package
                    builder.addPackage(packageDescr);
                }
            }

            ruleBase.addPackage(builder.getPackage());
        } else {
            log.warn("DroolsTemplate" + beanName
                + "didn't set the rule files");
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.beans.factory.BeanNameAware#setBeanName(java.lang.String)
     */
    public void setBeanName(String name) {
        beanName = name;
    }

    public void setDslFile(Resource dslFile) {
        this.dslFile = dslFile;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.context.ResourceLoaderAware#setResourceLoader(org.springframework.core.io.ResourceLoader)
     */
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void setRuleFiles(String[] ruleFiles) {
        this.ruleFiles = ruleFiles;
    }
}
