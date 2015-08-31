package com.co.colector.helpers;

import android.util.Log;

import com.co.colector.model.FormRegistry;
import com.co.colector.utils.ColectorConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 31/08/2015.
 */
public class NetworkHelper {

    public static ArrayList<JSONObject> buildJSONToPost(String formId){

        ArrayList<FormRegistry> list = DatabaseHelper.getRegistrysToPost(formId);
        ArrayList<JSONObject> requests = new ArrayList<JSONObject>();
        JSONObject jsonObject;

        try {

            for (FormRegistry form : list) {

                jsonObject = new JSONObject();

                jsonObject.put(ColectorConstants.catalogoIdJsonTag, Integer.parseInt(form.getCatalogoId()));
                jsonObject.put(ColectorConstants.dbSistemaJsonTag, form.getSistemaDb());
                jsonObject.put(ColectorConstants.sistemaIdJsonTag, Integer.parseInt(form.getSistemaId()));
                jsonObject.put(ColectorConstants.idTabletTag, form.getTabletId());
                jsonObject.put(ColectorConstants.grupoEntradaJsonTag, Integer.parseInt(form.getGrupoEntrada()));
                jsonObject.put(ColectorConstants.tabIdJsonTag, Integer.parseInt(form.getTabId()));
                jsonObject.put(ColectorConstants.jsonTagIdUsuario, Integer.parseInt(form.getTabId()));
                jsonObject.put(ColectorConstants.entradaIdJsonTag, Integer.parseInt(form.getEntradaId()));

                switch (Integer.parseInt(form.getTypeEntry())) {

                    case 1:
                    case 4:
                    case 6:   jsonObject.put(ColectorConstants.respuestaJsonTag, form.getRespuesta());
                              break;

                    case 3:   String[] arrayMultipleOptions = form.getRespuesta().split(";");
                              JSONArray jsonArray = new JSONArray();

                              for (int i = 0; i < arrayMultipleOptions.length; i++)
                                  jsonArray.put(arrayMultipleOptions[i]);

                              jsonObject.put(ColectorConstants.respuestaJsonTag, jsonArray);
                              break;
                }

                Log.i("json", jsonObject.toString());
                requests.add(jsonObject);

            }

        } catch (JSONException e){

        }

        return requests;
    }

}
