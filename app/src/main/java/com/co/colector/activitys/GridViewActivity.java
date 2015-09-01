package com.co.colector.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.co.colector.R;
import com.co.colector.adapters.GridViewImageAdapter;
import com.co.colector.helpers.ApplicationHelper;
import com.co.colector.utils.ColectorConstants;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by User on 28/08/2015.
 */
public class GridViewActivity extends Activity {

    private GridView gridView;
    private int columnWidth;
    private ArrayList<String> paths;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        intent = getIntent();

        gridView = (GridView) findViewById(R.id.grid_view);
        initilizeGridLayout();

        paths = ApplicationHelper.getFilePaths(intent.getStringExtra("directory"));

        if (paths.size() != 0) {
            gridView.setAdapter(new GridViewImageAdapter(GridViewActivity.this, paths ,
                    columnWidth));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
                    new AlertDialog.Builder(GridViewActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setMessage(R.string.imagen_eliminar)
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    File fileDelete = new File(paths.get(position));
                                    fileDelete.delete();
                                    Toast.makeText(GridViewActivity.this,"La imagen fue eliminada exitosamente", Toast.LENGTH_SHORT).show();
                                    Intent intentRepeat = new Intent(GridViewActivity.this, GridViewActivity.class);
                                    intentRepeat.putExtra("directory", intent.getStringExtra("directory"));
                                    startActivity(intentRepeat);
                                    finish();
                                }

                            })
                            .setNegativeButton("Cancelar", null)
                            .show();

                }

            });
        }
        else {
            onBackPressed();
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}