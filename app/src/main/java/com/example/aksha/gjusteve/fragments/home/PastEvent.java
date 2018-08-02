package com.example.aksha.gjusteve.fragments.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.aksha.gjusteve.POJO.EventJson;
import com.example.aksha.gjusteve.POJO.Events;
import com.example.aksha.gjusteve.POJO.PastDataObject;
import com.example.aksha.gjusteve.R;
import com.example.aksha.gjusteve.adapter.PastEventAdapter;
import com.example.aksha.gjusteve.database.APIClient;
import com.example.aksha.gjusteve.database.APIInterface;
import com.example.aksha.gjusteve.helper.CustomTaskLoader;
import com.example.aksha.gjusteve.helper.DataParser;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.aksha.gjusteve.App.baseUrl;

public class PastEvent extends Fragment implements DataParser, LoaderManager.LoaderCallbacks {

    private static final String TAG = "Past Event";
    private Context mContext;
    private RecyclerView pastEventRecyclerView;
    private List<PastDataObject> pastEventList;
    private RecyclerView.Adapter pastEventAdapter;
    private TextView noPastEventTextView;
    private long timeLeft;
    private Date currentTime;
    private String eventEndingDate;
    private Date finalEventEndingTime;
    private String eventEndingTime;
    private ProgressBar mProgressBar;
    private Toast toast;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pastEventList = new ArrayList<>();
        mContext = getContext();
        pastEventAdapter = new PastEventAdapter(mContext, pastEventList);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_past_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        mProgressBar = view.findViewById(R.id.past_progress_Bar);
        noPastEventTextView = view.findViewById(R.id.no_past_event_text_view);
        pastEventRecyclerView = view.findViewById(R.id.recyclerViewPastevents);
        noPastEventTextView.setVisibility(View.GONE);
        noPastEventTextView.setOnClickListener(view1 -> {
            FragmentManager fragmentManager =getFragmentManager();
            assert fragmentManager != null;
            fragmentManager.beginTransaction().replace(R.id.frame_layout, new Home(),"Home").commit();
        });
        pastEventRecyclerView.setHasFixedSize(true);
        LinearLayoutManager pastEventLinearLayoutManager = new LinearLayoutManager(mContext);
        pastEventRecyclerView.setLayoutManager(pastEventLinearLayoutManager);
        pastEventRecyclerView.setAdapter(pastEventAdapter);
    }
    public void prepareData() {
        Calendar cal = Calendar.getInstance();
        currentTime = cal.getTime();
        APIInterface apiService = APIClient.getClient(baseUrl).create(APIInterface.class);
        Call<EventJson> call = apiService.getEventList();
        call.enqueue(new Callback<EventJson>() {
            @Override
            public void onResponse(@NonNull Call<EventJson> call, @NonNull Response<EventJson> response) {
                EventJson event = response.body();
                assert event != null;
                List<Events> eventDetail = event.getEvent();
                for (Events e : eventDetail) {
                    eventEndingDate = e.getEndingdate();
                    eventEndingTime = e.getEndingtime();
                    String eventEndTime = eventEndingDate + " " + eventEndingTime;
                    try {
                        finalEventEndingTime = new SimpleDateFormat("dd-MM-yyyy HH-mm", Locale.US).parse(eventEndTime);
                        timeLeft = dateDifference(currentTime, finalEventEndingTime);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    if (timeLeft < 0) {
                        PastDataObject dO = new PastDataObject(e.getId(), e.getTitle(), e.getDescription(), e.getImage(), e.getCircleimage(), e.getStartingDate(), e.getStartingtime(), e.getEndingdate(), e.getEndingtime(), e.getVenue(), e.getGenre(), e.getLikes());
                        pastEventList.add(dO);
                    }
                }
                if (pastEventList.isEmpty()) {
                    pastEventRecyclerView.setVisibility(View.GONE);
                    noPastEventTextView.setVisibility(View.VISIBLE);
                    noPastEventTextView.setText(R.string.NoPastEvent);
                }
                mProgressBar.setVisibility(View.GONE);
                pastEventAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(@NonNull Call<EventJson> call, @NonNull Throwable t) {
                // Log error here since request failed
                mProgressBar.setVisibility(View.GONE);
                noPastEventTextView.setVisibility(View.VISIBLE);
                noPastEventTextView.setText(R.string.touch_to_refresh);
                if (toast != null) {
                    toast.cancel();
                    toast = Toast.makeText(mContext, "Unable to fetch! Check Internet Connection", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    toast = Toast.makeText(mContext, "Unable to fetch! Check Internet Connection", Toast.LENGTH_SHORT);
                    toast.show();
                }
                Log.e(TAG, t.toString());
            }
        });
    }

    private long dateDifference(Date startDate, Date endDate) {
        return endDate.getTime() - startDate.getTime();
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        String fragmentName ="PastEvent";
        mProgressBar.setVisibility(View.VISIBLE);
        pastEventRecyclerView.setVisibility(View.GONE);
        noPastEventTextView.setVisibility(View.GONE);
        return new CustomTaskLoader(PastEvent.this, mContext,fragmentName);
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object o) {
        pastEventRecyclerView.setVisibility(View.VISIBLE);
        noPastEventTextView.setVisibility(View.GONE);
        pastEventAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Thread t1 =new Thread(){
            @Override
            public void run() {
                super.run();
                getLoaderManager().initLoader(0, null, PastEvent.this);
                Objects.requireNonNull(getLoaderManager().getLoader(0)).startLoading();
            }
        };
        t1.run();
    }

    @Override
    public void onResume() {
        super.onResume();
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
