package com.example.aksha.gjusteve.fragments.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.aksha.gjusteve.MainActivity;
import com.example.aksha.gjusteve.POJO.LoginJson;
import com.example.aksha.gjusteve.POJO.UserJson;
import com.example.aksha.gjusteve.R;
import com.example.aksha.gjusteve.database.APIClient;
import com.example.aksha.gjusteve.database.APIInterface;
import com.example.aksha.gjusteve.helper.DataParser;
import com.example.aksha.gjusteve.helper.SQLiteHandler;
import com.example.aksha.gjusteve.helper.SessionManager;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.aksha.gjusteve.App.baseUrl;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentRegister extends Fragment implements DataParser, LoaderManager.LoaderCallbacks {

    private EditText inputFullName;
    private EditText inputFatherName;
    private EditText inputEmail;
    private EditText inputPhone;
    private EditText inputPassword;
    private EditText inputConfirmPassword;
    private TextView label;
    private Button btnRegister;
    private SessionManager session;
    private SQLiteHandler db;
    private int REGISTERATION_STEP = 1;
    private String Roll_no;
    private String fathername;
    private String Email;
    private String Phone;
    private ProgressBar progressBar;
    private String errorMsg = "";
    private Toast toast;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(Objects.requireNonNull(getContext()));
        mContext = getContext();
        db = new SQLiteHandler(getContext());
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
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_student_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        label = view.findViewById(R.id.registration_step);
        inputFullName = view.findViewById(R.id.RollNo);
        inputFatherName = view.findViewById(R.id.father_name);
        inputEmail = view.findViewById(R.id.email);
        inputPhone = view.findViewById(R.id.phone_number);
        inputPassword = view.findViewById(R.id.password);
        inputConfirmPassword = view.findViewById(R.id.confirm_password);
        btnRegister = view.findViewById(R.id.btnRegister);
        progressBar = view.findViewById(R.id.register_progress_Bar);
        updateFragment();
        btnRegister.setOnClickListener(view1 -> updateFragment());
    }




    private void updateFragment() {
        switch (REGISTERATION_STEP) {
            case 1: {
                label.setText(R.string.new_user_register_here);
                btnRegister.setText(R.string.next);
                inputFullName.setVisibility(View.VISIBLE);
                inputFatherName.setVisibility(View.VISIBLE);
                Roll_no = inputFullName.getText().toString().trim();
                fathername = inputFatherName.getText().toString().trim();
                if (!Roll_no.isEmpty() && !fathername.isEmpty()) {
                    REGISTERATION_STEP = 2;
                    if (errorMsg.isEmpty()) {
                        updateFragment();
                    } else
                        errorMsg = "";
                } else if (Roll_no.isEmpty()) {
                    inputFullName.requestFocus();
                } else {
                    inputFatherName.requestFocus();
                }
                break;
            }
            case 2: {
                label.setText(R.string.personal_details);
                inputFatherName.setVisibility(View.GONE);
                inputFullName.setVisibility(View.GONE);
                inputEmail.setVisibility(View.VISIBLE);
                inputPhone.setVisibility(View.VISIBLE);
                Email = inputEmail.getText().toString().trim();
                Phone = inputPhone.getText().toString().trim();
                if (!Email.isEmpty() && !Phone.isEmpty()) {
                    REGISTERATION_STEP = 3;
                    updateFragment();
                } else if (Email.isEmpty()) {
                    inputEmail.requestFocus();
                } else {
                    inputPhone.requestFocus();
                }
                break;
            }
            case 3: {
                label.setText(R.string.confirm_password);
                inputEmail.setVisibility(View.GONE);
                inputPhone.setVisibility(View.GONE);
                inputPassword.setVisibility(View.VISIBLE);
                inputConfirmPassword.setVisibility(View.VISIBLE);
                btnRegister.setText(R.string.register);
                String password = inputPassword.getText().toString().trim();
                String confirm_password = inputConfirmPassword.getText().toString().trim();

                if (!password.isEmpty() && !confirm_password.isEmpty() && Objects.equals(password, confirm_password)) {
                    progressBar.setVisibility(View.VISIBLE);
                    inputConfirmPassword.setVisibility(View.GONE);
                    inputPassword.setVisibility(View.GONE);
                    btnRegister.setVisibility(View.GONE);
                    registerUser(Roll_no, Email, password, fathername, Phone);

                } else if (password.isEmpty()) {
                    inputPassword.requestFocus();
                } else if (confirm_password.isEmpty() || !Objects.equals(confirm_password, password)) {
                    inputConfirmPassword.requestFocus();
                    inputConfirmPassword.setError("Password Mismatch");
                }
            }
        }
    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     */
    private void registerUser(final String roll, final String email,
                              final String password, final String father, final String phone) {
        // Tag used to cancel the request
        APIInterface apiService = APIClient.getClient(baseUrl).create(APIInterface.class);
        Call<LoginJson> call = apiService.register(roll, email, password, father, phone);
        call.enqueue(new Callback<LoginJson>() {
            @Override
            public void onResponse(@NonNull Call<LoginJson> call, @NonNull Response<LoginJson> response) {
                LoginJson loginJson = response.body();
                assert loginJson != null;
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
                    Log.e("STUDENT", "" + roll + name + email + course + phone + loginJson.getFather());
                    db.addUser(name, email, roll, course, phone, profileImage);
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                    Objects.requireNonNull(getActivity()).finish();
                    onDestroy();
                } else {
                    progressBar.setVisibility(View.GONE);
                    inputConfirmPassword.setVisibility(View.GONE);
                    inputPassword.setVisibility(View.GONE);
                    btnRegister.setVisibility(View.VISIBLE);
                    inputConfirmPassword.setText("");
                    inputPassword.setText("");
                    errorMsg = loginJson.getErrorMsg();
                    if (toast != null) {
                        toast.cancel();
                        toast = Toast.makeText(mContext, "Unable to fetch! Check Internet Connection", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else{
                        toast = Toast.makeText(mContext, "Unable to fetch! Check Internet Connection", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    REGISTERATION_STEP = 1;
                    updateFragment();

                }

            }

            @Override
            public void onFailure(@NonNull Call<LoginJson> call, @NonNull Throwable t) {
                // Log error here since request failed
                progressBar.setVisibility(View.GONE);
                inputConfirmPassword.setVisibility(View.VISIBLE);
                inputPassword.setVisibility(View.VISIBLE);
                btnRegister.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void prepareData() {

    }

    @NonNull
    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object o) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }
}
