package com.example.aksha.gjusteve.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shrim on 7/30/2017.
 */

public class EventJson {

    @SerializedName("event")
    @Expose
    private List<Events> event = null;

    public List<Events> getEvent() {
        return event;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setEvent(List<Events> event) {
//        this.event = event;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)
}
