package com.co.colector.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by User on 26/08/2015.
 */
public class registros {

    public static final String TABLE_NAME = "registros";
    public static final String NOMBRE = "nombre";
    public static final String REGISTRO = "registro";
    public static final String ACTUALIZADO = "actualizado";
    private ContentValues cv;

    public registros(){

    }

    public void insert(SQLiteDatabase db, String nombre, String registro){
        cv = new ContentValues();
        cv.put(NOMBRE, nombre);
        cv.put(REGISTRO, registro);
        cv.put(ACTUALIZADO,"0");
        db.insert(TABLE_NAME, "registros", cv);
    }

    public Cursor consulta(SQLiteDatabase db){
        return db.rawQuery("select * from registros", null);
    }

    public void delete(SQLiteDatabase db){ db.delete(TABLE_NAME,null,null); }
}
