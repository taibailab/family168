package com.family168.lingo;

import java.util.EventListener;


public interface UserListener extends EventListener {
    public void onResult(String data);

    public void stop();

    public void onException(Exception e);
}
