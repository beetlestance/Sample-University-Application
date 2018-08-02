package com.example.aksha.gjusteve.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.aksha.gjusteve.POJO.FacultiesJson;
import com.example.aksha.gjusteve.R;
import com.example.aksha.gjusteve.helper.ViewHolder;

import java.util.List;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

public class FacultiesAdapter extends ArrayAdapter<FacultiesJson> {

    final private List<FacultiesJson> contactList;
    final private LayoutInflater layoutInflater;
    final private Context context;
    public FacultiesAdapter(Context context, List<FacultiesJson> objects){

        super(context,0,objects);
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        contactList = objects;
    }

    @Override
    public FacultiesJson getItem(int position){
        return contactList.get(position);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){

        final ViewHolder viewHolder;
        if (convertView == null) {
            View view = layoutInflater.inflate(R.layout.contacts, parent, false);
            viewHolder = ViewHolder.create((RelativeLayout) view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        FacultiesJson item = getItem(position);

        assert item != null;
        viewHolder.textViewName.setText(item.getName());
        viewHolder.textViewDesignation.setText(item.getDesignation());
        viewHolder.textViewPhone.setText(item.getPhone());
        viewHolder.textViewEmail.setText(item.getEmail());
        //Glide.with(context).load(item.getProfilePic()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(viewHolder.imageView);
        Glide.with(context).asBitmap()
                .load(item.getProfilePic())
                .apply(new RequestOptions()
                       .encodeFormat(Bitmap.CompressFormat.PNG)
                        .encodeQuality(100)
                       .placeholder(R.mipmap.ic_launcher)
                       .error(R.mipmap.ic_launcher)
                       .format(PREFER_ARGB_8888)
                       .diskCacheStrategy(DiskCacheStrategy.DATA))
                .into(viewHolder.imageView);
        return viewHolder.rootView;

    }
}
