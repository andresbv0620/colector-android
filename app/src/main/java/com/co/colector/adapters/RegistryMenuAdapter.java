package com.co.colector.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Network;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.co.colector.R;
import com.co.colector.activitys.BaseActivity;
import com.co.colector.helpers.DatabaseHelper;
import com.co.colector.helpers.NetworkHelper;
import com.co.colector.model.Registry;
import com.co.colector.utils.ConnectionManager;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 27/08/2015.
 */
public class RegistryMenuAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Registry> registryArrayList;

    public RegistryMenuAdapter(Context context, ArrayList<Registry> registryArrayList) {
        mContext = context;
        this.registryArrayList = registryArrayList;
    }

    @Override
    public int getCount() {
        return registryArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return registryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.row_registros,
                parent, false);

        final Registry registry = registryArrayList.get(position);

        TextView labTitle = (TextView) view
                .findViewById(R.id.textViewNamePersonaje);

        TextView labSubTitle = (TextView) view
                .findViewById(R.id.textViewTiempoActualizacion);

        final ImageButton imageButton = (ImageButton) view.findViewById(R.id.imageButtonActualizar);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ConnectionManager.isConnected())
                    ((BaseActivity) mContext).postJson(NetworkHelper.buildJSONToPost(registry.getId()), registry.getId());
                else
                    Toast.makeText(mContext,"Se necesita conexion a red de datos o WiFi para continuar con la operacion", Toast.LENGTH_LONG).show();
            }
        });

        labTitle.setText(registry.getName());
        labSubTitle.setText(registry.getRegistryLabel());

        if (Integer.parseInt(registry.getUpdated()) == 1) {
            imageButton.setImageResource(R.drawable.btn_check_verde);
            long diffTime = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - Long.parseLong(registry.getRegistryLabel()));
            labSubTitle.setText("Actualizado hace: "+String.format("%d",diffTime)+" minutos");
        }
        else {
            labSubTitle.setText("El registro no ha sido actualizado");
        }

        return view;
    }
}
