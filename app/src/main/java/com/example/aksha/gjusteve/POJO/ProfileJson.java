package com.example.aksha.gjusteve.POJO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shrim on 9/12/2017.
 */

public class ProfileJson {

    private boolean success;
    @SerializedName("message")
    private
    String message;

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }
}
