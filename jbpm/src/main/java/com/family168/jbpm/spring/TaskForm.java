package com.family168.jbpm.spring;

import java.util.List;

import org.jbpm.taskmgmt.exe.TaskInstance;


/**
 * task form，为任务输入变量的表单.
 *
 * @author Lingo
 */
public class TaskForm {
    /** * 任务实例. */
    private TaskInstance taskInstance = null;

    /** * 变量列表. */
    private List<VariableForm> variableFormList = null;

    /**
     * 构造方法.
     *
     * @param taskInstance TaskInstance
     * @param variableFormList list
     */
    public TaskForm(TaskInstance taskInstance,
        List<VariableForm> variableFormList) {
        this.taskInstance = taskInstance;
        this.variableFormList = variableFormList;
    }

    /** * @return TaskInstance. */
    public TaskInstance getTaskInstance() {
        return this.taskInstance;
    }

    /** * @return variable form list. */
    public List<VariableForm> getVariableFormList() {
        return variableFormList;
    }
}
