package com.example.aksha.gjusteve.fragments.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aksha.gjusteve.POJO.DataObject;
import com.example.aksha.gjusteve.POJO.DataObjectHorizontal;
import com.example.aksha.gjusteve.POJO.EventJson;
import com.example.aksha.gjusteve.R;
import com.example.aksha.gjusteve.adapter.MyRecyclerViewAdapter;
import com.example.aksha.gjusteve.adapter.MyRecyclerViewAdapterHorizontal;
import com.example.aksha.gjusteve.database.APIClient;
import com.example.aksha.gjusteve.database.APIInterface;
import com.example.aksha.gjusteve.helper.CustomTaskLoader;
import com.example.aksha.gjusteve.helper.DataParser;
import com.example.aksha.gjusteve.helper.EventActivityInterface;
import com.example.aksha.gjusteve.helper.EventSQLite;

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

public class Events extends Fragment implements EventActivityInterface, DataParser, LoaderManager.LoaderCallbacks {

    private RecyclerView mRecyclerViewHorizontal;
    private MyRecyclerViewAdapter mAdapterVertical;
    private MyRecyclerViewAdapterHorizontal mAdapterHorizontal;
    private List<DataObject> mList;
    private List<DataObjectHorizontal> mListHorizontal;
    private TextView fav;
    private View separator;
    private Context mContext;
    private EventSQLite db;
    private long timeStartLeft;
    private long timeEndLeft;
    private String eventStartingDate;
    private Date finalEventStartingTime;
    private Date currentTime;
    private String eventStartingTime;
    private String eventEndingDate;
    private Date finalEventEndingTime;
    private String eventEndingTime;
    private String startingDate, endingDate;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerViewVertical;
    private TextView noEventTextView;
    private NestedScrollView scrollView;
    private Toast toast;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        db = new EventSQLite(getContext());
        mList = new ArrayList<>();
        mListHorizontal = new ArrayList<>();
        mAdapterVertical = new MyRecyclerViewAdapter(Events.this, getContext(), mList);
        mAdapterHorizontal = new MyRecyclerViewAdapterHorizontal(getContext(), mListHorizontal, Events.this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.activity_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fav = view.findViewById(R.id.text_fav);
        separator = view.findViewById(R.id.view_separator);
        noEventTextView = view.findViewById(R.id.no_event_text_view);
        mProgressBar = view.findViewById(R.id.event_progress_Bar);
        scrollView = view.findViewById(R.id.scrollview);
        noEventTextView.setVisibility(View.GONE);

        mRecyclerViewHorizontal = view.findViewById(R.id.recyclerViewHorizontal);
        mRecyclerViewVertical = view.findViewById(R.id.recyclerView);

        noEventTextView.setOnClickListener(view1 -> {
            FragmentManager fragmentManager = getFragmentManager();
            Objects.requireNonNull(fragmentManager).beginTransaction().replace(R.id.frame_layout, new Home(), "Home").commit();
        });

        mRecyclerViewVertical.setNestedScrollingEnabled(false);
        mRecyclerViewHorizontal.setHasFixedSize(true);
        mRecyclerViewHorizontal.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
        mRecyclerViewVertical.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        LinearLayoutManager mLinearLayoutManagerHorizontal = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewHorizontal.setLayoutManager(mLinearLayoutManagerHorizontal);
        mRecyclerViewVertical.setLayoutManager(mLinearLayoutManager);

        if (mListHorizontal.isEmpty()) {
            fav.setVisibility(View.GONE);
            mRecyclerViewHorizontal.setVisibility(View.GONE);
            separator.setVisibility(View.GONE);
        }
        mRecyclerViewVertical.setAdapter(mAdapterVertical);
        mRecyclerViewHorizontal.setAdapter(mAdapterHorizontal);
    }

    public void refreshHorizontalAdapter() {
        mListHorizontal = db.getAllElements();
        fav.setVisibility(View.VISIBLE);
        mRecyclerViewHorizontal.setVisibility(View.VISIBLE);
        separator.setVisibility(View.VISIBLE);
        mAdapterHorizontal.reloadList(mListHorizontal);
    }

    @Override
    public void refreshVerticalAdapterLikeImage(String uid) {
        View view = mRecyclerViewVertical.findViewWithTag(uid);
        RecyclerView.ViewHolder viewHolder = mRecyclerViewVertical.findContainingViewHolder(view);
        assert viewHolder != null;
        int position = viewHolder.getAdapterPosition();
        mAdapterVertical.changeIcon(position);
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
                List<com.example.aksha.gjusteve.POJO.Events> eventDetail = event.getEvent();
                for (com.example.aksha.gjusteve.POJO.Events e : eventDetail) {

                    eventStartingDate = e.getStartingDate();
                    eventStartingTime = e.getStartingtime();

                    eventEndingDate = e.getEndingdate();
                    eventEndingTime = e.getEndingtime();

                    String eventStartTime = eventStartingDate + " " + eventStartingTime;
                    String eventEndTime = eventEndingDate + " " + eventEndingTime;

                    try {
                        finalEventStartingTime = new SimpleDateFormat("dd-MM-yyyy HH-mm", Locale.US).parse(eventStartTime);
                        startingDate = finalEventStartingTime.toString();
                        finalEventEndingTime = new SimpleDateFormat("dd-MM-yyyy HH-mm", Locale.US).parse(eventEndTime);
                        endingDate = finalEventEndingTime.toString();
                        timeStartLeft = dateDifference(currentTime, finalEventStartingTime);
                        timeEndLeft = dateDifference(currentTime, finalEventEndingTime);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    if (timeEndLeft > 0) {
                        DataObject dataObject = new DataObject(e.getId(), e.getTitle(), e.getDescription(), e.getImage(), e.getCircleimage(), startingDate, e.getStartingtime(), endingDate, e.getEndingtime(), e.getVenue(), e.getGenre(), timeStartLeft);
                        mList.add(dataObject);
                    }
                    mProgressBar.setVisibility(View.GONE);
                    mAdapterVertical.notifyDataSetChanged();
                    refreshHorizontalAdapter();
                }

            }

            @Override
            public void onFailure(@NonNull Call<EventJson> call, @NonNull Throwable t) {
                // Log error here since request failed
                mProgressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.GONE);
                noEventTextView.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
                noEventTextView.setText(R.string.touch_to_refresh);

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


    private long dateDifference(Date startDate, Date endDate) {
        return endDate.getTime() - startDate.getTime();
    }
    @NonNull
    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        String fragmentName = "Events";
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerViewVertical.setVisibility(View.GONE);
        noEventTextView.setVisibility(View.GONE);
        return new CustomTaskLoader(Events.this, mContext, fragmentName);

    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object o) {
        mRecyclerViewVertical.setVisibility(View.VISIBLE);
        noEventTextView.setVisibility(View.GONE);
        mAdapterVertical.notifyDataSetChanged();
        refreshHorizontalAdapter();
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
        Thread t1 = new Thread() {
            @Override
            public void run() {
                super.run();
                getLoaderManager().initLoader(0, null, Events.this);
                Objects.requireNonNull(getLoaderManager().getLoader(0)).startLoading();
            }
        };
        t1.run();
    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        mAdapterVertical.notifyDataSetChanged();
        refreshHorizontalAdapter();
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