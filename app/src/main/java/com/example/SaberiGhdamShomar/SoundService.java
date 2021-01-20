package com.example.SaberiGhdamShomar;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;

import android.media.MediaPlayer;

import android.os.IBinder;

import android.util.Log;


public class SoundService extends Service {


    public static MediaPlayer mediaPlayer;


    @Override
    public IBinder onBind(Intent intent) {


        return null;

    }

    @Override
    public void onCreate() {
        super.onCreate();


        int id = 0;
        SharedPreferences getShare = getSharedPreferences("save", 0);
        id = getShare.getInt("identifier", -1);


        int idd = MainActivity.whichSound;
        Log.d("d_id", id + "");
        Log.e("main_id", id + "");
        if (idd == 1)
            mediaPlayer = MediaPlayer.create(this, R.raw.finishing);
//
        else if (idd == 2) {

            mediaPlayer = MediaPlayer.create(this, R.raw.naturesound);

        }
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
