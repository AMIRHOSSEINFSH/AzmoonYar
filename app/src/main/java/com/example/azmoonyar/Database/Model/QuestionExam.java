package com.example.azmoonyar.Database.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "exam_question")
public class QuestionExam implements Parcelable{


    protected QuestionExam(Parcel in) {
        id = in.readInt();
        ExamReference = in.readInt();
        base = in.readString();
        season = in.readString();
        lesson = in.readString();
        option1 = in.readString();
        option2 = in.readString();
        option3 = in.readString();
        option4 = in.readString();
        checkedOption = in.readInt();
        CorrectOption = in.readInt();
        difficulty = in.readString();
        imgSourceBinary = in.readString();
        isExpanded = in.readByte() != 0;
        haveImage = in.readByte() != 0;
        description = in.readString();
    }

    public static final Creator<QuestionExam> CREATOR = new Creator<QuestionExam>() {
        @Override
        public QuestionExam createFromParcel(Parcel in) {
            return new QuestionExam(in);
        }

        @Override
        public QuestionExam[] newArray(int size) {
            return new QuestionExam[size];
        }
    };

    public void ConvertFromQuestion(Question question){
        setDescription(question.getDescription());
        setBase(question.getBase());
        setSeason(question.getSeason());
        setLesson(question.getLesson());
        setOption1(question.getOption1());
        setOption2(question.getOption2());
        setOption3(question.getOption3());
        setOption4(question.getOption4());
        setCorrectOption(question.getCorrectOption());
        setDifficulty(question.getDifficulty());
        setImgSourceBinary(question.getImgSourceBinary());
    }






    public int getExamReference() {
        return ExamReference;
    }

    public void setExamReference(int examReference) {
        ExamReference = examReference;
    }

    public QuestionExam(){

    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int ExamReference;
    private String base;
    private String season;
    private String lesson;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int    checkedOption;


    public int getCheckedOption() {
        return checkedOption;
    }

    public void setCheckedOption(int checkedOption) {
        this.checkedOption = checkedOption;
    }

    public boolean isHaveImage() {
        return haveImage;
    }

    public String getImgSourceBinary() {
        return imgSourceBinary;
    }

    public void setImgSourceBinary(String imgSourceBinary) {
        this.imgSourceBinary = imgSourceBinary;
    }

    public boolean haveImage() {
        return haveImage;
    }

    public void setHaveImage(boolean haveImage) {
        this.haveImage = haveImage;
    }

    private int     CorrectOption;
    private String  difficulty;
    private String  imgSourceBinary;
    @Ignore()
    private boolean isExpanded=false;

    @Ignore()
    private boolean haveImage=false;





    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }



    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getCorrectOption() {
        return CorrectOption;
    }

    public void setCorrectOption(int correctOption) {
        CorrectOption = correctOption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(ExamReference);
        parcel.writeString(base);
        parcel.writeString(season);
        parcel.writeString(lesson);
        parcel.writeString(option1);
        parcel.writeString(option2);
        parcel.writeString(option3);
        parcel.writeString(option4);
        parcel.writeInt(checkedOption);
        parcel.writeInt(CorrectOption);
        parcel.writeString(difficulty);
        parcel.writeString(imgSourceBinary);
        parcel.writeByte((byte) (isExpanded ? 1 : 0));
        parcel.writeByte((byte) (haveImage ? 1 : 0));
        parcel.writeString(description);
    }
}
