package com.co.colector.activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.GridView;

import com.co.colector.R;
import com.co.colector.adapters.GridViewImageAdapter;
import com.co.colector.helpers.ApplicationHelper;
import com.co.colector.utils.ColectorConstants;

/**
 * Created by User on 28/08/2015.
 */
public class GridViewActivity extends Activity {

    private GridView gridView;
    private int columnWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Intent intent = getIntent();

        gridView = (GridView) findViewById(R.id.grid_view);
        initilizeGridLayout();

        gridView.setAdapter(new GridViewImageAdapter(GridViewActivity.this, ApplicationHelper.getFilePaths(intent.getStringExtra("directory")),
        columnWidth));
    }

    private void initilizeGridLayout() {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                ColectorConstants.GRID_PADDING, r.getDisplayMetrics());

        columnWidth = (int) ((ApplicationHelper.getScreenWidth() - ((ColectorConstants.NUM_OF_COLUMNS + 1) * padding)) / ColectorConstants.NUM_OF_COLUMNS);

        gridView.setNumColumns(ColectorConstants.NUM_OF_COLUMNS);
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding,
                (int) padding);
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);
    }
}
