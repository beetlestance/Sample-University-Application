package com.example.aksha.gjusteve.fragments.feedback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aksha.gjusteve.POJO.FeedbackJson;
import com.example.aksha.gjusteve.POJO.FeedbackList;
import com.example.aksha.gjusteve.R;
import com.example.aksha.gjusteve.adapter.SuggestionRecyclerViewAdapter;
import com.example.aksha.gjusteve.database.APIClient;
import com.example.aksha.gjusteve.database.APIInterface;
import com.example.aksha.gjusteve.helper.CustomTaskLoader;
import com.example.aksha.gjusteve.helper.DataParser;
import com.example.aksha.gjusteve.helper.DividerItemDecoration;
import com.example.aksha.gjusteve.helper.ItemClickSupport;
import com.example.aksha.gjusteve.helper.SessionManager;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aksha.gjusteve.App.baseUrl;

public class SuggestionAndFeedback extends Fragment implements DataParser, LoaderManager.LoaderCallbacks {
    private ArrayList<FeedbackJson> feedbackList;
    private Context mContext;
    private RecyclerView rView;
    private ProgressBar mProgressBar;
    private SessionManager session;
    private TextView noFeedback;
    private Toast toast;

    public SuggestionAndFeedback() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedbackList = new ArrayList<>();
        mContext = getContext();
        assert mContext != null;
        session = new SessionManager(mContext);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        //setAdapter(feedbackList);
        return layoutInflater.inflate(R.layout.activity_suggestion_and_feedback, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressBar = view.findViewById(R.id.suggestion_progress_bar);
        noFeedback = view.findViewById(R.id.no_feedback_text_view);
        noFeedback.setVisibility(View.GONE);
        noFeedback.setOnClickListener(view12 -> {
            FragmentManager fragmentManager = getFragmentManager();
            assert fragmentManager != null;
            fragmentManager.beginTransaction().replace(R.id.frame_layout, new SuggestionAndFeedback()).commit();
        });
        rView = view.findViewById(R.id.recycler_view);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.new_thread);
        floatingActionButton.setOnClickListener(view1 -> {
            if (session.isGuestLoggedIn()) {
                Toast.makeText(getContext(), "Sorry! You are a Guest User.", Toast.LENGTH_LONG).show();
                if (toast != null) {
                    toast.cancel();
                    toast = Toast.makeText(mContext, "Sorry! You are logged in as Guest", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    toast = Toast.makeText(mContext, "Sorry! You are logged in as Guest", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } else {
                Intent intent = new Intent(getActivity(), NewFeedbackActivity.class);
                startActivityForResult(intent, 1);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


    }

    private void setAdapter(ArrayList<FeedbackJson> feedbackList) {
        SuggestionRecyclerViewAdapter adapter = new SuggestionRecyclerViewAdapter(mContext, feedbackList);
        LinearLayoutManager lLayout = new LinearLayoutManager(getActivity());
        rView.setLayoutManager(lLayout);
        Drawable dividerDrawable = ContextCompat.getDrawable(Objects.requireNonNull(getActivity()), R.drawable.divider);
        rView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        ItemClickSupport.addTo(rView).setOnItemClickListener((recyclerView, position, v) -> {
            int id = recyclerView.findViewHolderForAdapterPosition(position).itemView.getId();
            FeedbackDetailsActivity.getuid(id);
            Intent intent = new Intent(getActivity(), FeedbackDetailsActivity.class);
            startActivityForResult(intent, 1);
            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        rView.setAdapter(adapter);
    }

    public void prepareData() {
        APIInterface apiInterface = APIClient.getClient(baseUrl).create(APIInterface.class);
        Call<FeedbackList> call = apiInterface.getFeedbackarray();
        call.enqueue(new Callback<FeedbackList>() {
            @Override
            public void onResponse(@NonNull Call<FeedbackList> call, @NonNull Response<FeedbackList> response) {
                if (response.isSuccessful()) {
                    FeedbackList mFeedbackList = response.body();
                    assert mFeedbackList != null;
                    feedbackList = mFeedbackList.getFeedbackarray();
                    mProgressBar.setVisibility(View.GONE);
                    setAdapter(feedbackList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FeedbackList> call, @NonNull Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                noFeedback.setVisibility(View.VISIBLE);
                noFeedback.setText(R.string.touch_to_refresh);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getActivity();
        if (resultCode == Activity.RESULT_OK) {
            Objects.requireNonNull(getLoaderManager().getLoader(0)).forceLoad();
        }
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        String fragment = "SuggestionAndFeedback";
        mProgressBar.setVisibility(View.VISIBLE);
        rView.setVisibility(View.GONE);
        noFeedback.setVisibility(View.GONE);
        return new CustomTaskLoader(SuggestionAndFeedback.this, mContext, fragment);
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object o) {
        rView.setVisibility(View.VISIBLE);
        noFeedback.setVisibility(View.GONE);
        //setAdapter(feedbackList);
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
                getLoaderManager().initLoader(0, null, SuggestionAndFeedback.this);
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
