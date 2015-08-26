package com.co.colector.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.co.colector.R;

/**
 * Created by User on 08/01/2015.
 */

public class DrawerMenuAdapterList extends BaseAdapter {

    private Context mContext;
    private String[] mTitles;
    private String[] mSubTitles;

    public DrawerMenuAdapterList(Context context, String[] mTitles, String[] mSubTitles) {
        mContext = context;
        this.mTitles = mTitles;
        this.mSubTitles = mSubTitles;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public Object getItem(int position) {
        return mTitles[position];
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

        TextView labTitle = (TextView) view
                .findViewById(R.id.drawerMenuItemName);

        TextView labSubTitle = (TextView) view
                .findViewById(R.id.drawerSubTitle);

        TextView labTitleUp = (TextView) view
                .findViewById(R.id.drawerTitleUp);

        labTitle.setText(mTitles[position]);
        labSubTitle.setText(mSubTitles[position]);

         if (position != 0)
             labTitleUp.setVisibility(View.GONE);


        return view;
    }

}