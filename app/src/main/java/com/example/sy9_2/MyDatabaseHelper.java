package com.example.sy9_2;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
public class MyDatabaseHelper  extends SQLiteOpenHelper {

    public static final String CREATE_PERSON = "create table Person ("
            + "id integer primary key autoincrement, "
            + "sex text, "
            + "phone text, "
            + "name text)";



    private Context mContext;

    public MyDatabaseHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PERSON);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Person");
//        db.execSQL("drop table if exists Category");
        onCreate(db);
    }

}