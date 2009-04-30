package com.family168;

import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;


public class HistoryEventListener implements PostUpdateEventListener {
    public void onPostUpdate(PostUpdateEvent event) {
        Object id = event.getId();
        String type = event.getEntity().getClass().getSimpleName();
        System.out.println("id:" + id + ",type:" + type);
    }
}
