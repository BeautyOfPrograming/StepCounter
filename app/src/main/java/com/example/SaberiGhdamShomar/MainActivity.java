package com.example.SaberiGhdamShomar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.SaberiGhdamShomar.Asyntask.AsynTaskProgressBar;
import com.example.SaberiGhdamShomar.StepCounter.StepDetector;
import com.example.SaberiGhdamShomar.StepCounter.StepListener;
import com.example.SaberiGhdamShomar.step.StepViewModel;
import com.example.SaberiGhdamShomar.word.Word;
import com.example.SaberiGhdamShomar.word.WordListAdapter;
import com.example.SaberiGhdamShomar.word.WordViewModel;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.text.DecimalFormat;

import java.text.NumberFormat;
import java.util.List;


import ir.tapsell.sdk.Tapsell;
import ir.tapsell.sdk.TapsellAdRequestListener;
import ir.tapsell.sdk.TapsellAdRequestOptions;
import ir.tapsell.sdk.TapsellAdShowListener;
import ir.tapsell.sdk.TapsellShowOptions;

import static com.example.SaberiGhdamShomar.Asyntask.AsynTaskProgressBar.pb;


public class MainActivity extends AppCompatActivity implements SensorEventListener, StepListener, SetGoalStep.StepDialogListener {


    private static final String TAPSELL_KEY = "lidcgcfjcmijptjikokmcdmbrqdhkajtfmtkfkbqprfqijegmofgkeploaodlojjhqpggt";
    // nested scroll view
    NestedScrollView nestedScrollView;

    // calories and distance
    private static double METRIC_RUNNING_FACTOR = 1.02784823;
    private static double IMPERIAL_RUNNING_FACTOR = 0.75031498;

    private static double METRIC_WALKING_FACTOR = 0.708;
    private static double IMPERIAL_WALKING_FACTOR = 0.517;

    public static int whichSound = 0;

    private double mCalories = 0;
    double mDistance = 0;
    boolean mIsMetric;
    boolean mIsRunning;
    float mStepLength;
    float mBodyWeight;
    static int clicked = -3;

    public static MediaPlayer mediaPlayer;

    static int counter = 0;

    RelativeLayout relativeLayout;
//    int pStatus = 0;
//    private Button button;

    //    private Handler handler = new Handler();
    TextView tv, tourguide;
    FloatingActionButton fab;
    protected BottomAppBar bottomAppBar;

    // the part for step counter
    private TextView textView, counterkil, calory, history, counterhm, distance;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    public static int numSteps;
    private TextView TvSteps, goal;
    private Button BtnStart;
    private Button BtnStop, showSettings;
    FloatingActionButton floatactionbar;
    private ImageView PlaySound;
    private ImageView StopSound;
    private static ProgressBar mProgress;
    private EditText goaleditText;
    ProgressBar progressBar;
    private Dialog dialog, myDialog;
    private static long saveTime;

    private AsynTaskProgressBar asynTaskProgressBar;

    RecyclerView.LayoutManager RecyclerViewLayoutManager;

    RecyclerView recyclerView;
    // Linear Layout Manager
    LinearLayoutManager HorizontalLayout;
    static Chronometer simpleChronometer;


    /**
     * StepViewMddel instance
     */

    public WordListAdapter adapter;


    StepViewModel stepViewModel;
    public static WordViewModel mWordViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;


    // Timer variables
    long hour;
    long startTime;
    long elapsedTime;
    long elapsedSeconds;
    long secondsDisplay;
    long elapsedMinutes;


    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;


//


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent objIntent = new Intent(this, SoundService.class);
        stopService(objIntent);
        counter = 0;

        removeIdentifier();


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent objIntent = new Intent(this, SoundService.class);
        stopService(objIntent);

