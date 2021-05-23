package com.example.azmoonyar.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azmoonyar.Database.Model.QuestionExam;
import com.example.azmoonyar.Fragments.RunTimeExamFragment;
import com.example.azmoonyar.R;

import java.util.HashMap;
import java.util.List;

public class ExamQuestionTimeAdapter extends RecyclerView.Adapter<ExamQuestionTimeAdapter.MyViewHolder> {

    public List<QuestionExam> questionExamList;
    View view;

    public ExamQuestionTimeAdapter(List<QuestionExam> questionExams){
        this.questionExamList=questionExams;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view=parent;
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_exam,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    holder.bind(position);
    int check;
    holder.radioButton1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            questionExamList.get(position).setCheckedOption(1);
        }
    });

        holder.radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionExamList.get(position).setCheckedOption(2);
            }
        });

        holder.radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionExamList.get(position).setCheckedOption(3);
            }
        });

        holder.radioButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionExamList.get(position).setCheckedOption(4);
            }
        });
    /*if (holder.radioButton1.isChecked()){
    resultMap.put(questionExamList.get(position),1);
    }
    else if(holder.radioButton2.isChecked()){
        resultMap.put(questionExamList.get(position),2);
    }
    else if (holder.radioButton3.isChecked()){
        resultMap.put(questionExamList.get(position),3);
    }
    else if(holder.radioButton4.isChecked()){
        resultMap.put(questionExamList.get(position),4);
    }*/
    }

    @Override
    public int getItemCount() {
        return questionExamList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtDesc;
        ImageView imgQue;

        RadioButton radioButton1,radioButton2,radioButton3,radioButton4;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDesc=itemView.findViewById(R.id.txtDescription);
            imgQue=itemView.findViewById(R.id.imgExamPic);
            radioButton1=itemView.findViewById(R.id.radioBtn1);
            radioButton2=itemView.findViewById(R.id.radioBtn2);
            radioButton3=itemView.findViewById(R.id.radioBtn3);
            radioButton4=itemView.findViewById(R.id.radioBtn4);


        }

        public void bind(int position){
            QuestionExam questionExam=questionExamList.get(position);
            txtDesc.setText(questionExam.getDescription());
            if (questionExam.getImgSourceBinary()!=null){
                byte[] decodedString = Base64.decode(questionExam.getImgSourceBinary(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imgQue.setImageBitmap(decodedByte);
            }
            radioButton1.setText(questionExam.getOption1());
            radioButton2.setText(questionExam.getOption2());
            radioButton3.setText(questionExam.getOption3());
            radioButton4.setText(questionExam.getOption4());

        }
    }

    public void toast(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //resultMap.forEach((key, value) -> Log.i("TAG", "toast: "+key+" "+value));
        }
    }
}
