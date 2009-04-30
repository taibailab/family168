package com.family168.jbpm;

import java.io.InputStream;

import java.util.List;

import org.jbpm.ProcessDefinition;
import org.jbpm.ProcessDefinitionQuery;

import org.jbpm.env.Environment;

import org.jbpm.internal.log.Log;

import org.jbpm.jpdl.internal.model.JpdlProcessDefinition;
import org.jbpm.jpdl.internal.repository.JpdlDeployer;
import org.jbpm.jpdl.internal.xml.JpdlParser;

import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.repository.Deployer;
import org.jbpm.pvm.internal.repository.DeploymentImpl;
import org.jbpm.pvm.internal.xml.Parse;

import org.jbpm.session.RepositorySession;


public class SpringJpdlDeployer extends JpdlDeployer {
    private static Log log = Log.getLog(SpringJpdlDeployer.class.getName());
    static JpdlParser jpdlParser = new JpdlParser();

    public void deploy(DeploymentImpl deployment) {
        for (String resourceName : deployment.getResourceNames()) {
            if (resourceName.endsWith(".jpdl.xml")) {
                InputStream inputStream = deployment.getResourceAsStream(resourceName);
                Parse parse = jpdlParser.createParse();
                parse.setProblems(deployment.getProblems());
                parse.setInputStream(inputStream);
                parse.execute();

                JpdlProcessDefinition processDefinition = (JpdlProcessDefinition) parse
                    .getDocumentObject();

                if ((processDefinition != null)
                        && (processDefinition.getName() != null)) {
                    String processDefinitionName = processDefinition
                        .getName();

                    processDefinition.setDeploymentDbid(deployment.getDbid());

                    if (deployment.hasObjectProperties(
                                processDefinitionName)) {
                        String key = (String) deployment.getObjectProperty(processDefinitionName,
                                KEY_KEY);
                        String id = (String) deployment.getObjectProperty(processDefinitionName,
                                KEY_ID);
                        Long version = (Long) deployment.getObjectProperty(processDefinitionName,
                                KEY_VERSION);
                        processDefinition.setId(id);
                        processDefinition.setKey(key);
                        processDefinition.setVersion(version.intValue());
                    } else {
                        checkKey(processDefinition, deployment);
                        checkVersion(processDefinition, deployment);
                        checkId(processDefinition, deployment);

                        deployment.addObjectProperty(processDefinitionName,
                            KEY_KEY, processDefinition.getKey());
                        deployment.addObjectProperty(processDefinitionName,
                            KEY_VERSION,
                            new Long(processDefinition.getVersion()));
                        deployment.addObjectProperty(processDefinitionName,
                            KEY_ID, processDefinition.getId());
                    }

                    deployment.addObject(processDefinitionName,
                        processDefinition);
                }
            }
        }
    }
}
