package com.co.colector.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.co.colector.R;
import com.co.colector.activitys.BaseActivity;
import com.co.colector.adapters.MenuFormsAdapter;
import com.co.colector.model.Catalog;
import com.co.colector.model.Registry;
import com.co.colector.utils.ColectorConstants;

import java.util.ArrayList;

/**
 * Created by User on 27/08/2015.
 */
public class FragmentFormsList extends Fragment {

    private ArrayList<Registry> registryArrayList;
    private ArrayList<Catalog> catalogArrayList;
    public FragmentFormsList(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.menu_ppal_layout,
                container, false);

        view.findViewById(R.id.imageButtonPlusRegistry).setVisibility(View.GONE);

        registryArrayList = new ArrayList<Registry>();
        catalogArrayList = ColectorConstants.catalogArrayList;

        for (Catalog c: catalogArrayList){
            registryArrayList.add(new Registry(c.getCatalogTitle(),""));
        }

        if (registryArrayList.size() == 0){
            view.findViewById(R.id.listViewRegistros).setVisibility(View.GONE);
            view.findViewById(R.id.textViewNoForms).setVisibility(View.VISIBLE);
        }
        else {
            view.findViewById(R.id.listViewRegistros).setVisibility(View.VISIBLE);
            view.findViewById(R.id.textViewNoForms).setVisibility(View.GONE);

            ((ListView) view.findViewById(R.id.listViewRegistros)).
                    setAdapter(new MenuFormsAdapter(getActivity(), registryArrayList));

            ((ListView) view.findViewById(R.id.listViewRegistros)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ColectorConstants.indexCatalogSelected = i;
                    startActivity(new Intent(getActivity(), BaseActivity.class));
                    getActivity().finish();
                }
            });
        }

        return view;
    }
}