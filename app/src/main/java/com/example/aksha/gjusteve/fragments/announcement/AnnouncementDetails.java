package com.example.aksha.gjusteve.fragments.announcement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.aksha.gjusteve.R;

public class AnnouncementDetails extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_detail);

        TextView mTextView = findViewById(R.id.text_announcement_detail);

        Bundle intent = getIntent().getExtras();
        assert intent != null;
        String mData = intent.getString("data");
        mTextView.setText(mData);



    }
}
