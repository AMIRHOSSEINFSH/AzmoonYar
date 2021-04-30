package com.example.azmoonyar.Fragments.Dialog;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azmoonyar.Adapters.PicChooserAdapter;
import com.example.azmoonyar.Database.Model.Question;
import com.example.azmoonyar.Fragments.QuestionBankEdit;
import com.example.azmoonyar.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


public class EditDialogFragment extends DialogFragment implements PicChooserAdapter.SelectingPicListener {

    Question question;
    private OnEditDialogListener dialogListener;
    private int CorrectOp;
    private OnAddDialogListener  addDialogListener;
    private String encodedImage;
    private String pathFromRecycler;
    private List<String> images=new ArrayList<>();
    private static final int MY_READ_PERMISSION_CODE = 201;
    RecyclerView recPic;
    private ImageView imgQue;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Fragment fragment=getFragmentManager().findFragmentById(R.id.fragment_main);
        if (fragment instanceof QuestionBankEdit){

            dialogListener =(OnEditDialogListener) fragment;
            addDialogListener =(OnAddDialogListener) fragment;

            if (getArguments()!=null)
            question =getArguments().getParcelable("question");

        }
    }

    public void loadImages() {
        //images=listOfImages(getContext());
        //Log.i("TAG", "loadImages: "+file.isFile()+"\n");
        images=listOfImages(getContext());
        for (int i = 0; i < images.size(); i++) {
            Log.i("TAG", "loadImages: "+images.get(i)+"\n");
        }

       // if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
          //  ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_READ_PERMISSION_CODE);
       // }else{
            PicChooserAdapter adapter=new PicChooserAdapter(this,images);
            recPic.setLayoutManager(new GridLayoutManager(getContext(),4));
            recPic.setAdapter(adapter);
        //}


    }

    public ArrayList<String> listOfImages(Context context){
        Uri uri;
        Cursor cursor;
        int column_index_data,column_index_folder_name;
        ArrayList<String> listOfAllImages=new ArrayList<>();
        String absolutePathOfImage;
        uri= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection={MediaStore.MediaColumns.DATA,MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        String order_by=MediaStore.Video.Media.DATE_TAKEN;
        cursor=context.getContentResolver().query(uri,projection,null,null,order_by+" DESC");
        column_index_data=cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()){
            absolutePathOfImage=cursor.getString(column_index_data);
            listOfAllImages.add(absolutePathOfImage);
        }

        return listOfAllImages;
    }
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private void onLoadPermission(){

        if (EasyPermissions.hasPermissions(getContext(), galleryPermissions)) {
            loadImages();
        } else {
            EasyPermissions.requestPermissions(this, "Access for storage",
                    201, galleryPermissions);
        }

       /* if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_READ_PERMISSION_CODE);
        }else{
            loadImages();
        }*/

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit, null, false);
        recPic=view.findViewById(R.id.rec_choosePic);
        builder.setView(view);
        onLoadPermission();


        imgQue=view.findViewById(R.id.imgQue);

        Spinner spinnerBase = view.findViewById(R.id.spinner_base);
        Spinner spinnerLesson = view.findViewById(R.id.spinner_lesson);
        Spinner spinnerSeason = view.findViewById(R.id.spinner_Season);
        Spinner spinnerDiff = view.findViewById(R.id.spinner_Diff);


        // Spinner spinnerPath=view.findViewById(R.id.spinner_path);

        //***
       /* File externalFilesDir = getActivity().getExternalFilesDir(null);
        File[] list=externalFilesDir.listFiles();
        List<String> pathList=new ArrayList<>();
        pathList.add("...");

        for (int i = 0; i < list.length; i++) {

            if (list[i].isFile() && list[i].getName().contains(".jpg"))
                pathList.add(list[i].getPath());

        }*/

        /*PicChooserAdapter adapter=new PicChooserAdapter(this,images);
        recPic.setLayoutManager(new GridLayoutManager(getContext(),4));
        recPic.setAdapter(adapter);*/

        //***

        Button btnOk = view.findViewById(R.id.AgreeEditProcess);
        Button btnCancel = view.findViewById(R.id.btnCancelEdit);
        Button btnDelete = view.findViewById(R.id.btnDeleteProcess);
        //Button btnInsert=view.findViewById(R.id.btnImageInset);
        Button btnDeleteImg=view.findViewById(R.id.btnRemoveImage);

        EditText txtDesc = view.findViewById(R.id.txt_desc_model);

        RadioButton radio1 = view.findViewById(R.id.option1);
        RadioButton radio2 = view.findViewById(R.id.option2);
        RadioButton radio3 = view.findViewById(R.id.option3);
        RadioButton radio4 = view.findViewById(R.id.option4);

        EditText et_Op1=view.findViewById(R.id.et_option1);
        EditText et_Op2=view.findViewById(R.id.et_option2);
        EditText et_Op3=view.findViewById(R.id.et_option3);
        EditText et_Op4=view.findViewById(R.id.et_option4);



        if (question!=null) {
            /*et_Op1.setEnabled(false);
            et_Op2.setEnabled(false);
            et_Op3.setEnabled(false);
            et_Op4.setEnabled(false);*/
            if (question.getImgSourceBinary()!=null){
                byte[] decodedString = Base64.decode(question.getImgSourceBinary(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imgQue.setImageBitmap(decodedByte);
                Log.i("TAG", "onCreateDialog:h ");
            }
            else{
               // imgQue.setImageResource(R.drawable.ic_baseline_image_not_supported_24);
            }


            int base = 0;
            if (question.getBase().equals("هفتم"))
                base = 0;
            else if (question.getBase().equals("هشتم"))
                base = 1;
            else if (question.getBase().equals("نهم"))
                base = 2;

            spinnerBase.setSelection(base);

            spinnerSeason.setSelection(Integer.parseInt(question.getSeason())-1);

            int lesson=0;
            if (question.getLesson().equals("علوم تجربی"))
                lesson = 0;
            else if (question.getLesson().equals("ریاضی"))
                lesson = 1;
            else if (question.getLesson().equals("مطالعات اجتماعی"))
                lesson = 2;
            else if (question.getLesson().equals("ادبیات"))
                lesson = 3;

            spinnerLesson.setSelection(lesson);

            int Diff=0;
            if (question.getDifficulty().equals("آسان"))
                Diff=0;
            else if(question.getDifficulty().equals("متوسط"))
                Diff=1;
            else if(question.getDifficulty().equals("سخت"))
                Diff=2;

            spinnerDiff.setSelection(Diff);

            txtDesc.setText(question.getDescription());
            radio1.setText(question.getOption1());
            radio2.setText(question.getOption2());
            radio3.setText(question.getOption3());
            radio4.setText(question.getOption4());

            et_Op1.setText(radio1.getText().toString());
            et_Op2.setText(radio2.getText().toString());
            et_Op3.setText(radio3.getText().toString());
            et_Op4.setText(radio4.getText().toString());

            switch (question.getCorrectOption()) {
                case 1:
                    CorrectOp=1;
                    radio1.setChecked(true);
                    break;
                case 2:
                    CorrectOp=2;
                    radio2.setChecked(true);
                    break;
                case 3:
                    CorrectOp=3;
                    radio3.setChecked(true);
                    break;
                case 4:
                    CorrectOp=4;
                    radio4.setChecked(true);
                    break;
            }

        }
        else {

            et_Op1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                radio1.setText(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            et_Op2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    radio2.setText(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            et_Op3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    radio3.setText(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            et_Op4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    radio4.setText(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            btnDelete.setVisibility(View.GONE);

        }


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radio1.isChecked())
                    CorrectOp=1;
                else if(radio2.isChecked())
                    CorrectOp=2;
                else if(radio3.isChecked())
                    CorrectOp=3;
                else if(radio4.isChecked())
                    CorrectOp=4;

                if (question!=null) {
                    if (pathFromRecycler==null)
                        encodedImage=null;

                    question.setImgSourceBinary(encodedImage);
                    question.setBase(spinnerBase.getSelectedItem().toString());
                    question.setLesson(spinnerLesson.getSelectedItem().toString());
                    question.setDifficulty(spinnerDiff.getSelectedItem().toString());
                    question.setSeason(spinnerSeason.getSelectedItem().toString());
                    question.setOption1(et_Op1.getText().toString());
                    question.setOption2(et_Op2.getText().toString());
                    question.setOption3(et_Op3.getText().toString());
                    question.setOption4(et_Op4.getText().toString());
                    question.setDescription(txtDesc.getText().toString());
                    question.setCorrectOption(CorrectOp);
                    Log.i("TAG", "onClick: "+question.getImgSourceBinary());
                    dialogListener.onEdit(question);
                } else {

                     question = new Question(spinnerBase.getSelectedItem().toString(),
                            spinnerSeason.getSelectedItem().toString(),
                            spinnerLesson.getSelectedItem().toString(),
                            radio1.getText().toString(),
                            radio2.getText().toString(),
                            radio3.getText().toString(),
                            radio4.getText().toString(),
                            CorrectOp,spinnerDiff.getSelectedItem().toString(),
                            txtDesc.getText().toString(),encodedImage);
                     question.setHaveImage(encodedImage != null);

                    addDialogListener.OnAdd(question);
                }


                /*if (radio1.isChecked())
                    question.setCorrectOption(1);
                else if (radio2.isChecked())
                    question.setCorrectOption(2);
                else if (radio3.isChecked())
                    question.setCorrectOption(3);
                else if (radio4.isChecked())
                    question.setCorrectOption(4);*/


                dismiss();
                Toast.makeText(view.getContext(), "Ok", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "cancel", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListener.OnDelete(question);
                dismiss();
                Toast.makeText(view.getContext(), "delete", Toast.LENGTH_SHORT).show();

            }
        });

        /*btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pathFromRecycler!=null){
                    encodedImage=ConvertImgToBit(imgQue,pathFromRecycler);
//                    question.setImgSourceBinary(encodedImage);
                }else{
                    Toast.makeText(getContext(), "How do you want to Insert empty Directory ???!!!", Toast.LENGTH_SHORT).show();
                }


            }
        });*/

        btnDeleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgQue.setImageBitmap(null);
            }
        });

        return builder.create();
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public String ConvertImgToBit(ImageView img,String path,Bitmap bitmap){
       // Bitmap bm = BitmapFactory.decodeFile(path);
        //bitmap=getResizedBitmap(bitmap,100);
        img.setImageBitmap(/*bm*/bitmap);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        /*bm*/bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    @Override
    public void onSendBackPath(String path,Bitmap bitmap) {
        Log.i("TAG", "onSendBackPath: "+path);
        pathFromRecycler=path;
        if (pathFromRecycler!=null){

            encodedImage=ConvertImgToBit(imgQue,pathFromRecycler,bitmap);
        }

        else{
            Toast.makeText(getContext(), "Path is null", Toast.LENGTH_SHORT).show();
        }

    }

    public interface OnEditDialogListener {
        void OnDelete(Question question);

        void onEdit(Question question);
    }

    public interface OnAddDialogListener {
        void OnAdd(Question question);
    }
}
