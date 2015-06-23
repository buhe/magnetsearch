package com.aoppp.magnetsearch.connector;

/**
 * Created by guguyanhua on 6/23/15.
 */
public class ConnectState {
    boolean success;
    long speed;
    String error;

    public ConnectState(boolean success, long speed, String error) {
        this.success = success;
        this.speed = speed;
        this.error = error;
    }
}
