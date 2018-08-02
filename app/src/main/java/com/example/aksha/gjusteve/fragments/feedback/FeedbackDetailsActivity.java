package com.example.aksha.gjusteve.fragments.feedback;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.aksha.gjusteve.POJO.FeedbackJson;
import com.example.aksha.gjusteve.POJO.FeedbackList;
import com.example.aksha.gjusteve.R;
import com.example.aksha.gjusteve.database.APIClient;
import com.example.aksha.gjusteve.database.APIInterface;
import com.example.aksha.gjusteve.helper.DataParser;
import com.example.aksha.gjusteve.helper.EventSQLite;
import com.example.aksha.gjusteve.helper.RoundedImageView;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;
import static com.example.aksha.gjusteve.App.baseUrl;
import static com.example.aksha.gjusteve.App.getContext;

public class FeedbackDetailsActivity extends AppCompatActivity implements View.OnClickListener,DataParser {

    private ArrayList<FeedbackJson> setlectedFeedbackList;
    private EventSQLite db;
    private TextView personName;
    private static String uid;
    private boolean isLiked = false;
    private TextView date;
    private TextView upvote;
    private TextView comments;
    private TextView status;
    private TextView category;
    private TextView suggestion;
    private RoundedImageView profilePic;
    private Toast toast;
    private Context mContext;

    public FeedbackDetailsActivity(){
    }
    public static void getuid(int id){
        uid=Integer.toString(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_details);
        db = new EventSQLite(this);
        ArrayList<String> impEvents = db.getUpvote();
        personName = findViewById(R.id.userName);
        date = findViewById(R.id.displayDate);
        comments = findViewById(R.id.displayComments);
        status = findViewById(R.id.displayStatus);
        category = findViewById(R.id.displayCategory);
        suggestion = findViewById(R.id.dispalySuggestion);
        upvote = findViewById(R.id.Upvote);
        profilePic = findViewById(R.id.iv_profile_pic);
        setlectedFeedbackList = new ArrayList<>();
        mContext = getApplicationContext();
        Button closeThread = findViewById(R.id.close_thread);
        closeThread.setOnClickListener(this);
        for(int i = 0; i< impEvents.size(); i++){
            if (Objects.equals(uid, impEvents.get(i))) {
                upvote.setTag(true);
                upvote.setVisibility(View.GONE);
                break;
            } else {
                upvote.setTag(false);
            }
        }
        prepareData();
    }
    @Override
    public void onBackPressed(){
        setResult(Activity.RESULT_OK);
        finish();
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right );
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        setResult(Activity.RESULT_OK);
        finish();
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right );
    }

    @Override
    public void prepareData() {
        APIInterface apiInterface = APIClient.getClient(baseUrl).create(APIInterface.class);
        Call<FeedbackList> call = apiInterface.getSelectedFeedbackarray(uid);
        call.enqueue(new Callback<FeedbackList>() {
            @Override
            public void onResponse(@NonNull Call<FeedbackList> call, @NonNull Response<FeedbackList> response) {
                if(response.isSuccessful()) {
                    FeedbackList feedbackList = response.body();
                    assert feedbackList!=null;
                    setlectedFeedbackList = feedbackList.getFeedbackarray();
                    personName.setText(setlectedFeedbackList.get(0).getUsername());
                    date.setText(setlectedFeedbackList.get(0).getDate());
                    comments.setText(setlectedFeedbackList.get(0).getComments());
                    status.setText(setlectedFeedbackList.get(0).getStatus());
                    category.setText(setlectedFeedbackList.get(0).getCategory());
                    suggestion.setText(setlectedFeedbackList.get(0).getSuggestion());
                    Glide.with(getContext())
                            .asBitmap()
                            .load(baseUrl+"/android/"+setlectedFeedbackList.get(0).getUserprofile())
                            .apply(new RequestOptions()
                                    .encodeFormat(Bitmap.CompressFormat.PNG)
                                    .encodeQuality(100)
                                    .placeholder(R.drawable.ic_person_24dp)
                                    .error(R.drawable.ic_person_24dp)
                                    .format(PREFER_ARGB_8888)
                                    .diskCacheStrategy(DiskCacheStrategy.DATA))
                            .into(profilePic);
                    upvote.setOnClickListener(view -> {
                        Log.d("UID",uid);
                        if(upvote.getTag()!=null){
                            isLiked = (boolean) upvote.getTag();
                        }
                        if (!isLiked){
                            upvote.setTag(true);
                            db.addUpvote(uid);
                            updateUpvotes(uid);
                            upvote.setVisibility(View.GONE);
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<FeedbackList> call, @NonNull Throwable t) {

            }
        });
    }

    private void updateUpvotes(String uid) {
        APIInterface apiService =
                APIClient.getClient(baseUrl).create(APIInterface.class);

        Call<FeedbackJson> call = apiService.getUpvoteUpdate(uid, "1");
        call.enqueue(new Callback<FeedbackJson>() {
            @Override
            public void onResponse(@NonNull Call<FeedbackJson> call, @NonNull Response<FeedbackJson> response) {
                FeedbackJson events=response.body();
                assert events!=null;
                String count = events.getComments();
                comments.setText(String.valueOf(count));
            }

            @Override
            public void onFailure(@NonNull Call<FeedbackJson> call, @NonNull Throwable t) {
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

}
