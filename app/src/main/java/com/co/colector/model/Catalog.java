package com.co.colector.model;

import java.util.ArrayList;

/**
 * Created by User on 25/08/2015.
 */
public class Catalog {
    private String catalogTitle;
    private String catalogDescription;
    private ArrayList<Tab> tabs;

    public Catalog(String catalogTitle, String catalogDescription){
        this.setCatalogTitle(catalogTitle);
        this.setCatalogDescription(catalogDescription);
        this.tabs = new ArrayList<Tab>();
    }

    public String getCatalogTitle() {
        return catalogTitle;
    }

    public void setCatalogTitle(String catalogTitle) {
        this.catalogTitle = catalogTitle;
    }

    public String getCatalogDescription() {
        return catalogDescription;
    }

    public void setCatalogDescription(String catalogDescription) {
        this.catalogDescription = catalogDescription;
    }

    public ArrayList<Tab> getTabs() {
        return tabs;
    }

    public void setTabs(ArrayList<Tab> tabs) {
        this.tabs = tabs;
    }
}
