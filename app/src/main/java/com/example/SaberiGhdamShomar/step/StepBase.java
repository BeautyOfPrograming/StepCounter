package com.example.SaberiGhdamShomar.step;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "step_table")
public class StepBase {

    @PrimaryKey(autoGenerate = true)
    private  int id;

    private int stepNumber;
    private int timeTaken;
    private int meter;
    private int kilometer;

    public StepBase(int stepNumber, int timeTaken, int meter, int kilometer) {
        this.stepNumber = stepNumber;
        this.timeTaken = timeTaken;
        this.meter = meter;
        this.kilometer = kilometer;

    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public int getTimeTaken() {
        return timeTaken;
    }

    public int getMeter() {
        return meter;
    }

    public int getKilometer() {
        return kilometer;
    }
}
