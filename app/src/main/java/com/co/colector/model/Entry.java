package com.co.colector.model;

import java.util.ArrayList;

/**
 * Created by User on 25/08/2015.
 */
public class Entry {

    private String labelTitle;
    private int typeEntry;
    private int mandatoryEntry;
    private ArrayList<String> options;

    public Entry(String labelTitle, int typeEntry, int mandatoryEntry){
        this.setLabelTitle(labelTitle);
        this.setTypeEntry(typeEntry);
        this.setMandatoryEntry(mandatoryEntry);
        this.options = new ArrayList<String>();
    }

    public String getLabelTitle() {
        return labelTitle;
    }

    public void setLabelTitle(String labelTitle) {
        this.labelTitle = labelTitle;
    }

    public int getTypeEntry() {
        return typeEntry;
    }

    public void setTypeEntry(int typeEntry) {
        this.typeEntry = typeEntry;
    }

    public int getMandatoryEntry() {
        return mandatoryEntry;
    }

    public void setMandatoryEntry(int mandatoryEntry) {
        this.mandatoryEntry = mandatoryEntry;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
}
