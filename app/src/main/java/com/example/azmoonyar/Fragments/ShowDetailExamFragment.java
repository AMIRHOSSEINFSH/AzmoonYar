package com.example.azmoonyar.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;

import com.example.azmoonyar.Adapters.DetailExamAdapter;
import com.example.azmoonyar.Database.AppDatabase;
import com.example.azmoonyar.Database.Model.Exam;
import com.example.azmoonyar.Database.Model.QuestionExam;
import com.example.azmoonyar.Database.QuestionExamDao;
import com.example.azmoonyar.R;

import java.util.List;

public class ShowDetailExamFragment extends Fragment {


    QuestionExamDao questionExamDao;
    List<QuestionExam> list;
    String result;
    String timeDuration;
    String Date;
    String Desc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        questionExamDao= AppDatabase.getAppDatabase(getContext()).getQuestionExamDao();
        Exam exam=getArguments().getParcelable("exam");
        Desc = exam.getDescription();
        result= String.valueOf(exam.getResult());
        timeDuration=exam.getTimeOfExam();
        Date=exam.getYear()+"/"+exam.getMonthNumber()+"/"+
                exam.getDayNumber()+"\n"+exam.getHour()+":"+exam.getMinute();
        list=questionExamDao.getAllQuestionExams(exam.getId());
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_exam_detail_show,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        TextView txtTime=view.findViewById(R.id.txtTime);
        TextView txtDate=view.findViewById(R.id.txtDate);
        TextView txtDescription=view.findViewById(R.id.txtDescription);
        txtDescription.setText(Desc);
        txtDate.setText(Date);
        txtTime.setText(timeDuration);
        DetailExamAdapter adapter=new DetailExamAdapter(list);
        RecyclerView recyclerView=view.findViewById(R.id.rec_show_questions);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(adapter);
    }
}
