package com.example.azmoonyar.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.azmoonyar.Adapters.ExamMakerAdapter;
import com.example.azmoonyar.Adapters.QuestionsAdapter;
import com.example.azmoonyar.Database.AppDatabase;
import com.example.azmoonyar.Database.Model.Question;
import com.example.azmoonyar.Database.QuestionDao;
import com.example.azmoonyar.MainActivity;
import com.example.azmoonyar.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class AddExamFragment extends Fragment  {


    private QuestionDao questionDao;
    private QuestionsAdapter adapter;
    BadgeDrawable badge;
    List<Question> ExamQuList=new ArrayList<>();

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
        questionDao= AppDatabase.getAppDatabase(view.getContext()).getQuestionDao();
        badge=((MainActivity)getActivity()).badge;

        TextView SearchEt=view.findViewById(R.id.et_search);
        RecyclerView recQuestionList=view.findViewById(R.id.rec_showAdd);
        View fab_Add=view.findViewById(R.id.fab_main_add_new_task);
        EditText et_search=view.findViewById(R.id.et_edit_search);
        Button btnFilter=view.findViewById(R.id.btnAddFilter);

        List<Question> list=questionDao.getQuestions();
        ExamMakerAdapter adapter=new ExamMakerAdapter(list,this);
        recQuestionList.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        recQuestionList.setAdapter(adapter);

        fab_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                EditExamFragment fragment=new EditExamFragment();
                
                fragmentTransaction.replace(R.id.editExamFragment,new EditExamFragment());
                fragmentTransaction.commit();*/
            }
        });


    }



}