package com.example.aksha.gjusteve.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aksha.gjusteve.fragments.home.EventDetails;
import com.example.aksha.gjusteve.POJO.DataObjectHorizontal;
import com.example.aksha.gjusteve.R;
import com.example.aksha.gjusteve.helper.EventActivityInterface;
import com.example.aksha.gjusteve.helper.EventSQLite;

import java.util.List;

import static com.example.aksha.gjusteve.App.baseUrl;


public class MyRecyclerViewAdapterHorizontal extends RecyclerView.Adapter<MyRecyclerViewAdapterHorizontal.DataObjectHolderHorizontal> {

    private List<DataObjectHorizontal> mDataSet;
    final private Context mContext;
    private EventSQLite db;
    final private EventActivityInterface mInterface;

    class DataObjectHolderHorizontal extends RecyclerView.ViewHolder {
        final LinearLayout mFavEvents;
        final ImageView imageViewHorizontal;
        final TextView mHeadingHorizontal;
        final TextView mDescriptionHorizontal;
        final Button mBack;
        final Button mDelete;


        DataObjectHolderHorizontal(View itemViewHorizontal) {
            super(itemViewHorizontal);
            imageViewHorizontal = itemViewHorizontal.findViewById(R.id.image_back);
            mHeadingHorizontal =  itemViewHorizontal.findViewById(R.id.text_heading_horizontal);
            mDescriptionHorizontal = itemViewHorizontal.findViewById(R.id.text_description_horizontal);
            mFavEvents =itemViewHorizontal.findViewById(R.id.fav_Events);
            mDelete =itemViewHorizontal.findViewById(R.id.delete);
            mBack =itemViewHorizontal.findViewById(R.id.back);
        }
    }


    public MyRecyclerViewAdapterHorizontal(Context context, List<DataObjectHorizontal> dataSetHorizontal,EventActivityInterface eventActivityInterface) {
        mContext = context;
        mInterface = eventActivityInterface;
        mDataSet = dataSetHorizontal;
    }


    @NonNull
    @Override
    public MyRecyclerViewAdapterHorizontal.DataObjectHolderHorizontal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHorizontal = LayoutInflater.from(mContext).inflate(R.layout.card_horiziontal, parent, false);

        return new DataObjectHolderHorizontal(viewHorizontal);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyRecyclerViewAdapterHorizontal.DataObjectHolderHorizontal holder, int position) {
        final DataObjectHorizontal mDataObject = mDataSet.get(position);
        db = new EventSQLite(mContext);
        holder.mFavEvents.setOnClickListener(view -> {
            Intent i = new Intent(mContext,
                    EventDetails.class);
            i.putExtra("uid",mDataObject.getmUid());
            mContext.startActivity(i);
        });
        holder.mFavEvents.setOnLongClickListener(view -> {
            holder.mDelete.setVisibility(View.VISIBLE);
            holder.mBack.setVisibility(View.VISIBLE);
            holder.mFavEvents.setVisibility(View.INVISIBLE);
            return true;
        });
        holder.mDelete.setOnClickListener(view -> {
            db.deleteEvent(mDataObject.getmUid());
            holder.mDelete.setVisibility(View.GONE);
            holder.mBack.setVisibility(View.GONE);
            holder.mFavEvents.setVisibility(View.VISIBLE);
            mInterface.refreshVerticalAdapterLikeImage(mDataObject.getmUid());
            mInterface.refreshHorizontalAdapter();
        });
        holder.mBack.setOnClickListener(view -> {
            holder.mDelete.setVisibility(View.GONE);
            holder.mBack.setVisibility(View.GONE);
            holder.mFavEvents.setVisibility(View.VISIBLE);
        });
        Glide.with(mContext).load(baseUrl+mDataObject.getmImage()).into(holder.imageViewHorizontal);
        /*Glide.with(mContext).load(baseUrl+mDataObject.getmImage())
                .asBitmap()
                .encoder(new BitmapEncoder(Bitmap.CompressFormat.PNG,100))
                .placeholder(R.drawable.ic_person_24dp)
                .error(R.drawable.ic_person_24dp)
                .format(PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.imageViewHorizontal);
        */
        holder.mHeadingHorizontal.setText(mDataObject.getmHeading());
        holder.mDescriptionHorizontal.setText(mDataObject.getmDescription());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void reloadList(List<DataObjectHorizontal> mpList) {
        mDataSet = mpList;
        notifyDataSetChanged();
    }

    /*
    public final void addItem(List<DataObjectHorizontal> mDataSetHor,DataObjectHorizontal dataObj) {
        mDataSetHor.add(dataObj);
        notifyItemInserted(mDataSetHor.size());
    }

    public void deleteItem(int index) {
        mDataSet.remove(index);
        notifyItemRemoved(index);
    }
    */
}
