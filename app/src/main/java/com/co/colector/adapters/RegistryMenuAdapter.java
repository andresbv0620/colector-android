package com.co.colector.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Network;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.co.colector.R;
import com.co.colector.activitys.BaseActivity;
import com.co.colector.activitys.EditFormActivity;
import com.co.colector.helpers.DatabaseHelper;
import com.co.colector.helpers.NetworkHelper;
import com.co.colector.model.Registry;
import com.co.colector.utils.ConnectionManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
        final ImageButton menuOptions = (ImageButton) view.findViewById(R.id.imageButtonPopUpMapa);

        menuOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog;
                final AlertDialog alert;
                LayoutInflater inflater;
                View convertView;
                String[] names = {"Editar", "Eliminar"};

                alertDialog = new AlertDialog.Builder(mContext);
                inflater = ((Activity)mContext).getLayoutInflater();
                convertView = (View) inflater.inflate(R.layout.list, null);
                alertDialog.setView(convertView);
                alertDialog.setTitle("Seleccionar accion");

                ((ListView) convertView.findViewById(R.id.lv)).
                        setAdapter(new ArrayAdapter<String>(mContext, R.layout.mytextview, names));

                alert = alertDialog.create();
                alert.show();

                ((ListView) convertView.findViewById(R.id.lv)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        if (position == 0){
                            Intent editFormIntent = new Intent(mContext, EditFormActivity.class);
                            editFormIntent.putExtra("id", registry.getId());
                            editFormIntent.putExtra("enabled",Integer.parseInt(registry.getUpdated()) == 1);
                            mContext.startActivity(editFormIntent);
                            ((Activity)mContext).finish();
                        }
                        else {
                            new AlertDialog.Builder(mContext)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setMessage(R.string.registro_eliminar)
                                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            alert.dismiss();
                                            dialog.dismiss();
                                            DatabaseHelper.deleteRegistroForm(registry.getId());
                                            mContext.startActivity(new Intent(mContext, BaseActivity.class));
                                            Toast.makeText(mContext,"El registro fue eliminado exitosamente", Toast.LENGTH_LONG).show();
                                            ((Activity)mContext).finish();
                                        }

                                    })
                                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            alert.dismiss();
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .show();
                        }
                    }
                });
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ConnectionManager.isConnected()) {
                    ArrayList<String> idsDb = new ArrayList<String>(Arrays.asList(registry.getId()));
                    ((BaseActivity) mContext).postJson(NetworkHelper.buildJSONToPost(registry.getId()), registry.getId(), idsDb);
                }
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
