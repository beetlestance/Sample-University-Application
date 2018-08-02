package com.example.aksha.gjusteve.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewFeedback {

    @SerializedName("success")
    @Expose
    private Boolean success;

    public Boolean getSuccess() {
        return success;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setSuccess(Boolean success) {
//        this.success = success;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

}