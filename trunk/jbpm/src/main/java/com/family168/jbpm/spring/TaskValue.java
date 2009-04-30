package com.family168.jbpm.spring;

import java.util.ArrayList;
import java.util.List;


/**
 * 任务数据.
 *
 * @author Lingo
 */
public class TaskValue {
    /** * 任务id. */
    private long taskInstanceId = -1L;

    /** * variable form list. */
    private List<VariableForm> variableFormList = new ArrayList<VariableForm>();

    /** * transition. */
    private String transition = Constants.TYPE_DEFAULT_END;

    /**
     * 构造方法.
     *
     * @param taskInstanceId 任务id
     * @param transition String
     */
    public TaskValue(long taskInstanceId, String transition) {
        this.taskInstanceId = taskInstanceId;
        this.transition = transition;
    }

    /** * @return task instance id. */
    public long getTaskInstanceId() {
        return taskInstanceId;
    }

    /** * @return variableForm list. */
    public List<VariableForm> getVariableFormList() {
        return variableFormList;
    }

    /** * @return transition. */
    public String getTransition() {
        return transition;
    }

    /** * @return 是否是默认结束标记. */
    public boolean isDefaultEnd() {
        return Constants.TYPE_DEFAULT_END.equals(transition);
    }

    /** * @return 是否是保存. */
    public boolean isSave() {
        return Constants.TYPE_SAVE.equals(transition);
    }

    /** * @return 是否是取消. */
    public boolean isCancel() {
        return Constants.TYPE_CANCEL.equals(transition);
    }

    /**
     * 设置变量.
     *
     * @param names 变量名
     * @param values 变量值
     * @param writables 是否可写
     */
    public void setVariables(String[] names, String[] values,
        String[] writables) {
        for (int i = 0; i < names.length; i++) {
            try {
                VariableForm variableForm = new VariableForm(names[i],
                        values[i], !"true".equals(writables[i]));
                variableFormList.add(variableForm);
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }
    }

    /** * @return String. */
    public String toString() {
        return "{taskInstanceId:" + taskInstanceId + ",transition:"
        + transition + ",variableFormList:" + variableFormList + "}";
    }
}
