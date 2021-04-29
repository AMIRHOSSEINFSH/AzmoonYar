package com.example.azmoonyar.Database.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "QuestionTbl")
public class Question  implements Parcelable{

    public Question(){

    }

    @PrimaryKey(autoGenerate = true)
    private int id;


    private String base;
    private String season;
    private String lesson;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

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



    protected Question(Parcel in) {
        id = in.readInt();
        base = in.readString();
        season = in.readString();
        lesson = in.readString();
        option1 = in.readString();
        option2 = in.readString();
        option3 = in.readString();
        option4 = in.readString();
        CorrectOption = in.readInt();
        difficulty = in.readString();
        isExpanded = in.readByte() != 0;
        description = in.readString();
    }


    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public Question(String base, String season, String lesson, String option1, String option2, String option3, String option4, int correctOption, String difficulty, String description,String encodedImage) {
        this.base = base;
        this.season = season;
        this.lesson = lesson;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        CorrectOption = correctOption;
        this.difficulty = difficulty;
        this.description = description;
        this.imgSourceBinary= encodedImage;
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(base);
        dest.writeString(season);
        dest.writeString(lesson);
        dest.writeString(option1);
        dest.writeString(option2);
        dest.writeString(option3);
        dest.writeString(option4);
        dest.writeInt(CorrectOption);
        dest.writeString(difficulty);
        dest.writeString(imgSourceBinary);
        dest.writeByte((byte) (isExpanded ? 1 : 0));
        dest.writeByte((byte) (haveImage ? 1 : 0));
        dest.writeString(description);
    }
}
