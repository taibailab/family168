package com.family168;

import java.util.*;

import org.apache.felix.framework.Felix;

import org.osgi.framework.*;


public class FelixService {
    private Felix felix;

    public void setFelix(Felix felix) {
        this.felix = felix;
    }

    public BundleContext getBundleContext() {
        return felix.getBundleContext();
    }

    public Bundle[] getBundles() {
        return getBundleContext().getBundles();
    }

    public Bundle install(String location) throws BundleException {
        return getBundleContext().installBundle(location);
    }

    public Bundle getBundle(long id) {
        return getBundleContext().getBundle(id);
    }

    public void start(long id) throws BundleException {
        getBundle(id).start();
    }

    public void stop(long id) throws BundleException {
        getBundle(id).stop();
    }

    public void uninstall(long id) throws BundleException {
        getBundle(id).uninstall();
    }

    public <T> T getService(Class<T> serviceClass) {
        String serviceName = serviceClass.getName();
        ServiceReference serviceRef = getBundleContext()
                                          .getServiceReference(serviceName);

        return (T) getBundleContext().getService(serviceRef);
    }
}
