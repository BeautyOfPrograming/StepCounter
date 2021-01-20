package com.example.SaberiGhdamShomar.step;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {StepBase.class}, version = 1, exportSchema = false)
public abstract class StepDataBase extends RoomDatabase {

    public static StepDataBase instance;

    public abstract StepDoa getStepObject();


    public static synchronized StepDataBase getInstance(Context context) {

        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(), StepDataBase.class, "step_database")
                    .addCallback(callback)
                    .build();

            Log.d("from_database","works");
//            instance = Room.databaseBuilder(context.getApplicationContext(),
//                    StepDataBase.class, "step_database")
//                    .allowMainThreadQueries()

//            instance = Room.databaseBuilder(context.getApplicationContext(), StepDataBase.class, "step_database")
//                    .fallbackToDestructiveMigration()
//                    .addCallback(callback)
//                    .build();
        }
        Log.d("from_database","works00");

        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {



        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            Log.d("from_database","works1");

            super.onCreate(db);
            new PopulateSpetAsynDb(instance).execute();
        }
    };


    private static class PopulateSpetAsynDb extends AsyncTask<Void, Void, Void> {

        private StepDoa StepDoa;

        public PopulateSpetAsynDb(StepDataBase database) {
            Log.d("from_database","works2");

            this.StepDoa = database.getStepObject();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            StepDoa.insert(new StepBase(3, 10, 9, 6));
            StepDoa.insert(new StepBase(4, 11, 10, 7));
            StepDoa.insert(new StepBase(5, 12, 11, 8));
            StepDoa.insert(new StepBase(6, 13, 13, 9));
            Log.d("from_database","works3");


            return null;
        }
    }

}
