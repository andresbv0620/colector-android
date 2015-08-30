package com.co.colector.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by User on 30/08/2015.
 */
public class tab {

    public static final String TABLE_NAME = "tab";
    public static final String ID = "id";
    public static final String TAB_ID = "tab_id";
    public static final String CATALOGO_ID = "catalogo_id";

    private ContentValues cv;

    public tab(){

    }

    public void insert(SQLiteDatabase db, String tab_id, String catalogo_id){
        cv = new ContentValues();
        cv.put(TAB_ID, tab_id);
        cv.put(CATALOGO_ID, catalogo_id);
        db.insert(TABLE_NAME, "tab", cv);
    }

    public Cursor consulta(SQLiteDatabase db){
        return db.rawQuery("select * from tab", null);
    }

    public void delete(SQLiteDatabase db){ db.delete(TABLE_NAME,null,null); }

}
