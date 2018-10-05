   /* License
    *Copyright (c) 2018 Ma Yuhang, yuhang5@ualberta.ca
    *Permission is hereby granted, this program is free software: you can use, copy and modify it
    *When you copy substantial portions of the Software, you should include the above copyright notice and this permission notice
    *This Software is just for help studying, it is not allowed to trade in any condtion for any person.

    */

package com.example.dev.feelsbook;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Context mContext;
    private MyFeelingDBOpenHelper myDBHelper;
    private SQLiteDatabase db;
    @Override

    //Make 6 TextViews and let user can click them
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        myDBHelper = new MyFeelingDBOpenHelper(mContext, "myfeelings.db", null, 1);
        db = myDBHelper.getWritableDatabase();
        TextView angry = findViewById(R.id.txt_angry);
        angry.setClickable(true);
        angry.setOnClickListener(this);
        TextView fear = findViewById(R.id.txt_fear);
        fear.setClickable(true);
        fear.setOnClickListener(this);
        TextView joy = findViewById(R.id.txt_joy);
        joy.setClickable(true);
        joy.setOnClickListener(this);
        TextView love = findViewById(R.id.txt_love);
        love.setClickable(true);
        love.setOnClickListener(this);
        TextView sadness = findViewById(R.id.txt_sadness);
        sadness.setClickable(true);
        sadness.setOnClickListener(this);
        TextView suprise = findViewById(R.id.txt_suprise);
        suprise.setClickable(true);
        suprise.setOnClickListener(this);


        // get a button to connect the HistoryActivity, use Intent to send data for HistoryActivity.
        Button history = findViewById(R.id.btn_viewHistory);

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showHistoryActivity = new Intent(getApplicationContext(), HistoryActivity.class);

                startActivity(showHistoryActivity);
            }
        });

        //similarity, send data to OverallActivity to get the counts of all emoji.
        Button overall = findViewById(R.id.btn_viewOverall);
        overall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showOverallActivity = new Intent(getApplicationContext(), OverallStatusActivity.class);

                startActivity(showOverallActivity);
            }
        });
    }


    @Override

    // implement onClick method, every time, user click on one TextView, this method will be invoked
    // This will save the clicked emotion into db and go to HistoryActivity.
    public void onClick(View v) {
        TextView tv = (TextView) v;
        String mood = tv.getText().toString();
        ContentValues values1 = new ContentValues();
        values1.put("type",mood);
        values1.put("description","");
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        String dateString = formatter.format(date);
        values1.put("date",dateString);
        db.insert("feelings", null, values1);

        Intent showHistoryActivity = new Intent(getApplicationContext(), HistoryActivity.class);

        startActivity(showHistoryActivity);



    }
    
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
