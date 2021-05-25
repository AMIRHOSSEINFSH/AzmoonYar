package com.example.azmoonyar.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azmoonyar.Fragments.ExamTimeFragment;
import com.example.azmoonyar.Database.Model.Exam;
import com.example.azmoonyar.Fragments.CheckExamFragment;
import com.example.azmoonyar.Fragments.ShowDetailExamFragment;
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

                    Bundle bundle=new Bundle();
                    bundle.putParcelable("exam",listExam.get(position));

                    FragmentTransaction transaction=fragment.getFragmentManager().beginTransaction();
                    ShowDetailExamFragment fragment=new ShowDetailExamFragment();
                    fragment.setArguments(bundle);
                    transaction.setCustomAnimations(R.anim.slide_in,R.anim.fade_out, R.anim.fade_in,R.anim.slide_out);
                    transaction.replace(R.id.fragment_main,fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
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
        ImageView img_doc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIsPassed=itemView.findViewById(R.id.examIsPassed);
            txtTimeDuration=itemView.findViewById(R.id.ExamTime);
            txtDate=itemView.findViewById(R.id.DateExam);
            txtDescription=itemView.findViewById(R.id.ExamDescription);
            img_doc=itemView.findViewById(R.id.doc_menu_icon);

        }

        public void bind(int position){
            txtTimeDuration.setText(listExam.get(position).getTimeOfExam());
            txtDescription.setText(listExam.get(position).getDescription());
            txtDate.setText(listExam.get(position).getHour()+":"+listExam.get(position).getMinute()+"\n"+listExam.get(position).getYear()+"/"+listExam.get(position).getMonthNumber()+"/"+listExam.get(position).getDayNumber());
            if (listExam.get(position).isPassed())
                imgIsPassed.setColorFilter(Color.GREEN);
            else
                imgIsPassed.setColorFilter(Color.RED);




            PopupMenu popup = new PopupMenu(fragment.getContext(), img_doc);
            popup.getMenuInflater()
                    .inflate(R.menu.popup_menu_doc, popup.getMenu());

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return true;
                }
            });

            img_doc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popup.show();
                }
            });



        }
    }
}
