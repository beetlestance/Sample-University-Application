package com.example.aksha.gjusteve.POJO;


public class AnnouncementObject {

    private String mString;
    private String uid;
    private String mPdfUrl;


    public AnnouncementObject(String id, String string,String pdfUrl) {
        uid = id;
        mPdfUrl =pdfUrl;
        mString = string;
    }

    public String getmString() {
        return mString;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setmString(String mString) {
//        this.mString = mString;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public String getUid() {
//        return uid;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setUid(String uid) {
//        this.uid = uid;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getmPdfUrl() {
        return mPdfUrl;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setmPdfUrl(String mPdfUrl) {
//        this.mPdfUrl = mPdfUrl;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)
}
