package com.example.aksha.gjusteve;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.example.aksha.gjusteve.fragments.Student_Portal.StudentPortal;
import com.example.aksha.gjusteve.fragments.announcement.Announcement;
import com.example.aksha.gjusteve.fragments.calendar.CalendarActivity;
import com.example.aksha.gjusteve.fragments.faculties.Faculties;
import com.example.aksha.gjusteve.fragments.feedback.SuggestionAndFeedback;
import com.example.aksha.gjusteve.fragments.home.Home;
import com.example.aksha.gjusteve.helper.SQLiteHandler;
import com.example.aksha.gjusteve.helper.SessionManager;
import com.example.aksha.gjusteve.login.LoginChoice;
import com.example.aksha.gjusteve.login.StartIntro;
import de.hdodenhof.circleimageview.CircleImageView;
import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;
import static com.example.aksha.gjusteve.App.baseUrl;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private SQLiteHandler db;
    private SessionManager session;
    private DrawerLayout drawerLayout;
    private CircleImageView profileImage;
    private TextView profileName;
    private TextView profileEmail;
    private Handler handler;
    private Runnable r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        setTitle("Event Viewer");

        db = new SQLiteHandler(getApplicationContext());

        session = new SessionManager(getApplicationContext());

        if (!session.isFirstTime()) {
            Intent intent = new Intent(MainActivity.this, StartIntro.class);
            startActivity(intent);
            finish();
        } else if (!session.isGuestLoggedIn()) {
            if (!session.isLoggedIn()) {
                logoutUser();
            }
        }

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        View headerView = navigationView.getHeaderView(0);

        profileImage = headerView.findViewById(R.id.iv_profile_pic);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        drawerLayout.removeDrawerListener(mDrawerToggle);

        profileName = headerView.findViewById(R.id.student_name);
        profileEmail = headerView.findViewById(R.id.student_email);

        if (session.isLoggedIn()) {
            headerView.setOnClickListener(view -> {
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            });
        }
        if (savedInstanceState == null) {

            handler = new Handler();
            r = () -> {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.frame_layout, new Home(), "Home").commitAllowingStateLoss();
            };
            handler.postDelayed(r, 200);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Home myFragment = (Home) getSupportFragmentManager().findFragmentByTag("Home");
            if (myFragment != null && myFragment.isVisible()) {
                super.onBackPressed();
            } else {
                setTitle("Event Viewer");
                navigationView.getMenu().getItem(0).setChecked(true);
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction().setCustomAnimations(R.anim.up_from_bottom, R.anim.slide_out_right).replace(R.id.frame_layout, new Home(), "Home").commit();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        int id = item.getItemId();
        if (!item.isChecked()) {
            switch (id) {

                case R.id.nav_Home: {
                    setTitle("Event Viewer");
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.up_from_bottom, R.anim.slide_out_right).replace(R.id.frame_layout, new Home(), "Home").commit();
                    break;
                }

                case R.id.nav_student_portal: {
                    setTitle("Dashboard");
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.up_from_bottom, R.anim.slide_out_right).replace(R.id.frame_layout, new StudentPortal()).commit();
                   break;
                }

                case R.id.nav_announcement: {
                    setTitle("Announcement");
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.up_from_bottom, R.anim.slide_out_right).replace(R.id.frame_layout, new Announcement()).commit();
                    break;
                }
                case R.id.nav_faculties: {
                    setTitle("Faculties");
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.up_from_bottom, R.anim.slide_out_right).replace(R.id.frame_layout, new Faculties()).commit();
                    break;
                }
                case R.id.nav_calendar: {
                    setTitle("Academic Calendar");
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.up_from_bottom, R.anim.slide_out_right).replace(R.id.frame_layout, new CalendarActivity()).commit();
                    break;
                }
                case R.id.nav_menu_suggestion: {
                    setTitle("Suggestions And Feedback");
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.up_from_bottom, R.anim.slide_out_right).replace(R.id.frame_layout, new SuggestionAndFeedback()).commit();
                    break;
                }

                case R.id.nav_about: {
                    Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    item.setCheckable(false);
                    break;
                }
                case R.id.nav_sign_out: {
                    item.setCheckable(false);
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                    builder.setMessage("Are you sure to Log Out")
                            .setCancelable(false)
                            .setPositiveButton("yes", (dialogInterface, i) -> logoutUser()).setNegativeButton("No", (dialogInterface, i) -> {
                            }).show();
                    break;
                }
                default:
                    return false;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return !session.isGuestLoggedIn();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent profileEditActivity = new Intent(MainActivity.this, ProfileEditActivity.class);
        startActivity(profileEditActivity);
        return true;
    }

    private void logoutUser() {
        session.setLogin(false);
        session.setGuestLogin(false);
        db.deleteUsers();
        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginChoice.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Glide.with(this).load(baseUrl + "/android/" + db.getUserDetails().get("url"))
                .apply(new RequestOptions()
                .placeholder(R.drawable.ic_person_24dp))
                .into(profileImage);
        profileEmail.setText(db.getUserDetails().get("email"));
        profileName.setText(db.getUserDetails().get("name"));
    }

    @Override
    protected void onResume() {

        super.onResume();
        /*if(App.getGlideVersion()==App.getPreGlideVersion()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Glide.get(MainActivity.this).clearDiskCache();
                }
            }).start();
            App.setPreGlideVersion(App.getGlideVersion());
        }*/
        Glide.with(this).asBitmap()
                .load(baseUrl + "/android/" + db.getUserDetails().get("url"))
                .apply(new RequestOptions()
                        .encodeFormat(Bitmap.CompressFormat.PNG)
                        .encodeQuality(100)
                        .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_person_24dp))
                        .error(R.drawable.ic_person_24dp)
                        .format(PREFER_ARGB_8888)
                        .signature(new ObjectKey(Integer.toString(App.getGlideVersion()))))
                .into(profileImage);

        //forceUpdate(); giving error
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (handler != null) {
            handler.removeCallbacks(r);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(r);
        }
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void forceUpdate() {
//        PackageManager packageManager = this.getPackageManager();
//        PackageInfo packageInfo = null;
//        try {
//            packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        String currentVersion = null;
//        if (packageInfo != null) {
//            currentVersion = packageInfo.versionName;
//        }
//        new ForceUpdateAsync(currentVersion, MainActivity.this).execute();
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)


}
