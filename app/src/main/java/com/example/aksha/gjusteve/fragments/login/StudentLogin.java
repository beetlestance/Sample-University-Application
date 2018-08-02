package com.example.aksha.gjusteve.fragments.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.aksha.gjusteve.MainActivity;
import com.example.aksha.gjusteve.POJO.LoginJson;
import com.example.aksha.gjusteve.POJO.UserJson;
import com.example.aksha.gjusteve.R;
import com.example.aksha.gjusteve.database.APIClient;
import com.example.aksha.gjusteve.database.APIInterface;
import com.example.aksha.gjusteve.fragments.register.StudentRegister;
import com.example.aksha.gjusteve.helper.SQLiteHandler;
import com.example.aksha.gjusteve.helper.SessionManager;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.aksha.gjusteve.App.baseUrl;

public class StudentLogin extends Fragment {
    private EditText inputRollNo;
    private EditText inputPassword;
    private int Activity_Identifier=1;
    private SessionManager session;
    private SQLiteHandler db;
    private FrameLayout frameLayout;
    private Button btnLogin;
    private TextView btnLinkToRegister;
    private ProgressBar progressBar;
    private TextView invalid_login;
    private TextView fragmentLabel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // SQLite database handler
        db = new SQLiteHandler(getContext());
        // Session manager
        session = new SessionManager(Objects.requireNonNull(getContext()));
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            onDestroy();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_student_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputRollNo = view.findViewById(R.id.RollNo);
        inputPassword = view.findViewById(R.id.Password);
        frameLayout = view.findViewById(R.id.register_container);
        btnLogin = view.findViewById(R.id.Login);
        btnLinkToRegister = view.findViewById(R.id.not_registered);
        progressBar =view.findViewById(R.id.login_progress_Bar);
        invalid_login =view.findViewById(R.id.invalid_login);
        Button help = view.findViewById(R.id.login_help);
        fragmentLabel =view.findViewById(R.id.Sign_In);
        help.setOnClickListener(view13 -> Snackbar.make(view13,"Reach us at stancebeetle@gmail.com", Snackbar.LENGTH_LONG).show());
        btnLogin.setOnClickListener(view12 -> {
            String roll = inputRollNo.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            // Check for empty data in the form
            if (!roll.isEmpty() && !password.isEmpty()) {
                // login user
                btnLogin.setVisibility(View.INVISIBLE);
                inputRollNo.setVisibility(View.INVISIBLE);
                inputPassword.setVisibility(View.INVISIBLE);
                invalid_login.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                checkLogin(roll, password);
            } else {
                // Prompt user to enter credentials
                Toast.makeText(getContext(),
                        "Please enter the credentials!", Toast.LENGTH_LONG)
                        .show();
            }
        });

        btnLinkToRegister.setOnClickListener(view1 -> {
            if(Activity_Identifier==1) {
                Activity_Identifier=2;
                btnLogin.setVisibility(View.INVISIBLE);
                inputRollNo.setVisibility(View.INVISIBLE);
                inputPassword.setVisibility(View.INVISIBLE);
                invalid_login.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.GONE);
                btnLinkToRegister.setText(R.string.already_registered);
                frameLayout.setVisibility(View.VISIBLE);
                fragmentLabel.setText(R.string.register);
                FragmentManager fragmentManager = getFragmentManager();
                Objects.requireNonNull(fragmentManager).beginTransaction().setCustomAnimations(R.anim.up_from_bottom,R.anim.up_from_bottom).add(R.id.register_container, new StudentRegister()).commit();
            }
            else{
                Activity_Identifier=1;
                fragmentLabel.setText(R.string.sign_in);
                btnLinkToRegister.setText(R.string.not_registered);
                FragmentManager fragmentManager = getFragmentManager();
                Objects.requireNonNull(fragmentManager).beginTransaction().remove(Objects.requireNonNull(getFragmentManager()).findFragmentById(R.id.register_container)).commit();
                frameLayout.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);
                inputRollNo.setVisibility(View.VISIBLE);
                inputPassword.setVisibility(View.VISIBLE);

            }
        });

    }
// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    private void animateExit() {
//        int anim = R.anim.down_from_top;
//        Animation animation =AnimationUtils.loadAnimation(getActivity(), anim);
//        animation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });
//        frameLayout.startAnimation(animation);
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    private void checkLogin(final String roll, final String password) {
        // Tag used to cancel the request
        APIInterface apiService = APIClient.getClient(baseUrl).create(APIInterface.class);

        Call<LoginJson> call = apiService.getLogin(roll, password);

        call.enqueue(new Callback<LoginJson>() {
            @Override
            public void onResponse(@NonNull Call<LoginJson> call, @NonNull Response<LoginJson> response) {
                LoginJson loginJson =response.body();
                assert loginJson!=null;
                Boolean error = loginJson.getError();
                if (!error) {
                    session.setFirstTime(true);
                    session.setLogin(true);
                    String roll = loginJson.getRoll();
                    UserJson userJson = loginJson.getUser();
                    String name = userJson.getName();
                    String email = userJson.getEmail();
                    String course = userJson.getCourse();
                    String phone = userJson.getPhone();
                    String profileImage = userJson.getProfileImage();
                    db.addUser(name,email,roll,course,phone,profileImage);
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(getContext(),MainActivity.class);
                    startActivity(intent);
                    Objects.requireNonNull(getActivity()).finish();
                    onDestroy();
                } else {
                    btnLogin.setVisibility(View.VISIBLE);
                    inputRollNo.setVisibility(View.VISIBLE);
                    inputPassword.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    invalid_login.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginJson> call, @NonNull Throwable t) {
                // Log error here since request failed
                btnLogin.setVisibility(View.VISIBLE);
                inputRollNo.setVisibility(View.VISIBLE);
                inputPassword.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                invalid_login.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Unable to fetch data! No Internet Connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
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
