package com.co.colector.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by User on 30/08/2015.
 */
public class empresa {

    public static final String TABLE_NAME = "empresa";
    public static final String ID = "id";
    public static final String NOMBRE = "nombre";
    public static final String SISTEMA_ID = "sistema_id";
    public static final String DESCRIPCION = "descripcion";
    public static final String DB_SISTEMA = "db_sistema";

    private ContentValues cv;

    public empresa(){

    }

    public void insert(SQLiteDatabase db, String nombre, String sistema_id, String descripcion, String db_sistema){
        cv = new ContentValues();
        cv.put(NOMBRE, nombre);
        cv.put(SISTEMA_ID, sistema_id);
        cv.put(DESCRIPCION, descripcion);
        cv.put(DB_SISTEMA, db_sistema);
        db.insert(TABLE_NAME, "empresa", cv);
    }

    public Cursor consulta(SQLiteDatabase db){
        return db.rawQuery("select * from empresa", null);
    }

    public void delete(SQLiteDatabase db){ db.delete(TABLE_NAME,null,null); }
}
