package com.example.aksha.gjusteve.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Events {

    @SerializedName("uid")
    @Expose
    private String uid;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("circleimage")
    @Expose
    private String circleimage;


    @SerializedName("startingdate")
    @Expose
    private String startingdate;

    @SerializedName("startingtime")
    @Expose
    private String startingtime;

    @SerializedName("endingdate")
    @Expose
    private String endingdate;

    @SerializedName("endingtime")
    @Expose
    private String endingtime;

    @SerializedName("venue")
    @Expose
    private String venue;

    @SerializedName("genre")
    @Expose
    private String genre;

    @SerializedName("count")
    @Expose
    private int count;

    @SerializedName("likes")
    @Expose
    private int likes;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("organisername")
    @Expose
    private String organisername;


    public String getId() {
        return uid;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setId(String uid) {
//        this.uid = uid;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getTitle() {
        return title;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setTitle(String title) {
//        this.title = title;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getDescription() {
        return description;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setDescription(String description) {
//        this.description = description;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getImage() {
        return image;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setImage(String image) {
//        this.image = image;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getCircleimage() {
        return circleimage;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setCircleimage(String circleimage) {
//        this.circleimage = circleimage;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getStartingDate() {
        return startingdate;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setStartingDate(String startingdate) {
//        this.startingdate = startingdate;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getEndingdate() {
        return endingdate;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setEndingdate(String endingdate) {
//        this.endingdate = endingdate;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getEndingtime() {
        return endingtime;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setEndingtime(String endingtime) {
//        this.endingtime = endingtime;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getStartingtime() {
        return startingtime;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setStartingtime(String startingtime) {
//        this.startingtime = startingtime;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public String getMessage() {
//        return message;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setMessage(String message) {
//        this.message = message;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public int getCount() {
//        return count;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setCount(int count) {
//        this.count = count;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public int getLikes() {
        return likes;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setLikes(int likes) {
//        this.likes = likes;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getVenue() {
        return venue;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setVenue(String venue) {
//        this.venue = venue;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getGenre() {
        return genre;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setGenre(String genre) {
//        this.genre = genre;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getOrganiserName() {
        return organisername;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setOrganiserName(String organisername) {
//        this.organisername = organisername;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)
}