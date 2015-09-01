package com.co.colector.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.co.colector.ColectorApplication;
import com.co.colector.R;
import com.co.colector.adapters.DrawerMenuAdapterList;
import com.co.colector.fragments.FragmentForm;
import com.co.colector.fragments.FragmentInitialMenu;
import com.co.colector.helpers.DatabaseHelper;
import com.co.colector.interfaces.BaseMethodsActivity;
import com.co.colector.model.Catalog;
import com.co.colector.network.NetworkCalls;
import com.co.colector.utils.ColectorConstants;
import com.co.colector.utils.ConnectionManager;
import com.co.colector.utils.OperationWsCall;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by User on 24/08/2015.
 */
public class BaseActivity extends FragmentActivity implements BaseMethodsActivity, PopupMenu.OnMenuItemClickListener{

    private DrawerLayout mDrawerLayout;
    private FragmentTransaction fragmentTransaction;
    private NetworkCalls networkCalls;
    private ArrayList<Catalog> catalogArrayList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);
        catalogArrayList = new ArrayList<Catalog>();
        networkCalls = new NetworkCalls(this);

        if (ColectorConstants.catalogArrayList == null) {
            progressDialog = ProgressDialog.show(this,
                    getString(R.string.downloading_title),
                    getString(R.string.please_take_a_moment),
                    true);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    networkCalls.makeWsCall(OperationWsCall.FORMS_CALL, OperationWsCall.MENU_ACTIVITY);
                }
            });
        }
        else init(ColectorConstants.catalogArrayList);
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        popup.setOnMenuItemClickListener(this);
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.show();
    }

    public void init(ArrayList<Catalog> catalogArrayList){
        this.catalogArrayList = catalogArrayList;
        ColectorConstants.catalogSelected = catalogArrayList.get(ColectorConstants.indexCatalogSelected);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        networkCalls = new NetworkCalls(this);
        buildMenu((ListView) findViewById(R.id.listViewMenu));

        ((ListView) findViewById(R.id.listViewMenu)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ColectorConstants.indexCatalogSelected = i;
                startActivity(new Intent(BaseActivity.this, BaseActivity.class));
                finish();
            }
        });

        makeTransaction(new FragmentInitialMenu(), getSupportFragmentManager(), R.id.content_frame);
        try {
            progressDialog.hide();
        }catch (NullPointerException e){

        }
    }

    public void syncronizeAll(ArrayList<JSONObject> jsonObjectArrayList, ArrayList<String> idsRegistrys){
        if (ConnectionManager.isConnected()) {
            postJson(jsonObjectArrayList, null, idsRegistrys);
        }
        else
            Toast.makeText(this, "Se necesita conexion a red de datos o WiFi para continuar con la operacion", Toast.LENGTH_LONG).show();
    }

    @Override
    public void buildMenu(ListView listView) {
        listView.setAdapter(new DrawerMenuAdapterList(this, catalogArrayList));
    }

    private String[] getFormsTitles(){
        String[] titles = new String[catalogArrayList.size()];
        int i = 0;
         for (Catalog c: catalogArrayList){
             titles[i] = c.getCatalogTitle();
             i++;
         }
        return titles;
    }

    @Override
    public void makeTransaction(Fragment fragment, FragmentManager fragmentManager, int resourceView) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(resourceView, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void openDrawerMenu(View v) {
        mDrawerLayout.openDrawer((ListView) findViewById(R.id.listViewMenu));
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.lista_vista:
                return true;

            case R.id.mapa_vista:
                return true;

            case R.id.buscar_registros:
                return true;

            case R.id.configuracion:
                return true;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, ListFormsActivity.class));
        finish();
    }

    public void postJson(final ArrayList<JSONObject> jsonObjects, final String idUpdate, final ArrayList<String> databaseIds){

        //TODO - Refactor all this code piece.

        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {

            private ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                progressDialog = new ProgressDialog(BaseActivity.this);
                progressDialog.setTitle("Updating");
                progressDialog.setMessage(getResources().getString(R.string.please_take_a_moment));
                progressDialog.setIndeterminate(true);
                progressDialog.show();
            }

            @Override
            protected String doInBackground(Void... voids) {

                for (JSONObject json : jsonObjects) {

                    String url = ColectorApplication.getInstance().getResources().getString(R.string.url_inputs);
                    HttpClient client = new DefaultHttpClient();
                    HttpResponse response;

                    HttpPost post = new HttpPost(url);
                    StringEntity se = null;
                    try {
                        se = new StringEntity(json.toString());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);
                    try {
                        response = client.execute(post);
                        Log.i("resp", EntityUtils.toString(response.getEntity()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {

                if (idUpdate != null)
                    DatabaseHelper.updateRegistroForm(idUpdate);
                else {
                    for (String id: databaseIds)
                        DatabaseHelper.updateRegistroForm(id);
                }

                progressDialog.dismiss();
                startActivity(new Intent(BaseActivity.this, BaseActivity.class));
                finish();
            }
        };

        asyncTask.execute();
    }
}