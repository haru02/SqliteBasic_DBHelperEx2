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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String DB_NAME = "SQLite.db";
    public static final int DB_VERSION = 1;

    WordDBHelper mHelper;
    EditText mText;
    MyData myData;
    ArrayList<MyData> myDataArrayList;

    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new WordDBHelper(this, DB_NAME, null, DB_VERSION);
        mText = (EditText)findViewById(R.id.edittext);
    }

    public void mOnClick(View v){
        SQLiteDatabase db;
        myData = new MyData();
        myDataArrayList = new ArrayList<>();

        switch(v.getId()){
            case R.id.insert :
                db = mHelper.getWritableDatabase();
                myData.title = "제목"+i;
                myData.name = "이름" +i;
                myDataArrayList.add(myData);
                db.execSQL("insert into bbs6(title,name) values ('"+myData.title+"','"+myData.name+"')");
                //db.execSQL("insert into bbs6(title,name) values ('"+myData.title+"','"+myData.name+"')");
                i++;
                mHelper.close();;
                mText.setText("Insert Success");
                break;
            case R.id.delete :
                db = mHelper.getWritableDatabase();
                db.delete("bbs6", null, null);
                mHelper.close();
                mText.setText("Delete Success");
                break;
            case R.id.update :
                db = mHelper.getWritableDatabase();
                db.execSQL("update bbs6 set name='이름0',title='수정제목' where name='이름0'");
                mHelper.close();
                mText.setText("Update Sucess");
                break;
            case R.id.select :
                db = mHelper.getReadableDatabase();
                Cursor cursor;
                cursor = db.rawQuery("select title, name, ndate from bbs6", null);

                String result = "";
                while(cursor.moveToNext()){
                    String title = cursor.getString(0);
                    String name = cursor.getString(1);
                    String ndate = cursor.getString(2);
                    result += (title + " = " + name + " " + ndate + "\n");
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


