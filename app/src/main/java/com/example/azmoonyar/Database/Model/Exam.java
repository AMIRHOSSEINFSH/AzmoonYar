package com.example.azmoonyar.Database.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "ExamTable")
public class Exam implements Parcelable {

    public Exam(){

    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String dayName;
    private String dayNumber;
    private String monthNumber;
    private String monthName;
    private String year;
    private String hour;
    private String minute;
    private String description;

    protected Exam(Parcel in) {
        id = in.readInt();
        dayName = in.readString();
        dayNumber = in.readString();
        monthNumber = in.readString();
        monthName = in.readString();
        year = in.readString();
        hour = in.readString();
        minute = in.readString();
        description = in.readString();
        questionExamList = in.createTypedArrayList(Question.CREATOR);
    }

    public static final Creator<Exam> CREATOR = new Creator<Exam>() {
        @Override
        public Exam createFromParcel(Parcel in) {
            return new Exam(in);
        }

        @Override
        public Exam[] newArray(int size) {
            return new Exam[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Ignore()
    private ArrayList<Question> questionExamList;





    public ArrayList<Question> getQuestionExamList() {
        return questionExamList;
    }

    public void setQuestionExamList(ArrayList<Question> questionExamList) {
        this.questionExamList = questionExamList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(String dayNumber) {
        this.dayNumber = dayNumber;
    }


    public String getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(String monthNumber) {
        this.monthNumber = monthNumber;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(dayName);
        parcel.writeString(dayNumber);
        parcel.writeString(monthNumber);
        parcel.writeString(monthName);
        parcel.writeString(year);
        parcel.writeString(hour);
        parcel.writeString(minute);
        parcel.writeString(description);
        parcel.writeTypedList(questionExamList);
    }
}
