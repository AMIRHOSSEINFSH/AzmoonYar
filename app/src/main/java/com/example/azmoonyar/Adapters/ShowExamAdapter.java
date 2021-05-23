package com.example.azmoonyar.Adapters;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azmoonyar.Activitys.ExamTimeFragment;
import com.example.azmoonyar.Database.Model.Exam;
import com.example.azmoonyar.Fragments.CheckExamFragment;
import com.example.azmoonyar.R;

import java.util.List;

public class ShowExamAdapter extends RecyclerView.Adapter<ShowExamAdapter.MyViewHolder> {

    private List<Exam> listExam;
    private CheckExamFragment fragment;
    public ShowExamAdapter(List<Exam> listExam,CheckExamFragment fragment){
        this.listExam=listExam;
        this.fragment=fragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(fragment.getContext()).inflate(R.layout.item_exam,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!listExam.get(position).isPassed()){
                    FragmentTransaction transaction=fragment.getFragmentManager().beginTransaction();
                    ExamTimeFragment examTimeFragment=new ExamTimeFragment();
                    Bundle bundle=new Bundle();
                    bundle.putParcelable("Exam",listExam.get(position));
                    examTimeFragment.setArguments(bundle);
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.fragment_main,examTimeFragment);
                    transaction.commit();

                    //fragment.getActivity().finish();
                }
                else{
                    Toast.makeText(fragment.getContext(), "You have Passed this\nwait for updating this section ...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listExam.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTimeDuration;
        TextView txtDate;
        TextView txtDescription;
        ImageView imgIsPassed;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIsPassed=itemView.findViewById(R.id.examIsPassed);
            txtTimeDuration=itemView.findViewById(R.id.ExamTime);
            txtDate=itemView.findViewById(R.id.DateExam);
            txtDescription=itemView.findViewById(R.id.ExamDescription);

        }

        public void bind(int position){
            txtTimeDuration.setText(listExam.get(position).getTimeOfExam());
            txtDescription.setText(listExam.get(position).getDescription());
            txtDate.setText(listExam.get(position).getHour()+":"+listExam.get(position).getMinute()+"\n"+listExam.get(position).getYear()+"/"+listExam.get(position).getMonthNumber()+"/"+listExam.get(position).getDayNumber());
            if (listExam.get(position).isPassed())
                imgIsPassed.setColorFilter(Color.GREEN);
            else
                imgIsPassed.setColorFilter(Color.RED);


        }
    }
}
