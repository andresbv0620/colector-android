package com.co.colector.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.co.colector.ColectorApplication;
import com.co.colector.R;
import com.co.colector.activitys.BaseActivity;
import com.co.colector.activitys.FormActivity;
import com.co.colector.adapters.RegistryMenuAdapter;
import com.co.colector.helpers.DatabaseHelper;
import com.co.colector.helpers.NetworkHelper;
import com.co.colector.model.Registry;
import com.co.colector.model.Tab;
import com.co.colector.utils.ColectorConstants;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 26/08/2015.
 */
public class FragmentInitialMenu extends Fragment {

    private ArrayList<Registry> registryArrayList;

    public FragmentInitialMenu(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.menu_ppal_layout,
                container, false);

        registryArrayList = DatabaseHelper.getRegistrysOfForms(ColectorConstants.catalogSelected);

         if (registryArrayList.size() == 0){
             view.findViewById(R.id.listViewRegistros).setVisibility(View.GONE);
             view.findViewById(R.id.textViewNoForms).setVisibility(View.VISIBLE);
             ((TextView) view.findViewById(R.id.textViewNoForms)).setText(getActivity().getResources().getString(R.string.no_registros));
         }
         else {
             view.findViewById(R.id.listViewRegistros).setVisibility(View.VISIBLE);
             view.findViewById(R.id.textViewNoForms).setVisibility(View.GONE);

             ((ListView) view.findViewById(R.id.listViewRegistros)).
                     setAdapter(new RegistryMenuAdapter(getActivity(),registryArrayList));
         }

        ((ImageButton) view.findViewById(R.id.imageButtonPlusRegistry)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FormActivity.class));
                getActivity().finish();
            }
        });

        ((ImageButton) view.findViewById(R.id.imageButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<ArrayList<JSONObject>> syncronizedJsons = new ArrayList<ArrayList<JSONObject>>();
                ArrayList<JSONObject> jsonObjectArrayList = new ArrayList<JSONObject>();
                ArrayList<String> idsRegistrys = new ArrayList<String>();

                for (Registry registry: registryArrayList)
                    if (Integer.parseInt(registry.getUpdated()) == 0) {
                        syncronizedJsons.add(NetworkHelper.buildJSONToPost(registry.getId()));
                        idsRegistrys.add(registry.getId());
                    }

                for (ArrayList<JSONObject> objectArrayList: syncronizedJsons)
                   for (JSONObject jsonObject: objectArrayList)
                        jsonObjectArrayList.add(jsonObject);

                ((BaseActivity) getActivity()).syncronizeAll(jsonObjectArrayList, idsRegistrys);
            }
        });

        return view;
    }
}
