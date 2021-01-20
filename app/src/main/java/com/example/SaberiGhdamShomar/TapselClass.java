package com.example.SaberiGhdamShomar;

import android.app.Application;

import ir.tapsell.sdk.Tapsell;

public class TapselClass extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Tapsell.initialize(getApplicationContext(),"lidcgcfjcmijptjikokmcdmbrqdhkajtfmtkfkbqprfqijegmofgkeploaodlojjhqpggt" );
    }
}
