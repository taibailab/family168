package com.family168.jbpm;

import org.hibernate.Session;

import org.jbpm.Deployment;

import org.jbpm.pvm.internal.repository.DeployerManager;
import org.jbpm.pvm.internal.repository.RepositoryCache;
import org.jbpm.pvm.internal.repository.RepositorySessionImpl;


public class SpringRepositorySession extends RepositorySessionImpl {
    public void setSession(Session session) {
        this.session = session;
    }

    public void setRepositoryCache(RepositoryCache repositoryCache) {
        this.repositoryCache = repositoryCache;
    }

    public void setDeployerManager(DeployerManager deployerManager) {
        this.deployerManager = deployerManager;
    }
}
