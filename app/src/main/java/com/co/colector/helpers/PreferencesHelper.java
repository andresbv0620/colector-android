package com.co.colector.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.co.colector.ColectorApplication;

/**
 * Created by User on 24/08/2015.
 */
public class PreferencesHelper {

    private static SharedPreferences sharedPreferences;
    private static String keyPreferences = "myPreferences";
    private static String userIdKey = "userIdKey";
    private static String sistemaIdKey = "sistemaIdKey";
    private static String loadedDataKey = "loadedDataKey";
    private static Editor editor;

    private static void instanciateSharedPreferences(){
        sharedPreferences = ColectorApplication.getInstance().getSharedPreferences(keyPreferences, Context.MODE_PRIVATE);
    }

    public static void insertIdUser(int idUser){
        instanciateSharedPreferences();
        editor = sharedPreferences.edit();
        editor.putInt(userIdKey,idUser);
        editor.commit();
    }

    public static void insertIdSistema(int idSistema){
        instanciateSharedPreferences();
        editor = sharedPreferences.edit();
        editor.putInt(sistemaIdKey,idSistema);
        editor.commit();
    }

    public static void insertLoadedDataKey(int dataLoaded){
        instanciateSharedPreferences();
        editor = sharedPreferences.edit();
        editor.putInt(loadedDataKey,dataLoaded);
        editor.commit();
    }

    public static int getIdSistema(){
        instanciateSharedPreferences();
        return sharedPreferences.getInt(sistemaIdKey,0);
    }
}