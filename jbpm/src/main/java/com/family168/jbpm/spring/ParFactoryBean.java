package com.family168.jbpm.spring;

import java.net.URL;

import org.jbpm.graph.def.ProcessDefinition;

import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import org.springframework.core.io.Resource;


/**
 * 可以发布par的工厂类.
 *
 * @author Lingo
 */
public class ParFactoryBean implements FactoryBean, InitializingBean {
    /** * 流程定义. */
    private ProcessDefinition processDefinition = null;

    /** * 资源. */
    private Resource definitionLocation = null;

    /**
     * initializingBean中定义的，在applicationContext.xml读取时，初始化bean的方法.
     *
     * @throws Exception 异常
     */
    public void afterPropertiesSet() throws Exception {
        if (this.definitionLocation == null) {
            throw new FatalBeanException(
                "Property [definitionLocation] of class ["
                + ParFactoryBean.class.getName() + "] is required.");
        }

        URL url = this.definitionLocation.getURL();
        System.out.println(url);
        this.processDefinition = ParUtils.parsePar(url);
    }

    /**
     * @return Object.
     *
     * @throws Exception 异常
     */
    public Object getObject() throws Exception {
        return this.processDefinition;
    }

    /**
     * @return 返回的类型.
     */
    public Class getObjectType() {
        if (processDefinition == null) {
            return ProcessDefinition.class;
        } else {
            return processDefinition.getClass();
        }
    }

    /**
     * @return 是否是单例.
     */
    public boolean isSingleton() {
        return true;
    }

    /** * @param definitionLocation 资源的位置. */
    public void setDefinitionLocation(Resource definitionLocation) {
        this.definitionLocation = definitionLocation;
    }
}
