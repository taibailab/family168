package com.family168.manager;

import org.springframework.stereotype.Service;


@Service
public class UserManager {
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
