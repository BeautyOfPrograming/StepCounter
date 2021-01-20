package com.example.SaberiGhdamShomar.Asyntask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.SaberiGhdamShomar.MainActivity;

public class   AsynTaskProgressBar extends AsyncTask<Integer, Integer, Integer> {

    int max;
    public static ProgressBar pb;

    public AsynTaskProgressBar(int a,ProgressBar progressBar) {

        this.pb = progressBar;
        this.max = a;



    }



     public void setProgressBar() {

         this.pb.setMax(this.max);
         Log.d("asyntask_max_value",this.max+"");

     }
//
     @Override
     protected Integer doInBackground(Integer[] objects) {

        int i =0;


         for ( int numSteps = MainActivity.numSteps; MainActivity.numSteps <=objects[0]; i++) {

//             while (MainActivity.numSteps <= objects[0]) {

                 try {
                     publishProgress(MainActivity.numSteps);

                     Thread.sleep(1000);
                     Log.d("asyntask_while", i + "");
                     Log.d("asyntask_while_numSteps", numSteps + "");

                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }


//             }
         }

//         for (int i = numSteps; i < objects[0]; i++){
//             status++;
//
//         }
         return null;
     }
     @Override
     protected void onProgressUpdate(Integer[] values) {
         pb.setProgress(values[0]);
         super.onProgressUpdate(values);
     }


}
