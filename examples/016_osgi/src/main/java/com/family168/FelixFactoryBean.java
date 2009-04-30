package com.family168;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.felix.framework.Felix;
import org.apache.felix.framework.util.FelixConstants;

import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;


public class FelixFactoryBean implements FactoryBean, InitializingBean,
    DisposableBean {
    private Felix felix = null;

    public void afterPropertiesSet() {
        Map configMap = new HashMap();
        configMap.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA,
            "com.family168");
        //configMap.put("org.osgi.framework.bootdelegation", "com.family168.*");
        configMap.put("felix.cache.rootdir", "felix.cache.rootdir");

        //
        //felixActivator = new FelixActivator();
        //List list = new ArrayList();
        //list.add(felixActivator);
        //configMap.put(FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP, list);
        try {
            felix = new Felix(configMap);
            felix.start();
        } catch (Exception ex) {
            System.err.println("Could not create framework: " + ex);
            ex.printStackTrace();
        }
    }

    public void destroy() {
        try {
            felix.stop();
            felix.waitForStop(0L);
        } catch (Exception ex) {
            System.err.println("Could not stop framework: " + ex);
            ex.printStackTrace();
        }
    }

    public Object getObject() {
        return this.felix;
    }

    public Class getObjectType() {
        return Felix.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
