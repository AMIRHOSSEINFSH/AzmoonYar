package com.example.azmoonyar.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.azmoonyar.Database.Model.Question;
import com.example.azmoonyar.Fragments.CheckExamFragment;
import com.example.azmoonyar.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class ExamMakerAdapter extends RecyclerView.Adapter<ExamMakerAdapter.MyViewHolder> {

    List<Question> questionList;
    List<Question>    SelectedList=new ArrayList<>();
    CheckExamFragment fragment;

    public ExamMakerAdapter(List<Question> questions, CheckExamFragment fragment){
        this.questionList=questions;
        this.fragment=fragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialCardView cardView= (MaterialCardView) view;
                if (cardView.isChecked()){
                    SelectedList.add(questionList.get(position));
                    cardView.setChecked(false);
                }else{
                    SelectedList.remove(questionList.get(position));
                    cardView.setChecked(true);
                }
                //fragment.onSendList(SelectedList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtLesson,txtDifficulty,txtDescription,txtSeason,txtBase;
        private ImageView expand_arrow,imgQue;
        private ConstraintLayout expandableLayout;
        private RadioButton      radio1,radio2,radio3,radio4;
        private RadioGroup radioGroup;

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

            txtLesson.setText("Lesson\n"+questionList.get(position).getLesson());
            txtDifficulty.setText("Difficulty\n"+questionList.get(position).getDifficulty());
            txtDescription.setText(questionList.get(position).getDescription());
            txtSeason.setText("Season\n"+questionList.get(position).getSeason());
            txtBase.setText("Base\n"+questionList.get(position).getBase());

            boolean isExpanded = questionList.get(position).isExpanded();

            if (isExpanded){
                expand_arrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        questionList.get(position).setExpanded(false);
                        expand_arrow.setRotation(180);
                        expandableLayout.setVisibility(View.GONE);
                        notifyItemChanged(getAdapterPosition());
                    }
                });
                //Toast.makeText(queBankEditIns.getContext(), "IS_EXPANDED", Toast.LENGTH_SHORT).show();
                if (questionList.get(position).getImgSourceBinary()!=null){
                    Log.i("TAG", "bindhhh: ");
                    byte[] decodedString = Base64.decode(questionList.get(position).getImgSourceBinary(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imgQue.setImageBitmap(decodedByte);
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
                        Question question = questionList.get(position);
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

    public interface OnListListener{
        void onSendList(List<Question> list);
    }


    }

