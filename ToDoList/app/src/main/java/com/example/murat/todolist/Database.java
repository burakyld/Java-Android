package com.example.murat.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Kullanıcılar";
    private static final String DATABASE_TABLE = "Kisi";
    private static final int DATABASE_VERSION = 1;

    Context ctx;
    SQLiteDatabase myDb;


    public Database(Context ct) {
        super(ct, DATABASE_NAME,null,DATABASE_VERSION);
        ctx = ct;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+DATABASE_TABLE+" (_id INTEGER PRIMARY KEY AUTOINCREMENT,aktivite TEXT,saat TEXT,sure TEXT);");
        Log.i("Database","Table Created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE If EXISTS "+DATABASE_TABLE);
        onCreate(db);

    }


    public boolean insertData(String aktivite,String saat,String sure){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("aktivite",aktivite);
        contentValues.put("saat",saat);
        contentValues.put("sure",sure);
        long result = db.insert(DATABASE_TABLE,null,contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public Cursor getAllData(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res  = db.rawQuery("select * from "+DATABASE_TABLE,null);
        return res;
    }
    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DATABASE_TABLE," _id = ? ",new String[] {id});
    }
}

