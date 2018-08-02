package com.example.aksha.gjusteve;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.example.aksha.gjusteve.POJO.LoginJson;
import com.example.aksha.gjusteve.POJO.ProfileJson;
import com.example.aksha.gjusteve.database.APIClient;
import com.example.aksha.gjusteve.database.APIInterface;
import com.example.aksha.gjusteve.database.ConnectionCheck;
import com.example.aksha.gjusteve.helper.SQLiteHandler;
import com.example.aksha.gjusteve.helper.SessionManager;
import com.example.aksha.gjusteve.login.LoginChoice;
import com.rengwuxian.materialedittext.MaterialEditText;
import java.io.File;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;
import static com.example.aksha.gjusteve.App.baseUrl;
import static com.example.aksha.gjusteve.App.getContext;

public class ProfileEditActivity extends AppCompatActivity {

    private EditText mNewEmail;
    private EditText mNewPhone;
    private EditText mNewPass;
    private EditText mRepeatNewPass;
    private CircleImageView mProfileImage;

    final private int RESULT_LOAD_IMAGE = 1;
    private Context mContext;
    private SQLiteHandler sqlHandler;
    private String currentPassword;
    private String sEmail;
    private String sPhone;
    private String sNewPass;
    private String userName;
    private String userRoll;
    private String userCourse;
    private File file;
    private SessionManager session;
    private Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        mContext = getApplicationContext();
        sqlHandler = new SQLiteHandler(mContext);
        TextView mProfileName = findViewById(R.id.profile_edit_name);
        session = new SessionManager(mContext);
        userName = sqlHandler.getUserDetails().get("name");
        userRoll = sqlHandler.getUserDetails().get("roll");
        userCourse = sqlHandler.getUserDetails().get("course");
        String userEmail = sqlHandler.getUserDetails().get("email");
        String userPhone = sqlHandler.getUserDetails().get("phone");
        mProfileName.setText(userName);
        mNewEmail = findViewById(R.id.profile_edit_email);
        mNewPhone = findViewById(R.id.profile_edit_phone);
        mNewPass = findViewById(R.id.profile_edit_password);
        mRepeatNewPass = findViewById(R.id.profile_edit_repeat_pass);
        mProfileImage = findViewById(R.id.profile_edit_image);
        Glide.with(mContext).asBitmap()
                .load(baseUrl + "/android/" + sqlHandler.getUserDetails().get("url"))
                .apply(new RequestOptions()
                        .encodeFormat(Bitmap.CompressFormat.PNG)
                        .encodeQuality(100)
                        .placeholder(R.drawable.ic_person_24dp)
                        .error(R.drawable.ic_person_24dp)
                        .format(PREFER_ARGB_8888)
                        .signature(new ObjectKey(Integer.toString(App.getGlideVersion()))))
                .into(mProfileImage);

