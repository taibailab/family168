package com.family168;

public class UserMBeanImpl implements UserMBean {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
