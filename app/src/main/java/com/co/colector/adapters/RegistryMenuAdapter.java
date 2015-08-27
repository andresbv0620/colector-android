package com.co.colector.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.co.colector.R;
import com.co.colector.model.Registry;

import java.util.ArrayList;

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

        TextView labTitle = (TextView) view
                .findViewById(R.id.textViewNamePersonaje);

        TextView labSubTitle = (TextView) view
                .findViewById(R.id.textViewTiempoActualizacion);

        final ImageButton imageButton = (ImageButton) view.findViewById(R.id.imageButtonActualizar);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog = new ProgressDialog(mContext);
                progressDialog.setTitle("Updating");
                progressDialog.setMessage(mContext.getResources().getString(R.string.please_take_a_moment));
                progressDialog.setIndeterminate(true);

                    progressDialog.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            imageButton.setImageResource(R.drawable.btn_check_verde);
                        }
                    }, 2000);
            }
        });

        Registry registry = registryArrayList.get(position);

        labTitle.setText(registry.getName());
        labSubTitle.setText(registry.getRegistryLabel());

        return view;
    }
}
