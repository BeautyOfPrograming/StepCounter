package com.example.SaberiGhdamShomar.step;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StepDoa {

    @Insert
    void insert(StepBase setp);

    @Update
    void update(StepBase step);

    @Delete
    void delete(StepBase step);

    @Query("Delete  from step_table")
    void deleteAll();

    @Query("Select * from step_table order by id")
    LiveData<List<StepBase>> getAllSteps();

}
