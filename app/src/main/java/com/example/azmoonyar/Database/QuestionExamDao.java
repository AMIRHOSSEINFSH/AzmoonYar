package com.example.azmoonyar.Database;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.azmoonyar.Database.Model.QuestionExam;

@Dao
public interface QuestionExamDao {

    @Insert
    long AddQuestionExam(QuestionExam questionExam);

}