        removeIdentifier();
        counter = 0;

    }

    @Override
    protected void onPause() {
        super.onPause();
//        Intent objIntent = new Intent(this, SoundService.class);
//        stopService(objIntent);

//        removeIdentifier();

        counter = 0;

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent objIntent = new Intent(this, SoundService.class);
        stopService(objIntent);

        if(mediaPlayer!=null)
           mediaPlayer.stop();


        removeIdentifier();

        counter = 0;


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//
//        Tapsell tapsell = new Tapsell();
//
//        tapsell.requestAd(getApplicationContext(),
//                "5f67a3c942f96300018a9acd",
//                new TapsellAdRequestOptions(),
//                new TapsellAdRequestListener() {
//                    @Override
//                    public void onAdAvailable(String adId) {
//
//
//                        tapsell.showAd(getApplicationContext(),
//                                "5f67a3c942f96300018a9acd",
//                                adId,
//                                new TapsellShowOptions(),
//                                new TapsellAdShowListener() {
//                                    @Override
//                                    public void onOpened() {
//                                    }
//
//                                    @Override
//                                    public void onClosed() {
//                                    }
//
//                                    @Override
//                                    public void onError(String message) {
//                                    }
//
//                                    @Override
//                                    public void onRewarded(boolean completed) {
//                                    }
//                                });
//
//
//                    }
//
//                    @Override
//                    public void onError(String message) {
//                    }
//                });
//


        Tapsell tapsell = new Tapsell();

        tapsell.requestAd(getApplicationContext(),
                "5f679ca442f96300018a9acb",
                new TapsellAdRequestOptions(),
                new TapsellAdRequestListener() {
                    @Override
                    public void onAdAvailable(String adId) {


                        tapsell.showAd(getApplicationContext(),
                                "5f679ca442f96300018a9acb",
                                adId,
                                new TapsellShowOptions(),
                                new TapsellAdShowListener() {
                                    @Override
                                    public void onOpened() {
                                    }

                                    @Override
                                    public void onClosed() {
                                    }

                                    @Override
                                    public void onError(String message) {
                                    }

                                    @Override
                                    public void onRewarded(boolean completed) {
                                    }
                                });


                    }

                    @Override
                    public void onError(String message) {
                    }
                });


        tourguide = findViewById(R.id.today_text);
//        ObjectAnimator colorAnim = ObjectAnimator.ofInt(tourguide, "textColor",
//                Color.RED, Color.GREEN);
//        colorAnim.setEvaluator(new ArgbEvaluator());
//        colorAnim.start();
//        colorAnim.reverse();

//        Integer colorFrom = getResources().getColor(R.color.bluedark);
//        Integer colorTo = getResources().getColor(R.color.DarkRed);
//        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
//        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

//            @Override
//            public void onAnimationUpdate(ValueAnimator animator) {
//                tourguide.setTextColor((Integer)animator.getAnimatedValue());
//            }
//
//        });
//        colorAnimation.start();
//
//        colorAnimation.reverse();
//


        tourguide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playTourGuide();
            }
        });
        // nested scroll view
        nestedScrollView = findViewById(R.id.main_nested);


        showSettings = findViewById(R.id.location);
        showSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/////kjnknk
                SharedPreferences sh = getSharedPreferences("setting", 0);
                int height = sh.getInt("height", -1);


                whichSound = 1;
//                Toast.makeText(getApplicationContext(), height + "", Toast.LENGTH_SHORT).show();
            }
        });

        history = findViewById(R.id.history);


        simpleChronometer = findViewById(R.id.simpleChronometer);
        simpleChronometer.setFormat(" %s   ");
        simpleChronometer.setBase(SystemClock.elapsedRealtime());

//        simpleChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
//            @Override
//            public void onChronometerTick(Chronometer chronometer) {
//                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 10000) {
//                    chronometer.setBase(SystemClock.elapsedRealtime());
//                    Toast.makeText(MainActivity.this, "Bing!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


        /**
         * RecyclerView instance and useage
         */


        recyclerView = findViewById(R.id.recycler);

        RecyclerViewLayoutManager
                = new LinearLayoutManager(
                getApplicationContext());


        recyclerView.setLayoutManager(RecyclerViewLayoutManager);



        adapter = new WordListAdapter(getApplicationContext());

        // Set Horizontal Layout Manager
        // for Recycler view
        HorizontalLayout
                = new LinearLayoutManager(
                MainActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);

        recyclerView.setLayoutManager(HorizontalLayout);

        setUpRecyclerView();

//        recyclerView.setAdapter(adapter);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);


        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(words);

                if (adapter.getItemCount() == 0) {

                   // calory.setVisibility(View.GONE);
                    history.setVisibility(View.GONE);
                }

                else {
                    history.setVisibility(View.VISIBLE);

                }

            }


        });

        adapter.setOnClickListener(new WordListAdapter.onItemClickLisnter() {
            @Override
            public void onItemClick(Word noteBase) {
                counterhm.setText(noteBase.getWord());
                counterkil.setText(noteBase.getDuration());
                calory.setText(noteBase.getCalories());
                distance.setText(noteBase.getDistance() + "");
            }
        });


        /** added recycler
         */
