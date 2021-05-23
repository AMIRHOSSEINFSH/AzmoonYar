package com.example.azmoonyar.Activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azmoonyar.Adapters.ExamQuestionTimeAdapter;
import com.example.azmoonyar.Database.AppDatabase;
import com.example.azmoonyar.Database.ExamDao;
import com.example.azmoonyar.Database.Model.Exam;
import com.example.azmoonyar.Database.Model.QuestionExam;
import com.example.azmoonyar.Database.QuestionExamDao;
import com.example.azmoonyar.Fragments.Dialog.ExamDialogResult;
import com.example.azmoonyar.MainActivity;
import com.example.azmoonyar.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.azmoonyar.Fragments.Dialog.ExamDialogResult.prev;

public class ExamTimeFragment extends Fragment implements ExamDialogResult.OnChangeDecisionExamListener {



    private TextView txtTime;
    private Button btnFinish;
    private ExamQuestionTimeAdapter adapter;
    private Exam exam;
    private QuestionExamDao questionExamDao;
    private ExamDao examDao;
    public  boolean                    finished=false;
    public ExamDialogResult.stateType stateType;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        questionExamDao= AppDatabase.getAppDatabase(getContext()).getQuestionExamDao();
        examDao = AppDatabase.getAppDatabase(getContext()).getExamDao();
        return inflater.inflate(R.layout.fragment_exam_time, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        (((MainActivity) getActivity()).img_select_charactor).setVisibility(View.GONE);
        exam=getArguments().getParcelable("Exam");

        txtTime=view.findViewById(R.id.txtTimeExam);
        btnFinish=view.findViewById(R.id.btnExamFinish);
        RecyclerView recyclerView=view.findViewById(R.id.rec_main_exam);
        int timeInSeconds=Integer.parseInt(exam.getTimeOfExam());
        long timeInMili=timeInSeconds*1000;
        new CountDownTimer(timeInMili,1000) {

            public void onTick(long millisUntilFinished) {
                txtTime.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                finished=true;
                stateType= ExamDialogResult.stateType.TIME_FINISHED;
                finishAction();
                Toast.makeText(getContext(), "Exam finished!!", Toast.LENGTH_SHORT).show();
            }
        }.start();


        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateType= ExamDialogResult.stateType.SELF_FINISH;
                (((MainActivity) getActivity()).img_select_charactor).setVisibility(View.VISIBLE);
                finishAction();
            adapter.toast();
            }
        });

        list=questionExamDao.getAllQuestionExams(exam.getId());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        adapter=new ExamQuestionTimeAdapter(list);
        recyclerView.setAdapter(adapter);


    }
    List<QuestionExam> list;
    private void finishAction(){
        if (!prev){
            ExamDialogResult dialogResult=new ExamDialogResult();
            dialogResult.setCancelable(false);
            Bundle bundle=new Bundle();
            bundle.putSerializable("StateType",stateType);
            bundle.putString("result",calculate(adapter.questionExamList));
            dialogResult.setArguments(bundle);
            dialogResult.show(getFragmentManager(),"ExamDialogResult");
        }

    }

    private String calculate(List<QuestionExam> list){

        int count=0;

        for (int i = 0; i < list.size(); i++) {

            if ( list.get(i).getCheckedOption() == list.get(i).getCorrectOption()){
                    count++;
            }
        }
        String result= String.valueOf(count/(list.size()));
        return result;
    }


    @Override
    public void onFinish() {
        int count=0;

        List<QuestionExam> list=adapter.questionExamList;
        for (int i = 0; i < list.size(); i++) {

            if ( list.get(i).getCheckedOption() == list.get(i).getCorrectOption()){
                    count+=1;
            }
        }
        for (int i = 0; i < list.size(); i++) {
            int result=questionExamDao.updateQuestionAfterExam(list.get(i));

        }
        int res=examDao.updateResultOfExam((count/list.size()),true,exam.getId());

        getActivity().onBackPressed();
    }
}