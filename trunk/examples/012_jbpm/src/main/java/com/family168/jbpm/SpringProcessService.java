package com.family168.jbpm;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jbpm.Deployment;
import org.jbpm.ProcessDefinition;
import org.jbpm.ProcessDefinitionQuery;
import org.jbpm.ProcessService;
import org.jbpm.RepositoryService;


public class SpringProcessService implements ProcessService {
    protected RepositoryService repositoryService;

    public Deployment createDeployment() {
        throw new UnsupportedOperationException("not implement");
    }

    public List<String> findProcessDefinitionKeys() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                                                        .execute();
        Set<String> keys = new HashSet<String>();

        for (ProcessDefinition pd : list) {
            keys.add(pd.getKey());
        }

        return new ArrayList<String>(keys);
    }

    public List<ProcessDefinition> findProcessDefinitionsByKey(
        String processDefinitionKey) {
        throw new UnsupportedOperationException("not implement");
    }

    public ProcessDefinition findLatestProcessDefinitionByKey(
        String processDefinitionKey) {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                                                        .key(processDefinitionKey)
                                                        .orderDesc("versionProperty.longValue")
                                                        .execute();

        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public ProcessDefinition findProcessDefinitionById(
        String processDefinitionId) {
        throw new UnsupportedOperationException("not implement");
    }

    public ProcessDefinitionQuery createProcessDefinitionQuery() {
        throw new UnsupportedOperationException("not implement");
    }

    public void deleteProcessDefinition(String processDefinitionId) {
        throw new UnsupportedOperationException("not implement");
    }

    public void deleteProcessDefinitionCascade(String processDefinitionId) {
        throw new UnsupportedOperationException("not implement");
    }

    public void setUserId(String userId) {
        throw new UnsupportedOperationException("not implement");
    }

    public void setConnection(Connection connection) {
        throw new UnsupportedOperationException("not implement");
    }

    public void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }
}