//        Button fab = findViewById(R.id.kcal);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
//                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
//            }
//        });


//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, LEFT | RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//
//                Toast.makeText(getApplicationContext(),"onSwipe",Toast.LENGTH_LONG).show();
//
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//
////                mWordViewModel.delete(adapter.getPosition(viewHolder.getAdapterPosition()));
//
//                Toast.makeText(getApplicationContext(),"onSwipe",Toast.LENGTH_LONG).show();
//            }
//
//        }).attachToRecyclerView(recyclerView);


//        ItemTouchHelper.Callback callback =
//                new SwipeController(adapter);
//        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
//        touchHelper.attachToRecyclerView(recyclerView);


//        RecyclerView recyclerView = findViewById(R.id.recycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);
//
//
//        final StepRecyclerAdaptor stepRecyclerAdaptor = new StepRecyclerAdaptor();
//        recyclerView.setAdapter(stepRecyclerAdaptor);
//
//        stepViewModel =  new ViewModelProvider(this).get(StepViewModel.class);
//
//        StepBase stepBase = new  StepBase(6, 13, 13, 9);
//
//        stepViewModel.insert(stepBase);
//        stepViewModel.getAllSteps().observe(this, new Observer<List<StepBase>>() {
//            @Override
//            public void onChanged(List<StepBase> stepBases) {
//
//                stepRecyclerAdaptor.submitList(stepBases);
//            }
//        });

        myDialog = new Dialog(getApplicationContext());


        /**
         progress bar section is below
         */
        numSteps = 0;
        progressBar = findViewById(R.id.circularProgressbar);
        mProgress = (ProgressBar) findViewById(R.id.circularProgressbar);
        mProgress.setProgress(0);   // Main Progress
//        mProgress.setSecondaryProgress(100); // Secondary Progress
//        mProgress.setMax(100); // Maximum Progress

        /**
         *
         *  Variables in MainActivity
         * */
        goaleditText = findViewById(R.id.goaledit);
        goaleditText.setCursorVisible(false);
        goal = findViewById(R.id.goal);
        counterkil = findViewById(R.id.counterkil);
        calory = findViewById(R.id.km);
        counterhm = findViewById(R.id.counterhm);
        distance = findViewById(R.id.counterkal);

        relativeLayout = findViewById(R.id.relativelayout);

        floatactionbar = findViewById(R.id.fab);
        //        Resources res = getResources();
        //        Drawable drawable = res.getDrawable(R.drawable.circle);
        //        mProgress.setProgressDrawable(drawable);

      /*  ObjectAnimator animation = ObjectAnimator.ofInt(mProgress, "progress", 0, 100);
        animation.setDuration(50000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();*/


        tv = (TextView) findViewById(R.id.tv);
//        kilo = findViewById(R.id.killotext);


//        fab = findViewById(R.id.fab);
        bottomAppBar = findViewById(R.id.main_bottom_appbar);
        bottomAppBar.replaceMenu(R.menu.bottomappbar_menu);
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.chat:
                        Intent intent = new Intent(MainActivity.this, settings.class);
                        startActivity(intent);
//                        Toast.makeText(getApplicationContext(),"works from main",Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }

            }
        });


        /**
         *   Background Buttons Play and Stop
         * */

        PlaySound = findViewById(R.id.volume_up);
        StopSound = findViewById(R.id.volume_off);

        /**
         *  BackGround sound
         */
        PlaySound.setVisibility(View.GONE);
        StopSound.setVisibility(View.VISIBLE);
        play();


        dialog = new Dialog(this);
/*
        button = findViewById(R.id.addbutton);
        button.bringToFront();
*/


//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "هدیه مجازی به روح الله مناقبی", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });



        /*  the part for step counter
            Get an instance of the SensorManager
        */
        sensorLogic();

//       show();

        floatactionbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                showColorPickerDialog();
//                showAlertDialogButtonClicked(view);
//                show();
//                CustomDialogClass cdd=new CustomDialogClass(MainActivity.this);
//                cdd.show();

//                showNoticeDialog();

//
//                Intent intent = new Intent(getApplicationContext(), DialogAlert.class);
//                startActivityForResult(intent, 1);
//                showPop();

            }
        });

        floatactionbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showColorPickerDialog();
//                showAlertDialogButtonClicked(view);
//                show();

