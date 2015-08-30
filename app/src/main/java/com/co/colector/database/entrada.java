package com.co.colector.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by User on 30/08/2015.
 */
public class entrada {

    public static final String TABLE_NAME = "entrada";
    public static final String ID = "id";
    public static final String ENTRADA_ID = "entrada_id";
    public static final String TAB_ID = "tab_id";

    private ContentValues cv;

    public entrada(){

    }

    public void insert(SQLiteDatabase db, String entrada_id, String tab_id){
        cv = new ContentValues();
        cv.put(TAB_ID, tab_id);
        cv.put(ENTRADA_ID, entrada_id);
        db.insert(TABLE_NAME, "entrada", cv);
    }

    public Cursor consulta(SQLiteDatabase db){
        return db.rawQuery("select * from entrada", null);
    }

    public void delete(SQLiteDatabase db){ db.delete(TABLE_NAME,null,null); }
}
