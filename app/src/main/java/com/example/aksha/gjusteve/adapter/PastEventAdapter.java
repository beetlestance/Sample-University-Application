package com.example.aksha.gjusteve.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.aksha.gjusteve.POJO.Events;
import com.example.aksha.gjusteve.POJO.PastDataObject;
import com.example.aksha.gjusteve.R;
import com.example.aksha.gjusteve.database.APIClient;
import com.example.aksha.gjusteve.database.APIInterface;
import com.example.aksha.gjusteve.database.ConnectionCheck;
import com.example.aksha.gjusteve.helper.EventSQLite;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;
import static com.example.aksha.gjusteve.App.baseUrl;

public class PastEventAdapter extends RecyclerView.Adapter<PastEventAdapter.PastDataHolder> {

    final private Context mContext;
    final private List<PastDataObject> mDataSet;
    private boolean isLiked=false;
    private EventSQLite db;

    final private ArrayList<String> impEvents;

    class PastDataHolder extends RecyclerView.ViewHolder {

        final private TextView mTitle;
        final private TextView mDescription;
        final private ImageView mImage;
        final private ImageView mCircleImage;
        final private TextView mDate;
        final private TextView mLikes;
        final private ImageView mImageLike;

        PastDataHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.past_text_title);
            mDescription = itemView.findViewById(R.id.past_text_description);
            mDate = itemView.findViewById(R.id.past_text_date);
            mImage = itemView.findViewById(R.id.past_image_view);
            mCircleImage = itemView.findViewById(R.id.past_circle_image);
            mImageLike = itemView.findViewById(R.id.past_satisfy_button);
            mLikes = itemView.findViewById(R.id.past_like_count_text);
        }
    }

    public PastEventAdapter(Context context, List<PastDataObject> mDataList) {
        mContext = context;
        mDataSet = mDataList;
        db = new EventSQLite(mContext);
        impEvents = db.getLike();
    }

    @NonNull
    @Override
    public PastDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View pastView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_past, parent, false);
        return new PastDataHolder(pastView);
    }

    @Override
    public void onBindViewHolder(@NonNull final PastDataHolder holder, int position) {
        final PastDataObject obj = mDataSet.get(position);
        db = new EventSQLite(mContext);
        holder.mTitle.setText(obj.getmTitle());
        holder.mDescription.setText(obj.getmDescription());
        holder.mDate.setText(obj.getmStartingDate());

        int likeUpdate = obj.getmLikes();

        for(int i=0; i<impEvents.size();i++){
            if (Objects.equals(obj.getmUid(), impEvents.get(i))) {
                holder.mImageLike.setColorFilter(ContextCompat.getColor(mContext, R.color.pastEventLike));
                holder.mImageLike.setTag(true);
                break;
            } else {
                holder.mImageLike.setColorFilter(ContextCompat.getColor(mContext, R.color.pastEventDislike));
                holder.mImageLike.setTag(false);

            }
        }
        holder.mLikes.setText(setLikeUs(likeUpdate));
        //Glide.with(mContext).load(baseUrl+obj.getmThumbnail()).into(holder.mImage);
        //Glide.with(mContext).load(baseUrl+obj.getmCircleImage()).into(holder.mCircleImage);

        Glide.with(mContext).asBitmap()
                .load(baseUrl+obj.getmThumbnail())
                .apply(new RequestOptions()
                        .encodeFormat((Bitmap.CompressFormat.PNG))
                        .encodeQuality(100)
                        .placeholder(R.drawable.ic_person_24dp)
                        .error(R.drawable.ic_person_24dp)
                        .format(PREFER_ARGB_8888)
                        .diskCacheStrategy(DiskCacheStrategy.DATA))
                .into(holder.mImage);
        Glide.with(mContext).asBitmap()
                .load(baseUrl+obj.getmCircleImage())
                .apply(new RequestOptions()
                        .encodeFormat(Bitmap.CompressFormat.PNG)
                        .encodeQuality(100)
                        .placeholder(R.drawable.ic_person_24dp)
                        .error(R.drawable.ic_person_24dp)
                        .format(PREFER_ARGB_8888)
                        .diskCacheStrategy(DiskCacheStrategy.DATA))
                .into(holder.mCircleImage);
        holder.mImageLike.setOnClickListener(view -> {

            if(ConnectionCheck.isOnline(mContext))
            {
                if(holder.mImageLike.getTag()!=null){
                    isLiked = (boolean) holder.mImageLike.getTag();
                }
                if (!isLiked){
                    holder.mImageLike.setColorFilter(ContextCompat.getColor(mContext, R.color.pastEventLike));
                    holder.mImageLike.setTag(true);
                    db.addLike(obj.getmUid());
                    UpdateCount(obj.getmUid(),"1",holder);


                }
                else{
                    holder.mImageLike.setColorFilter(ContextCompat.getColor(mContext, R.color.pastEventDislike));
                    holder.mImageLike.setTag(false);
                    db.deleteLike(obj.getmUid());
                    UpdateCount(obj.getmUid(),"2",holder);
                }
            }
            else {
                Snackbar.make(view, "No Internet Connection!", Snackbar.LENGTH_LONG).show();

            }

        });

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

private void UpdateCount(final String uid, String likes, final PastDataHolder holder){
    APIInterface apiService = APIClient.getClient(baseUrl).create(APIInterface.class);

    Call<Events> call = apiService.getLikeUpdate(uid,likes);

    call.enqueue(new Callback<Events>() {
        @Override
        public void onResponse(@NonNull Call<Events> call, @NonNull Response<Events> response) {
            Events events=response.body();
            assert events!=null;
            int count = events.getLikes();
            holder.mLikes.setText(setLikeUs(count));

        }

        @Override
        public void onFailure(@NonNull Call<Events> call, @NonNull Throwable t) {

        }
    });

}

    private String setLikeUs(int likeUpdate){
        String likes;
        switch (likeUpdate) {
            case 0:
                likes = "No Likes";
                break;
            case 1:
                likes = Integer.toString(likeUpdate) + " Like";
                break;
            default:
                likes = Integer.toString(likeUpdate) + " Likes";
                break;
        }
        return likes;
    }



}
