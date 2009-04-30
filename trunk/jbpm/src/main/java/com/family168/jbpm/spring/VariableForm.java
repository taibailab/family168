package com.family168.jbpm.spring;


/**
 * 变量输入表单.
 *
 * @author Lingo
 */
public class VariableForm {
    /** * name. */
    private String name = null;

    /** * value. */
    private Object value = null;

    /** * readonly. */
    private boolean readonly = false;

    /** * 默认构造方法. */
    public VariableForm() {
    }

    /**
     * 赋值用构造方法.
     *
     * @param name String
     * @param value Object
     * @param readonly boolean
     */
    public VariableForm(String name, Object value, boolean readonly) {
        this.name = name;
        this.value = value;
        this.readonly = readonly;
    }

    /** * @return name. */
    public String getName() {
        return name;
    }

    /** * @return value. */
    public String getValue() {
        if (value == null) {
            return "";
        } else {
            return value.toString();
        }
    }

    /** * @return readonly. */
    public boolean isReadonly() {
        return readonly;
    }

    /** * @return String. */
    public String toString() {
        return "[name:" + name + ",value:" + value + ",readonly:"
        + readonly + "]";
    }
}
