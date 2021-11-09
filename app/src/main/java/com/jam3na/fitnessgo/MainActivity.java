package com.jam3na.fitnessgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    private ListView listV;
    static ArrayList<fitness> savedActivity=new ArrayList<>();
    static ArrayList<String> savedTitle=new ArrayList<String>();
    public Thread addThread = new Thread(new Runnable() {
        @Override
        public void run() {
            Intent i = new Intent(getApplicationContext(),todayFit.class);
            startActivity(i);
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupComp();
        listV=(ListView)findViewById(R.id.list_activites);
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object listItem = listV.getItemAtPosition(position);
                Log.d(TAG, "onItemClick: "+position);
                openActivityDet(position);
            }
        

        });
        Log.d(TAG, "onCreate: Start");
        Bundle bundle = getIntent().getExtras();

        if (bundle!=null) {
            String flag = bundle.getString("save");
            if (flag.equals("saveClick")) {
                UpdateListView();
            }else if (flag.equals("details")){
                Log.d(TAG, "onStart flag: "+flag);
                LoadList(savedTitle);
            }
        }
        //
//        savedActivity.clear();
//        savedTitle.clear();
//        UpdateListView();
           }

    private void setupComp() {
        listV=findViewById(R.id.list_activites);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mF= getMenuInflater();
        mF.inflate(R.menu.fmenu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        addThread.run();

        return true;
    }



    private void UpdateListView() {
        SharedPreferences  mPrefs = getSharedPreferences("sharedPref",MODE_PRIVATE);
        //read json and update list view
        Gson gson = new Gson();

        String json = mPrefs.getString("MyObject", "");
        fitness obj = gson.fromJson(json, fitness.class);
        Log.d(TAG, "UpdateListView:"+savedTitle);
        if ((obj!=null)) {
            String activityTitle = obj.getDate() + " " + obj.getDay() + " " + obj.getWorkType();
            savedActivity.add(obj);
            savedTitle.add(activityTitle);

//            Toast.makeText(this, "toast heelo", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "UpdateListView: Befor load"+savedTitle);
            LoadList(savedTitle);
        }


    }

    private void LoadList(ArrayList<String> savedTitle) {
    ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,savedTitle);
    listV.setAdapter(arrayAdapter);



    }
    private void openActivityDet(int position) {
        fitness activityObj= savedActivity.get(position);
//
        Intent i = new Intent(getApplicationContext(),ActivityDetails.class);
        i.putExtra("pos",position);
       // i.putExtra("obj",activityObj);
        SharedPreferences  mPrefs = getSharedPreferences("sharedDet",MODE_PRIVATE);

        //read json and update list view
        Gson gson = new Gson();
        //set variables of 'myObject'.
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        String json = gson.toJson(activityObj);
        prefsEditor.putString("ActivityObject", json);
        prefsEditor.commit();
        startActivity(i);
    }
}