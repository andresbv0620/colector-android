package com.co.colector.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.co.colector.R;
import com.co.colector.adapters.DrawerMenuAdapterList;
import com.co.colector.fragments.FragmentFormsList;
import com.co.colector.fragments.FragmentInitialMenu;
import com.co.colector.interfaces.BaseMethodsActivity;
import com.co.colector.model.Catalog;
import com.co.colector.network.NetworkCalls;
import com.co.colector.utils.ColectorConstants;
import com.co.colector.utils.OperationWsCall;

import java.util.ArrayList;

/**
 * Created by User on 27/08/2015.
 */
public class ListFormsActivity extends FragmentActivity implements BaseMethodsActivity, PopupMenu.OnMenuItemClickListener{

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
        if (ColectorConstants.catalogArrayList == null) {
            networkCalls = new NetworkCalls(this);
            progressDialog = ProgressDialog.show(this,
                    getString(R.string.downloading_title),
                    getString(R.string.please_take_a_moment),
                    true);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    networkCalls.makeWsCall(OperationWsCall.FORMS_CALL, OperationWsCall.LIST_FORMS_ACTIVITY);
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
        ColectorConstants.catalogSelected = catalogArrayList.get(0);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        networkCalls = new NetworkCalls(this);
        buildMenu((ListView) findViewById(R.id.listViewMenu));

        ((ListView) findViewById(R.id.listViewMenu)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ColectorConstants.indexCatalogSelected = i;
                startActivity(new Intent(ListFormsActivity.this, BaseActivity.class));
                finish();
            }
        });

        makeTransaction(new FragmentFormsList(), getSupportFragmentManager(), R.id.content_frame);
        try {
            progressDialog.hide();
        }catch (NullPointerException e){

        }
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
    }
}
