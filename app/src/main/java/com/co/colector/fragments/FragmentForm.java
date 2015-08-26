package com.co.colector.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.co.colector.R;
import com.co.colector.activitys.SurfaceViewCameraActivity;
import com.co.colector.model.Catalog;
import com.co.colector.model.Entry;
import com.co.colector.model.Tab;
import com.co.colector.utils.ColectorConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by User on 25/08/2015.
 */
public class FragmentForm extends Fragment {

    private ArrayList<Tab> tabsCatalog;
    private ArrayList<Entry> entriesTab;

    public FragmentForm(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_form,
                container, false);

        ((TextView) view.findViewById(R.id.textView3)).setText(ColectorConstants.catalogSelected.getCatalogTitle());

        tabsCatalog = ColectorConstants.catalogSelected.getTabs();

         for (Tab t: tabsCatalog)
           prepareView((LinearLayout)view.findViewById(R.id.form),t);

        return view;
    }

    private void prepareView(LinearLayout parent, Tab tab){

        LayoutInflater inflater =
                (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_view, null);
        LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.main);

        TextView textViewEntry = new TextView(getActivity());
        textViewEntry.setText(entry.getLabelTitle());
        textViewEntry.setPadding(10, 10, 10, 10);
        mainLayout.addView(textViewEntry);

        switch (entry.getTypeEntry()){

            case 1:
                    EditText editText = new EditText(getActivity());
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
                        radioButtons[i] = new CheckBox(getActivity());
                        radioButtons[i].setText(option);
                        radioButtons[i].setPadding(10, 10, 10, 10);
                        mainLayout.addView(radioButtons[i]);
                        i++;
                    }
                    break;

            case 5:
                    ImageButton imageButton = new ImageButton(getActivity());
                    imageButton.setClickable(true);
                    imageButton.setImageResource(android.R.drawable.ic_input_add);
                    imageButton.setPadding(10, 10, 10, 10);
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getActivity(), SurfaceViewCameraActivity.class));
                        }
                    });
                    mainLayout.addView(imageButton);
                    break;

            case 6:
                    final EditText edittext = new EditText(getActivity());
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
                            new DatePickerDialog(getActivity(), date, myCalendar
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
