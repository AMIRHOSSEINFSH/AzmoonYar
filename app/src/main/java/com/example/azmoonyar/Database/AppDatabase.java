package com.example.azmoonyar.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.azmoonyar.Database.Model.Exam;
import com.example.azmoonyar.Database.Model.Question;
import com.example.azmoonyar.Database.Model.QuestionExam;

@Database(entities = {Question.class, QuestionExam.class, Exam.class} ,version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;

    public static AppDatabase getAppDatabase(Context context) {
        if (appDatabase==null)
            appDatabase= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"db_app")
                    //todo for querying on main thread
                    .allowMainThreadQueries()
                    .build();
        return appDatabase;
    }

    public abstract QuestionDao getQuestionDao();
    public abstract ExamDao getExamDao();
    public abstract QuestionExamDao getQuestionExamDao();



}
