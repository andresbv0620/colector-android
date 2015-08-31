package com.co.colector.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.co.colector.R;
import com.co.colector.helpers.DatabaseHelper;
import com.co.colector.model.Catalog;

import java.util.ArrayList;

/**
 * Created by User on 08/01/2015.
 */

public class DrawerMenuAdapterList extends BaseAdapter {

    private Context mContext;
    private ArrayList<Catalog> mTitles;

    public DrawerMenuAdapterList(Context context, ArrayList<Catalog> mTitles) {
        mContext = context;
        this.mTitles = mTitles;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public Object getItem(int position) {
        return mTitles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.navigation_drawer_list_item,
                parent, false);

        Catalog catalog = mTitles.get(position);

        TextView labTitle = (TextView) view
                .findViewById(R.id.drawerMenuItemName);

        TextView labSubTitle = (TextView) view
                .findViewById(R.id.drawerSubTitle);

        TextView labTitleUp = (TextView) view
                .findViewById(R.id.drawerTitleUp);

        labTitle.setText(mTitles.get(position).getCatalogTitle());
        labSubTitle.setText(DatabaseHelper.getRegistrysOfForms(mTitles.get(position)).size()+" registros");

         if (position != 0)
             labTitleUp.setVisibility(View.GONE);


        return view;
    }

}