package com.typicode.app.typicode.msgType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Vinod Kris
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {

    private String title;
    private String body;
    private int userId;
    private int id;

    public Post(){}

    public Post(String title, String body, int userId) {
        this.title = title;
        this.body = body;
        this.userId = userId;
    }

    public Post(String title, String body, int userId,int id) {
        this.title = title;
        this.body = body;
        this.userId = userId;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
