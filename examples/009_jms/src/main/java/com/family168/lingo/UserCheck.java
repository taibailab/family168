package com.family168.lingo;

import com.family168.User;


public interface UserCheck {
    public void asynGetResidual(User user, UserListener userListener);

    public User synGetResidual(User user);
}
