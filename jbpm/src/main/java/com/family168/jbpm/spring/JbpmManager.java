package com.family168.jbpm.spring;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Session;

import org.jbpm.JbpmContext;

import org.jbpm.context.def.VariableAccess;

import org.jbpm.db.GraphSession;
import org.jbpm.db.TaskMgmtSession;

import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;

import org.jbpm.identity.Entity;
import org.jbpm.identity.hibernate.IdentitySession;
import org.jbpm.identity.xml.IdentityXmlParser;

import org.jbpm.taskmgmt.def.TaskController;
import org.jbpm.taskmgmt.exe.TaskInstance;

import org.springmodules.workflow.jbpm31.JbpmCallback;
import org.springmodules.workflow.jbpm31.JbpmTemplate;


/**
 * 通用的jbpm管理器.
 *
 * @author Lingo
 */
public class JbpmManager {
    /** * logger. */
    private Log logger = LogFactory.getLog(JbpmManager.class);

    /** * jbpm template. */
    private JbpmTemplate jbpmTemplate = null;

    /**
     * 读取身份.
     *
     * @param resource String
     */
    public void loadIdentities(String resource) {
        logger.debug("loadIdentities");
        jbpmTemplate.execute(new LoadIdentitiesCallback(resource));
    }

    /**
     * 获得用户列表.
     *
     * @return user list
     */
    public List getUsers() {
        logger.debug("getUsers");

        return (List) jbpmTemplate.execute(new GetUsersCallback());
    }

    /**
     * 启动任务.
     *
     * @param username 用户名
     * @param processDefinitionId 流程定义id
     * @return TaskInstance 任务
     */
    public TaskInstance startTask(String username, long processDefinitionId) {
        logger.debug("startTask");

        return (TaskInstance) jbpmTemplate.execute(new StartTaskCallback(
                username, processDefinitionId));
    }

    /**
     * 获得所有任务.
     *
     * @param username 用户名
     * @return task instance list
     */
    public List getTaskInstanceList(String username) {
        logger.debug("getTaskInstanceList");

        return (List) jbpmTemplate.execute(new GetTaskInstanceListCallback(
                username));
    }

    /**
     * 获得流程定义列表.
     *
     * @param username 用户名
     * @return process definition list
     */
    public List getProcessDefinitionList(String username) {
        logger.debug("getProcessDefinitionList");

        return (List) jbpmTemplate.execute(new GetProcessDefinitionListCallback(
                username));
    }

    /**
     * 查看任务信息.
     *
     * @param username 用户名
     * @param taskInstanceId 任务id
     * @return task form
     */
    public TaskForm viewTask(String username, long taskInstanceId) {
        logger.debug("viewTask");

        return (TaskForm) jbpmTemplate.execute(new ViewTaskCallback(
                username, taskInstanceId));
    }

    /**
     * 执行任务.
     *
     * @param username String
     * @param taskValue TaskValue
     */
    public void doTask(String username, TaskValue taskValue) {
        logger.debug("doTask");
        jbpmTemplate.execute(new DoTaskCallback(username, taskValue));
    }

    //
    /** * @param jbpmTemplate jbpm template. */
    public void setJbpmTemplate(JbpmTemplate jbpmTemplate) {
        this.jbpmTemplate = jbpmTemplate;
    }

    /**
     * 读取身份用的inner class.
     */
    public static class LoadIdentitiesCallback implements JbpmCallback {
        /** * resource. */
        private String resource = null;

        /**
         * 构造方法.
         *
         * @param resource String
         */
        public LoadIdentitiesCallback(String resource) {
            this.resource = resource;
        }

        /**
         * 执行.
         *
         * @param jbpmContext JbpmContext
         * @return null
         */
        public Object doInJbpm(JbpmContext jbpmContext) {
            Entity[] entities = IdentityXmlParser.parseEntitiesResource(resource);

            Session session = jbpmContext.getSession();
            IdentitySession identitySession = new IdentitySession(session);

            for (int i = 0; i < entities.length; i++) {
                identitySession.saveEntity(entities[i]);
            }

            return null;
        }
    }

    /**
     * 获得用户列表的inner class.
     */
    public static class GetUsersCallback implements JbpmCallback {
        /**
         * 执行.
         *
         * @param jbpmContext JbpmContext
         * @return user list
         */
        public Object doInJbpm(JbpmContext jbpmContext) {
            Session session = jbpmContext.getSession();
            IdentitySession identitySession = new IdentitySession(session);

            return identitySession.getUsers();
        }
    }

    /**
     * 启动一个任务.
     */
    public static class StartTaskCallback implements JbpmCallback {
        /** * username. */
        private String username = null;

        /** * processDefinitionId. */
        private long processDefinitionId = 0L;

        /**
         * 构造方法.
         *
         * @param username String
         * @param processDefinitionId long
         */
        public StartTaskCallback(String username, long processDefinitionId) {
            this.username = username;
            this.processDefinitionId = processDefinitionId;
        }

