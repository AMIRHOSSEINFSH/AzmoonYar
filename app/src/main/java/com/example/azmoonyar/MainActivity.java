package com.example.azmoonyar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.animation.Animator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.azmoonyar.Adapters.PicChooserAdapter;
import com.example.azmoonyar.Database.AppDatabase;
import com.example.azmoonyar.Database.QuestionDao;
import com.example.azmoonyar.Fragments.AddExamFragment;
import com.example.azmoonyar.Fragments.Dialog.EditDialogFragment;
import com.example.azmoonyar.Fragments.QuestionBankEdit;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public BadgeDrawable badge;
    QuestionDao questionDao;

    List<String> images;
    private static final int MY_READ_PERMISSION_CODE = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        BottomNavigationView btnView=findViewById(R.id.bottomNavigation_main);
        FrameLayout fragment_container=findViewById(R.id.fragment_main);
        btnView.setSelectedItemId(R.id.Edit_Sample);

        questionDao=AppDatabase.getAppDatabase(this).getQuestionDao();

        badge=btnView.getOrCreateBadge(R.id.Edit_Sample);
        badge.setVisible(true);
        badge.setNumber(questionDao.getRowCount());


        view.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                btnView.setVisibility(View.VISIBLE);
                fragment_container.setVisibility(View.VISIBLE);
                setUpViews();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        btnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId()==R.id.Edit_Sample){

                    FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_main,new QuestionBankEdit());
                    transaction.commit();

                }else if(item.getItemId()==R.id.Create_Exam){

                    FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_main,new AddExamFragment());
                    transaction.commit();

                }
                return true;
            }
        });

        btnView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

            }
        });

    }

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
        transaction.replace(R.id.fragment_main,new QuestionBankEdit());
        transaction.commit();
    }
}