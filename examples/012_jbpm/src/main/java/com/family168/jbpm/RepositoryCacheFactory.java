package com.family168.jbpm;

import org.jbpm.pvm.internal.repository.RepositoryCache;
import org.jbpm.pvm.internal.repository.RepositoryCacheImpl;


public class RepositoryCacheFactory {
    private RepositoryCache repositoryCache = new RepositoryCacheImpl();

    public RepositoryCache getRespositoryCache() {
        return repositoryCache;
    }
}
