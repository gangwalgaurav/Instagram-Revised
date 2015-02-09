package com.codepath.gangwal.instagramclient.pojo;

/**
 * Created by gangwal on 2/8/15.
 */
public class Comment {

    String username;
    String commentText;
    String createdTime;

    public Comment(String username, String commentText) {
        this.username = username;
        this.commentText = commentText;
        this.createdTime = "";

    }

    public Comment(String username, String commentText, String createdTime) {
        this.username = username;
        this.commentText = commentText;
        this.createdTime = "<br> <font color=#A5A9AC>"+createdTime+" </font>";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "<b> <font color=#2D5B81>"
                + username +"</font>"+
                "</b> '" + commentText +createdTime;
    }
}
