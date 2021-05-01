package com.example.azmoonyar.Fragments.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.azmoonyar.Database.Model.Exam;
import com.example.azmoonyar.Fragments.ExamCreatorFragment;
import com.example.azmoonyar.R;
import com.google.android.material.textfield.TextInputEditText;

import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.api.PersianPickerDate;
import ir.hamsaa.persiandatepicker.api.PersianPickerListener;


public class DialogPreMakerExam extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    MakerExamListener listener;
    TextInputEditText editText;
    Button btnDate;
    Button btnTime;
    TextView txtDate;
    TextView txtTime;
    Exam exam;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        exam=getArguments().getParcelable("ExamIns");

        Fragment fragment=getFragmentManager().findFragmentById(R.id.fragment_main);
        if (fragment instanceof ExamCreatorFragment){
            listener =(ExamCreatorFragment) fragment;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View view= LayoutInflater.from(getContext()).inflate(R.layout.fragment_dialog_pre_maker_exam,null,false);
        builder.setView(view);

        editText=view.findViewById(R.id.etDescription);
        btnDate=view.findViewById(R.id.btnDatePicker);
        btnTime=view.findViewById(R.id.btnTimePicker);
        txtDate=view.findViewById(R.id.txtDatePicker);
        txtTime=view.findViewById(R.id.txtTimePicker);

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog=new TimePickerDialog(getContext(), DialogPreMakerExam.this,24,60,true);
                dialog.show();
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersianDatePickerDialog picker=new PersianDatePickerDialog(getContext())
                        .setPositiveButtonString("باشه")
                        .setNegativeButton("بیخیال")
                        .setTodayButton("امروز")
                        .setTodayButtonVisible(true)
                        .setMinYear(1300)
                        .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                        .setInitDate(1370, 3, 13)
                        .setActionTextColor(Color.GRAY)
                        .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                        .setShowInBottomSheet(true)
                        .setListener(new PersianPickerListener() {
                            @Override
                            public void onDateSelected(PersianPickerDate persianPickerDate) {

                                exam.setDayName(persianPickerDate.getPersianDay()+"");
                                exam.setDayNumber(persianPickerDate.getPersianDay()+"");
                                exam.setMonthName(persianPickerDate.getPersianDayOfWeekName());
                                exam.setMonthNumber(persianPickerDate.getPersianMonth()+"");
                                exam.setYear(persianPickerDate.getPersianYear()+"");
                                txtDate.setText(persianPickerDate.getPersianLongDate());

                                /*Log.d(TAG, "onDateSelected: " + persianPickerDate.getTimestamp());//675930448000
                                Log.d(TAG, "onDateSelected: " + persianPickerDate.getGregorianDate());//Mon Jun 03 10:57:28 GMT+04:30 1991
                                Log.d(TAG, "onDateSelected: " + persianPickerDate.getPersianLongDate());// دوشنبه  13  خرداد  1370
                                Log.d(TAG, "onDateSelected: " + persianPickerDate.getPersianMonthName());//خرداد
                                Log.d(TAG, "onDateSelected: " + PersianCalendarUtils.isPersianLeapYear(persianPickerDate.getPersianYear()));//true
                                Toast.makeText(context, persianPickerDate.getPersianYear() + "/" + persianPickerDate.getPersianMonth() + "/" + persianPickerDate.getPersianDay(), Toast.LENGTH_SHORT).show();*/
                            }

                            @Override
                            public void onDismissed() {

                            }
                        });
                picker.show();
            }
        });

        Button btnFinish=view.findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFinish(editText.getText().toString(),exam);
                dismiss();
            }
        });

        return builder.create();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour_of_day, int minute) {
        exam.setHour(hour_of_day+"");
        exam.setMinute(minute+"");
        txtTime.setText(hour_of_day+":"+minute);
    }


    public interface MakerExamListener{
        void onFinish(String description,Exam exam);
    }

}