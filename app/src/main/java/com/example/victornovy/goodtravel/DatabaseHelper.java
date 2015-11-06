package com.example.victornovy.goodtravel;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

/**
 * Created by Victor Novy on 02/11/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE = "goodTravel";
    private static int VERSION = 1;

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE, null, VERSION);
    }
    //TODO page 130
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE TRAVEL (" +
            "_id INTEGER PRIMERY KEY, " +
            "DESTINY TEXT, " +
            "IDTRAVELTYPE INTEGER, " +
            "DTGOOUT DATE, " +
            "DTGOBACK DATE, " +
            "BUDGET DOUBLE, " +
            "MANYPEOPLE INTEGER);"
        );

        db.execSQL("CREATE TABLE SPENT(" +
            "_id INTEGER PRIMARY KEY, " +
            "CATEGORY TEXT, " +
            "DATE DATE, " +
            "VALUE DOUBLE, " +
            "DESCRIPTION TEXT, " +
            "PLACE TEXT, " +
            "IDTRAVEL INTEGER, " +
            "FOREIGN KEY (IDTRAVEL) REFERENCES TRAVEL(_id));"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}
}
