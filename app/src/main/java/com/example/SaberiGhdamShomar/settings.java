package com.example.SaberiGhdamShomar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.SaberiGhdamShomar.R;
//import com.getkeepsafe.taptargetview.TapTarget;
//import com.getkeepsafe.taptargetview.TapTargetView;

import java.util.ArrayList;
import java.util.List;


public class settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button save;
    TextView exit;

    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setUp();


    }


    private void setUp() {

        saveButton();
        spinnerSetUp();


    }

    private void spinnerSetUp() {


        // get Spinner reference
        Spinner spinner = (Spinner) findViewById(R.id.settingspinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List gender = new ArrayList();
        gender.add("مرد");
        gender.add("زن");


        // Creating array adapter for spinner
        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gender);

        // Drop down style will be listview with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView parent, View view, int position, long id) {
        // getting selected item
        String item = parent.getItemAtPosition(position).toString();
        gender = item;
        // Showing selected spinner item in toast
        Toast.makeText(parent.getContext(), "جنسیت شما" + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private void saveButton() {

        exit = findViewById(R.id.settingtxtclose);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }

        });
        save=findViewById(R.id.saveB);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText hieght, weight, age, bodyOil;
                hieght = findViewById(R.id.heightid);
                weight = findViewById(R.id.wieghtid);
                age = findViewById(R.id.agetid);
//                bodyOil = findViewById(R.id.setEd);

                SharedPreferences appdettings = getSharedPreferences("setting", MODE_PRIVATE);


                if (hieght.length() != 0 && weight.length() != 0 && age.length() != 0) {  //&& bodyOil.length() != 0
                    Toast.makeText(getApplicationContext(), "works" + gender, Toast.LENGTH_LONG).show();

                    SharedPreferences.Editor editor = appdettings.edit();
                    editor.putInt("height", Integer.valueOf(hieght.getText().toString()));
                    editor.putInt("weight", Integer.valueOf(weight.getText().toString()));
                    editor.putInt("age", Integer.valueOf(age.getText().toString()));
//                editor.putInt("bodyoil", Integer.valueOf(bodyOil.getText().toString()));
                    editor.putString("gender", gender);
                    editor.commit();
                    editor.apply();

                    startActivity(new Intent(settings.this, MainActivity.class));
                    finish();

                } else
                    Toast.makeText(getApplicationContext(), "لطفا تمامی فیلد ها را پر کنید! ", Toast.LENGTH_LONG).show();

            }
        });
    }
}