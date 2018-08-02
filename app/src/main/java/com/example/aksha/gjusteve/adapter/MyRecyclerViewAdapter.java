package com.example.aksha.gjusteve.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aksha.gjusteve.POJO.DataObject;
import com.example.aksha.gjusteve.POJO.Events;
import com.example.aksha.gjusteve.R;
import com.example.aksha.gjusteve.database.APIClient;
import com.example.aksha.gjusteve.database.APIInterface;
import com.example.aksha.gjusteve.fragments.home.EventDetails;
import com.example.aksha.gjusteve.helper.EventActivityInterface;
import com.example.aksha.gjusteve.helper.EventSQLite;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aksha.gjusteve.App.baseUrl;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder> {

    final private List<DataObject> mDataSet;
    final private Context mContext ;
    final private EventActivityInterface mInterface;
    private int lastPosition = -1;
    private EventSQLite db;
    private boolean isInterested = false;
    private String finalTime;

    class DataObjectHolder extends RecyclerView.ViewHolder {

        final TextView heading;
        final TextView description;
        final TextView mTimerView;
        final TextView mDate;
        final ImageView mImage;
        final ImageView mCircleImage;
        final ImageView mImageLike;
        final LinearLayout mEventHolder;
        CountDownTimer downTimer;

        DataObjectHolder(View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.textView);
            heading.setTextColor(Color.BLACK);
            description = itemView.findViewById(R.id.textView2);
            mTimerView =  itemView.findViewById(R.id.timeText);
            mDate =  itemView.findViewById(R.id.text_date);
            mImage = itemView.findViewById(R.id.imageView);
            mCircleImage =  itemView.findViewById(R.id.circle_image);
            mImageLike = itemView.findViewById(R.id.imageLike);
            mEventHolder =itemView.findViewById(R.id.event_holder);
        }
    }
    public MyRecyclerViewAdapter(EventActivityInterface eventActivityInterface, Context context, List<DataObject> dataSet) {

        mInterface = eventActivityInterface;
        mContext = context;
        mDataSet = dataSet;
    }
    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_row, parent, false);
        return new DataObjectHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DataObjectHolder holder, int position, @NonNull List<Object> payloads) {
        if(payloads.isEmpty()){
            onBindViewHolder(holder,position);
        }
        else
        {
            holder.mImageLike.setColorFilter(ContextCompat.getColor(mContext,R.color.iconStarEmpty));
            holder.mImageLike.setTag(false);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull final DataObjectHolder holder, int position) {

        final DataObject dataObject = mDataSet.get(position);
        db = new EventSQLite(mContext);

        holder.mEventHolder.setOnClickListener(view -> {
            Intent i = new Intent(mContext,
                    EventDetails.class);
            i.putExtra("uid",dataObject.getmUid());
            mContext.startActivity(i);
        });

        ArrayList<String> impEvents = db.getImpEvents();
        for(int i=0; i<impEvents.size();i++){
            if (Objects.equals(dataObject.getmUid(), impEvents.get(i))) {
                holder.mImageLike.setColorFilter(ContextCompat.getColor(mContext, R.color.iconStarFilled));
                holder.mImageLike.setTag(true);
                break;
            } else {
                holder.mImageLike.setColorFilter(ContextCompat.getColor(mContext, R.color.iconStarEmpty));
                holder.mImageLike.setTag(false);
            }
        }

        holder.mImageLike.setOnClickListener(view -> {
            if(holder.mImageLike.getTag()!=null){
                isInterested = (boolean)holder.mImageLike.getTag();
            }
            if(!isInterested){
                db.addEvent(dataObject.getmUid(),dataObject.getmTitle(),dataObject.getmDescription(),dataObject.getmThumbnail());
                holder.mImageLike.setColorFilter(ContextCompat.getColor(mContext,R.color.iconStarFilled));
                holder.mImageLike.setTag(true);
                updateCount(dataObject.getmUid(),"1");
                mInterface.refreshHorizontalAdapter();
            }
            else{
                holder.mImageLike.setColorFilter(ContextCompat.getColor(mContext,R.color.iconStarEmpty));
                holder.mImageLike.setTag(false);
                updateCount(dataObject.getmUid(),"2");
                db.deleteEvent(dataObject.getmUid());
                mInterface.refreshHorizontalAdapter();
            }
        });
        holder.heading.setText(dataObject.getmTitle());
        holder.itemView.setTag(dataObject.getmUid());
        holder.description.setText(dataObject.getmDescription());
        holder.mDate.setText(String.format("%s,%s", dataObject.getmStartingDate().substring(0, 3), dataObject.getmStartingDate().substring(3, 10)));
        Glide.with(mContext).load(baseUrl+dataObject.getmThumbnail()).into(holder.mImage);
        Glide.with(mContext).load(baseUrl+dataObject.getmCircleImage()).into(holder.mCircleImage);
        String timer = dataObject.getmTimer();
        long longTime = Long.parseLong(timer);
        if(holder.downTimer!=null)
            holder.downTimer.cancel();
        holder.downTimer = new CountDownTimer(longTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int totalSecs = (int) millisUntilFinished / 1000;
                int hours = totalSecs / 3600;
                int minutes = (totalSecs % 3600) / 60;
                int seconds = totalSecs % 60;
                if (hours > 24) {
                    int days = hours / 24;
                    finalTime = days + " Days";
                } else {
                    finalTime = hours + ":" + minutes + ":" + seconds;
                }
                holder.mTimerView.setText(finalTime);
                holder.mTimerView.setTextColor(Color.RED);
            }

            @Override
            public void onFinish() {
                holder.mTimerView.setText(R.string.EventStarted);
                holder.mTimerView.setTextColor(mContext.getResources().getColor(R.color.pastEventLikeCount));
            }
        }.start();

        for (int i = 0; i < getItemCount(); i++) {
            animate(holder.itemView, i);
        }
    }
    public void changeIcon(int id){
            ArrayList<Object> payload = new ArrayList<>();
            payload.add("changeIcon");
            notifyItemChanged(id,payload);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
/*
    public void addItem(DataObject dataObj, int index) {
        mDataSet.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataSet.remove(index);
        notifyItemRemoved(index);
    }
*/


    private void animate(final View view, final int position) {
        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        view.startAnimation(animation);
        lastPosition = position;
    }

    private void updateCount(String uid, String count) {
        APIInterface apiService =
                APIClient.getClient(baseUrl).create(APIInterface.class);

        Call<Events> call = apiService.getCountUpdate(uid, count);
        call.enqueue(new Callback<Events>() {
            @Override
            public void onResponse(@NonNull Call<Events> call, @NonNull Response<Events> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Events> call, @NonNull Throwable t) {
            }
        });
    }
}
