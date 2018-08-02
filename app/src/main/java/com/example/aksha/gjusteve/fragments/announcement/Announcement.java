package com.example.aksha.gjusteve.fragments.announcement;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aksha.gjusteve.App;
import com.example.aksha.gjusteve.POJO.AnnouncementObject;
import com.example.aksha.gjusteve.POJO.Announcements;
import com.example.aksha.gjusteve.POJO.AnnouncementsJson;
import com.example.aksha.gjusteve.R;
import com.example.aksha.gjusteve.adapter.AnnouncementAdapter;
import com.example.aksha.gjusteve.database.APIClient;
import com.example.aksha.gjusteve.database.APIInterface;
import com.example.aksha.gjusteve.database.DownloadService;
import com.example.aksha.gjusteve.helper.CustomTaskLoader;
import com.example.aksha.gjusteve.helper.DataParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aksha.gjusteve.App.baseUrl;
import static com.example.aksha.gjusteve.adapter.AnnouncementAdapter.MESSAGE_PROGRESS;

public class Announcement extends Fragment implements AnnouncementAdapter.DownloadFile,DataParser,LoaderManager.LoaderCallbacks{

    private Context mContext;
    private List<AnnouncementObject> mList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private TextView noTextView;
    private ProgressBar mProgressBar;
    private String mUrl;
    private Toast toast;

    public Announcement() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext =context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver();
        mList = new ArrayList<>();
        mAdapter = new AnnouncementAdapter(Announcement.this, mList);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        return layoutInflater.inflate(R.layout.activity_announcement, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        noTextView = view.findViewById(R.id.no_announcement_textView);
        noTextView.setVisibility(View.GONE);
        mProgressBar = view.findViewById(R.id.announcement_progress_Bar);
        mRecyclerView = view.findViewById(R.id.recycler_view_announcement);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        noTextView.setOnClickListener(view1 -> {
            FragmentManager fragmentManager =getFragmentManager();
            assert fragmentManager != null;
            fragmentManager.beginTransaction().replace(R.id.frame_layout, new Announcement()).commit();
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void prepareData() {

        APIInterface apiService = APIClient.getClient(baseUrl).create(APIInterface.class);

        Call<AnnouncementsJson> call = apiService.getAnnouncement();

        call.enqueue(new Callback<AnnouncementsJson>() {
            @Override
            public void onResponse(@NonNull Call<AnnouncementsJson> call, @NonNull Response<AnnouncementsJson> response) {
                AnnouncementsJson announcements = response.body();
                assert announcements != null;
                if (response.isSuccessful()) {
                    List<Announcements> announcementDetail = announcements.getAnnouncement();
                    try {
                        for (Announcements a : announcementDetail) {
                            AnnouncementObject obj = new AnnouncementObject(a.getUid(), a.getHeading(), a.getPdfUrl());
                            mList.add(obj);
                        }
                        if (mList.isEmpty()) {
                            noTextView.setVisibility(View.VISIBLE);
                            noTextView.setText(R.string.NoAnnouncement);
                            mRecyclerView.setVisibility(View.GONE);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    mProgressBar.setVisibility(View.GONE);
                    mAdapter.notifyDataSetChanged();

                }
            }
            @Override
            public void onFailure(@NonNull Call<AnnouncementsJson> call, @NonNull Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                noTextView.setVisibility(View.VISIBLE);
                noTextView.setText(R.string.touch_to_refresh);
                if (toast != null) {
                    toast.cancel();
                    toast = Toast.makeText(mContext, "Unable to fetch! Check Internet Connection", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    toast = Toast.makeText(mContext, "Unable to fetch! Check Internet Connection", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        noTextView.setVisibility(View.GONE);
        String fragmentName = "Announcement";
        return new CustomTaskLoader(Announcement.this,mContext, fragmentName);
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object o) {
        mRecyclerView.setVisibility(View.VISIBLE);
        noTextView.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }

    private void requestPermission(){
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            int PERMISSION_REQUEST_CODE = 1;
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
        checkPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startDownload(mUrl);
        }
    }

    private boolean checkPermission(){
        int result = ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void startDownload(String fetchUrl){
        Log.d("url",fetchUrl);
        Intent intent = new Intent(App.getContext(),DownloadService.class);
        intent.putExtra("url",fetchUrl);
        App.getContext().startService(intent);
    }

    private void registerReceiver(){
        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(mContext);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    public void downloadFile(String url){
        mUrl=url;
        if(checkPermission()){
            startDownload(mUrl);
        } else {
            requestPermission();
        }
    }
    final private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(Objects.equals(intent.getAction(), MESSAGE_PROGRESS)){

                intent.getParcelableExtra("download");
            }
        }
    };
    @Override
    public void onStart() {
        super.onStart();
        Thread t1 =new Thread(){
            @Override
            public void run() {
                super.run();
                getLoaderManager().initLoader(0, null, Announcement.this);
                Objects.requireNonNull(getLoaderManager().getLoader(0)).startLoading();
            }
        };
        t1.run();
    }
    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mList = null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }



}
