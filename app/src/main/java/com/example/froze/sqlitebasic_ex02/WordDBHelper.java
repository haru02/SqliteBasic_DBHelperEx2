package com.example.froze.sqlitebasic_ex02;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by froze on 2016-10-16.
 */


class WordDBHelper extends SQLiteOpenHelper {

    String name;
    int version;

    public WordDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String scheme = "create table bbs6(no integer primary key autoincrement not null"
                + ",title text not null, name text not null, ndate not null default current_timestamp)";
        db.execSQL(scheme);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists bbs6");
        onCreate(db);
    }
}
