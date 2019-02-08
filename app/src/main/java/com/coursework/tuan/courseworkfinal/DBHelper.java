package com.coursework.tuan.courseworkfinal;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper{

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE activities(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                    "name VARCHAR(128) NOT NULL," +
                    "time REAL NOT NULL," +
                    "metres VARCHAR(128) NOT NULL," +
                    "speed VARCHAR(128) NOT NULL," +
                    "aDate TEXT NOT NULL)";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL("INSERT INTO activities(name,time,metres,speed,aDate) VALUES ('Friday Night Running',1500,'200','6.23',datetime('now','-1 days'))");
        db.execSQL("INSERT INTO activities(name,time,metres,speed,aDate) VALUES ('Sunday Morning Running',3600,'150','10.52',datetime('now', '-6 days'))");
        db.execSQL("INSERT INTO activities(name,time,metres,speed,aDate) VALUES ('Thursday Noon Running',5400,'300','15.5',datetime('now', 'start of month'))");
        db.execSQL("INSERT INTO activities(name,time,metres,speed,aDate) VALUES ('Friday Night Running',9000,'600','25.5',datetime('now', '-1 years'))");
        db.execSQL("INSERT INTO activities(name,time,metres,speed,aDate) VALUES ('Tuesday Morning Running',3000,'100','12.52',datetime('now', '-10 days'))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS activities");
        onCreate(db);
    }
}
