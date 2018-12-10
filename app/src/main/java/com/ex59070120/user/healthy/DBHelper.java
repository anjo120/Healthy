package com.ex59070120.user.healthy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ex59070120.user.healthy.Sleep.Sleep;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;

    public DBHelper(Context context) {
        super(context, "healthy_sleep.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS sleep (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date VARCHAR(6), sleep_time VARCHAR(6), wakeup_time VARCHAR(6));";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS sleep";
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addSleep(Sleep sleep){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", sleep.getDate());
        values.put("sleep_time", sleep.getTime_sleep());
        values.put("wakeup_time", sleep.getTime_wakeup());
        sqLiteDatabase.insert("sleep", null, values);
        sqLiteDatabase.close();
    }

    public ArrayList<Sleep> getSleepList(){
        ArrayList<Sleep> sleeps = new ArrayList<>();
        Sleep sleep;
        sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("sleep", null, null,null,null,null,"date DESC", null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()){
            sleep = new Sleep();
            sleep.setDate(cursor.getString(1));
            sleep.setTime_sleep(cursor.getString(2));
            sleep.setTime_wakeup(cursor.getString(3));
            sleeps.add(sleep);
            cursor.moveToNext();
        }
        sqLiteDatabase.close();
        return sleeps;
    }

    public void updateSleep(Sleep sleep) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", sleep.getDate());
        values.put("sleep_time", sleep.getTime_sleep());
        values.put("wakeup_time", sleep.getTime_wakeup());
        sqLiteDatabase.update("sleep",
                values,
                "id = ? ",
                new String[] { String.valueOf(sleep.getId()) });
        sqLiteDatabase.close();
    }
}