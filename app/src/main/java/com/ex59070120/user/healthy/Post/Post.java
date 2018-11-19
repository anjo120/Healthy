package com.ex59070120.user.healthy.Post;

public class Post {
    private int userId;
    private int post_id;
    private String post_title;
    private String post_body;

    public Post(){}
    public Post(int post_id, String post_title, String post_body){
        this.post_id = post_id;
        this.post_title = post_title;
        this.post_body = post_body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_body() {
        return post_body;
    }

    public void setPost_body(String post_body) {
        this.post_body = post_body;
    }
}
