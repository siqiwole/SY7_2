package com.example.sy9_2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(this, "Persons.db", null, 2);
        Button createDatabase = (Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });
        Button addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put("name", "zhangsan");
                values.put("sex", "Boy");
                values.put("phone", "1394704123");

                db.insert("Person", null, values);
                values.clear();
                // 开始组装第二条数据
                values.put("name", "lisi");
                values.put("sex", "Girl");
                values.put("phone", "1836492038");

                db.insert("Person", null, values);
            }
        });


        Button queryButton = (Button) findViewById(R.id.query_data);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                Cursor cursor = db.query("Person", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {

                        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                        @SuppressLint("Range") String sex = cursor.getString(cursor.getColumnIndex("sex"));
                        @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));

                        Log.d("MainActivity", "Person name is " + name);
                        Log.d("MainActivity", "Person sex is " + sex);
                        Log.d("MainActivity", "Person phone is " + phone);

                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }

}