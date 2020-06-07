/*
 * Copyright (c) 2020.
 * Created by Jakub Sabuda.
 * App is created only for personal use .
 */

package com.jakubsabuda.self.util;

import android.app.Application;

public class JournalApi extends Application {

    private String username;
    private String userId;

    private static JournalApi instance;

    public static JournalApi getInstance() {
        if (instance == null)
            instance = new JournalApi();
        return instance;
    }

    public JournalApi() {
    }

    //getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
