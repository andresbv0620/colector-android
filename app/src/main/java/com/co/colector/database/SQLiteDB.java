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
        db.execSQL("CREATE TABLE registros (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, registro TEXT, actualizado TEXT);");
        db.execSQL("CREATE TABLE empresa (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, sistema_id TEXT, descripcion TEXT, db_sistema TEXT);");
        db.execSQL("CREATE TABLE catalogo (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, catalogo_id TEXT, descripcion TEXT, grupo_entrada TEXT);");
        db.execSQL("CREATE TABLE tab (id INTEGER PRIMARY KEY AUTOINCREMENT, tab_id TEXT, catalogo_id TEXT);");
        db.execSQL("CREATE TABLE entrada (id INTEGER PRIMARY KEY AUTOINCREMENT, entrada_id TEXT, tab_id TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS registros");
        db.execSQL("DROP TABLE IF EXISTS empresa");
        db.execSQL("DROP TABLE IF EXISTS catalogo");
        db.execSQL("DROP TABLE IF EXISTS tab");
        db.execSQL("DROP TABLE IF EXISTS entrada");
        onCreate(db);
    }

    public void deleteDatabase(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS registros");
        db.execSQL("DROP TABLE IF EXISTS empresa");
        db.execSQL("DROP TABLE IF EXISTS catalogo");
        db.execSQL("DROP TABLE IF EXISTS tab");
        db.execSQL("DROP TABLE IF EXISTS entrada");
        onCreate(db);
    }
}
