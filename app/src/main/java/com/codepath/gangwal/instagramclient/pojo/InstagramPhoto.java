package com.codepath.gangwal.instagramclient.pojo;

import org.json.JSONArray;

/**
 * Created by gangwal on 2/4/15.
 */

public class InstagramPhoto {
    public String username;
    public String caption;
    public String imageUrl;
    public int likesCount =0;
    public int imageHeight;
    public String profilePicUrl;
    public String createTime;
    public String location;
    public int commentsCount;
    public Comment comment1;
    public Comment comment2;
    public JSONArray commentJson;



    public Comment getComment1() {
        return comment1;
    }

    public void setComment1(Comment comment1) {
        this.comment1 = comment1;
    }

    public Comment getComment2() {
        return comment2;
    }

    public void setComment2(Comment comment2) {
        this.comment2 = comment2;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public JSONArray getCommentJson() {
        return commentJson;
    }

    public void setCommentJson(JSONArray commentJson) {
        this.commentJson = commentJson;
    }
}
