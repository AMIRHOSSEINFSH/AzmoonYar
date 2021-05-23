package com.example.azmoonyar.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.azmoonyar.Adapters.ExamMakerAdapter;
import com.example.azmoonyar.Adapters.QuestionsAdapter;
import com.example.azmoonyar.Adapters.ShowExamAdapter;
import com.example.azmoonyar.Database.AppDatabase;
import com.example.azmoonyar.Database.ExamDao;
import com.example.azmoonyar.Database.Model.Exam;
import com.example.azmoonyar.Database.Model.Question;
import com.example.azmoonyar.Database.QuestionDao;
import com.example.azmoonyar.MainActivity;
import com.example.azmoonyar.R;
import com.google.android.material.badge.BadgeDrawable;

import java.util.ArrayList;
import java.util.List;


public class CheckExamFragment extends Fragment  {


    BadgeDrawable   badge;
    List<Exam> ExamList =new ArrayList<>();
    ShowExamAdapter adapter;
    ExamDao examDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bank, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        examDao= AppDatabase.getAppDatabase(view.getContext()).getExamDao();
        badge=((MainActivity)getActivity()).badge;

        TextView SearchEt=view.findViewById(R.id.et_search);
        RecyclerView recExamList=view.findViewById(R.id.rec_showExam);

        view.findViewById(R.id.fab_main_add_new_task).setVisibility(View.GONE);
        view.findViewById(R.id.fab_add_new_Exam).setVisibility(View.GONE);

        EditText et_search=view.findViewById(R.id.et_edit_search);
        Button btnFilter=view.findViewById(R.id.btnAddFilter);



        List<Exam> examList=examDao.getExamList();
        ExamList.addAll(examList);

        adapter=new ShowExamAdapter(ExamList,this);
        recExamList.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        recExamList.setAdapter(adapter);



    }



}