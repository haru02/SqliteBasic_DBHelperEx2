package com.example.froze.sqlitebasic_ex02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String DB_NAME = "SQLite.db";
    public static final int DB_VERSION = 1;

    WordDBHelper mHelper;
    EditText mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new WordDBHelper(this, DB_NAME, null, DB_VERSION);
        mText = (EditText)findViewById(R.id.edittext);
    }

    public void mOnClick(View v){
        SQLiteDatabase db;
        ContentValues row;
        switch(v.getId()){
            case R.id.insert :
                db = mHelper.getWritableDatabase();
                row = new ContentValues();
                row.put("eng", "apple");
                row.put("han", "사과");
                db.insert("dic", null, row);
                db.execSQL("insert into dic values (null, 'grape', '포도')");
                mHelper.close();;
                mText.setText("Insert Success");
                break;
            case R.id.delete :
                db = mHelper.getWritableDatabase();
                db.delete("dic", null, null);
                mHelper.close();
                mText.setText("Delete Success");
                break;
            case R.id.update :
                db = mHelper.getWritableDatabase();
                row = new ContentValues();
                row.put("han", "애플");
                db.update("dic", row, "eng='apple'", null);
                mHelper.close();
                mText.setText("Update Sucess");
                break;
            case R.id.select :
                db = mHelper.getReadableDatabase();
                Cursor cursor;
                cursor = db.rawQuery("select eng, han from dic", null);

                String result = "";
                while(cursor.moveToNext()){
                    String eng = cursor.getString(0);
                    String han = cursor.getString(1);
                    result += (eng + " = " + han + "\n");
                }

                if(result.length() == 0){
                    mText.setText("Empty set");
                }else{
                    mText.setText(result);
                }

                cursor.close();
                mHelper.close();
                break;
        }
    }
}



class WordDBHelper extends SQLiteOpenHelper{

    String name;
    int version;

    public WordDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
        this.name = name;
        this.version = version;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table dic ( _id integer primary key autoincrement, eng text, han text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists dic");
        onCreate(db);
    }
}
