package com.example.azmoonyar.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.azmoonyar.Database.Model.QuestionExam;

import java.util.List;

@Dao
public interface QuestionExamDao {

    @Insert
    long AddQuestionExam(QuestionExam questionExam);

    @Query("SELECT * FROM  exam_question WHERE ExamReference=:ref ;")
    List<QuestionExam> getAllQuestionExams(int ref);

    @Update
    int updateQuestionAfterExam(QuestionExam questionExam);

}
