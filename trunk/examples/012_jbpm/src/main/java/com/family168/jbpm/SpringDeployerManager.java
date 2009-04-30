package com.family168.jbpm;

import java.util.List;

import org.jbpm.pvm.internal.repository.Deployer;
import org.jbpm.pvm.internal.repository.DeployerManager;
import org.jbpm.pvm.internal.repository.DeploymentImpl;


public class SpringDeployerManager extends DeployerManager {
    protected List<Deployer> deployers;

    public void setDeployers(List<Deployer> deployers) {
        this.deployers = deployers;
    }

    public void deploy(DeploymentImpl deployment) {
        for (Deployer deployer : deployers) {
            deployer.deploy(deployment);
        }
    }
}
