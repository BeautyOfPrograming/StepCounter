package com.example.SaberiGhdamShomar.word;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_table")

public class Word {

//    public Word(){
//
//    }

    /**
     * @PrimaryKey
     * @NonNull
     * @ColumnInfo(name = "word")
     * private String mWord;
     */

    @PrimaryKey(autoGenerate = true)
    private int id;


    private String mWord;

    private String duration;
    private String distance;
    private String calories;


    public String getDuration() {
        return this.duration;
    }

    public Word(@NonNull String mWord, String duration, String distance, String calories) {

        this.distance = distance;
        this.mWord = mWord;
        this.duration = duration;
        this.calories = calories;
    }

    public String getCalories() {
        return calories;
    }


    public String getDistance() {
        return distance;
    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return this.mWord;
    }

    public void setId(int id) {
        this.id = id;
    }



}



