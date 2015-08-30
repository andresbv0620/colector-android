package com.co.colector.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by User on 30/08/2015.
 */
public class catalogo {

    public static final String TABLE_NAME = "catalogo";
    public static final String ID = "id";
    public static final String NOMBRE = "nombre";
    public static final String CATALOGO_ID = "catalogo_id";
    public static final String DESCRIPCION = "descripcion";
    public static final String GRUPO_ENTRADA = "grupo_entrada";

    private ContentValues cv;

    public catalogo(){

    }

    public void insert(SQLiteDatabase db, String nombre, String catalogo_id, String descripcion, String grupo_entrada){
        cv = new ContentValues();
        cv.put(NOMBRE, nombre);
        cv.put(CATALOGO_ID, catalogo_id);
        cv.put(DESCRIPCION, descripcion);
        cv.put(GRUPO_ENTRADA, grupo_entrada);
        db.insert(TABLE_NAME, "catalogo", cv);
    }

    public Cursor consulta(SQLiteDatabase db){
        return db.rawQuery("select * from catalogo", null);
    }

    public void delete(SQLiteDatabase db){ db.delete(TABLE_NAME,null,null); }

}
