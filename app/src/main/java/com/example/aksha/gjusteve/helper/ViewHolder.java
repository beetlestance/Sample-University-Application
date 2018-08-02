package com.example.aksha.gjusteve.helper;


import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aksha.gjusteve.R;

public class ViewHolder {
    public final RelativeLayout rootView;
    public final ImageView imageView;
    public final TextView textViewName;
    public final TextView textViewDesignation;
    public final TextView textViewPhone;
    public final TextView textViewEmail;


    private ViewHolder(RelativeLayout rootView, ImageView imageView, TextView textViewName, TextView textViewDesignation, TextView textViewPhone, TextView textViewEmail) {
        this.rootView = rootView;
        this.imageView = imageView;
        this.textViewName = textViewName;
        this.textViewDesignation = textViewDesignation;
        this.textViewPhone = textViewPhone;
        this.textViewEmail = textViewEmail;
    }

    public static ViewHolder create(RelativeLayout rootView) {
        ImageView imageView =  rootView.findViewById(R.id.profilePic);
        TextView textViewName =  rootView.findViewById(R.id.textViewName);
        TextView textViewDesignation =  rootView.findViewById(R.id.textViewDesignation);
        TextView textViewPhone =  rootView.findViewById(R.id.textViewPhone);
        TextView textViewEmail = rootView.findViewById(R.id.textViewEmail);
        return new ViewHolder(rootView, imageView, textViewName, textViewDesignation, textViewPhone, textViewEmail);
    }
}
