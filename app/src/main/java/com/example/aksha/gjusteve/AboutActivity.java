package com.example.aksha.gjusteve;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Button button = findViewById(R.id.back);
        button.setOnClickListener(this);

    }

    @Override
    public void onBackPressed(){
        finish();
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right );
            super.onBackPressed();

    }

    @Override
    public void onClick(View view) {
        finish();
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right );
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }
    @Override
    protected void onStop(){
        super.onStop();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
