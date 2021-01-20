package com.example.SaberiGhdamShomar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;


public class ResultDialog extends Activity {

    protected FrameLayout ok;

    public static boolean isRunning = false;
    @Override
    protected void onStart() {
        super.onStart();
//        isRunning = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
//        isRunning = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        isRunning = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);

        getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));


        MainActivity.whichSound = 1;
        SharedPreferences res = getSharedPreferences("save", MODE_PRIVATE);
        SharedPreferences.Editor editor = res.edit();
        editor.putInt("identifier", 1);
        editor.apply();



//
//        if (MainActivity.whichSound == 2) {
//
//
//            Intent objIntent = new Intent(getApplicationContext(), SoundService.class);
//            stopService(objIntent);
//
//            playSound();
//
//
//        } else {
//            Intent objIntent = new Intent(getApplicationContext(), SoundService.class);
//            stopService(objIntent);
//
//            playSound();
//        }





        //ok button
        ok = findViewById(R.id.confirmingOk);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences g = getSharedPreferences("save", 0);
                g.edit().remove("identifier");
                g.edit().apply();
                confirmingOk();
            }
        });

    }

    protected void confirmingOk() {


        Intent objIntent = new Intent(this, SoundService.class);
        stopService(objIntent);
//        isRunning = false;
        MainActivity.mediaPlayer.stop();
        finish();

    }


    void playSound() {

        Intent intent = new Intent(getApplicationContext(), SoundService.class);
        startService(intent);


    }
}
