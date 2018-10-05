package com.example.dev.feelsbook;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener{
    //use DBHelper and SqLite to store Data.
    private MyFeelingDBOpenHelper myDBHelper;
    private SQLiteDatabase db;
    private Context mContext;
    @Override

    // While loading HistoryActivity, refreshHistoryTable() will be invoked, this method is used to get all data and put it into TableLayout
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mContext = HistoryActivity.this;
        myDBHelper = new MyFeelingDBOpenHelper(mContext, "myfeelings.db", null, 1);

        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showEmojiActivity = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(showEmojiActivity);
            }
        });

        refreshHistoryTable();


    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    //Cursor is a collection od data which from Database. When cursor.moveToFirst=true our collection is not empty.
    //Then manage every row's data in collection. We take every value of column and put it to the corresponding textViews
    //Make a TableRow for every row's data and put evey TableRow in our table.
    public void refreshHistoryTable(){
        final TableLayout table = findViewById(R.id.table_history);
        table.removeAllViews();
        db = myDBHelper.getReadableDatabase();
        Cursor cursor = db.query("feelings",new String[]{"id","type","description","date"},null,null,null,null,"date DESC");

        // Move cursor in SQLiteDatabase. Limit the numbers of words.
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String  type = cursor.getString(cursor.getColumnIndex("type"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                System.out.println("current id " + id);
                System.out.println("type : " + type);
                TableRow row = new TableRow(this);
                TextView typeView = new TextView(this);
                EditText desView = new EditText(this);
                TextView dateView = new TextView(this);
                desView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
                Button btnDelete = new Button(this);
                row.setContentDescription(String.valueOf(id));
                desView.setLayoutParams(new TableRow.LayoutParams(50 , TableRow.LayoutParams.WRAP_CONTENT, 1));
                typeView.setText(type);
                desView.setText(description);
                dateView.setText(date);
                btnDelete.setText("Delete");

                //when this EditText'status changes from onFucus to !onFocus, it will save this emotion with changed comment.
                desView.setOnFocusChangeListener(new View.OnFocusChangeListener()
                {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus)
                    {
                        if (!hasFocus){
                            TableRow tableRow = (TableRow) v.getParent();

                            String idString = tableRow.getContentDescription().toString();
                            EditText desc  = (EditText) tableRow.getChildAt(1);
                            ContentValues cv = new ContentValues();
                            cv.put("description",desc.getText().toString());
                            db = myDBHelper.getWritableDatabase();
                            String[] args = {String.valueOf(idString)};
                            db.update("feelings",cv,"id=?",args);
                        }

                    }
                });
                //When I click and use Enter, my description will save in myDBHelper
                desView.setOnKeyListener(new View.OnKeyListener() {
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                (keyCode == KeyEvent.KEYCODE_ENTER)) {

                                 TableRow tableRow = (TableRow) v.getParent();

                            String idString = tableRow.getContentDescription().toString();
                            EditText desc  = (EditText) tableRow.getChildAt(1);
                            ContentValues cv = new ContentValues();
                            cv.put("description",desc.getText().toString());
                            db = myDBHelper.getWritableDatabase();
                            String[] args = {String.valueOf(idString)};
                            db.update("feelings",cv,"id=?",args);

                            return true;
                        }
                        return false;
                    }
                });

                // From DeleteButton to delete data
                btnDelete.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        TableRow tableRow = (TableRow) v.getParent();
                        table.removeView(tableRow);
                        String idString = tableRow.getContentDescription().toString();

                        db = myDBHelper.getWritableDatabase();
                        String[] args = {String.valueOf(idString)};
                        db.delete("feelings","id=?",args);

                    }
                });
                //
                row.addView(typeView,0);
                row.addView(desView,1);
                row.addView(dateView,2);
                row.addView(btnDelete,3);

                table.addView(row);

            } while (cursor.moveToNext());
        }
        cursor.close();



    }
}