//                CustomDialogClass cdd=new CustomDialogClass(MainActivity.this);
//                cdd.show();

//                showNoticeDialog();


//                showPop();

                Intent intent = new Intent(getApplicationContext(), DialogAlert.class);
                startActivityForResult(intent, 1);


            }
        });

        PlaySound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlaySound.setVisibility(View.GONE);
                StopSound.setVisibility(View.VISIBLE);
                play();

//                runSlidertour();

            }
        });

        StopSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent objIntent = new Intent(getApplicationContext(), SoundService.class);
                stopService(objIntent);
                StopSound.setVisibility(View.GONE);
                PlaySound.setVisibility(View.VISIBLE);
//                Toast.makeText(getApplicationContext(),"works from stopbutton",Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void runSlidertour() {

        Intent intent = new Intent(getApplicationContext(), ScreenSlidePagerActivity.class);
        startActivity(intent);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String returnString = data.getStringExtra("result");
                goaleditText.setText(returnString);
                LayoutInflater layoutInflater = getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.activity_main, null);

                mProgress = findViewById(R.id.circularProgressbar);

                mProgress.setMax(Integer.valueOf(returnString.trim()));

//                mProgress.setMax();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        counter = 0;
        final int LONG_DELAY = 3500; // 3.5 seconds
        final int SHORT_DELAY = 2000; // 2 seconds

//        nestedScrollView.post(runnableUp);

        // inside this method you should finish each animation of view and when it is finished and shows it's toast
        //   Ghanat move to other view and repeat the cycle


    }

    private void playTourGuide() {

        moveVie1(floatactionbar);
//        StopSound.setVisibility(View.VISIBLE);

//        moveVie(progressBar);

//        moveVie1(StopSound);

        Log.e("1", " " + counter + " ");
//        if(counter ==1){
//
//
//        }

    }

    @
            Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        play();

    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        tv.setText(numSteps + "%");
        double setp = getDistanceRun(numSteps);
//        Toast.makeText(getApplicationContext(),"number of meters"+setp,Toast.LENGTH_LONG).show();

        reachGoal();
//        TvSteps.setText(TEXT_NUM_STEPS + numSteps);





   /*
       new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (numSteps < Integer.valueOf(goaleditText.getText().toString())) {
//                    numSteps += 1;

                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            mProgress.setProgress(numSteps);
                            tv.setText(numSteps + "%");

                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        // Just to display the progress slowly
                        Thread.sleep(16); //thread will take approx 3 seconds to finish
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    */


    }


    private void sensorLogic() {

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);
        TvSteps = (TextView) findViewById(R.id.tv);
        BtnStart = (Button) findViewById(R.id.btn_start);
        BtnStop = (Button) findViewById(R.id.btn_stop);
        BtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


                simpleChronometer.setBase(SystemClock.elapsedRealtime());
                simpleChronometer.start();
                mIsRunning = true;


//                Toast.makeText(getApplicationContext(),simpleChronometer.getText().toString(),Toast.LENGTH_SHORT).show();
                calories();
                String character = "0";
                String text = goaleditText.getText().toString();
//                if (text.contains (character)) {
//                    Toast.makeText (MyActivity.this, "character found", Toast.LENGTH_SHORT).show ();
//                }
                if (text.length() == 0) {

                    Toast.makeText(getApplicationContext(), "لطفا تعداد قدم ها را مشخص کیند!", Toast.LENGTH_SHORT).show();
                } else {
                    numSteps = 0;
                    sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
                    BtnStart.setVisibility(View.GONE);
                    BtnStop.setVisibility(View.VISIBLE);
                    asynTaskProgressBar = new AsynTaskProgressBar(Integer.valueOf(goaleditText.getText().toString()), mProgress);
                    asynTaskProgressBar.setProgressBar();
                    asynTaskProgressBar.execute(Integer.valueOf(goaleditText.getText().toString()));
                }
            }
        });
        BtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


                restTime();


                simpleChronometer.stop();
                numSteps = 0;
                tv.setText(numSteps + "");
                sensorManager.unregisterListener(MainActivity.this);
                mIsRunning = false;
                BtnStop.setVisibility(View.GONE);
                BtnStart.setVisibility(View.VISIBLE);
                pb.setProgress(0);
            }
        });

    }


    @Override
    public void onDialogCloseClick(DialogFragment dialog) {

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custome_dialog, null);


        // get the value of the numberpicker
        //   set the value to goaledit text in main activity
        // dismiss dialog box

