package com.co.colector.helpers;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;

import com.co.colector.ColectorApplication;
import com.co.colector.model.FormRegistry;
import com.co.colector.utils.ColectorConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
                              requests.add(jsonObject);
                              break;

                    case 3:   String[] arrayMultipleOptions = form.getRespuesta().split(";");
                              JSONArray jsonArray = new JSONArray();

                              for (int i = 0; i < arrayMultipleOptions.length; i++)
                                  jsonArray.put(arrayMultipleOptions[i]);

                              jsonObject.put(ColectorConstants.respuestaJsonTag, jsonArray);
                              requests.add(jsonObject);
                              break;

                    case 5:  ArrayList<String> paths = ApplicationHelper.getFilePaths(form.getDirectoryPhoto(), false);

                             if (paths.size() != 0){

                                 JSONArray jsonArrayPhotos = new JSONArray();
                                 Resources r = ColectorApplication.getInstance().getResources();
                                 float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                         ColectorConstants.GRID_PADDING, r.getDisplayMetrics());

                                 int width = (int) ((ApplicationHelper.getScreenWidth() - ((ColectorConstants.NUM_OF_COLUMNS + 1) * padding)) / ColectorConstants.NUM_OF_COLUMNS);

                                 for (String path : paths){
                                    jsonArrayPhotos.put(convertToBase64String(decodeFile(path,width,width)));
                                 }
                                 jsonObject.put(ColectorConstants.respuestaJsonTag, jsonArrayPhotos);
                                 requests.add(jsonObject);
                             }
                }

                Log.i("json", jsonObject.toString());


            }

        } catch (JSONException e){

        }

        return requests;
    }

    public static Bitmap decodeFile(String filePath, int WIDTH, int HIGHT) {
        try {

            File f = new File(filePath);

            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            final int REQUIRED_WIDTH = WIDTH;
            final int REQUIRED_HIGHT = HIGHT;
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_WIDTH
                    && o.outHeight / scale / 2 >= REQUIRED_HIGHT)
                scale *= 2;

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertToBase64String(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

}