package com.jam3na.fitnessgo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import static android.content.ContentValues.TAG;

public class ActivityDetails extends AppCompatActivity {
    private TextView dateAns,dayAns,goesAns,descAns,worktypeAns,title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        int objPos=1000;
        Bundle bundle = getIntent().getExtras();

         objPos=bundle.getInt("pos");

        if(objPos!= 1000)
        {
            Log.d(TAG, "onCreate: "+objPos);
        }

        SharedPreferences mPrefs = getSharedPreferences("sharedDet",MODE_PRIVATE);
        //read json and update list view
        Gson gson = new Gson();

        String json = mPrefs.getString("ActivityObject", "");
        fitness obj = gson.fromJson(json, fitness.class);
        setupComp();
        LoadData(obj);




    }



    private void LoadData(fitness obj) {
        Log.d(TAG, "LoadData: "+obj);
        title.setText(obj.getWorkType()+" "+getString(R.string.workoutTitle));
        dateAns.setText(obj.getDate());
        dayAns.setText(obj.getDay());
        goesAns.setText(obj.getGoesQu());
        descAns.setText(obj.getWordDesc());
        worktypeAns.setText(obj.getWorkType());

    }

    private void setupComp() {
        dateAns=findViewById(R.id.dateTextView);
        dayAns=findViewById(R.id.dayTextView);
        goesAns=findViewById(R.id.yesNoTextView);
        descAns=findViewById(R.id.desc_ans);
        worktypeAns=findViewById(R.id.whatplayedTextView);
        title=findViewById(R.id.title);
    }


    public void backToMain(View view) {
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        i.putExtra("save","details");
        startActivity(i);
    }
}