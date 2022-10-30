package com.example.sy9_2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseProvider extends ContentProvider {
    public static final int PERSON_DIR = 0;

    public static final int PERSON_ITEM = 1;
    public static final String AUTHORITY = "com.example.sy9_2.provider";

    private static UriMatcher uriMatcher;

    private MyDatabaseHelper dbHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "person", PERSON_DIR);
        uriMatcher.addURI(AUTHORITY, "person/#", PERSON_ITEM);

    }

    @Override
    public boolean onCreate() {
        dbHelper = new MyDatabaseHelper(getContext(), "Persons.db", null, 2);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // 查询数据
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case PERSON_DIR:
                cursor = db.query("Person", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PERSON_ITEM:
                String personId = uri.getPathSegments().get(1);
                cursor = db.query("Person", projection, "id = ?", new String[] { personId }, null, null, sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // 添加数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)) {
            case PERSON_DIR:
            case PERSON_ITEM:
                long newBookId = db.insert("Person", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/person/" + newBookId);
                break;
            default:
                break;
        }
        return uriReturn;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }



    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case PERSON_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.sy9_2. provider.person";
            case PERSON_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.sy9_2. provider.person";
        }
        return null;
    }

}