package com.family168.manager;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;

import org.springframework.stereotype.Repository;


@Repository
public class RoleManager {
    @Autowired
    @Qualifier("userManager")
    private UserManager userManager;

    @PostConstruct
    public void post() {
        System.out.println("post");
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
