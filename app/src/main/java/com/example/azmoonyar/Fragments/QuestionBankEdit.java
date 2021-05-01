package com.example.azmoonyar.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.azmoonyar.Adapters.ExamMakerAdapter;
import com.example.azmoonyar.Adapters.QuestionsAdapter;
import com.example.azmoonyar.Database.AppDatabase;
import com.example.azmoonyar.Database.Model.Question;
import com.example.azmoonyar.Database.QuestionDao;
import com.example.azmoonyar.Fragments.Dialog.BtnSheetFilter;
import com.example.azmoonyar.Fragments.Dialog.EditDialogFragment;
import com.example.azmoonyar.MainActivity;
import com.example.azmoonyar.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;


public class QuestionBankEdit extends Fragment implements EditDialogFragment.OnAddDialogListener,
        EditDialogFragment.OnEditDialogListener, BtnSheetFilter.ChangeFilterListener, ExamMakerAdapter.OnListListener {

    private QuestionDao questionDao;
    private QuestionsAdapter adapter;
    private List<Question> ExamQuList=new ArrayList<>();
    BadgeDrawable badge;
    public QuestionBankEdit() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bank, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        questionDao= AppDatabase.getAppDatabase(view.getContext()).getQuestionDao();
        MaterialButton btnFilter=view.findViewById(R.id.btnAddFilter);
        EditText SearchEt=view.findViewById(R.id.et_edit_search);
        View fabAddExam=view.findViewById(R.id.fab_add_new_Exam);
        badge=((MainActivity)getActivity()).badge;


        SearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0){
                    List<Question> list=questionDao.search(s.toString());
                    adapter.setQuestionList(list);
                }else{
                    adapter.setQuestionList(questionDao.getQuestions());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        View fab_AddQuestion=view.findViewById(R.id.fab_main_add_new_task);
        fab_AddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDialogFragment editDialogFragment=new EditDialogFragment();
                Bundle bundle=new Bundle();
                bundle.putParcelable("question",null);
                editDialogFragment.setArguments(bundle);
                editDialogFragment.setCancelable(true);
                editDialogFragment.show(getFragmentManager(),null);
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnSheetFilter btnSheetFilter=new BtnSheetFilter(/*QuestionBankEdit.this*/);
                btnSheetFilter.show(getFragmentManager(),null);
            }
        });


        //questionDao.addQuestion(new Question("هشتم","بهار","2","12","23","32","20",1,"متوسط","ایران چند استان دارد ؟"));
        List<Question> list=questionDao.getQuestions();
        RecyclerView recyclerView=view.findViewById(R.id.rec_showAdd);
        adapter=new QuestionsAdapter(list,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        fabAddExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExamQuList=adapter.SelectedList;
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                ExamCreatorFragment fragment=new ExamCreatorFragment();
                Toast.makeText(getContext(), ExamQuList.size()+"", Toast.LENGTH_SHORT).show();
                ArrayList<Question> s=new ArrayList<Question>(ExamQuList);
                Bundle bundle=new Bundle();
                bundle.putParcelableArrayList("questionList",s);
                fragment.setArguments(bundle);
                //fragment.newInstance(s);
                transaction.addToBackStack(null);
                transaction.replace(R.id.fragment_main,fragment);
                transaction.commit();
            }
        });


    }

    @Override
    public void OnAdd(Question question) {
        badge.setNumber(badge.getNumber()+1);
        questionDao.addQuestion(question);
        adapter.onAdd(question);
        //adapter.questionList.add(question);
        //adapter.notifyItemInserted(adapter.questionList.size()-1);
    }

    @Override
    public void OnDelete(Question question) {
        int result=questionDao.deleteQuestion(question);
        if (result>0){
            badge.setNumber(badge.getNumber()-1);
            adapter.OnDelete(question);
        }else{
            Toast.makeText(getContext(), "Not Deleted!!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onEdit(Question question) {

    int result=questionDao.EditQuestionInfo(question);
        Log.i("TAG", "onEdit: "+result+" "+question.getCorrectOption());
    if (result>0){
        adapter.onEdit(question);
    }else{
        Toast.makeText(getContext(), "IS Not Edited!!", Toast.LENGTH_SHORT).show();
    }

    }

    @Override
    public void OnChangeFilterSearch(String base, String lesson) {
        List<Question> previousList=adapter.getList();
        List<Question> newList=new ArrayList<>();
        for (int i = 0; i < previousList.size(); i++) {
            if (base.equals(previousList.get(i).getBase()) && lesson.equals(previousList.get(i).getLesson())){
                newList.add(previousList.get(i));
            }
        }
        adapter.setQuestionList(newList);
    }

    @Override
    public void onSendList(List<Question> list) {
        this.ExamQuList=list;
        Toast.makeText(getContext(), ExamQuList.size(), Toast.LENGTH_SHORT).show();
    }
}