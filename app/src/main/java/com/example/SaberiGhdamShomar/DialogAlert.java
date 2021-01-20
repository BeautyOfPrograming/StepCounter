package com.example.SaberiGhdamShomar;

import androidx.core.content.res.ResourcesCompat;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DialogAlert extends Activity {


    private TextView close;
    private Button ok;
    private EditText opt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));


        close = findViewById(R.id.txtclose);
        ok = findViewById(R.id.ok);
        opt = findViewById(R.id.setEd);
        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.vazir);
        opt.setTypeface(typeface);
        setStep();
    }

    private void setStep() {

        final Intent returnedInent = new Intent();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (opt.getText().length()==0) {
                    opt.setText("0");
                    returnedInent.putExtra("result", opt.getText().toString().trim());
                    setResult(MainActivity.RESULT_OK, returnedInent);

                    finish();
                } else {
                    returnedInent.putExtra("result", opt.getText().toString().trim());
                    setResult(MainActivity.RESULT_OK, returnedInent);
                    finish();
                }

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }




}
