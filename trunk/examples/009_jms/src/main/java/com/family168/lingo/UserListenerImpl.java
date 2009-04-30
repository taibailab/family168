package com.family168.lingo;

import java.util.ArrayList;
import java.util.List;


public class UserListenerImpl implements UserListener {
    private List<String> results = new ArrayList();
    private Object semaphore = new Object();
    private boolean stopped;
    private Exception onException;
    private long waitTime = 1000;

    public synchronized void onResult(String data) {
        results.add(data);

        synchronized (semaphore) {
            semaphore.notifyAll();
        }
    }

    public void stop() {
        stopped = true;
    }

    public void onException(Exception e) {
        onException = e;
    }

    public Exception getOnException() {
        return onException;
    }

    public List getResults() {
        return results;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void waitForAsyncResponses(int messageCount) {
        System.out.println("Waiting for: " + messageCount
            + " responses to arrive");

        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            try {
                if (hasReceivedResponses(messageCount)) {
                    break;
                }

                synchronized (semaphore) {
                    semaphore.wait(waitTime);
                }
            } catch (InterruptedException e) {
                System.out.println("Caught: " + e);
            }
        }

        long end = System.currentTimeMillis() - start;

        System.out.println("End of wait for " + end + " millis");
    }

    protected boolean hasReceivedResponse() {
        return results.isEmpty();
    }

    protected synchronized boolean hasReceivedResponses(int messageCount) {
        return results.size() >= messageCount;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }
}
