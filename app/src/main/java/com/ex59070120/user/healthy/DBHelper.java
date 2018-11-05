package com.ex59070120.user.healthy;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.ex59070120.user.healthy.Sleep.Sleep;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sql;
    public DBHelper(Context context) {
        super(context, "healthy.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS sleep ("+
                                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                                "date VARCHAR(6), time_sleep VARCHAR(6), time_wakeup VARCHAR(6));";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS sleep";
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addSleep(Sleep sleep) {
        sql = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date",sleep.getDate());
        values.put("time_sleep",sleep.getTime_sleep());
        values.put("time_wakeup",sleep.getTime_wakeup());
        sql.insert("sleep", null, values);
        sql.close();
    }


    public List<Sleep> getSleepList(){
        List<Sleep> sleeps = new ArrayList<>();
        Sleep sleep;
        sql = this.getWritableDatabase();
        Cursor cursor = sql.query("sleep", null, null,null,null,null,"date DESC", null);
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
        sql.close();
        return sleeps;
    }

    public void updateSleep(Sleep sleep){
        sql = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", sleep.getDate());
        values.put("time_sleep", sleep.getTime_sleep());
        values.put("time_wakeup", sleep.getTime_wakeup());
        sql.update("sleep",
                values,
                "id = ? ",
                new String[] { String.valueOf(sleep.getID()) });
        sql.close();
    }
}

