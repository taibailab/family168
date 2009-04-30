package com.family168.manager;

import java.io.IOException;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;

import org.springframework.context.ResourceLoaderAware;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

import org.springframework.stereotype.Service;


@Service
public class RescManager implements InitializingBean, BeanNameAware,
    ResourceLoaderAware {
    private ResourceLoader resourceLoader;

    public void demo() throws IOException {
        ResourcePatternResolver resolver = ResourcePatternUtils
            .getResourcePatternResolver(resourceLoader);
        Resource[] rescs = resolver.getResources(
                "classpath*:spring/applicationContext*.xml");
        System.out.println(rescs);
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void afterPropertiesSet() {
    }

    public void setBeanName(String beanName) {
    }
}