        /**
         * 执行.
         *
         * @param jbpmContext JbpmContext
         * @return taskInstance
         */
        public Object doInJbpm(JbpmContext jbpmContext) {
            jbpmContext.setActorId(username);

            GraphSession graphSession = jbpmContext.getGraphSession();
            ProcessDefinition processDefinition = graphSession
                .loadProcessDefinition(processDefinitionId);
            ProcessInstance processInstance = new ProcessInstance(processDefinition);
            TaskInstance taskInstance = processInstance.getTaskMgmtInstance()
                                                       .createStartTaskInstance();

            jbpmContext.save(taskInstance);

            return taskInstance;
        }
    }

    /**
     * 获得所有任务.
     */
    public static class GetTaskInstanceListCallback implements JbpmCallback {
        /** * username. */
        private String username;

        /**
         * 构造方法.
         *
         * @param username String
         */
        public GetTaskInstanceListCallback(String username) {
            this.username = username;
        }

        /**
         * 执行.
         *
         * @param jbpmContext JbpmContext
         * @return taskInstance list
         */
        public Object doInJbpm(JbpmContext jbpmContext) {
            jbpmContext.setActorId(username);

            TaskMgmtSession taskMgmtSession = jbpmContext
                .getTaskMgmtSession();

            return taskMgmtSession.findTaskInstances(username);
        }
    }

    /**
     * 获得流程定义列表.
     */
    public static class GetProcessDefinitionListCallback
        implements JbpmCallback {
        /** * username. */
        private String username;

        /**
         * 构造方法.
         *
         * @param username String
         */
        public GetProcessDefinitionListCallback(String username) {
            this.username = username;
        }

        /**
         * 执行.
         *
         * @param jbpmContext JbpmContext
         * @return processDefintion list
         */
        public Object doInJbpm(JbpmContext jbpmContext) {
            jbpmContext.setActorId(username);

            GraphSession graphSession = jbpmContext.getGraphSession();

            return graphSession.findLatestProcessDefinitions();
        }
    }

    /**
     * 查看任务信息.
     */
    public static class ViewTaskCallback implements JbpmCallback {
        /** * username. */
        private String username;

        /** * taskInstanceId. */
        private long taskInstanceId;

        /**
         * 构造方法.
         *
         * @param username String
         * @param taskInstanceId long
         */
        public ViewTaskCallback(String username, long taskInstanceId) {
            this.username = username;
            this.taskInstanceId = taskInstanceId;
        }

        /**
         * 执行.
         *
         * @param jbpmContext JbpmContext
         * @return processDefintion list
         */
        public Object doInJbpm(JbpmContext jbpmContext) {
            jbpmContext.setActorId(username);

            TaskMgmtSession taskMgmtSession = jbpmContext
                .getTaskMgmtSession();
            TaskInstance taskInstance = taskMgmtSession.loadTaskInstance(taskInstanceId);

            List<VariableForm> variableFormList = new ArrayList<VariableForm>();

            TaskController taskController = taskInstance.getTask()
                                                        .getTaskController();

            if (taskController != null) {
                List<VariableAccess> variableAccesses = (List<VariableAccess>) taskController
                    .getVariableAccesses();

                for (VariableAccess var : variableAccesses) {
                    String name = var.getMappedName();
                    variableFormList.add(new VariableForm(name,
                            taskInstance.getVariable(name),
                            !var.isWritable()));
                }
            }

            TaskForm taskForm = new TaskForm(taskInstance, variableFormList);

            return taskForm;
        }
    }

    /**
     * 执行任务.
     */
    public static class DoTaskCallback implements JbpmCallback {
        /** * username. */
        private String username;

        /** * taskValue. */
        private TaskValue taskValue;

        /**
         * 构造方法.
         *
         * @param username String
         * @param taskValue TaskValue
         */
        public DoTaskCallback(String username, TaskValue taskValue) {
            this.username = username;
            this.taskValue = taskValue;
        }

        /**
         * 执行.
         *
         * @param jbpmContext JbpmContext
         * @return processDefintion list
         */
        public Object doInJbpm(JbpmContext jbpmContext) {
            jbpmContext.setActorId(username);

            TaskMgmtSession taskMgmtSession = jbpmContext
                .getTaskMgmtSession();

            TaskInstance taskInstance = taskMgmtSession.loadTaskInstance(taskValue
                    .getTaskInstanceId());

            if (taskValue.isSave() || taskValue.isDefaultEnd()) {
                for (VariableForm variableForm : taskValue
                    .getVariableFormList()) {
                    if (!variableForm.isReadonly()
                            && (variableForm.getValue() != null)) {
                        taskInstance.setVariable(variableForm.getName(),
                            variableForm.getValue());
                    }
                }
            }

            if (taskValue.isDefaultEnd()) {
                taskInstance.end();
            } else if (!taskValue.isSave() && !taskValue.isCancel()) {
                taskInstance.end(taskValue.getTransition());
            }

            jbpmContext.save(taskInstance);

            return null;
        }
    }
}
