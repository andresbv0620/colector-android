package com.co.colector.activitys;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.co.colector.R;
import com.co.colector.model.Catalog;
import com.co.colector.model.Entry;
import com.co.colector.model.Tab;
import com.co.colector.network.NetworkCalls;
import com.co.colector.utils.ColectorConstants;
import com.co.colector.utils.OperationWsCall;

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

        this.catalogArrayList = catalogArrayList;
        ColectorConstants.catalogSelected = catalogArrayList.get(ColectorConstants.indexCatalogSelected);
        tabsCatalog = ColectorConstants.catalogSelected.getTabs();

        for (Tab t: tabsCatalog)
            prepareView((LinearLayout) findViewById(R.id.form),t);

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
            addBottomLayouts(optionsLayout,e);

        parent.addView(view);

    }

    private void addBottomLayouts(LinearLayout optionsLayout, Entry entry) {

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

            case 3:
            case 4: CheckBox[] radioButtons = new CheckBox[entry.getOptions().size()];
                int i = 0;
                for (String option: entry.getOptions()){
                    radioButtons[i] = new CheckBox(this);
                    radioButtons[i].setText(option);
                    radioButtons[i].setPadding(10, 10, 10, 10);
                    mainLayout.addView(radioButtons[i]);
                    i++;
                }
                break;

            case 5:
                ImageButton imageButton = new ImageButton(this);
                imageButton.setClickable(true);
                imageButton.setImageResource(android.R.drawable.ic_input_add);
                imageButton.setPadding(10, 10, 10, 10);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(FormActivity.this, SurfaceViewCameraActivity.class));
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
}
