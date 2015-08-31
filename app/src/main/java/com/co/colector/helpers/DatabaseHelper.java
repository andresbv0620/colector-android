package com.co.colector.helpers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.co.colector.ColectorApplication;
import com.co.colector.database.SQLiteDB;
import com.co.colector.database.catalogo;
import com.co.colector.database.empresa;
import com.co.colector.database.entrada;
import com.co.colector.database.registro_form;
import com.co.colector.database.registros;
import com.co.colector.database.tab;
import com.co.colector.model.Catalog;
import com.co.colector.model.Enterprise;
import com.co.colector.model.Entry;
import com.co.colector.model.FormRegistry;
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
    private static registro_form registroFormTable;
    private static tab tabTable;
    private static Cursor cursor;
    private static ArrayList<Registry> registryArrayList;
    private static ArrayList<FormRegistry> formRegistryArrayList;

    private static void initializeDatabase(){
        sqLiteDB = new SQLiteDB(ColectorApplication.getInstance());
    }

    private static void initializeReadableDatabase(){
        sqLiteDatabase = sqLiteDB.getReadableDatabase();
    }

    private static void initializeWriteableDatabase(){
        sqLiteDatabase = sqLiteDB.getReadableDatabase();
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

    public static void insertRegistro(String catalogoId, String dbSistema, String sistemaId,
                                      String tabletId, String grupoEntrada, String tabId, String respuesta, String usuarioId, String entradaId, String directory_photos, String registroFormId, int typeEntry) {
        initializeDatabase();
        initializeWriteableDatabase();
        registrosTable = new registros();
        registrosTable.insert(sqLiteDatabase,catalogoId,dbSistema,sistemaId,
                tabletId,grupoEntrada,tabId,respuesta,usuarioId,entradaId,directory_photos, registroFormId, String.valueOf(typeEntry));
    }

    public static void insertRegistroForm() {
        initializeDatabase();
        initializeWriteableDatabase();
        registroFormTable = new registro_form();
        registroFormTable.insert(sqLiteDatabase);
    }

    public static void updateRegistro(String id, String answer){
        initializeDatabase();
        initializeWriteableDatabase();
        registrosTable = new registros();
        registrosTable.updateAnswer(sqLiteDatabase, id, answer);
    }

    public static ArrayList<Registry> getRegistrysOfForms(Catalog catalog){

        initializeDatabase();
        initializeReadableDatabase();
        registroFormTable = new registro_form();
        cursor = registroFormTable.consulta(sqLiteDatabase);
        registryArrayList = new ArrayList<Registry>();
        int i = 0;

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()){
                if (isRegistryOfCatalog(catalog.getCatalogId(), String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))))) {
                    registryArrayList.add(new Registry(String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))),
                            "Registro: " + (i + 1),
                            cursor.getString(cursor.getColumnIndex("timestamp")),
                            cursor.getString(cursor.getColumnIndex("updated"))));
                    i++;
                }

                cursor.moveToNext();
            }
            cursor.close();
        }
        sqLiteDB.close();
        return registryArrayList;
    }

    public static Boolean isRegistryOfCatalog(String catalogId, String registroFormId){

        registrosTable = new registros();
        Cursor cursor = registrosTable.consultaByCatalogId(sqLiteDatabase, catalogId, registroFormId, String.valueOf(PreferencesHelper.getIdSistema()));

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()){
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public static String getMaxId(){

        initializeDatabase();
        initializeReadableDatabase();
        final SQLiteStatement stmt = sqLiteDatabase
                .compileStatement("SELECT MAX(id) FROM registros");

        return ""+(int) stmt.simpleQueryForLong();

    }

    public static String getMaxIdFromRegistry(){

        initializeDatabase();
        initializeReadableDatabase();
        final SQLiteStatement stmt = sqLiteDatabase
                .compileStatement("SELECT MAX(id) FROM registro_form");

        return ""+(int) stmt.simpleQueryForLong();

    }

    public static ArrayList<FormRegistry> getRegistrysToPost(String idFormRegistry){

        initializeDatabase();
        initializeReadableDatabase();
        registrosTable = new registros();
        formRegistryArrayList = new ArrayList<FormRegistry>();
        cursor = registrosTable.consultaByRegistroFormId(sqLiteDatabase, idFormRegistry);

        Log.i("id-form",idFormRegistry);

        if (cursor.moveToFirst()) {

            while(!cursor.isAfterLast()){

                formRegistryArrayList.add(new FormRegistry(cursor.getString(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("catalogoId")),
                        cursor.getString(cursor.getColumnIndex("dbSistema")),
                        cursor.getString(cursor.getColumnIndex("sistemaId")),
                        cursor.getString(cursor.getColumnIndex("tablet_id")),
                        cursor.getString(cursor.getColumnIndex("grupoEntrada")),
                        cursor.getString(cursor.getColumnIndex("tabId")),
                        cursor.getString(cursor.getColumnIndex("respuesta")),
                        cursor.getString(cursor.getColumnIndex("usuarioId")),
                        cursor.getString(cursor.getColumnIndex("entradaId")),
                        cursor.getString(cursor.getColumnIndex("directory_photos")),
                        cursor.getString(cursor.getColumnIndex("registro_form_id")),
                        cursor.getString(cursor.getColumnIndex("type_entry"))));

                cursor.moveToNext();
            }
            cursor.close();
        }
        sqLiteDB.close();
        return formRegistryArrayList;
    }

}