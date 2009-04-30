package com.family168.jbpm;

import java.io.InputStream;

import org.jbpm.Deployment;
import org.jbpm.ProcessDefinitionQuery;
import org.jbpm.RepositoryService;

import org.jbpm.cmd.CommandService;

import org.jbpm.pvm.internal.cmd.DeleteDeploymentCmd;
import org.jbpm.pvm.internal.cmd.GetResourceAsStreamCmd;
import org.jbpm.pvm.internal.query.ProcessDefinitionQueryImpl;
import org.jbpm.pvm.internal.repository.DeploymentImpl;
import org.jbpm.pvm.internal.repository.RepositoryServiceImpl;


/**
 * jbpm4中使用反射为repositoryService设置commandService
 * 在spring中只好扩展一个子类，添加setter方法了，可惜这里的commandService不是protected的，无法继承使用。
 */
public class SpringRepositoryService extends RepositoryServiceImpl {
    protected CommandService commandService;

    public Deployment createDeployment() {
        return new DeploymentImpl(commandService);
    }

    public void deleteDeployment(long deploymentDbid) {
        commandService.execute(new DeleteDeploymentCmd(deploymentDbid));
    }

    public void deleteDeploymentCascade(long deploymentDbid) {
        commandService.execute(new DeleteDeploymentCmd(deploymentDbid, true));
    }

    public InputStream getResourceAsStream(long deploymentDbid,
        String resource) {
        return commandService.execute(new GetResourceAsStreamCmd(
                deploymentDbid, resource));
    }

    public ProcessDefinitionQuery createProcessDefinitionQuery() {
        return new ProcessDefinitionQueryImpl(commandService);
    }

    public void setCommandService(CommandService commandService) {
        this.commandService = commandService;
    }
}