        Toolbar mToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        setTitle("Edit Profile");
        mNewEmail.setText(userEmail);
        mNewPhone.setText(userPhone);
        mProfileImage.setOnClickListener(view -> {
            if (checkPermission()) {
                Intent imageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imageIntent, RESULT_LOAD_IMAGE);
            } else {
                requestPermission();
            }
        });
    }

    private boolean checkPermission() {
        int result = ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            int PERMISSION_REQUEST_CODE = 1;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        }
        checkPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent imageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(imageIntent, RESULT_LOAD_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String filePath = getPath(selectedImage);
                String file_extn = filePath.substring(filePath.lastIndexOf(".") + 1);
                if (file_extn.equals("img") || file_extn.equals("jpg") || file_extn.equals("jpeg") || file_extn.equals("png")) {
                    Glide.with(mContext).load(filePath).into(mProfileImage);
                } else {
                    //NOT IN REQUIRED FORMAT
                    Log.d("File ", "not found");
                }
                file = new File(filePath);
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private String getPath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        assert cursor != null;
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String imagePath = cursor.getString(columnIndex);
        cursor.close();
        return imagePath;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save: {
                sEmail = String.valueOf(mNewEmail.getText());
                Boolean eMailMatches = Patterns.EMAIL_ADDRESS.matcher(sEmail).matches();
                sPhone = String.valueOf(mNewPhone.getText());
                sNewPass = String.valueOf(mNewPass.getText());
                String sRepeatNewPass = String.valueOf(mRepeatNewPass.getText());
                if (!eMailMatches || sEmail.isEmpty()) {
                    mNewEmail.setError("Invalid Email");
                }
                int phoneLength = sPhone.length();
                if (phoneLength != 10) {
                    mNewPhone.setError("Invalid Phone Number");
                }
                Boolean passMatches;
                passMatches = sNewPass.equals(sRepeatNewPass);
                if (!passMatches) {
                    mRepeatNewPass.setError("Passwords don't match");
                }
                if (sNewPass == null && sRepeatNewPass == null) {
                    sNewPass = "0";
                }
                if ((sEmail.length() == 0 || eMailMatches) && (phoneLength == 0 || phoneLength == 10) && (passMatches || (sNewPass == null && sRepeatNewPass == null))) {
                    if (ConnectionCheck.isOnline(mContext))
                        createDialogBox();
                    else {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                        builder.setMessage("No Internet Connection!")
                                .setCancelable(false)
                                .setPositiveButton("Ok", (dialogInterface, i) -> {
                                }).show();
                    }
                }
                return true;
            }
            default:
                return true;
        }
    }

    private void createDialogBox() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProfileEditActivity.this);
        alertDialog.setTitle("You will be logged out!");
        LayoutInflater inflater = ProfileEditActivity.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_dialog_edit_profile,new LinearLayout(getContext()),false);
        final MaterialEditText input = view.findViewById(R.id.profile_edit_alert_dialog);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setTransformationMethod(PasswordTransformationMethod.getInstance());
        alertDialog.setView(view);
        alertDialog.setPositiveButton("Save", (dialogInterface, i) -> {
            currentPassword = input.getText().toString();
            updateDetails(currentPassword);
            if (file != null) {
                sendImage(file);
            }
            logoutUser();
        })
                .setNegativeButton("Discard", (dialogInterface, i) -> {
                }).show();
    }

    private void logoutUser() {
        session.setLogin(false);
        session.setGuestLogin(false);
        sqlHandler.deleteUsers();
        Intent intent = new Intent(ProfileEditActivity.this, LoginChoice.class);
        startActivity(intent);
        finish();
    }

    private void updateDetails(String passString) {
        APIInterface apiService = APIClient.getClient(baseUrl).create(APIInterface.class);
        Call<LoginJson> loginJson = apiService.getResetPassword(userRoll, passString, sEmail, sPhone, sNewPass);
        loginJson.enqueue(new Callback<LoginJson>() {
            @Override
            public void onResponse(@NonNull Call<LoginJson> call, @NonNull Response<LoginJson> response) {
                LoginJson json = response.body();
                assert json != null;
                Boolean error = json.getError();
                if (!error) {
                    sqlHandler.updateUser(userName, sEmail, userRoll, userCourse, sPhone);
                } else {
                    if (toast != null) {
                        toast.cancel();
                        toast = Toast.makeText(mContext, "Can't save details", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else{
                        toast = Toast.makeText(mContext, "Can't save details", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginJson> call, @NonNull Throwable t) {
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

    private void sendImage(File file) {
        APIInterface apiService =APIClient.getClient(baseUrl).create(APIInterface.class);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        Call<ProfileJson> call = apiService.postImage(body, name, sqlHandler.getUserDetails().get("roll"));
        call.enqueue(new Callback<ProfileJson>() {
            @Override
            public void onResponse(@NonNull Call<ProfileJson> call, @NonNull Response<ProfileJson> response) {

                ProfileJson serverResponse = response.body();
                assert serverResponse != null;
                if (response.isSuccessful()) {
                    if (serverResponse.getSuccess()) {
                        App.setGlideVersion(App.getGlideVersion() + 1);
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProfileJson> call, @NonNull Throwable t) {
                // Log error here since request failed
                if (toast != null) {
                    toast.cancel();
                    toast = Toast.makeText(mContext, "Unable to upload Image", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    toast = Toast.makeText(mContext, "Unable to upload Image", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
