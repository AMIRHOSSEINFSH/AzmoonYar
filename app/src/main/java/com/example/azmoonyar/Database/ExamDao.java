package com.example.azmoonyar.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.azmoonyar.Database.Model.Exam;

@Dao()
public interface ExamDao {

    @Insert
    long AddExam(Exam exam);


    @Query("SELECT MAX(id) FROM ExamTable;")
    int getLastPrimaryKey();

}
