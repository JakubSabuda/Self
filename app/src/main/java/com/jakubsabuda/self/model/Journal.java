/*
 * Copyright (c) 2020.
 * Created by Jakub Sabuda.
 * App is created only for personal use .
 */

package com.jakubsabuda.self.model;

import java.sql.Timestamp;

public class Journal {
    private String title;
    private String thought;
//    private String imageUrl;
    private String userId;
    private Timestamp timeAdded;
    private String userName;

    //Empty constructor to make Firebase work
    public Journal(){}


    //constructors
    //(, String imageUrl) was deleted from here
    public Journal(String title, String thought, String userId, Timestamp timeAdded, String userName) {
        this.title = title;
        this.thought = thought;
//        this.imageUrl = imageUrl;
        this.userId = userId;
        this.timeAdded = timeAdded;
        this.userName = userName;
    }

    //Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThought() {
        return thought;
    }

    public void setThought(String thought) {
        this.thought = thought;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(Timestamp timeAdded) {
        this.timeAdded = timeAdded;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}

