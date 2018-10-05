package com.example.dev.feelsbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyFeelingDBOpenHelper extends SQLiteOpenHelper {
    public MyFeelingDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                                 int version) {super(context, "myfeelings.db", null, 1); }
    @Override
    // When first time installing this App on phone, this onCreate() method will be called, this will create table feelings in db

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE feelings(id INTEGER PRIMARY KEY AUTOINCREMENT,type VARCHAR(20),description VARCHAR(20), date VARCHAR(20))");

    }

    @Override
    //When the version of db is changed, the onUpgrade() method will be called, for now , it is not needed
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
