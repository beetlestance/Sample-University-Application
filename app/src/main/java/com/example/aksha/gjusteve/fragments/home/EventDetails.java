package com.example.aksha.gjusteve.fragments.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aksha.gjusteve.POJO.Events;
import com.example.aksha.gjusteve.R;
import com.example.aksha.gjusteve.database.APIClient;
import com.example.aksha.gjusteve.database.APIInterface;
import com.example.aksha.gjusteve.helper.EventSQLite;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aksha.gjusteve.App.baseUrl;

public class EventDetails extends AppCompatActivity {

    private ImageView mCToolbarHeader;
    private TextView mTitle, mEventStartingDate;
    private TextView mGenre;
    private TextView mVenue;
    private TextView mDescription;
    private TextView mEventOrganiser;
    private TextView mTiming;
    private Context mContext;
    private Date finalEventStartingTime;
    private Date finalEventEndingTime;
    private String title;
    private String imageUrl;
    private String description;
    private static String startingDate;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        EventSQLite db = new EventSQLite(this);
        bindView();
        mContext = this;
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String uid = bundle.getString("uid");
        db.getImpEvents();
        setUI(uid);
    }

    private void bindView() {
        mTitle = findViewById(R.id.event_title);
        mGenre = findViewById(R.id.event_genre);
        mVenue = findViewById(R.id.event_venue);

        mDescription = findViewById(R.id.event_description);

        mCToolbarHeader = findViewById(R.id.cToolbarHeader);
        mTiming = findViewById(R.id.event_timing);
        mEventOrganiser = findViewById(R.id.event_organiser_name);
        findViewById(R.id.event_toolbar);

        mEventStartingDate = findViewById(R.id.event_starting_date);
    }

    private void setUI(String uid) {
        APIInterface apiService =
                APIClient.getClient(baseUrl).create(APIInterface.class);

        Call<Events> call = apiService.getSingleEvent(uid);
        call.enqueue(new Callback<Events>() {
            @Override
            public void onResponse(@NonNull Call<Events> call, @NonNull Response<Events> response) {
                if (response.isSuccessful()) {
                    Events events = response.body();
                    assert events != null;
                    title = events.getTitle();
                    String genre = events.getGenre();
                    String venue = events.getVenue();
                    description = events.getDescription();
                    startingDate = events.getStartingDate();
                    imageUrl = baseUrl + events.getImage();
                    try {
                        finalEventStartingTime = new SimpleDateFormat("dd-MM-yyyy HH-mm", Locale.US).parse(startingDate + " " + events.getStartingtime());
                        finalEventEndingTime = new SimpleDateFormat("dd-MM-yyyy HH-mm", Locale.US).parse(events.getEndingdate() + " " + events.getEndingtime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    String stDate = finalEventStartingTime.toString();
                    String enDate = finalEventEndingTime.toString();

                    mEventStartingDate.setText(String.format("Starting at %s,%s", stDate.substring(0, 3), stDate.substring(3, 10)));

                    mTiming.setText(String.format("%s-%s%s from %s to %s", stDate.substring(8, 10), enDate.substring(8, 10), enDate.substring(3, 7), events.getStartingtime(), events.getEndingtime()));
                    mTitle.setText(title);
                    mVenue.setText(venue);
                    mGenre.setText(genre);
                    mEventOrganiser.setText(String.format("Organised by : %s", events.getOrganiserName()));

                    mDescription.setText(description);
                    Glide.with(getApplicationContext())
                            .load(imageUrl)
                            .into(mCToolbarHeader);
                }

            }

            @Override
            public void onFailure(@NonNull Call<Events> call, @NonNull Throwable t) {
                if (toast != null) {
                    toast.cancel();
                    toast = Toast.makeText(mContext, "Unable to fetch! Check Internet Connection", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    toast = Toast.makeText(mContext, "Unable to fetch! Check Internet Connection", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

}