//        NumberPicker numberPicker = view.findViewById(R.id.numberPicker1);
//        goaleditText.setText(String.valueOf(numberPicker.getValue()));


    }

    @Override
    public void onDialogOkClick(DialogFragment dialog) {

    }


    /*
     *
     *   The nmosteps stop at a speacific number
     *    stop time  and save it's value to constant variable
     *
     * */

    protected boolean reachGoal() {

        if (numSteps == Integer.valueOf(goaleditText.getText().toString())) {
            sensorManager.unregisterListener(MainActivity.this);

            simpleChronometer.stop();

            long calculatedTime = SystemClock.elapsedRealtime() - simpleChronometer.getBase();

            simpleChronometer.setBase(SystemClock.elapsedRealtime() - calculatedTime);

            String time = simpleChronometer.getText().toString();

            simpleChronometer.setBase(SystemClock.elapsedRealtime());

            mIsRunning = false;

            // change the stop button to start butoon
            BtnStop.setVisibility(View.GONE);
            BtnStart.setVisibility(View.VISIBLE);


//            Toast.makeText(getApplicationContext(), time + "", Toast.LENGTH_SHORT).show();


//            restTime();


//            saveTime = simpleChronometer.getText().toString();
//            simpleChronometer.setBase(SystemClock.elapsedRealtime());
            ;

            SharedPreferences sh = getSharedPreferences("setting", 0);
            int weight = sh.getInt("weight", -1);
            int height = sh.getInt("height", -1);

            NumberFormat formatter = new DecimalFormat("#0.0000");

            if (numSteps < 25) {

                formatter.setMaximumFractionDigits(4);

            }
            if (numSteps > 25) {

                formatter.setMaximumFractionDigits(3);

            } else if (numSteps > 259) {   //5190= 0.00
                formatter.setMaximumFractionDigits(2);

            }


            Double res = stepsToKilomter(weight, height, numSteps);


            String calories = formatter.format(res);

//            Toast.makeText(getApplicationContext(), calories + "", Toast.LENGTH_LONG).show();

//            Toast.makeText(getApplicationContext(), "number of distance" + mDistance, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "به تعداد قدم ها رسیدید!", Toast.LENGTH_LONG).show();

            // add currently finished exerciese to database
            addrunAtivity(numSteps, calories, time);
            Intent showResult = new Intent(MainActivity.this, ResultDialog.class);

            startActivity(showResult);

            mediaPlayer = MediaPlayer.create(this, R.raw.finishing);
            mediaPlayer.start();

//             if(ResultDialog.isRunning = true){
//                mediaPlayer.start();
//            }
//            else {
//                mediaPlayer.stop();
//            }
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });


            // here we add a service for playing a sound


            return true;
        } else {

            return false;
        }
    }

    protected void play() {

        SharedPreferences sh = getSharedPreferences("setting", 0);
        int weight = sh.getInt("weight", -1);
        int height = sh.getInt("height", -1);
        int age = sh.getInt("age", -1);


        NumberFormat formatter = new DecimalFormat("#0.000");

        CalorieBurnedCalculator h = new CalorieBurnedCalculator(weight, height, 2000);

//        Double rees =stepsToKilomter(weight,height,2000);
//            String ne = formatter.format(res);
//            res = Math.round(res*100.0)/100.0;
        String strDouble = formatter.format(h.getCaloriesBurned()) + "";
//
//        Toast.makeText(getApplicationContext(), strDouble + "", Toast.LENGTH_LONG).show();

        whichSound = 2;
        SharedPreferences res = getSharedPreferences("save", MODE_PRIVATE);
        SharedPreferences.Editor editor = res.edit();
        editor.putInt("identifier", 2);
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), SoundService.class);
        startService(intent);


    }

    private void removeIdentifier() {
        SharedPreferences sharedPreferences = getSharedPreferences("save", 0);
        sharedPreferences.edit().remove("identifier");
        sharedPreferences.edit().apply();

    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
//            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
//            mWordViewModel.insert(word);
//        } else {
//            Toast.makeText(
//                    getApplicationContext(),
//                    R.string.empty_not_saved,
//                    Toast.LENGTH_LONG).show();
//        }
//    }


    public void addrunAtivity(int steps, String mCalories, String time) {

//        Toast.makeText(this, "works well" + simpleChronometer.getContentDescription(), Toast.LENGTH_SHORT).show();
        Word word = new Word(steps + "", time, mDistance + "", mCalories);
        mWordViewModel.insert(word);
//        saveTime ="0";

    }


    class SwipeController extends ItemTouchHelper.Callback {

        private final ItemTouchHelperAdapter mAdapter;

        public SwipeController(ItemTouchHelperAdapter adapter) {
            mAdapter = adapter;
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                              RecyclerView.ViewHolder target) {
            mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());


            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            Log.d("onswipehabib", "yes works");

            //            mWordViewModel.delete(adapter.getPosition(viewHolder.getAdapterPosition()));


        }
    }


    // add for swiping
    public interface ItemTouchHelperAdapter {

        boolean onItemMove(int fromPosition, int toPosition);

        void onItemDismiss(int position);
    }


    private void setUpRecyclerView() {
        recyclerView.setAdapter(adapter);

//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
        private WordListAdapter mAdapter;

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }


        public SwipeToDeleteCallback(WordListAdapter adapter) {
            super(0, ItemTouchHelper.UP | ItemTouchHelper.END);
            mAdapter = adapter;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
//            mAdapter.deleteItem(position);
        }
    }


    // calories and distance


    public void calories() {
        if (mIsRunning) {
            mCalories +=
                    (mBodyWeight * (mIsRunning ? METRIC_RUNNING_FACTOR : METRIC_WALKING_FACTOR))
                            // Distance:
                            * mStepLength // centimeters
                            / 100000.0; // centimeters/kilometer
        } else {
            mCalories +=
                    (mBodyWeight * (mIsRunning ? IMPERIAL_RUNNING_FACTOR : IMPERIAL_WALKING_FACTOR))
                            // Distance:
                            * mStepLength // inches
                            / 63360.0; // inches/mile
        }


//        notifyListener();


    }


    public void distance(double mStepLength) {
        if (mIsMetric) {
            mDistance += (float) (// kilometers
                    mStepLength // centimeters
                            / 100000.0); // centimeters/kilometer
        } else {
            mDistance += (float) (// miles
                    mStepLength // inches
                            / 63360.0); // inches/mile
            mDistance = mDistance * 0.0254;
        }
//        notifyListener();
    }


    //function to determine the distance run in kilometers using average step length for men and number of steps

