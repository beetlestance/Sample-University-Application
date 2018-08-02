package com.example.aksha.gjusteve.fragments.feedback;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aksha.gjusteve.POJO.NewFeedback;
import com.example.aksha.gjusteve.R;
import com.example.aksha.gjusteve.database.APIClient;
import com.example.aksha.gjusteve.database.APIInterface;
import com.example.aksha.gjusteve.database.ConnectionCheck;
import com.example.aksha.gjusteve.helper.SQLiteHandler;

import java.text.DateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aksha.gjusteve.App.baseUrl;


public class NewFeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feedback);
        mContext = this;
        final TextView userName = findViewById(R.id.userName);
        final TextView title = findViewById(R.id.feedback_title);
        final TextView feedback = findViewById(R.id.feedback);
        final SQLiteHandler sqLiteHandler = new SQLiteHandler(this);
        userName.setText(sqLiteHandler.getUserDetails().get("name"));
        Button closeThread = findViewById(R.id.close_thread);
        closeThread.setOnClickListener(this);
        TextView done = findViewById(R.id.done);
        done.setOnClickListener(view -> {

            if (ConnectionCheck.isOnline(mContext)) {
                String userProfile = sqLiteHandler.getUserDetails().get("url");
                String username = userName.getText().toString().trim();
                String date = DateFormat.getDateInstance().format(new Date());
                String status = "Unsolved";
                String category = title.getText().toString().trim();
                String suggestion = feedback.getText().toString().trim();
                if (!category.isEmpty() && !suggestion.isEmpty()) {
                    sendFeedback(userProfile, username, date, status, category, suggestion);
                } else {
                    if (toast != null) {
                        toast.cancel();
                        toast = Toast.makeText(mContext, "Please fill all the fields", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        toast = Toast.makeText(mContext, "Please fill all the fields", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            } else {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
                builder.setMessage("No Internet Connection!")
                        .setCancelable(false)
                        .setPositiveButton("Ok", (dialogInterface, i) -> {
                        }).show();
            }

        });
    }

    private void sendFeedback(String userProfile, String username, String date, String status, String category, String suggestion) {
        APIInterface apiInterface = APIClient.getClient(baseUrl).create(APIInterface.class);
        Call<NewFeedback> call = apiInterface.setFeedback(userProfile, username, date, status, category, suggestion);
        call.enqueue(new Callback<NewFeedback>() {
            @Override
            public void onResponse(@NonNull Call<NewFeedback> call, @NonNull Response<NewFeedback> response) {
                if (response.isSuccessful()) {
                    NewFeedback newFeedback = response.body();
                    assert newFeedback != null;
                    if (newFeedback.getSuccess().equals(true)) {
                        setResult(Activity.RESULT_OK);
                        onBackPressed();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewFeedback> call, @NonNull Throwable t) {
                if (toast != null) {
                    toast.cancel();
                    toast = Toast.makeText(mContext, "Unable to send! Try Again!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    toast = Toast.makeText(mContext, "Unable to send! Try Again!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onBackPressed();

    }


    @Override
    public void onClick(View view) {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
