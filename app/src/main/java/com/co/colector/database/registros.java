package com.co.colector.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by User on 26/08/2015.
 */
public class registros {

    public static final String TABLE_NAME = "registros";
    public static final String ID = "id";
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

    public void updateActualizado(SQLiteDatabase db, String id){

        ContentValues values = new ContentValues();
        values.put(ACTUALIZADO, "1");
        values.put(REGISTRO,String.valueOf(System.currentTimeMillis()));

        db.update(TABLE_NAME,
                values,
                ID+" = ?",
                new String[] { id });

        db.close();

    }

    public void delete(SQLiteDatabase db){ db.delete(TABLE_NAME,null,null); }
}
