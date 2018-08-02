package com.example.aksha.gjusteve.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.aksha.gjusteve.fragments.login.StudentLogin;
import com.example.aksha.gjusteve.fragments.login.GuestLogin;

public class LoginViewPagerAdapter extends FragmentStatePagerAdapter{
    public LoginViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return new GuestLogin();
            case 1:
                return new StudentLogin();
           // case 2:
              //  return new TeacherLogin();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
