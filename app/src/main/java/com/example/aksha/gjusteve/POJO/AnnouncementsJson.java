package com.example.aksha.gjusteve.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class AnnouncementsJson {

    @SerializedName("announcements")
   @Expose
    private List<Announcements> announcement = null;

    public List<Announcements> getAnnouncement() {
        return announcement;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setAnnouncement(List<Announcements> announcement) {
//        this.announcement = announcement;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)
}
