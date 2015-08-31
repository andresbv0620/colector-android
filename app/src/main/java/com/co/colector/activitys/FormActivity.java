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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.co.colector.MapsActivity;
import com.co.colector.R;
import com.co.colector.helpers.DatabaseHelper;
import com.co.colector.helpers.PreferencesHelper;
import com.co.colector.model.Catalog;
import com.co.colector.model.Entry;
import com.co.colector.model.Tab;
import com.co.colector.network.NetworkCalls;
import com.co.colector.utils.ColectorConstants;
import com.co.colector.utils.OperationWsCall;

import java.io.File;
import java.text.Normalizer;
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
    private int elementNumber = 0;
    private String di = "";
    private String timeStamp = "";
    private File mediaStorageDir = null;
    private String idRegistroForm = "";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_form);

        di = "";
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        di = Environment.getExternalStorageDirectory()+"/Colector/Images"+timeStamp;
        createPhotosDirectory();
        DatabaseHelper.insertRegistroForm();

        idRegistroForm = DatabaseHelper.getMaxIdFromRegistry();

                ((ImageButton) findViewById(R.id.imageButtonMenu)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeForm();
            }
        });

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

    private void createPhotosDirectory(){

        mediaStorageDir = new File(di);

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){

            }
        }

    }

    public void exitFormAlert(){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(R.string.back)
                .setPositiveButton("Descartar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(FormActivity.this, BaseActivity.class));
                        finish();
                    }

                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    public void storeForm(){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Guardar")
                .setMessage(R.string.fin)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(FormActivity.this, BaseActivity.class));
                        finish();
                    }

                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    public void init(ArrayList<Catalog> catalogArrayList){

        ((ImageButton) findViewById(R.id.imageButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FormActivity.this, MapsActivity.class));
            }
        });

        this.catalogArrayList = catalogArrayList;
        ColectorConstants.catalogSelected = catalogArrayList.get(ColectorConstants.indexCatalogSelected);

        Log.i("catalogID", ColectorConstants.catalogSelected.getCatalogId());

        ((TextView) findViewById(R.id.textView3)).setText(ColectorConstants.catalogSelected.getCatalogTitle());

        tabsCatalog = ColectorConstants.catalogSelected.getTabs();

        for (Tab t: tabsCatalog) {
            Log.i("tabID",t.getTabId());
            prepareView((LinearLayout) findViewById(R.id.form), t);
            elementNumber++;
        }

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

        int elementNumberTab = 0;
        for (Entry e: entriesTab) {
            addBottomLayouts(optionsLayout, e, elementNumberTab, tab);
            elementNumber++;
        }

        parent.addView(view);

    }

    private void insertingData(Boolean isImage, Tab tab, Entry entry){

        if (!isImage)
            DatabaseHelper.insertRegistro(ColectorConstants.catalogSelected.getCatalogId(), PreferencesHelper.getDbIdSistema(),
                String.valueOf(PreferencesHelper.getIdSistema()),"001", ColectorConstants.catalogSelected.getGrupoEntrada(),
                tab.getTabId(), "", String.valueOf(PreferencesHelper.getUserId()), entry.getEntryId(), "", idRegistroForm);
        else
            DatabaseHelper.insertRegistro(ColectorConstants.catalogSelected.getCatalogId(), PreferencesHelper.getDbIdSistema(),
                    String.valueOf(PreferencesHelper.getIdSistema()),"001", ColectorConstants.catalogSelected.getGrupoEntrada(),
                    tab.getTabId(), "", String.valueOf(PreferencesHelper.getUserId()), entry.getEntryId(), di, idRegistroForm);

    }

    private void addBottomLayouts(LinearLayout optionsLayout, final Entry entry, final int elementNumberTab, final Tab tab) {

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

                insertingData(false, tab, entry);
                final String idUpdate = DatabaseHelper.getMaxId();

                if (elementNumber == 0 && elementNumberTab == 0){

                    TextWatcher textWatcher = new TextWatcher() {
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        public void afterTextChanged(Editable s) {

                            if (s.length() == 0) {

                            } else{

                                //TODO - Here going the logic of update registry.

                                DatabaseHelper.updateRegistro(idUpdate,s.toString());

                            }
                        }
                    };

                    editText.addTextChangedListener(textWatcher);

                }

                mainLayout.addView(editText);
                break;

            case 3: Button button = new Button(this);
                    button.setPadding(10, 10, 10, 10);

                insertingData(false, tab, entry);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.LEFT;
                button.setLayoutParams(params);
                button.setText("Desplegar opciones");

                final String idUpdateMultipleAlerts = DatabaseHelper.getMaxId();

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        createBuilderTabs(entry.getOptions(),true, idUpdateMultipleAlerts);
                    }
                });
                mainLayout.addView(button);


                break;

            case 4: Button secondButton = new Button(this);
                    secondButton.setPadding(10, 10, 10, 10);

                     insertingData(false, tab, entry);

                    LinearLayout.LayoutParams firsButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    firsButtonParams.gravity = Gravity.LEFT;
                    secondButton.setLayoutParams(firsButtonParams);
                    secondButton.setText("Desplegar opciones");

                    final String idUpdateSimpleAlerts = DatabaseHelper.getMaxId();

                    secondButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            createBuilderTabs(entry.getOptions(),false, idUpdateSimpleAlerts);
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

                insertingData(true, tab, entry);

                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                buttonParams.gravity = Gravity.LEFT;
                imageButton.setLayoutParams(buttonParams);

                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openCamera();
                    }
                });

                Button buttonGallery = new Button(this);
                buttonGallery.setPadding(10, 10, 10, 10);
                buttonGallery.setText("Mostrar Galeria");

                buttonGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(FormActivity.this, GridViewActivity.class);
                        intent.putExtra("directory",di);
                        startActivity(intent);
                    }
                });

                mainLayout.addView(imageButton);
                mainLayout.addView(buttonGallery);
                break;

            case 6:
                final EditText edittext = new EditText(FormActivity.this);
                edittext.setMaxLines(1);
                edittext.setBackgroundResource(R.drawable.text_line);
                edittext.setSingleLine(true);
                edittext.setHint(entry.getLabelTitle());
                edittext.setPadding(10, 10, 10, 10);
                edittext.setFocusable(false);

                insertingData(false, tab, entry);
                final String idUpdateDate = DatabaseHelper.getMaxId();

                final Calendar myCalendar = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "dd/MM/yyyy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        edittext.setText(sdf.format(myCalendar.getTime()));
                        //TODO - Here going the logic of update registry.
                        DatabaseHelper.updateRegistro(idUpdateDate,sdf.format(myCalendar.getTime()));

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
        exitFormAlert();
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

    public void createBuilderTabs(final ArrayList<String> list, Boolean isSimpleChoice, final String updateId){

        AlertDialog.Builder builderTabs;

        final ArrayList<Integer> selList = new ArrayList();
        boolean[] checkedOptions = new boolean[list.size()];
        for(int i = 0; i < list.size(); i++)
            checkedOptions[i] = false;
        final CharSequence[] charsOptions = list.toArray(new CharSequence[list.size()]);

        DialogInterface.OnClickListener optionsDialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String singleAnswer = list.get(i);
                Log.i("single-answer", singleAnswer);
                //TODO - Here going the logic of update registry.
                DatabaseHelper.updateRegistro(updateId, singleAnswer);
                dialogInterface.dismiss();
            }
        };

        DialogInterface.OnMultiChoiceClickListener optionsMultpleDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
               if(isChecked) {
                   selList.add(which);
               }
               else if (selList.contains(which)) {
                   selList.remove(Integer.valueOf(which));
               }
            }
        };

        builderTabs = new AlertDialog.Builder(this);

        builderTabs.setTitle("Seleccion de opciones");

        if (isSimpleChoice)
            builderTabs.setSingleChoiceItems(charsOptions , -1 , optionsDialogListener);
        else
            builderTabs.setMultiChoiceItems(charsOptions , checkedOptions , optionsMultpleDialogListener);

        builderTabs.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                String msg = "";

                for (int i = 0; i < selList.size(); i++) {
                    if (i == 0)
                        msg = ""+charsOptions[selList.get(i)];
                    else msg = msg + ";"+charsOptions[selList.get(i)];

                }

                //TODO - Here going the logic of update registry.
                DatabaseHelper.updateRegistro(updateId, msg);
            }
        });

        builderTabs.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builderTabs.setCancelable(false);

        AlertDialog dialog = builderTabs.create();
        dialog.show();
    }

    private File getOutputMediaFile(int type){

        File mediaFile;
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        if (type == MEDIA_TYPE_IMAGE){

            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_"+ timeStamp + ".jpg");

        } else {
            return null;
        }

        return mediaFile;
    }
}