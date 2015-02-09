package com.codepath.gangwal.instagramclient.pojo;

/**
 * Created by gangwal on 2/8/15.
 */
public class Comment {

    String username;
    String commentText;
    String createdTime;
    String userProfilePic;

    public Comment(String username, String commentText) {
        this.username = username;
        this.commentText = commentText;
        this.createdTime = "";
        this.userProfilePic="";

    }

    public Comment(String username, String commentText, String createdTime,String userProfilePic) {
        this.username = username;
        this.commentText = commentText;
        this.createdTime = "<font color=#A5A9AC>"+createdTime+" </font>";
        this.userProfilePic = userProfilePic;
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

    public String getUserProfilePic() {
        return userProfilePic;
    }

    public void setUserProfilePic(String userProfilePic) {
        this.userProfilePic = userProfilePic;
    }

    @Override
    public String toString() {
        //Logic to add/change tag to hyperlink
        String commentTextWithName[] =  commentText.split(" ");
        StringBuffer finalCommentText = new StringBuffer();
        for(String word : commentTextWithName)
        {
            if (word.startsWith("@")) {
                word = "<font color=#2D5B81> " + word + " </font>";
            }
            finalCommentText.append(word);

        }
        return "<b> <font color=#2D5B81>"
                + username +"</font>"+
                "</b> '" + finalCommentText;
    }
}
