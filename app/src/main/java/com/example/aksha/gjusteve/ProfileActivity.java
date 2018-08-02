package com.example.aksha.gjusteve;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.example.aksha.gjusteve.helper.OnSwipeTouchListener;
import com.example.aksha.gjusteve.helper.SQLiteHandler;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;
import static com.example.aksha.gjusteve.App.baseUrl;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Context mContext = getApplicationContext();

        SQLiteHandler dbHandler = new SQLiteHandler(mContext);
        CircleImageView mProfileImage = findViewById(R.id.profile_picture);
        TextView mProfileName = findViewById(R.id.profile_name);
        TextView mProfileEmail = findViewById(R.id.profile_email);
        TextView mProfileCourse = findViewById(R.id.profile_course);
        TextView mProfileYearJoining = findViewById(R.id.profile_joining_year);
        TextView mProfilePhone = findViewById(R.id.profile_phone);
        mProfileName.setText(dbHandler.getUserDetails().get("name"));
        mProfileEmail.setText(dbHandler.getUserDetails().get("email"));
        mProfileCourse.setText(dbHandler.getUserDetails().get("course"));
        mProfilePhone.setText(dbHandler.getUserDetails().get("phone"));
        String rollNumber = dbHandler.getUserDetails().get("roll").substring(0,2);
        mProfileYearJoining.setText(String.format("%s%s", getString(R.string.twenty), rollNumber));
        Glide.with(mContext).asBitmap()
                .load(baseUrl+"/android/"+ dbHandler.getUserDetails().get("url"))
                .apply(new RequestOptions()
                        .encodeFormat(Bitmap.CompressFormat.PNG)
                        .encodeQuality(100)
                        .placeholder(R.drawable.ic_person_24dp)
                        .error(R.drawable.ic_person_24dp)
                        .format(PREFER_ARGB_8888)
                        .signature(new ObjectKey(Integer.toString(App.getGlideVersion()))))
                .into(mProfileImage);

        mProfileImage.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this , ProfileEditActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left );

        });

        FrameLayout frameLayout = findViewById(R.id.profile);
        frameLayout.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                onBackPressed();
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
    protected void onStart() {
        super.onStart();
    }

}
