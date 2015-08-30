package com.co.colector.helpers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.co.colector.ColectorApplication;
import com.co.colector.database.SQLiteDB;
import com.co.colector.database.catalogo;
import com.co.colector.database.empresa;
import com.co.colector.database.entrada;
import com.co.colector.database.registros;
import com.co.colector.database.tab;
import com.co.colector.model.Catalog;
import com.co.colector.model.Enterprise;
import com.co.colector.model.Entry;
import com.co.colector.model.Registry;
import com.co.colector.model.Tab;

import java.util.ArrayList;

/**
 * Created by User on 26/08/2015.
 */
public class DatabaseHelper {

    private static SQLiteDB sqLiteDB;
    private static SQLiteDatabase sqLiteDatabase;
    private static registros registrosTable;
    private static empresa empresaTable;
    private static catalogo catalogoTable;
    private static entrada entradaTable;
    private static tab tabTable;
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
        //TODO - Refactor call with
        //registrosTable.insert(sqLiteDatabase,nombre,registros);
    }

    public static void insertEmpresa(Enterprise enterprise){
        initializeDatabase();
        initializeWriteableDatabase();
        empresaTable = new empresa();
        empresaTable.insert(sqLiteDatabase,enterprise.getName(),enterprise.getSystem_id(), enterprise.getDescriptionEnterprise(), enterprise.getDb_system());
    }

    public static void insertTab(Tab tab){
        initializeDatabase();
        initializeWriteableDatabase();
        tabTable = new tab();
        tabTable.insert(sqLiteDatabase,tab.getTabId(), tab.getCatalogId());
    }

    public static void insertCatalogo(Catalog catalog){
        initializeDatabase();
        initializeWriteableDatabase();
        catalogoTable = new catalogo();
        catalogoTable.insert(sqLiteDatabase,catalog.getCatalogTitle(),catalog.getCatalogId(), catalog.getCatalogDescription(), catalog.getGrupoEntrada());
    }

    public static void insertEntry(Entry entry){
        initializeDatabase();
        initializeWriteableDatabase();
        entradaTable = new entrada();
        entradaTable.insert(sqLiteDatabase,entry.getEntryId(),entry.getTabId());
    }

    public static void updateRegistro(String id){
        initializeDatabase();
        initializeWriteableDatabase();
        registrosTable = new registros();
        registrosTable.updateActualizado(sqLiteDatabase,id);
    }

    public static ArrayList<Registry> getRegistrysOfForms(){
        initializeDatabase();
        initializeReadableDatabase();
        registrosTable = new registros();

        cursor = registrosTable.consulta(sqLiteDatabase);
        registryArrayList = new ArrayList<Registry>();

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()){
                registryArrayList.add(new Registry(String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))),
                                      cursor.getString(cursor.getColumnIndex("nombre")),
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