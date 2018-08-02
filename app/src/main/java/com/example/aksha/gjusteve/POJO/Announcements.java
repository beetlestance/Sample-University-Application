package com.example.aksha.gjusteve.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Announcements {

    @SerializedName("uid")
    @Expose
    private String uid;

    @SerializedName("pdfUrl")
    @Expose
    private  String pdfUrl;

    @SerializedName("heading")
    @Expose
    private String heading;

    public String getHeading() {
        return heading;
    }

    public String getUid() {
        return uid;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setHeading(String heading) {
//        this.heading = heading;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setUid(String uid) {
//        this.uid = uid;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getPdfUrl() {
        return pdfUrl;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setPdfUrl(String pdfUrl) {
//        this.pdfUrl = pdfUrl;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)
}
