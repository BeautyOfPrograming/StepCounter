package com.example.SaberiGhdamShomar;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;


public class CustomDialogClass extends Dialog implements android.view.View.OnClickListener {


    public Activity c;
    public Dialog d;
    public Button ok, no;

    public NumberPicker numberPicker;
    public TextView close,goaledit;


    public CustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_OPTIONS_PANEL);
        setContentView(R.layout.custome_dialog);
        ok = (Button) findViewById(R.id.ok);
        close = (TextView) findViewById(R.id.txtclose);

        LayoutInflater getView = getLayoutInflater();
        View v =getView.inflate(R.layout.activity_main,null);
        goaledit = v.findViewById(R.id.goaledit);

//        numberPicker = (NumberPicker)findViewById(R.id.numberPicker1);
//        numberPicker.setMinValue(1);
//        numberPicker.setMaxValue(100000000);


        ok.setOnClickListener(this);
        close.setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok:

                 goaledit.setText(String.valueOf(numberPicker.getValue()));

                c.finish();
                break;
            case R.id.txtclose:
                goaledit.setText(String.valueOf(numberPicker.getValue()));

                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}

