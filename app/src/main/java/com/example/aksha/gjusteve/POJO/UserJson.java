package com.example.aksha.gjusteve.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserJson {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("course")
    @Expose
    private String course;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("profileimage")
    @Expose
    private String profileImage;

    public String getProfileImage() {
        return profileImage;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setProfileImage(String profileImage) {
//        this.profileImage = profileImage;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)




    public String getPhone() {
        return phone;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getCourse() {
        return course;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public String getDob() {
//        return dob;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setCourse(String course) {
//        this.course = course;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setDob(String dob) {
//        this.dob = dob;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getName() {
        return name;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setName(String name) {
//        this.name = name;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getEmail() {
        return email;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setEmail(String email) {
//        this.email = email;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)
}
