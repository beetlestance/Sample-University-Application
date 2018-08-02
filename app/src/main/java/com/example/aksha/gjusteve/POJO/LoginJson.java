package com.example.aksha.gjusteve.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginJson {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("roll")
    @Expose
    private String roll;
    @SerializedName("user")
    @Expose
    private UserJson user;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("error_msg")
    @Expose
    private String errorMsg;

    @SerializedName("father")
    @Expose
    private Boolean father;

    @SerializedName("mother")
    @Expose
    private Boolean mother;

    @SerializedName("dob")
    @Expose
    private Boolean dateofbirth;


    public Boolean getFather() {
        return father;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public Boolean getMother() {
//        return mother;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setDateofbirth(Boolean dateofbirth) {
//        this.dateofbirth = dateofbirth;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setFather(Boolean father) {
//        this.father = father;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setMother(Boolean mother) {
//        this.mother = mother;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public Boolean getDateofbirth() {
//        return dateofbirth;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getRoll() {
        return roll;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setRoll(String roll) {
//        this.roll = roll;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)



    public Boolean getError() {
        return error;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setError(Boolean error) {
//        this.error = error;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public String getUid() {
//        return roll;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setUid(String uid) {
//        this.roll = uid;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public UserJson getUser() {
        return user;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setUser(UserJson user) {
//        this.user = user;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)
// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public String getTag() {
//        return tag;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setTag(String tag) {
//        this.tag = tag;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public Integer getSuccess() {
//        return success;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setSuccess(Integer success) {
//        this.success = success;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public String getErrorMsg() {
        return errorMsg;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setErrorMsg(String errorMsg) {
//        this.errorMsg = errorMsg;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

}
