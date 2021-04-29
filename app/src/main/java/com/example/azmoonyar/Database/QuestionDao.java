package com.example.azmoonyar.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.azmoonyar.Database.Model.Question;

import java.util.List;

@Dao
public interface QuestionDao {

    @Insert
    long addQuestion(Question question);

    @Query("SELECT * FROM QuestionTbl")
    List<Question> getQuestions();

    @Delete
    int deleteQuestion(Question question);

    @Update
    int EditQuestionInfo(Question question);

    @Query("SELECT * FROM QuestionTbl WHERE description LIKE  '%' || :query || '%' ")
    List<Question> search(String query);

    @Query("SELECT COUNT(id) FROM QuestionTbl")
    int getRowCount();

}
