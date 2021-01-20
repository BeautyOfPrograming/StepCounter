package com.example.SaberiGhdamShomar.step;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import java.util.List;

public class StepRepository {


    private StepDoa stepDoa;
    private LiveData<List<StepBase>> allSteps;

    public StepRepository(Application application) {

        StepDataBase stepDatabase = StepDataBase.getInstance(application);
        stepDoa = stepDatabase.getStepObject();
        allSteps = stepDoa.getAllSteps();

    }


    public LiveData<List<StepBase>> getAllSteps() {

        return allSteps;
    }


    public void deleteAllSteps() {

        new DeleteAllStepsAsyn(stepDoa).execute();
    }


    private static class DeleteAllStepsAsyn extends AsyncTask<Void, Void, Void> {

        StepDoa stepDoa;

        public DeleteAllStepsAsyn(StepDoa stepDoa) {
            this.stepDoa = stepDoa;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            stepDoa.deleteAll();
            return null;
        }
    }

    protected void delete(StepBase step) {

        new DeleteAsyn(stepDoa).execute(step);

    }


    public static class DeleteAsyn extends AsyncTask<StepBase, Void, Void> {

        StepDoa stepDoa;

        public DeleteAsyn(StepDoa stepDoa) {
            this.stepDoa = stepDoa;
        }

        @Override
        protected Void doInBackground(StepBase... stepBases) {

            stepDoa.delete(stepBases[0]);
            return null;
        }
    }


    public void insert(StepBase step) {

        new InsertAsyn(stepDoa).execute(step);
    }


    private static class InsertAsyn extends AsyncTask<StepBase, Void, Void> {


        StepDoa stepDoa;

        public InsertAsyn(StepDoa stepDoa) {
            this.stepDoa = stepDoa;
        }

        @Override
        protected Void doInBackground(StepBase... stepBase) {
            stepDoa.insert(stepBase[0]);
            return null;
        }
    }

}
