package com.example.azmoonyar.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.azmoonyar.Database.AppDatabase;
import com.example.azmoonyar.Database.QuestionDao;
import com.example.azmoonyar.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainStudentFragment extends Fragment {

    public BadgeDrawable badge;
    private QuestionDao questionDao;

    public MainStudentFragment() {
        // Required empty public constructor
    }


    public static MainStudentFragment newInstance(String param1, String param2) {
        MainStudentFragment fragment = new MainStudentFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_student, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpFirstView();
        BottomNavigationView btnView=view.findViewById(R.id.bottomNavigation_main);
        btnView.setSelectedItemId(R.id.QuestionBank);

        questionDao= AppDatabase.getAppDatabase(getContext()).getQuestionDao();

        badge=btnView.getOrCreateBadge(R.id.QuestionBank);
        badge.setVisible(true);
        badge.setNumber(questionDao.getRowCount());

        btnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId()==R.id.QuestionBank){

                    FragmentTransaction transaction=getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_main_Student,new QuestionBankEdit());
                    transaction.commit();

                }else if(item.getItemId()==R.id.ExamSection){

                    FragmentTransaction transaction=getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_main_Student,new CheckExamFragment());
                    transaction.commit();

                }
                return true;
            }
        });

        btnView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

            }
        });

    }

    private void setUpFirstView() {
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_main_Student,new QuestionBankEdit());
        transaction.commit();
    }
}