//
//    There are
//    many ways
//    to determine
//    step length:
//    you can
//    measure it
//    yourself,
//    estimate by
//    multiplying your
//    height in
//    centimeters by 0.415 for
//    men and 0.413 for
//    women or if you’
//    re not
//    overly concerned
//    with accuracy
//    you can
//    use the
//    averages 78cm for
//    men and 70cm for
//    women.I found
//    these figures
//    using a
//    quick google
//    search and
//    reading up
//    on the
//    first few
//    pages .

    public double getDistanceRun(double steps) {
        mDistance = (steps * 0.762);
        return mDistance;
    }


    private Double stepsToKilomter(double wie, double he, double steps) {


        double weight = wie; // kg

        double height = he; // cm

        double stepsCount = steps;

        //Don't edit below this


        double walkingFactor = 0.57;

        double CaloriesBurnedPerMile;

        double strip;

        double stepCountMile; // step/mile

        double conversationFactor;

        double CaloriesBurned;

        NumberFormat formatter = new DecimalFormat("#0.00");


        double distance;

        String d = "habib";


        CaloriesBurnedPerMile = walkingFactor * (weight * 2.2);

        strip = height * 0.415;

        stepCountMile = 160934.4 / strip;

        conversationFactor = CaloriesBurnedPerMile / stepCountMile;
        Log.d("d1", conversationFactor + "");

        CaloriesBurned = stepsCount * conversationFactor;
        Log.d("d2", CaloriesBurned + "");

//        System.out.println("Calories burned: "
//                +

        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        d = formatter.format(CaloriesBurned);


        distance = (stepsCount * strip) / 100000;
        Log.d("d3", d + "");
//        System.out.println("Distance: " + formatter.format(distance)
//                + " km");


        return CaloriesBurned;

    }


    // runnable for scroll View
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            nestedScrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
    };


    Runnable runnableUp = new Runnable() {
        @Override
        public void run() {
            nestedScrollView.fullScroll(ScrollView.FOCUS_UP);
        }
    };


    // showCase

    private void viewDialogBox() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder1.setView(inflater.inflate(R.layout.view_dialog, null))
                // Add action buttons
                .setPositiveButton("باشه فهمیدم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        nestedScrollView.post(runnable);
                        moveVie1(floatactionbar);
//                        moveVie1(progressBar);

                        dialog.dismiss();

                    }
                });


        builder1.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {


            }


        });


        final AlertDialog alertDialog = builder1.create();
        alertDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (alertDialog.isShowing()) {
//                    moveVie1(progressBar);
                    moveVie1(floatactionbar);

                    alertDialog.dismiss();

                }
            }
        }, 12000); //change 12000 with a specific time you want
    }

    private void viewDialogBox1() {


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.view_dialog1, null))
                // Add action buttons
                .setPositiveButton(R.string.hello_first_fragment, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        moveVie2(BtnStart);


                    }
                });


        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {


            }


        });


        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (alertDialog.isShowing()) {
                    moveVie2(BtnStart);
                    alertDialog.dismiss();
                }

            }
        }, 12000); //ch

    }

    private void viewDialogBox2() {


        AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder2.setView(inflater.inflate(R.layout.view_dialog3, null))
                // Add action buttons
                .setPositiveButton(R.string.hello_first_fragment, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

//                        Toast.makeText(getApplicationContext(), "this is the final ", Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                    }
                });


        builder2.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

                // view 3 is here


            }


        });

        final AlertDialog alertDialog2 = builder2.create();
        alertDialog2.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (alertDialog2.isShowing()) {

//                    view 3 is here


                    alertDialog2.dismiss();
                }

            }
        }, 10000); //change 5000 with a specific time you want


    }

    private void viewDialogBox3() {


        AlertDialog.Builder builder3 = new AlertDialog.Builder(MainActivity.this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder3.setView(inflater.inflate(R.layout.view_dialog4, null))
                // Add action buttons
                .setPositiveButton(R.string.hello_first_fragment, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

//                        Toast.makeText(getApplicationContext(), "اسن سومین نما است ", Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                    }
                });


        builder3.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

                // view 3 is here


            }


        });


        final AlertDialog alertDialog3 = builder3.create();
        alertDialog3.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (alertDialog3.isShowing()) {

//                    view 3 is here

                    alertDialog3.dismiss();
                }
            }
        }, 10000); //change 5000 with a specific time you want

    }

    private void viewDialogBox4() {


        AlertDialog.Builder builder3 = new AlertDialog.Builder(MainActivity.this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder3.setView(inflater.inflate(R.layout.view_dialog5, null))
                // Add action buttons
                .setPositiveButton(R.string.hello_first_fragment, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

//                        Toast.makeText(getApplicationContext(), "اسن سومین نما است ", Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                    }
                });


        builder3.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

                // view 3 is here


            }


        });


        final AlertDialog alertDialog3 = builder3.create();
        alertDialog3.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (alertDialog3.isShowing()) {

//                    view 3 is here

                    alertDialog3.dismiss();
                }
            }
        }, 10000); //change 5000 with a specific time you want

    }

    private void viewDialogBox5() {


        AlertDialog.Builder builder3 = new AlertDialog.Builder(MainActivity.this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder3.setView(inflater.inflate(R.layout.view_dialog6, null))
                // Add action buttons
                .setPositiveButton(R.string.hello_first_fragment, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

//                        Toast.makeText(getApplicationContext(), "اسن سومین نما است ", Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                    }
                });


        builder3.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

                // view 3 is here


            }


        });


        final AlertDialog alertDialog3 = builder3.create();
        alertDialog3.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (alertDialog3.isShowing()) {

//                    view 3 is here

                    alertDialog3.dismiss();
                }
            }
        }, 10000); //change 5000 with a specific time you want

    }

    private void moveVie(View view) {


        Animation animation;
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        animation.setDuration(10000);               // duration in ms
        animation.setRepeatCount(0);                // -1 = infinite repeated
        animation.setRepeatMode(Animation.REVERSE); // reverses each repeat
        animation.setFillAfter(false);
        view.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                viewDialogBox();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                Toast.makeText(getApplicationContext(), "I am over", Toast.LENGTH_LONG).show();


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });




