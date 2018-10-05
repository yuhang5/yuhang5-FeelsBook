package com.example.dev.feelsbook;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OverallStatusActivity extends AppCompatActivity implements View.OnClickListener {

    private MyFeelingDBOpenHelper myDBHelper;
    private SQLiteDatabase db;
    private Context mContext;
    @Override
    // While loading OverallStatusActivity, the method calculatorOverallStatus() will be called, this will calculate counts for each emotion and
    // put the count number in TextView.
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overall_status);
        mContext = OverallStatusActivity.this;
        myDBHelper = new MyFeelingDBOpenHelper(mContext, "myfeelings.db", null, 1);
        db = myDBHelper.getReadableDatabase();
        calculatorOverallStatus();
        Button btn_back = findViewById(R.id.btn_back_2);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showEmojiActivity = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(showEmojiActivity);
            }
        });




    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    // This method is used to get data from db and calculate counts for each emotion and then put the value in TextView.
    public void calculatorOverallStatus(){

        Cursor cAngry = db.query("feelings",new String[]{"id","type","description","date"},"type=?",new String[]{"Angry"},null,null,null);
        TextView txt_angry = findViewById(R.id.txt_angry_count);
        System.out.println("cursor : " + cAngry);
        System.out.println("cursor size : " + cAngry.getCount());
        int count = cAngry.getCount();
        System.out.println("txt_anry :" + txt_angry);
        txt_angry.setText(String.valueOf(count));

        Cursor cFear = db.query("feelings",null,"type=?",new String[]{"Fear"},null,null,null);
        TextView txt_fear = findViewById(R.id.txt_fear_count);
        txt_fear.setText(String.valueOf(cFear.getCount()));

        Cursor cJoy = db.query("feelings",null,"type=?",new String[]{"Joy"},null,null,null);
        TextView txt_joy = findViewById(R.id.txt_joy_count);
        txt_joy.setText(String.valueOf(cJoy.getCount()));

        Cursor cLove = db.query("feelings",null,"type=?",new String[]{"Love"},null,null,null);
        TextView txt_love = findViewById(R.id.txt_love_count);
        txt_love.setText(String.valueOf(cLove.getCount()));

        Cursor cSad = db.query("feelings",null,"type=?",new String[]{"Sadness"},null,null,null);
        TextView txt_sad = findViewById(R.id.txt_sad_count);
        txt_sad.setText(String.valueOf(cSad.getCount()));

        Cursor cSuprise = db.query("feelings",null,"type=?",new String[]{"Suprise"},null,null,null);
        TextView txt_suprise = findViewById(R.id.txt_suprise_count);
        txt_suprise.setText(String.valueOf(cSuprise.getCount()));
    }
}
