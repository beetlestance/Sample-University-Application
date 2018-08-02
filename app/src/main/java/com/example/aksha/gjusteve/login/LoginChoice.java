package com.example.aksha.gjusteve.login;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aksha.gjusteve.R;
import com.example.aksha.gjusteve.adapter.LoginViewPagerAdapter;

public class LoginChoice extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_choice);
        ViewPager pager = findViewById(R.id.photos_viewpager);
        PagerAdapter adapter = new LoginViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager, true);
        pager.setCurrentItem(1);
    }
}
