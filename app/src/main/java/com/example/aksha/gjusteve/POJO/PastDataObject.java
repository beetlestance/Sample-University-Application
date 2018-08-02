package com.example.aksha.gjusteve.POJO;

/**
 * Created by Abhishek on 08-08-2017.
 */

public class PastDataObject {


    private String mUid;
    private String mTitle;
    private final String mDescription;

    private String mThumbnail;
    private String mCircleImage;
    private String mStartingDate;
    private String mStartingTime;
    private String mEndingDate;
    private String mEndingTime;
    private String mVenue;
    private String mGenre;
    private int mLikes;


    //for past event
    public PastDataObject(String uid, String title, String description, String image, String circleImage, String startingDate, String startingTime, String endingDate, String endingTime, String venue, String genre, int likes) {
        mUid = uid;
        mTitle = title;
        mDescription = description;
        mThumbnail = image;
        mCircleImage = circleImage;
        mStartingDate = startingDate;
        mStartingTime = startingTime;
        mEndingDate = endingDate;
        mEndingTime = endingTime;
        mVenue = venue;
        mGenre = genre;
        mLikes = likes;
    }


    public String getmUid() {
        return mUid;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setmUid(String mUid) {
//        this.mUid = mUid;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getmTitle() {
        return mTitle;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setmTitle(String mText1) {
//        this.mTitle = mText1;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getmDescription() {
        return mDescription;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setmDescription(String mText2) {
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getmThumbnail() {
        return mThumbnail;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setmThumbnail(String mthumbnail) {
//        this.mThumbnail = mthumbnail;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getmCircleImage() {
        return mCircleImage;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setmCircleImage(String mCircleImage) {
//        this.mCircleImage = mCircleImage;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getmStartingDate() {
        return mStartingDate;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setmStartingDate(String mStartingDate) {
//        this.mStartingDate = mStartingDate;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public String getmStartingTime() {
//        return mStartingTime;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setmStartingTime(String mStartingTime) {
//        this.mStartingTime = mStartingTime;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public String getmEndingDate() {
//        return mEndingDate;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setmEndingDate(String mEndingDate) {
//        this.mEndingDate = mEndingDate;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public String getmEndingTime() {
//        return mEndingTime;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setmEndingTime(String mEndingTime) {
//        this.mEndingTime = mEndingTime;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public String getmGenre() {
//        return mGenre;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setmGenre(String mGenre) {
//        this.mGenre = mGenre;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public String getmVenue() {
//        return mVenue;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setmVenue(String mVenue) {
//        this.mVenue = mVenue;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public int getmLikes() {
        return mLikes;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setmLikes(int mLikes) {
//        this.mLikes = mLikes;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)
}
