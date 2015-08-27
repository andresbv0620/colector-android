package com.co.colector.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.co.colector.MapsActivity;
import com.co.colector.R;
import com.co.colector.model.Catalog;
import com.co.colector.model.Entry;
import com.co.colector.model.Tab;
import com.co.colector.network.NetworkCalls;
import com.co.colector.utils.ColectorConstants;
import com.co.colector.utils.OperationWsCall;

import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by User on 26/08/2015.
 */
public class FormActivity extends Activity {

    private ArrayList<Tab> tabsCatalog;
    private ArrayList<Entry> entriesTab;
    private ArrayList<Catalog> catalogArrayList;
    private NetworkCalls networkCalls;
    private ProgressDialog progressDialog;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private Uri fileUri;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_form);

        networkCalls = new NetworkCalls(this);
        if (ColectorConstants.catalogArrayList == null) {
            progressDialog = ProgressDialog.show(this,
                    getString(R.string.downloading_title),
                    getString(R.string.please_take_a_moment),
                    true);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    networkCalls.makeWsCall(OperationWsCall.FORMS_CALL, OperationWsCall.FORM_ACTIVITY);
                }
            });
        }
        else init(ColectorConstants.catalogArrayList);


    }

    public void init(ArrayList<Catalog> catalogArrayList){

        ((ImageButton) findViewById(R.id.imageButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FormActivity.this,MapsActivity.class));
            }
        });

        this.catalogArrayList = catalogArrayList;
        ColectorConstants.catalogSelected = catalogArrayList.get(ColectorConstants.indexCatalogSelected);
        tabsCatalog = ColectorConstants.catalogSelected.getTabs();

        for (Tab t: tabsCatalog)
            prepareView((LinearLayout) findViewById(R.id.form), t);

        try {
            progressDialog.hide();
        }catch (NullPointerException e){

        }
    }

    private void prepareView(LinearLayout parent, Tab tab){

        LayoutInflater inflater =
                (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.tab_layout,
                parent, false);

        TextView textViewTabTitle = (TextView) view.findViewById(R.id.textViewTabTitle);
        textViewTabTitle.setText(tab.getTitleTab());
        LinearLayout optionsLayout = (LinearLayout) view.findViewById(R.id.optionsLayout);

        entriesTab = tab.getEntries();

        for (Entry e: entriesTab)
            addBottomLayouts(optionsLayout, e);

        parent.addView(view);

    }

    private void addBottomLayouts(LinearLayout optionsLayout, final Entry entry) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_view, null);
        LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.main);

        TextView textViewEntry = new TextView(this);
        textViewEntry.setText(entry.getLabelTitle());
        textViewEntry.setPadding(10, 10, 10, 10);
        mainLayout.addView(textViewEntry);

        switch (entry.getTypeEntry()){

            case 1:
                EditText editText = new EditText(this);
                editText.setMaxLines(1);
                editText.setBackgroundResource(R.drawable.text_line);
                editText.setSingleLine(true);
                editText.setHint(entry.getLabelTitle());
                editText.setPadding(10, 10, 10, 10);
                mainLayout.addView(editText);
                break;

            case 3: Button button = new Button(this);
                    button.setPadding(10, 10, 10, 10);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.LEFT;
                button.setLayoutParams(params);
                button.setText("Desplegar opciones");

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        createBuilderTabs(entry.getOptions(),true);
                    }
                });
                mainLayout.addView(button);
                break;

            case 4: Button secondButton = new Button(this);
                    secondButton.setPadding(10, 10, 10, 10);

                    LinearLayout.LayoutParams firsButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    firsButtonParams.gravity = Gravity.LEFT;
                    secondButton.setLayoutParams(firsButtonParams);
                    secondButton.setText("Desplegar opciones");

                    secondButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            createBuilderTabs(entry.getOptions(),false);
                        }
                    });
                    mainLayout.addView(secondButton);
                break;

            case 5:
                ImageButton imageButton = new ImageButton(this);
                imageButton.setClickable(true);
                imageButton.setImageResource(R.drawable.btn_plus_photo);
                imageButton.setBackgroundResource(0);
                imageButton.setPadding(10, 10, 10, 10);

                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                buttonParams.gravity = Gravity.LEFT;
                imageButton.setLayoutParams(buttonParams);

                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
        openCamera();
                    }
                });
                mainLayout.addView(imageButton);
                break;

            case 6:
                final EditText edittext = new EditText(FormActivity.this);
                edittext.setMaxLines(1);
                edittext.setBackgroundResource(R.drawable.text_line);
                edittext.setSingleLine(true);
                edittext.setHint(entry.getLabelTitle());
                edittext.setPadding(10, 10, 10, 10);
                edittext.setFocusable(false);
                final Calendar myCalendar = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        // TODO - Pass this format to ColectorConstants file.
                        String myFormat = "dd/MM/yyyy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        edittext.setText(sdf.format(myCalendar.getTime()));
                    }

                };

                edittext.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        new DatePickerDialog(FormActivity.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                mainLayout.addView(edittext);
                break;
        }

        optionsLayout.addView(view);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, BaseActivity.class));
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void openCamera(){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    private Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    public void createBuilderTabs(ArrayList<String> list, Boolean isSimpleChoice){

        AlertDialog.Builder builderTabs;

        DialogInterface.OnClickListener optionsDialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        };

        DialogInterface.OnMultiChoiceClickListener optionsMultpleDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
               if (which > 1) {
                   dialog.dismiss();
               }
            }
        };

        boolean[] checkedOptions = new boolean[list.size()];
        for(int i = 0; i < list.size(); i++)
            checkedOptions[i] = false;

        builderTabs = new AlertDialog.Builder(this);

        builderTabs.setTitle("Seleccion de opciones");

        final CharSequence[] charsOptions = list.toArray(new CharSequence[list.size()]);
        if (isSimpleChoice)
            builderTabs.setSingleChoiceItems(charsOptions , -1 , optionsDialogListener);
        else
            builderTabs.setMultiChoiceItems(charsOptions , checkedOptions , optionsMultpleDialogListener);
        AlertDialog dialog = builderTabs.create();
        dialog.show();
    }

    private File getOutputMediaFile(int type){

        String di = "";

        if (type == MEDIA_TYPE_IMAGE){
            di = Environment.getExternalStorageDirectory()+"/Colector/Images";
        }

        File mediaStorageDir = new File(di);

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;

        if (type == MEDIA_TYPE_IMAGE){

            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_"+ timeStamp + ".jpg");

        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
}