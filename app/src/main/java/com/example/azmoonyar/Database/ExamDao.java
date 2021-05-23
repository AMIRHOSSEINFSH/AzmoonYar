package com.example.azmoonyar.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.azmoonyar.Database.Model.Exam;
import com.example.azmoonyar.Database.Model.Question;

import java.util.List;

@Dao()
public interface ExamDao {

    @Insert
    long AddExam(Exam exam);


    @Query("SELECT MAX(id) FROM ExamTable;")
    int getLastPrimaryKey();



    @Query("SELECT * FROM ExamTable;")
    List<Exam> getExamList();

    @Query("UPDATE ExamTable SET result=:result , isPassed=:isPassed WHERE id=:id ")
    int updateResultOfExam(int result,boolean isPassed,int id);

}
