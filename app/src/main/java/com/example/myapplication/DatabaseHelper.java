package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.Nullable;

import java.net.URI;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String databasename = "studentdb";
    public static final String tblstudent = "student";




    public DatabaseHelper( Context context) {
        super(context,databasename,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+tblstudent+"(id integer primary key autoincrement,studentimage varchar(50),studentname varchar(50),studentcourse varchar(25))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
       // onCreate(db);
    }

    public long addStudent(MyItem student){
        long ok = 1;
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("studentimage",student.getUriImage().toString());
            cv.put("studentname",student.getImgname());
            cv.put("studentcourse",student.getCourseName());
            ok = db.insert(tblstudent,null,cv);
            db.close();
            return ok;
    }

    public int removeStudent(int id){
        int result  = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        result = db.delete(tblstudent,"id=?",new String[]{id+""});
        db.close();
        return result;
    }

    public ArrayList<MyItem> getAllStudent(){
        ArrayList<MyItem> list = new ArrayList<MyItem>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tblstudent,null,null,null,null,null,"studentname");
        cursor.moveToFirst();
         while(!cursor.isAfterLast()){
             // not sure 26:00
             int id =  cursor.getInt(cursor.getColumnIndexOrThrow("id"));
             String uriimage = cursor.getString(cursor.getColumnIndexOrThrow("studentimage"));
             String name = cursor.getString(cursor.getColumnIndexOrThrow("studentname"));
             String course = cursor.getString(cursor.getColumnIndexOrThrow("studentcourse"));
             list.add(new MyItem(name,Uri.parse(uriimage),course,id));
             cursor.moveToNext();
         }
        cursor.moveToLast();
        db.close();
        cursor.close();
        return list;
    }
}
