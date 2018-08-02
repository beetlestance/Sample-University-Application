package com.example.aksha.gjusteve.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacultiesJson {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("profile_pic")
    @Expose
    private String profilePic;

    @SerializedName("designation")
    @Expose
    private String designation;

    @SerializedName("phone")
    @Expose
    private String phone;


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

    public String getProfilePic() {
        return profilePic;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setProfilePic(String profilePic) {
//        this.profilePic = profilePic;
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

    public String getDesignation() {
        return designation;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setDesignation(String designation) {
//        this.designation = designation;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)
}
