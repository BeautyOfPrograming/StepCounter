package com.example.SaberiGhdamShomar.step;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class StepViewModel extends AndroidViewModel {

    StepRepository stepRep;
    LiveData<List<StepBase>> allSteps;

    public StepViewModel(@NonNull Application application) {
        super(application);

        stepRep = new StepRepository(application);
        allSteps = stepRep.getAllSteps();

    }

    public LiveData<List<StepBase>> getAllSteps() {

        return allSteps;
    }

    public void insert(StepBase step) {

        stepRep.insert(step);

    }

    public void delete(StepBase step) {

        stepRep.delete(step);
    }


    public void deleteAllSteps() {

        stepRep.deleteAllSteps();
    }
}
