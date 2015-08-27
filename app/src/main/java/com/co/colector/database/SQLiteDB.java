package com.co.colector.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 12/05/2015.
 */
public class SQLiteDB extends SQLiteOpenHelper {

    public static final String NAMEDB = "colector.bd";

    public SQLiteDB(Context context) {
        super(context, NAMEDB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE registros (id  INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, registro TEXT, actualizado TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS registros");
        onCreate(db);
    }

    public void deleteDatabase(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS registros");
        onCreate(db);
    }
}
