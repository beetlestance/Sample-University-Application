package com.example.aksha.gjusteve.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.aksha.gjusteve.POJO.FeedbackJson;
import com.example.aksha.gjusteve.R;
import com.example.aksha.gjusteve.helper.RecyclerViewHolder;
import com.example.aksha.gjusteve.helper.SQLiteHandler;

import java.util.List;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;
import static com.example.aksha.gjusteve.App.baseUrl;

public class SuggestionRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    final private List<FeedbackJson> itemObjectsList;
    final private Context mContext;
    public SuggestionRecyclerViewAdapter(Context mContext,List<FeedbackJson> itemObjectsList){
        this.mContext = mContext;
        this.itemObjectsList=itemObjectsList;
        new SQLiteHandler(mContext);
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.itemView.setId(itemObjectsList.get(position).getUid());
        holder.personName.setText(itemObjectsList.get(position).getUsername());
        holder.date.setText(itemObjectsList.get(position).getDate());
        holder.comments.setText(itemObjectsList.get(position).getComments());
        holder.status.setText(itemObjectsList.get(position).getStatus());
        holder.category.setText(itemObjectsList.get(position).getCategory());
        holder.suggestion.setText(itemObjectsList.get(position).getSuggestion());

        Glide.with(mContext).asBitmap()
                .load(baseUrl+"/android/"+itemObjectsList.get(position).getUserprofile())
                .apply(new RequestOptions()
                        .encodeFormat(Bitmap.CompressFormat.PNG)
                        .encodeQuality(100)
                        .placeholder(R.drawable.ic_person_24dp)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .error(R.drawable.ic_person_24dp)
                        .format(PREFER_ARGB_8888))
                .into(holder.profileImage);
    }
    @Override
    public int getItemCount() {
        return this.itemObjectsList.size();
    }
}
