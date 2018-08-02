package com.example.aksha.gjusteve.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class FacultiesList {
    @SerializedName("contacts")
    @Expose
    private ArrayList<FacultiesJson> contacts =new ArrayList<>();

    public ArrayList<FacultiesJson> getContacts()
    {
        return contacts;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void setContacts(ArrayList<FacultiesJson> contacts) {
//        this.contacts = contacts;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)
}
