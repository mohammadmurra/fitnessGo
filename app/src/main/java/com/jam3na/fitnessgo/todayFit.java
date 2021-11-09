package com.jam3na.fitnessgo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;

import static android.content.ContentValues.TAG;

public class todayFit extends AppCompatActivity {

    private TextView dayTextView;
    private Spinner workout_spinner;
    private TextView date_lable;
    private RadioButton work_no_radio;
    private RadioButton work_yes_radio;
    private RadioGroup radio_G;
    private EditText desc_text;
    private Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_fit);


        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        setUpcomp();



    }

    private void setUpcomp() {
        dayTextView=findViewById(R.id.day_TextView);
        date_lable=findViewById(R.id.fitDate);
        workout_spinner=findViewById(R.id.WhatPlayed_spinner);
        desc_text=findViewById(R.id.descText);
        saveButton=findViewById(R.id.save_Button);
        work_no_radio=findViewById(R.id.no_radio);
        work_yes_radio=findViewById(R.id.yes_radio);
        radio_G=findViewById(R.id.radio_group);
        Date currentTime = Calendar.getInstance().getTime();
        date_lable.setText("   " +currentTime.getDate()+"/"+currentTime.getMonth()+"/"+currentTime.getYear());
        String[] day=currentTime.toString().split(" ");
        dayTextView.setText("  "+day[0]);
        ArrayAdapter<String> spinnerWorkoutArrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.Workout));
        workout_spinner.setAdapter(spinnerWorkoutArrayAdapter);
        desc_text.setInputType( InputType.TYPE_TEXT_VARIATION_URI ); // optional - sets the keyboard to URL mode

// kill keyboard when enter is pressed
        desc_text.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View arg0, int arg1, KeyEvent event) {
                // If the event is a key down event on the "enter" button
                if ( (event.getAction() == KeyEvent.ACTION_DOWN  ) &&
                        (arg1== KeyEvent.KEYCODE_ENTER)   )
                {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(desc_text.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        } );

    }

    public void save_process(View view) {
        String day=dayTextView.getText().toString();
        String date=date_lable.getText().toString();
        String workoutType=workout_spinner.getSelectedItem().toString();
        String workout_desc=desc_text.getText().toString();
        String goes_workout =
                ((RadioButton)findViewById(radio_G.getCheckedRadioButtonId()))
                        .getText().toString();

         fitness FitClass=new fitness(day,date,workoutType,workout_desc,goes_workout);

        if(day.equals("") || date.equals("") || workoutType.equals("") || workout_desc.equals("") || goes_workout.equals("") ){
            Toast.makeText(this, "Please Fill Your activity details", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "save_process: checked");
        }else {
            UploadToSharedPref(FitClass);

        }




    }

    private void UploadToSharedPref(fitness FitClass) {
        SharedPreferences  mPrefs = getSharedPreferences("sharedPref",MODE_PRIVATE);

        //read json and update list view
        Gson gson = new Gson();
        //set variables of 'myObject'.
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        String Writejson = gson.toJson(FitClass);
        prefsEditor.putString("MyObject", Writejson);
        prefsEditor.commit();
        NotfiAndClose();
    }

    private void NotfiAndClose() {
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
       i.putExtra("save","saveClick");
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent i = new Intent(getApplicationContext(),ActivityDetails.class);
        i.putExtra("activity","todatFit");
    }

}