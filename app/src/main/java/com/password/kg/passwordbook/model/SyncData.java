package com.password.kg.passwordbook.model;

import com.password.kg.model.User;

/**
 * Created by Nurs on 17.04.2016.
 */
public class SyncData {
    private User user;
    private Data data;

    public SyncData(User user, Data data) {
        this.user = user;
        this.data = data;
    }

    public SyncData() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
