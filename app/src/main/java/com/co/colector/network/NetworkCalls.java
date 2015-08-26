package com.co.colector.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.co.colector.ColectorApplication;
import com.co.colector.R;
import com.co.colector.VolleySingleton;
import com.co.colector.activitys.BaseActivity;
import com.co.colector.activitys.MainActivity;
import com.co.colector.helpers.PreferencesHelper;
import com.co.colector.model.Catalog;
import com.co.colector.model.Enterprise;
import com.co.colector.model.Entry;
import com.co.colector.model.Tab;
import com.co.colector.utils.ColectorConstants;
import com.co.colector.utils.OperationWsCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 24/08/2015.
 */
public class NetworkCalls {

    private Context mContext;
    private JSONObject jsonObject;
    private StringRequest sr;
    private String url;
    private JSONArray jsonArray;
    private ArrayList<Catalog> catalogs;

    public NetworkCalls(Context mContext){
        this.mContext = mContext;
    }

    public void makeWsCall(final OperationWsCall operation){

        switch (operation){
            case LOGIN: url = ColectorApplication.getInstance().getResources().getString(R.string.url_login); break;
            case ENTERPRISE_CALL: url = ColectorApplication.getInstance().getResources().getString(R.string.url_empresas); break;
            case FORMS_CALL: url = ColectorApplication.getInstance().getResources().getString(R.string.url_catalogos); break;
        }

        sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    jsonObject = new JSONObject(response);

                    if (operation == OperationWsCall.LOGIN) {
                        PreferencesHelper.insertIdUser(jsonObject.getInt(ColectorConstants.jsonTagIdUsuario));
                        ((MainActivity) mContext).requestEmpresas();
                    }
                    else if (operation == OperationWsCall.ENTERPRISE_CALL){
                        jsonArray = jsonObject.getJSONArray(ColectorConstants.sistemasTag);

                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject joInside = jsonArray.getJSONObject(i);
                            ((MainActivity) mContext).addEnterprise(new Enterprise(
                                    joInside.getInt(ColectorConstants.sistemaIdJsonTag),
                                    joInside.getString(ColectorConstants.nombreSistemaJsonTag),
                                    joInside.getString(ColectorConstants.descripcionSistemaJsonTag)));
                        }

                        ((MainActivity) mContext).hideDialog();
                        ((MainActivity) mContext).showEnterpriseDialog();
                    }
                    else if (operation == OperationWsCall.FORMS_CALL){
                        jsonArray = jsonObject.getJSONArray(ColectorConstants.catalogosJsonTag);
                        catalogs = new ArrayList<Catalog>();
                        for (int i = 0; i < jsonArray.length(); i++){

                            JSONObject jsonObjectIndisde = jsonArray.getJSONObject(i);

                            if (PreferencesHelper.getIdSistema() == jsonObjectIndisde.getInt(ColectorConstants.sistemaIdJsonTag)){

                                JSONArray catalogosInside = jsonObjectIndisde.getJSONArray(ColectorConstants.catalogosJsonTag);

                                for (int j = 0; j < catalogosInside.length(); j++){

                                        Catalog catalogInsert;

                                        JSONObject jsonObjectCatalogoInside = catalogosInside.getJSONObject(j);
                                        catalogInsert = new Catalog(jsonObjectCatalogoInside.getString(ColectorConstants.catalogoNombreJsonTag),
                                                                    jsonObjectCatalogoInside.getString(ColectorConstants.catalogoDescripcionJsonTag));

                                        JSONArray jsonArrayTabs = jsonObjectCatalogoInside.getJSONArray(ColectorConstants.tabsJsonTag);

                                            for (int k = 0; k < jsonArrayTabs.length(); k++){

                                                JSONObject jsonObjectTabsInside = jsonArrayTabs.getJSONObject(k);

                                                Tab tabInsert = new Tab(jsonObjectTabsInside.getString(ColectorConstants.tabNombreJsonTag),
                                                                        jsonObjectTabsInside.getString(ColectorConstants.tabDescripcionJsonTag));

                                                JSONArray jsonArrayEntries = jsonObjectTabsInside.getJSONArray(ColectorConstants.entradasJsonTag);

                                                    for (int l = 0; l < jsonArrayEntries.length(); l++){

                                                        JSONObject jsonObjectEntry = jsonArrayEntries.getJSONObject(l);

                                                        Entry entryInsert = new Entry(jsonObjectEntry.getString(ColectorConstants.entradaNombreJsonTag),
                                                                jsonObjectEntry.getInt(ColectorConstants.entradaTipoJsonTag),
                                                                jsonObjectEntry.getInt(ColectorConstants.entradaObligatorioJsonTag));


                                                        JSONArray optionsEntry = jsonObjectEntry.getJSONArray(ColectorConstants.optionsJsonTag);

                                                         if (optionsEntry.length() != 0){
                                                             for (int m = 0; m < optionsEntry.length(); m++){
                                                                 JSONObject jsonObjectOptionEntry = optionsEntry.getJSONObject(m);
                                                                 entryInsert.getOptions().add(jsonObjectOptionEntry.getString(ColectorConstants.optionNameJsonTag));
                                                             }
                                                         }

                                                        tabInsert.getEntries().add(entryInsert);

                                                    }

                                                catalogInsert.getTabs().add(tabInsert);

                                            }

                                        catalogs.add(catalogInsert);
                                    }
                            }
                        }

                        ((BaseActivity) mContext).init(catalogs);

                    }
                } catch (JSONException e) {
                    if (operation == OperationWsCall.LOGIN)
                        ((MainActivity) mContext).callToErrorLoginDisplay();
                    else if (operation == OperationWsCall.ENTERPRISE_CALL) {
                        ((MainActivity) mContext).hideDialog();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((MainActivity) mContext).callToErrorLoginDisplay();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(ColectorConstants.idTabletTag, ColectorConstants.idTablet);
                params.put(ColectorConstants.emailTag,ColectorConstants.username);
                params.put(ColectorConstants.passwordTag,ColectorConstants.password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        VolleySingleton.getInstance(ColectorApplication.getInstance()).addToRequestQueue(sr);
    }

}