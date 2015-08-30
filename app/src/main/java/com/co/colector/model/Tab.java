package com.co.colector.model;

import java.util.ArrayList;

/**
 * Created by User on 25/08/2015.
 */
public class Tab {

    private String titleTab;
    private String descriptionTab;
    private String tabId;
    private String catalogId;
    private ArrayList<Entry> entries;

    public Tab(String titleTab, String descriptionTab){
        this.setTitleTab(titleTab);
        this.descriptionTab = descriptionTab;
        this.entries = new ArrayList<Entry>();
    }

    public String getDescriptionTab() {
        return descriptionTab;
    }

    public void setDescriptionTab(String descriptionTab) {
        this.descriptionTab = descriptionTab;
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<Entry> entries) {
        this.entries = entries;
    }

    public String getTitleTab() {
        return titleTab;
    }

    public void setTitleTab(String titleTab) {
        this.titleTab = titleTab;
    }

    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }
}
