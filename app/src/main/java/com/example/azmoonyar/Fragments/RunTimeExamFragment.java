package com.example.azmoonyar.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.azmoonyar.Adapters.ExamQuestionTimeAdapter;
import com.example.azmoonyar.Database.AppDatabase;
import com.example.azmoonyar.Database.Model.Exam;
import com.example.azmoonyar.Database.Model.QuestionExam;
import com.example.azmoonyar.Database.QuestionExamDao;
import com.example.azmoonyar.R;

import java.util.List;


public class RunTimeExamFragment extends Fragment {

    ExamQuestionTimeAdapter adapter;
   Exam exam;
   QuestionExamDao questionExamDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            exam=getArguments().getParcelable("Exam");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        questionExamDao= AppDatabase.getAppDatabase(getContext()).getQuestionExamDao();
        return inflater.inflate(R.layout.fragment_run_time_exam, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView=view.findViewById(R.id.rec_main_exam);
        List<QuestionExam> list=questionExamDao.getAllQuestionExams(exam.getId());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        adapter=new ExamQuestionTimeAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}