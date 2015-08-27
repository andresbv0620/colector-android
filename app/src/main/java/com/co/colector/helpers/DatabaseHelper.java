package com.co.colector.helpers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.co.colector.ColectorApplication;
import com.co.colector.database.SQLiteDB;
import com.co.colector.database.registros;
import com.co.colector.model.Registry;

import java.util.ArrayList;

/**
 * Created by User on 26/08/2015.
 */
public class DatabaseHelper {

    private static SQLiteDB sqLiteDB;
    private static SQLiteDatabase sqLiteDatabase;
    private static registros registrosTable;
    private static Cursor cursor;
    private static ArrayList<Registry> registryArrayList;

    private static void initializeDatabase(){
        sqLiteDB = new SQLiteDB(ColectorApplication.getInstance());
    }

    private static void initializeReadableDatabase(){
        sqLiteDatabase = sqLiteDB.getReadableDatabase();
    }

    private static void initializeWriteableDatabase(){
        sqLiteDatabase = sqLiteDB.getReadableDatabase();
    }

    public static void insertRegistro(String nombre, String registros){
        initializeDatabase();
        initializeWriteableDatabase();
        registrosTable = new registros();
        registrosTable.insert(sqLiteDatabase,nombre,registros);
    }

    public static ArrayList<Registry> getRegistrysOfForms(){
        initializeDatabase();
        initializeReadableDatabase();
        registrosTable = new registros();

        cursor = registrosTable.consulta(sqLiteDatabase);
        registryArrayList = new ArrayList<Registry>();

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()){
                registryArrayList.add(new Registry(cursor.getString(cursor.getColumnIndex("nombre")),
                                      cursor.getString(cursor.getColumnIndex("registro")),
                                      cursor.getString(cursor.getColumnIndex("actualizado"))));
                cursor.moveToNext();
            }
            cursor.close();
        }
        sqLiteDB.close();
        return registryArrayList;
    }

}
