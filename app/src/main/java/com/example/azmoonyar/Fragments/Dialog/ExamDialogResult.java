package com.example.azmoonyar.Fragments.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.azmoonyar.Fragments.ExamTimeFragment;
import com.example.azmoonyar.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class ExamDialogResult extends DialogFragment {

    public enum stateType{
        TIME_FINISHED(1),SELF_FINISH(0);
        int value;
         stateType(int value){
            this.value=value;
        }
    }

    private stateType type;

    private OnChangeDecisionExamListener callBack;
    private TextView txtContext;
    private String result;
    public static boolean prev=false;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        type = (ExamDialogResult.stateType) getArguments().getSerializable("StateType");
        result=getArguments().getString("result");
        callBack=(ExamTimeFragment)getFragmentManager().findFragmentById(R.id.fragment_main);
    }


    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.result_dialog_exam, null, false);
        builder.setView(view);
        MaterialCardView cardView=view.findViewById(R.id.materialCard);

        LottieAnimationView animationView=view.findViewById(R.id.lottie_animation_Result);
        TextView txtResultContext=view.findViewById(R.id.txt_result);
        MaterialButton btnExit=view.findViewById(R.id.btn_exit_result);
        MaterialButton btnContinue=view.findViewById(R.id.btn_Continue_result);
        txtContext=view.findViewById(R.id.txtContext);
        //RecyclerView recyclerView=view.findViewById(R.id.rec_result_exam);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type==stateType.SELF_FINISH){
                    type=stateType.TIME_FINISHED;
                    prev=true;
                    btnContinue.setVisibility(View.GONE);
                    txtResultContext.setText("آزمون شما به اتمام رسیده است !!");
                    animationView.setAnimation(R.raw.finish_green);
                    jointTask();
                }
                else if(type==stateType.TIME_FINISHED){
                    callBack.onFinish();
                    dismiss();
                }
            }
        });

    if (type == stateType.SELF_FINISH){
        //recyclerView.setVisibility(View.GONE);
        txtContext.setVisibility(View.GONE);
        txtResultContext.setText("آیا از خروج اطمینان دارید ؟");
        animationView.setAnimation(R.raw.warning);


        /*((ExamTimeFragment)fragment).stateType=stateType.TIME_FINISHED;
        onCreateDialog(savedInstanceState);*/

    }
    else if(type == stateType.TIME_FINISHED){
        //recyclerView.setVisibility(View.VISIBLE);

        btnContinue.setVisibility(View.GONE);
        txtResultContext.setText("آزمون شما به اتمام رسیده است !!");
        animationView.setAnimation(R.raw.finish_green);
        jointTask();
    }

        return builder.create();
    }

    private void jointTask(){
        txtContext.setVisibility(View.VISIBLE);

        txtContext.setText("نمره شما : "+result);
    }


    public interface OnChangeDecisionExamListener{
        void onFinish();
    }
}
