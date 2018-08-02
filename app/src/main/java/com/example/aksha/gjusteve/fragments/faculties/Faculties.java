package com.example.aksha.gjusteve.fragments.faculties;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.aksha.gjusteve.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Objects;

public class Faculties extends Fragment implements MaterialSpinner.OnItemSelectedListener<String> {
    private MaterialSpinner departmentNameSpinner;

    public Faculties(){
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState)
    {
        //View for this Activity layout
        View view = layoutInflater.inflate(R.layout.activity_faculties,container,false);

        //Material spinner Faculty name
        MaterialSpinner facultyNameSpinner = view.findViewById(R.id.faculty_name);
        facultyNameSpinner.setItems("Select Faculty","FACULTY OF MEDIA STUDIES","FACULTY OF ENVIRONMENTAL AND BIO SCIENCES & TECHNOLOGY","HARYANA SCHOOL OF BUSINESS","FACULTY OF PHYSICAL SCIENCES & TECHNOLOGY","FACULTY OF ENGINEERING AND TECHNOLOGY","FACULTY OF MEDICAL SCIENCES");

        //Material spinner Department Name
        departmentNameSpinner = view.findViewById(R.id.department_name);
        departmentNameSpinner.setEnabled(false);

        //faculty name spinner on click listener
        facultyNameSpinner.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<String>) (materialSpinner, position, id, item) -> {
            departmentNameSpinner.setEnabled(true);
            departmentNameSpinner.setSelectedIndex(0);
            switch (item){
                case "Select Faculty":
                {
                    departmentNameSpinner.setEnabled(false);
                    departmentNameSpinner.setItems("Select Department");
                    if(Objects.requireNonNull(getFragmentManager()).findFragmentById(R.id.faculty_members)!=null) {
                        getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.faculty_members)).commit();
                    }
                    break;
                }
                case "FACULTY OF MEDIA STUDIES":
                {
                    departmentNameSpinner.setItems("Select Department","Department of Communication Management and Technology");
                    break;
                }
                case "FACULTY OF ENVIRONMENTAL AND BIO SCIENCES & TECHNOLOGY":
                {
                    departmentNameSpinner.setItems("Select Department","Department of Environmental Science & Engineering","Department of Bio & Nano Technology","Department of Food Technology");
                    break;
                }
                case "HARYANA SCHOOL OF BUSINESS":
                {
                    departmentNameSpinner.setItems("Select Department","Haryana School Of Business");
                    break;
                }
                case "FACULTY OF PHYSICAL SCIENCES & TECHNOLOGY":
                {
                    departmentNameSpinner.setItems("Select Department","Department of Physics","Department of Chemistry","Department of Mathematics");
                    break;
                }
                case "FACULTY OF ENGINEERING AND TECHNOLOGY":
                {
                    departmentNameSpinner.setItems("Select Department","Department of Printing Technology","Department of Computer Science & Engineering","Department of Electronics & Communication Engineering","Department of Biomedical Engineering","Department of Mechanical Engineering");
                    break;
                }
                case "FACULTY OF MEDICAL SCIENCES":
                {
                    departmentNameSpinner.setItems("Select Department","Department of Physiotherapy","Department of Applied Psychology","Department of Pharmaceutical Sciences");
                    break;
                }
            }
        });
        departmentNameSpinner.setOnItemSelectedListener(this);
        return view;
    }

    @Override
    public void onItemSelected(MaterialSpinner materialSpinner, int position, long id, String item) {
        switch (item) {
            case "Select Department": {
                Objects.requireNonNull(getFragmentManager()).beginTransaction().remove(getFragmentManager().findFragmentById(R.id.faculty_members)).commit();
                break;
            }
            case "Department of Communication Management and Technology": {
                FacultyMembers.setUrl(item);
                Objects.requireNonNull(getFragmentManager()).beginTransaction().replace(R.id.faculty_members, new FacultyMembers()).commit();
                break;
            }
            case "Department of Environmental Science & Engineering": {
                FacultyMembers.setUrl(item);
                Objects.requireNonNull(getFragmentManager()).beginTransaction().replace(R.id.faculty_members, new FacultyMembers()).commit();
                break;
            }
            case "Department of Bio & Nano Technology": {
                FacultyMembers.setUrl(item);
                Objects.requireNonNull(getFragmentManager()).beginTransaction().replace(R.id.faculty_members, new FacultyMembers()).commit();
                break;
            }
            case "Department of Food Technology": {
                FacultyMembers.setUrl(item);
                Objects.requireNonNull(getFragmentManager()).beginTransaction().replace(R.id.faculty_members, new FacultyMembers()).commit();
                break;
            }
            case "Haryana School Of Business": {
                FacultyMembers.setUrl(item);
                Objects.requireNonNull(getFragmentManager()).beginTransaction().replace(R.id.faculty_members, new FacultyMembers()).commit();
                break;
            }
            case "Department of Physics": {
                FacultyMembers.setUrl(item);
                Objects.requireNonNull(getFragmentManager()).beginTransaction().replace(R.id.faculty_members, new FacultyMembers()).commit();
                break;
            }
            case "Department of Chemistry": {
                FacultyMembers.setUrl(item);
                Objects.requireNonNull(getFragmentManager()).beginTransaction().replace(R.id.faculty_members, new FacultyMembers()).commit();
                break;
            }
            case "Department of Mathematics": {
                FacultyMembers.setUrl(item);
                Objects.requireNonNull(getFragmentManager()).beginTransaction().replace(R.id.faculty_members, new FacultyMembers()).commit();
                break;
            }
            case "Department of Printing Technology": {
                FacultyMembers.setUrl(item);
                Objects.requireNonNull(getFragmentManager()).beginTransaction().replace(R.id.faculty_members, new FacultyMembers()).commit();
                break;
            }
            case "Department of Computer Science & Engineering": {
                FacultyMembers.setUrl(item);
                Objects.requireNonNull(getFragmentManager()).beginTransaction().replace(R.id.faculty_members, new FacultyMembers()).commit();
                break;
            }
            case "Department of Electronics & Communication Engineering": {
                FacultyMembers.setUrl(item);
                Objects.requireNonNull(getFragmentManager()).beginTransaction().replace(R.id.faculty_members, new FacultyMembers()).commit();
                break;
            }
            case "Department of Biomedical Engineering": {
                FacultyMembers.setUrl(item);
                Objects.requireNonNull(getFragmentManager()).beginTransaction().replace(R.id.faculty_members, new FacultyMembers()).commit();
                break;
            }
            case "Department of Mechanical Engineering": {
                FacultyMembers.setUrl(item);
                Objects.requireNonNull(getFragmentManager()).beginTransaction().replace(R.id.faculty_members, new FacultyMembers()).commit();
                break;
            }
            case "Department of Physiotherapy": {
                FacultyMembers.setUrl(item);
                Objects.requireNonNull(getFragmentManager()).beginTransaction().replace(R.id.faculty_members, new FacultyMembers()).commit();
                break;
            }
            case "Department of Applied Psychology": {
                FacultyMembers.setUrl(item);
                Objects.requireNonNull(getFragmentManager()).beginTransaction().replace(R.id.faculty_members, new FacultyMembers()).commit();
                break;
            }
            case "Department of Pharmaceutical Sciences": {
                FacultyMembers.setUrl(item);
                Objects.requireNonNull(getFragmentManager()).beginTransaction().replace(R.id.faculty_members, new FacultyMembers()).commit();
                break;
            }
        }
    }
    public void onDetach()
    {
        super.onDetach();
    }
}