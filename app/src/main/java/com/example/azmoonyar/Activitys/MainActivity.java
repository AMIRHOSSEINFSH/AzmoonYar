package com.example.azmoonyar.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.animation.Animator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.azmoonyar.Fragments.ExamTimeFragment;
import com.example.azmoonyar.Database.QuestionDao;
import com.example.azmoonyar.Fragments.Dialog.EditDialogFragment;
import com.example.azmoonyar.Fragments.ExamCreatorFragment;
import com.example.azmoonyar.Fragments.MainStudentFragment;
import com.example.azmoonyar.R;
import com.google.android.material.badge.BadgeDrawable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public BadgeDrawable badge;
    QuestionDao questionDao;
    public View img_select_charactor;
    List<String> images;
    private static final int MY_READ_PERMISSION_CODE = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DrawerLayout mDrawerLayout=findViewById(R.id.drawer_layout);

        img_select_charactor=findViewById(R.id.imgNavigateDrawer);
        img_select_charactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        //setUpViews();

        //RecyclerView recyclerView=findViewById(R.id.rec);

        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_READ_PERMISSION_CODE);
        }else{
            loadImages();
        }

        PicChooserAdapter adapter=new PicChooserAdapter(this,images);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(adapter);*/
        //getExternalFilesDir(Environment.DIRECTORY_DCIM);

        //File file=new File();
        //File[] files=getExternalFilesDir(Environment.DIRECTORY_DCIM).listFiles();

        //Log.i("TAG", "onCreate: "+files.length);

       // Toast.makeText(this, , Toast.LENGTH_LONG).show();

        LottieAnimationView view=findViewById(R.id.lottie_main_animationView);


        view.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                findViewById(R.id.imgNavigateDrawer).setVisibility(View.VISIBLE);
                setUpViews();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });



    }

    @Override
    public void onBackPressed() {
        Fragment fragment=getSupportFragmentManager().findFragmentById(R.id.fragment_main);

        if (fragment instanceof ExamTimeFragment) {
            img_select_charactor.setVisibility(View.VISIBLE);
            super.onBackPressed();
        }

        else if (fragment instanceof ExamCreatorFragment){
            img_select_charactor.setVisibility(View.VISIBLE);
            super.onBackPressed();
        }
            else{

            super.onBackPressed();
                Toast.makeText(this, "زود اومدی نخواه زود هم بری :))", Toast.LENGTH_SHORT).show();
            }


        }


        //super.onBackPressed();


    private void loadImages() {
        //images=listOfImages(this);
       // File file;
        //file=new File(images.get(0));
        //Log.i("TAG", "loadImages: "+file.isFile()+"\n");
        images=listOfImages(this);
        for (int i = 0; i < images.size(); i++) {
            Log.i("TAG", "loadImages: "+images.get(i)+"\n");
        }

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
        File file;
        while (cursor.moveToNext()){
            absolutePathOfImage=cursor.getString(column_index_data);
            file=new File(absolutePathOfImage);
            long fileSizeInBytes = file.length();

            long fileSizeInKB = fileSizeInBytes / 1024;

            long fileSizeInMB = fileSizeInKB / 1024;
            Log.i("TAG", "listOfImages: "+fileSizeInMB+" name : "+file.getName());
            if (fileSizeInKB<200){
                Log.i("TAG", "listOfImages N: "+fileSizeInMB+" name : "+file.getName()+" Size: "+fileSizeInKB);
                listOfAllImages.add(absolutePathOfImage);
            }

        }

        return listOfAllImages;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==MY_READ_PERMISSION_CODE){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Read External Storage Granted", Toast.LENGTH_SHORT).show();
                EditDialogFragment fragment=new EditDialogFragment();
                fragment.loadImages();
                //loadImages();
            }else{
                Toast.makeText(this, "Read External Storage Rejected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setUpViews() {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_main,new MainStudentFragment());
        transaction.commit();
    }
}