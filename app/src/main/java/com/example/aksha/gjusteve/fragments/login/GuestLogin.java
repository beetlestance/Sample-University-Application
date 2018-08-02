package com.example.aksha.gjusteve.fragments.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.aksha.gjusteve.MainActivity;
import com.example.aksha.gjusteve.R;
import com.example.aksha.gjusteve.helper.SessionManager;

import java.util.Objects;


public class GuestLogin extends Fragment {

    private SessionManager session;
    public GuestLogin() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(Objects.requireNonNull(getContext()));
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_guest_login, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button loginButton = view.findViewById(R.id.Login);

        loginButton.setOnClickListener(view1 -> {
            session.setFirstTime(true);
            session.setGuestLogin(true);
            Intent intent = new Intent(getContext(),MainActivity.class);
            startActivity(intent);
            Objects.requireNonNull(getActivity()).finish();
        });
    }
}
