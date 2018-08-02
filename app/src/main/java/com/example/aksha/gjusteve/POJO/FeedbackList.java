package com.example.aksha.gjusteve.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FeedbackList {

    @SerializedName("feedbackarray")
    @Expose
    private ArrayList<FeedbackJson> feedbackarray = new ArrayList<>();

    public ArrayList<FeedbackJson> getFeedbackarray() {
        return feedbackarray;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setFeedbackarray(ArrayList<FeedbackJson> feedbackarray) {
//        this.feedbackarray = feedbackarray;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

}