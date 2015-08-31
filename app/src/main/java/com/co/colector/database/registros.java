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
    public static final String CATALOGO_ID = "catalogoId";
    public static final String SISTEMA_DB = "dbSistema";
    public static final String SISTEMA_ID = "sistemaId";
    public static final String TABLET_ID = "tablet_id";
    public static final String GRUPO_ENTRADA = "grupoEntrada";
    public static final String TAB_ID = "tabId";
    public static final String RESPUESTA = "respuesta";
    public static final String USUARIO_ID = "usuarioId";
    public static final String ENTRADA_ID = "entradaId";
    public static final String DIRECTORY_PHOTOS = "directory_photos";
    private ContentValues cv;

    public registros(){

    }

    public void insert(SQLiteDatabase db, String catalogoId, String dbSistema, String sistemaId,
                       String tabletId, String grupoEntrada, String tabId, String respuesta, String usuarioId, String entradaId, String directory_photos){

        cv = new ContentValues();

        cv.put(CATALOGO_ID, catalogoId);
        cv.put(SISTEMA_DB, dbSistema);
        cv.put(SISTEMA_ID, sistemaId);
        cv.put(TABLET_ID, tabletId);
        cv.put(GRUPO_ENTRADA, grupoEntrada);
        cv.put(TAB_ID, tabId);
        cv.put(RESPUESTA, respuesta);
        cv.put(USUARIO_ID, usuarioId);
        cv.put(ENTRADA_ID, entradaId);
        cv.put(DIRECTORY_PHOTOS, directory_photos);

        db.insert(TABLE_NAME, "registros", cv);
    }

    public Cursor consulta(SQLiteDatabase db){
        return db.rawQuery("select * from registros", null);
    }

    public void updateActualizado(SQLiteDatabase db, String id){

        //TODO - refactor after fill the table correctly

        /*ContentValues values = new ContentValues();
        values.put(ACTUALIZADO, "1");
        values.put(REGISTRO,String.valueOf(System.currentTimeMillis()));

        db.update(TABLE_NAME,
                values,
                ID+" = ?",
                new String[] { id });

        db.close();*/

    }

    public void delete(SQLiteDatabase db){ db.delete(TABLE_NAME,null,null); }
}
