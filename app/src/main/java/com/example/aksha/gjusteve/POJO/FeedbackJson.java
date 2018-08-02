package com.example.aksha.gjusteve.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedbackJson {

    @SerializedName("id")
    @Expose
    private int uid;
    @SerializedName("userprofile")
    @Expose
    private String userprofile;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("suggestion")
    @Expose
    private String suggestion;

    public int getUid() {
        return uid;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setUid(int uid) {
//        this.uid = uid;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getUserprofile() {
        return userprofile;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setUserprofile(String userprofile) {
//        this.userprofile = userprofile;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getUsername() {
        return username;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setUsername(String username) {
//        this.username = username;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getDate() {
        return date;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setDate(String date) {
//        this.date = date;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getComments() {
        return comments;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setComments(String comments) {
//        this.comments = comments;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getStatus() {
        return status;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setStatus(String status) {
//        this.status = status;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getCategory() {
        return category;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setCategory(String category) {
//        this.category = category;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getSuggestion() {
        return suggestion;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setSuggestion(String suggestion) {
//        this.suggestion = suggestion;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)


}