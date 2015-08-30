package com.co.colector.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Environment;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.co.colector.ColectorApplication;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by User on 28/08/2015.
 */
public class ApplicationHelper {

    public static ArrayList<String> getFilePaths() {

        ArrayList<String> filePaths = new ArrayList<String>();

        File directory = new File(
                Environment.getExternalStorageDirectory()+"/Colector/Images");

        // check for directory
        if (directory.isDirectory()) {
            // getting list of file paths
            File[] listFiles = directory.listFiles();

            // Check for count
            if (listFiles.length > 0) {

                // loop through all files
                for (int i = 0; i < listFiles.length; i++) {

                    // get file path
                    String filePath = listFiles[i].getAbsolutePath();

                    // check for supported file extension

                        // Add image path to array list
                        filePaths.add(filePath);

                }
            } else {
                // image directory is empty
                Toast.makeText(
                        ColectorApplication.getInstance(),
                                "El directorio esta vacio",
                        Toast.LENGTH_LONG).show();
            }

        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(ColectorApplication.getInstance());
            alert.setTitle("Error!");
            alert.setMessage("El directorio no existe en estos momentos");
            alert.setPositiveButton("OK", null);
            alert.show();
        }

        return filePaths;
    }

    public static int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) ColectorApplication.getInstance()
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }

}
