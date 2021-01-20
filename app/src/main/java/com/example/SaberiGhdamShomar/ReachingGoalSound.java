package com.example.SaberiGhdamShomar;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;


public class ReachingGoalSound extends Service {


    public static MediaPlayer mediaPlayer;


    @Override
    public IBinder onBind(Intent intent) {


        return null;

    }

    @Override
    public void onCreate() {
        super.onCreate();


        mediaPlayer = MediaPlayer.create(this, R.raw.finishing);


        mediaPlayer.setLooping(true); // Set looping
        mediaPlayer.setVolume(100, 100);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {


        mediaPlayer.start();
//        Toast.makeText(getApplicationContext(), "Playing Bohemian Rashpody in the Background", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    public void onStart(Intent intent, int startId) {
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public void onLowMemory() {
    }

}
