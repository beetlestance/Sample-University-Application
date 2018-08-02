package com.example.aksha.gjusteve.helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.aksha.gjusteve.R;


public class RecyclerViewHolder extends RecyclerView.ViewHolder{
    final public TextView personName;
    final public TextView date;
    final public TextView comments;
    final public TextView status;
    final public TextView category;
    final public TextView suggestion;
    final public RoundedImageView profileImage;
    public RecyclerViewHolder(View itemView) {
        super(itemView);
        personName = itemView.findViewById(R.id.userName);
        date = itemView.findViewById(R.id.displayDate);
        comments = itemView.findViewById(R.id.displayComments);
        status = itemView.findViewById(R.id.displayStatus);
        category = itemView.findViewById(R.id.displayCategory);
        suggestion = itemView.findViewById(R.id.dispalySuggestion);
        profileImage = itemView.findViewById(R.id.iv_profile_pic);

    }

}
