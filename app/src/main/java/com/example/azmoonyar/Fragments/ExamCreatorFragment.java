package com.example.azmoonyar.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.azmoonyar.Adapters.SortAdapter;
import com.example.azmoonyar.Database.AppDatabase;
import com.example.azmoonyar.Database.ExamDao;
import com.example.azmoonyar.Database.Model.Exam;
import com.example.azmoonyar.Database.Model.Question;
import com.example.azmoonyar.Database.Model.QuestionExam;
import com.example.azmoonyar.Database.QuestionDao;
import com.example.azmoonyar.Database.QuestionExamDao;
import com.example.azmoonyar.Fragments.Dialog.DialogPreMakerExam;
import com.example.azmoonyar.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ExamCreatorFragment extends Fragment implements DialogPreMakerExam.MakerExamListener {

    ArrayList<Question> questionList;
    SortAdapter adapter;

    QuestionExamDao questionExamDao;
    ExamDao examDao;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        examDao=AppDatabase.getAppDatabase(getContext()).getExamDao();
        questionExamDao=AppDatabase.getAppDatabase(getContext()).getQuestionExamDao();

    }

    public static final String ARRAY_KEY = "questionList";
    public static ExamCreatorFragment newInstance(ArrayList<Question> questionList) {
        ExamCreatorFragment fragment = new ExamCreatorFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARRAY_KEY,questionList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionList=getArguments().getParcelableArrayList("questionList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exam_creator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView=view.findViewById(R.id.rec_ExamCreatorSort);
        View fab_AddExam=view.findViewById(R.id.fab_add_new_Exam);

        adapter=new SortAdapter(getContext(),questionList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        fab_AddExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MakePreExam();
            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(questionList, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

    public void MakePreExam(){
        DialogPreMakerExam preExamMaker=new DialogPreMakerExam();
        Exam exam = new Exam();
        //exam.setQuestionExamList(adapter.preListQuestions);
        Bundle bundle=new Bundle();
        bundle.putParcelable("ExamIns",exam);
        preExamMaker.setArguments(bundle);
        preExamMaker.show(getFragmentManager(),null);
    }

    @Override
    public void onFinish(String description, Exam exam) {
        ArrayList<Question> list=adapter.preListQuestions;
        ArrayList<QuestionExam> questionExams=new ArrayList<>();
        int ref=examDao.getLastPrimaryKey()+1;
        for (int i = 0; i < list.size(); i++) {
            Question question=list.get(i);
            QuestionExam questionExam=new QuestionExam();
            questionExam.ConvertFromQuestion(question);
            questionExams.add(questionExam);
            questionExam.setExamReference(ref);
            questionExamDao.AddQuestionExam(questionExam);
        }
        exam.setQuestionExamList(questionExams);
        exam.setDescription(description);
        examDao.AddExam(exam);
        getActivity().onBackPressed();
        Log.i("TAG", "onFinish: "+description+"\n\n"+exam);
    }
}