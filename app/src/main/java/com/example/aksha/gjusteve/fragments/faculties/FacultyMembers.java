package com.example.aksha.gjusteve.fragments.faculties;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aksha.gjusteve.App;
import com.example.aksha.gjusteve.POJO.FacultiesJson;
import com.example.aksha.gjusteve.POJO.FacultiesList;
import com.example.aksha.gjusteve.R;
import com.example.aksha.gjusteve.adapter.FacultiesAdapter;
import com.example.aksha.gjusteve.database.APIClient;
import com.example.aksha.gjusteve.database.APIInterface;
import com.example.aksha.gjusteve.helper.CustomTaskLoader;
import com.example.aksha.gjusteve.helper.DataParser;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;
import static com.example.aksha.gjusteve.App.baseUrl;
public class FacultyMembers extends Fragment implements DataParser,LoaderManager.LoaderCallbacks {

    private Context context;
    private ListView listView;
    private ArrayList<FacultiesJson> contactList;
    private FacultiesAdapter adapter;
    private static String department;
    private ProgressBar mProgressBar;
    private TextView no_faculty_view;
    private Toast toast;

    public FacultyMembers(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = App.getContext();
    }
    public static void setUrl(String department_name)
    {
        department=department_name;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_faculty_members, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contactList =new ArrayList<>();
        final View parentView = view.findViewById(R.id.parentLayout);
        listView = view.findViewById(R.id.listView);
        no_faculty_view = view.findViewById(R.id.no_faculty_view);
        no_faculty_view.setVisibility(View.GONE);
        no_faculty_view.setOnClickListener(view12 -> {
            FragmentManager fragmentManager = getFragmentManager();
            assert fragmentManager != null;
            fragmentManager.beginTransaction().replace(R.id.faculty_members, new FacultyMembers()).commit();
        });
        mProgressBar = view.findViewById(R.id.faculty_progress_Bar);
        listView.setOnItemClickListener((parent, view1, position, id) -> Snackbar.make(parentView, contactList.get(position).getName() + " => " + contactList.get(position).getPhone(), Snackbar.LENGTH_LONG).show());
    }
    @Override
    public void onStart() {
        super.onStart();
        Thread t1 =new Thread(){
            @Override
            public void run() {
                super.run();
                getLoaderManager().initLoader(0, null, FacultyMembers.this);
                Objects.requireNonNull(getLoaderManager().getLoader(0)).startLoading();
            }
        };
        t1.run();
    }


    public void prepareData(){
        APIInterface apiInterface = APIClient.getClient(baseUrl).create(APIInterface.class);
        Call<FacultiesList> call = apiInterface.getFaculties(department);
        call.enqueue(new Callback<FacultiesList>() {

            @Override
            public void onResponse(@NonNull Call<FacultiesList> call, @NonNull Response<FacultiesList> response ) {
                if (response.isSuccessful()) {
                    FacultiesList facultiesList =response.body();
                    assert facultiesList!=null;
                    contactList = facultiesList.getContacts();
                    adapter = new FacultiesAdapter(context, contactList);
                    mProgressBar.setVisibility(View.GONE);
                    listView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(@NonNull Call<FacultiesList> call, @NonNull Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
                no_faculty_view.setVisibility(View.VISIBLE);
                no_faculty_view.setText(R.string.touch_to_refresh);
                if (toast != null) {
                    toast.cancel();
                    toast = Toast.makeText(context, "Unable to fetch! Check Internet Connection", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    toast = Toast.makeText(context, "Unable to fetch! Check Internet Connection", Toast.LENGTH_SHORT);
                    toast.show();
                }
                Log.e(TAG, t.toString());
            }
        });
    }
    @Override
    public void onStop() {
        super.onStop();
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        mProgressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        return new CustomTaskLoader(FacultyMembers.this,context,"FacultyMembers");
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object o) {
        listView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }
}
