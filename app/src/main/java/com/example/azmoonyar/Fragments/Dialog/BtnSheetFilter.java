package com.example.azmoonyar.Fragments.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.azmoonyar.Fragments.QuestionBankEdit;
import com.example.azmoonyar.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BtnSheetFilter extends BottomSheetDialogFragment {


    private ChangeFilterListener listener;
    private String base;
    private String lesson;

    public BtnSheetFilter(/*ChangeFilterListener listener*/){

       // this.listener = listener;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        QuestionBankEdit CurrentFragment=(QuestionBankEdit) getFragmentManager().findFragmentById(R.id.fragment_main_Student);
        listener=CurrentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.bottom_sheet_dialog_add,container,false);
        //Toast.makeText(getContext(), (getParentFragment() instanceof QuestionBankEdit)+"" , Toast.LENGTH_SHORT).show();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnOk=view.findViewById(R.id.btn_filter_action);
        Spinner lessonSpinner=view.findViewById(R.id.spinner_lesson);
        Spinner BaseSpinner=view.findViewById(R.id.spinner_base);
        CheckBox checkLesson=view.findViewById(R.id.checkbox_lesson);
        CheckBox checkBase=view.findViewById(R.id.checkbox_base);

        lessonSpinner.setEnabled(false);
        BaseSpinner.setEnabled(false);
        checkLesson.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isEnable) {
                if (isEnable){
                    lessonSpinner.setEnabled(true);
                    lesson=lessonSpinner.getSelectedItem().toString();
                }
                else{
                    lesson=null;
                    lessonSpinner.setEnabled(false);
                }
                //lessonSpinner.setVisibility(isEnable ? View.VISIBLE : View.GONE);
            }
        });

        checkBase.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isEnable) {
                if (isEnable){
                    BaseSpinner.setEnabled(true);
                    base=BaseSpinner.getSelectedItem().toString();
                }
                else{
                    base=null;
                    BaseSpinner.setEnabled(false);
                }
                //BaseSpinner.setVisibility(isEnable ? View.VISIBLE : View.GONE);
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!BaseSpinner.getSelectedItem().toString().equals("") && !lessonSpinner.getSelectedItem().toString().equals("")){

                    listener.OnChangeFilterSearch(base,lesson);
                }

            }
        });
    }

    public interface ChangeFilterListener{
        void OnChangeFilterSearch(String base,String lesson);
    }
}
