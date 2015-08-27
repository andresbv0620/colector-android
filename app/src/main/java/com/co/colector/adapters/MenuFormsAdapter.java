package com.co.colector.adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.co.colector.R;
import com.co.colector.model.Registry;

import java.util.ArrayList;

/**
 * Created by User on 27/08/2015.
 */
public class MenuFormsAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Registry> registryArrayList;

    public MenuFormsAdapter(Context context, ArrayList<Registry> registryArrayList) {
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

        View view = inflater.inflate(R.layout.forms_row,
                parent, false);

        LinearLayout secondLayout = (LinearLayout) view.findViewById(R.id.secondLayout);

        secondLayout.setVisibility(View.GONE);

        TextView labTitle = (TextView) view
                .findViewById(R.id.textViewNamePersonaje);

        Registry registry = registryArrayList.get(position);

        labTitle.setText(registry.getName());

        return view;
    }
}
