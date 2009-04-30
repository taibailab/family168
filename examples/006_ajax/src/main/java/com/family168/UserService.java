package com.family168;

import java.util.ArrayList;
import java.util.List;


public class UserService {
    public List getAll() {
        List list = new ArrayList();
        User user = new User();
        user.setName("username 1");
        list.add(user);
        user = new User();
        user.setName("username 2");
        list.add(user);

        return list;
    }
}
