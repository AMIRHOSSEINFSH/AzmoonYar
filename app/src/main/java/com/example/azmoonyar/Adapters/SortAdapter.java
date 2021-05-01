package com.example.azmoonyar.Adapters;

import android.content.Context;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azmoonyar.Database.Model.Question;
import com.example.azmoonyar.R;

import java.util.ArrayList;

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.MyViewHolder> {

    ArrayList<Question> SortedQuestions;
    public ArrayList<Question> preListQuestions;
    Context context;

    public SortAdapter(Context context,ArrayList<Question> PreListQuestions){
        this.context=context;
        this.preListQuestions=PreListQuestions;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    holder.bind(position);
    }

    @Override
    public int getItemCount() {

        return preListQuestions.size();
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

            txtLesson.setText("Lesson\n"+preListQuestions.get(position).getLesson());
            txtDifficulty.setText("Difficulty\n"+preListQuestions.get(position).getDifficulty());
            txtDescription.setText(preListQuestions.get(position).getDescription());
            txtSeason.setText("Season\n"+preListQuestions.get(position).getSeason());
            txtBase.setText("Base\n"+preListQuestions.get(position).getBase());

            boolean isExpanded = preListQuestions.get(position).isExpanded();

            if (isExpanded){
                imgQue.setImageDrawable(null);
                expand_arrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        preListQuestions.get(position).setExpanded(false);
                        expand_arrow.setRotation(180);
                        expandableLayout.setVisibility(View.GONE);
                        notifyItemChanged(position);
                    }
                });
                //Toast.makeText(queBankEditIns.getContext(), "IS_EXPANDED", Toast.LENGTH_SHORT).show();
                if (preListQuestions.get(position).getImgSourceBinary()!=null){

                    Log.i("TAG", "bindhhh: position: "+imgQue.getImageMatrix());
                    byte[] decodedString = Base64.decode(preListQuestions.get(position).getImgSourceBinary(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imgQue.setImageBitmap(decodedByte);

                    //Glide.with(queBankEditIns.getContext()).load(questionList.get(position)).into(imgQue);
                }

                //Log.i("TAG", "bind: "+questionList.get(position).getCorrectOption());
                switch (preListQuestions.get(position).getCorrectOption()){
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
                radio1.setText(preListQuestions.get(position).getOption1());
                radio2.setText(preListQuestions.get(position).getOption2());
                radio3.setText(preListQuestions.get(position).getOption3());
                radio4.setText(preListQuestions.get(position).getOption4());
            }
            else{
                expand_arrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Question question = preListQuestions.get(position);
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