/*
        // Create an animation instance
      an = new RotateAnimation(0.0f, 360, 0, 0);

            // Set the animation's parameters
        an.setDuration(8000);               // duration in ms
        an.setRepeatCount(0);                // -1 = infinite repeated
        an.setRepeatMode(Animation.REVERSE); // reverses each repeat
        an.setFillAfter(true);               // keep rotation after animation

        // Aply animation to image view
        view.setAnimation(an);
        SpringAnimation animation = new SpringAnimation(view, SpringAnimation.X);
        // create a spring with desired parameters
        SpringForce spring = new SpringForce();
        // can also be passed directly in the constructor
        spring.setFinalPosition(100f);
        // optional, default is STIFFNESS_MEDIUM
        spring.setStiffness(SpringForce.STIFFNESS_LOW);
        // optional, default is DAMPING_RATIO_MEDIUM_BOUNCY
        spring.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        // set your animation's spring
        animation.setSpring(spring);
        // animate!
        animation.start();

/

 */
//        an.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        viewDialogBox1();

    }

    private void moveVie1(View view) {


        // Create an animation instance
        Animation an = new RotateAnimation(0.0f, 360.0f, 555, 150);

        // Set the animation's parameters
        an.setDuration(10000);               // duration in ms
        an.setRepeatCount(0);                // -1 = infinite repeated
        an.setRepeatMode(Animation.REVERSE); // reverses each repeat
        an.setFillAfter(false);               // keep rotation after animation


        view.startAnimation(an);

        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                viewDialogBox1();

            }

            @Override
            public void onAnimationEnd(Animation animation) {


//                Toast.makeText(getApplicationContext(),
//                        "I am the second view and I am over",
//                        Toast.LENGTH_LONG)
//                        .show();

                moveVie2(BtnStart);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    private void moveVie2(View view) {

        // Create an animation instance
        Animation an2 = new RotateAnimation(0.0f, 360.0f, 555, 150);


        // Set the animation's parameters
        an2.setDuration(10000);               // duration in ms
        an2.setRepeatCount(0);                // -1 = infinite repeated
        an2.setRepeatMode(Animation.REVERSE); // reverses each repeat
        an2.setFillAfter(false);               // keep rotation after animation

        // Aply animation to image view
        view.setAnimation(an2);

        an2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                viewDialogBox2();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                   Here we stop guideness tour
                moveVie3(progressBar);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


    private void moveVie3(View view) {


        // Create an animation instance
        Animation an2 = new RotateAnimation(0.0f, 360.0f, 555, 150);


        // Set the animation's parameters
        an2.setDuration(10000);               // duration in ms
        an2.setRepeatCount(0);                // -1 = infinite repeated
        an2.setRepeatMode(Animation.REVERSE); // reverses each repeat
        an2.setFillAfter(false);               // keep rotation after animation

        // Aply animation to image view
        view.setAnimation(an2);

        an2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                viewDialogBox3();
            }

            @Override
            public void onAnimationEnd(Animation animation) {

//                nestedScrollView.post(runnable);
                moveVie4(StopSound);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void moveVie4(View view) {


        // Create an animation instance
        Animation an2 = new RotateAnimation(0.0f, 360.0f, 555, 150);


        // Set the animation's parameters
        an2.setDuration(10000);               // duration in ms
        an2.setRepeatCount(0);                // -1 = infinite repeated
        an2.setRepeatMode(Animation.REVERSE); // reverses each repeat
        an2.setFillAfter(false);               // keep rotation after animation

        // Aply animation to image view
        view.setAnimation(an2);

        an2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                viewDialogBox4();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moveVie5(relativeLayout);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void moveVie5(View view) {


        // Create an animation instance
        Animation an2 = new RotateAnimation(0.0f, 360.0f, 555, 150);


        // Set the animation's parameters
        an2.setDuration(10000);               // duration in ms
        an2.setRepeatCount(0);                // -1 = infinite repeated
        an2.setRepeatMode(Animation.REVERSE); // reverses each repeat
        an2.setFillAfter(false);               // keep rotation after animation

        // Aply animation to image view
        view.setAnimation(an2);

        an2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                viewDialogBox5();
            }

            @Override
            public void onAnimationEnd(Animation animation) {


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


    private void startTime() {

        hour = 0;
        startTime = System.currentTimeMillis();
        elapsedTime = System.currentTimeMillis() - startTime;
        elapsedSeconds = elapsedTime / 1000;
        secondsDisplay = elapsedSeconds % 60;
        elapsedMinutes = elapsedSeconds / 60;

        if (elapsedMinutes == 60) {

            hour++;
            elapsedMinutes = 0L;


        }


    }


    private void restTime() {

        hour = 0;
        startTime = 0;
        elapsedTime = 0;
        elapsedSeconds = 0;
        secondsDisplay = 0;
        elapsedMinutes = 0;

    }


}
