package com.example.azmoonyar.Adapters;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azmoonyar.Database.Model.Question;
import com.example.azmoonyar.Database.Model.QuestionExam;
import com.example.azmoonyar.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class DetailExamAdapter extends RecyclerView.Adapter<DetailExamAdapter.MyViewHolder> {

    List<QuestionExam> questionList;

    public DetailExamAdapter(List<QuestionExam> list){
        this.questionList=list;
    }

    @Override
    public DetailExamAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailExamAdapter.MyViewHolder holder, int position) {
    holder.bind(position);
        MaterialCardView cardView=(MaterialCardView) holder.itemView;
        if (questionList.get(position).getCheckedOption()==questionList.get(position).getCorrectOption()){
            cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.getContext(), R.color.light_Green));
            switch (questionList.get(position).getCorrectOption()){
                case 1:
                    holder.radio1.setChecked(true);
                    holder.radio1.setTextColor(ContextCompat.getColor(cardView.getContext(), R.color.light_Green));
                    break;
                case 2:
                    holder.radio2.setChecked(true);
                    holder.radio2.setTextColor(ContextCompat.getColor(cardView.getContext(), R.color.light_Green));
                    break;
                case 3:
                    holder.radio3.setChecked(true);
                    holder.radio3.setTextColor(ContextCompat.getColor(cardView.getContext(), R.color.light_Green));
                    break;
                case 4:
                    holder.radio4.setChecked(true);
                    holder.radio4.setTextColor(ContextCompat.getColor(cardView.getContext(), R.color.light_Green));
                    break;
            }
        }
        else{
            cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.getContext(), R.color.light_Red));
            switch (questionList.get(position).getCorrectOption()){
                case 1:
                    holder.radio1.setChecked(true);
                    holder.radio1.setTextColor(ContextCompat.getColor(cardView.getContext(), R.color.light_Green));
                    break;
                case 2:
                    holder.radio2.setChecked(true);
                    holder.radio2.setTextColor(ContextCompat.getColor(cardView.getContext(), R.color.light_Green));
                    break;
                case 3:
                    holder.radio3.setChecked(true);
                    holder.radio3.setTextColor(ContextCompat.getColor(cardView.getContext(), R.color.light_Green));
                    break;
                case 4:
                    holder.radio4.setChecked(true);
                    holder.radio4.setTextColor(ContextCompat.getColor(cardView.getContext(), R.color.light_Green));
                    break;
            }

            switch (questionList.get(position).getCheckedOption()){
                case 1:
                    holder.radio1.setChecked(true);
                    holder.radio1.setTextColor(ContextCompat.getColor(cardView.getContext(), R.color.light_Red));
                    break;
                case 2:
                    holder.radio2.setChecked(true);
                    holder.radio2.setTextColor(ContextCompat.getColor(cardView.getContext(), R.color.light_Red));
                    break;
                case 3:
                    holder.radio3.setChecked(true);
                    holder.radio3.setTextColor(ContextCompat.getColor(cardView.getContext(), R.color.light_Red));
                    break;
                case 4:
                    holder.radio4.setChecked(true);
                    holder.radio4.setTextColor(ContextCompat.getColor(cardView.getContext(), R.color.light_Red));
                    break;

            }
        }

    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtLesson,txtDifficulty,txtDescription,txtSeason,txtBase;
        ImageView expand_arrow,imgQue;
        ConstraintLayout expandableLayout;
        RadioButton radio1,radio2,radio3,radio4;
        RadioGroup radioGroup;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            radio1=itemView.findViewById(R.id.option1);
            radio2=itemView.findViewById(R.id.option2);
            radio3=itemView.findViewById(R.id.option3);
            radio4=itemView.findViewById(R.id.option4);

            radioGroup=itemView.findViewById(R.id.radioGroup);

            txtLesson=itemView.findViewById(R.id.txt_lesson_model);
            txtDescription=itemView.findViewById(R.id.txt_desc_model);
            txtDifficulty=itemView.findViewById(R.id.txt_difficulty_model);
            txtSeason=itemView.findViewById(R.id.txt_season_model);
            txtBase=itemView.findViewById(R.id.txt_base_model);
            expandableLayout=itemView.findViewById(R.id.expandableLayout);
            imgQue=itemView.findViewById(R.id.imgQue);

            expand_arrow=itemView.findViewById(R.id.iv_expand_arrow);

        }

        public void bind(int position){

            radio1.setEnabled(false);
            radio2.setEnabled(false);
            radio3.setEnabled(false);
            radio4.setEnabled(false);

            txtLesson.setText("Lesson\n"+questionList.get(position).getLesson());
            txtDifficulty.setText("Difficulty\n"+questionList.get(position).getDifficulty());
            txtDescription.setText(questionList.get(position).getDescription());
            txtSeason.setText("Season\n"+questionList.get(position).getSeason());
            txtBase.setText("Base\n"+questionList.get(position).getBase());

            boolean isExpanded = questionList.get(position).isExpanded();

            if (isExpanded){
                imgQue.setImageDrawable(null);
                expand_arrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        questionList.get(position).setExpanded(false);
                        expand_arrow.setRotation(180);
                        expandableLayout.setVisibility(View.GONE);
                        notifyItemChanged(position);
                    }
                });
                //Toast.makeText(queBankEditIns.getContext(), "IS_EXPANDED", Toast.LENGTH_SHORT).show();
                if (questionList.get(position).getImgSourceBinary()!=null){

                    Log.i("TAG", "bindhhh: position: "+imgQue.getImageMatrix());
                    byte[] decodedString = Base64.decode(questionList.get(position).getImgSourceBinary(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imgQue.setImageBitmap(decodedByte);

                    //Glide.with(queBankEditIns.getContext()).load(questionList.get(position)).into(imgQue);
                }

                //Log.i("TAG", "bind: "+questionList.get(position).getCorrectOption());
                switch (questionList.get(position).getCorrectOption()){
                    case 1:
                        radio1.setChecked(true);
                        break;
                    case 2:
                        radio2.setChecked(true);
                        break;
                    case 3:
                        radio3.setChecked(true);
                        break;
                    case 4:
                        radio4.setChecked(true);
                        break;
                }

                radio1.setEnabled(false);
                radio2.setEnabled(false);
                radio3.setEnabled(false);
                radio4.setEnabled(false);
                radio1.setText(questionList.get(position).getOption1());
                radio2.setText(questionList.get(position).getOption2());
                radio3.setText(questionList.get(position).getOption3());
                radio4.setText(questionList.get(position).getOption4());
            }
            else{
                expand_arrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        QuestionExam question = questionList.get(position);
                        question.setExpanded(true);
                        expand_arrow.setRotation(180);
                        expandableLayout.setVisibility(View.VISIBLE);
                        //Toast.makeText(itemView.getContext(), ""+question.isExpanded()+" "+getAdapterPosition(), Toast.LENGTH_SHORT).show();
                        notifyItemChanged(position);
                    }
                });

            }
            expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        }
    }


    }

