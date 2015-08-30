package com.co.colector.model;

import java.util.ArrayList;

/**
 * Created by User on 25/08/2015.
 */
public class Catalog {
    private String catalogTitle;
    private String catalogDescription;
    private String catalogId;
    private String grupoEntrada;
    private ArrayList<Tab> tabs;

    public Catalog(String catalogTitle, String catalogDescription, String catalogId, String grupoEntrada){
        this.setCatalogTitle(catalogTitle);
        this.setCatalogDescription(catalogDescription);
        this.setCatalogId(catalogId);
        this.setGrupoEntrada(grupoEntrada);
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

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getGrupoEntrada() {
        return grupoEntrada;
    }

    public void setGrupoEntrada(String grupoEntrada) {
        this.grupoEntrada = grupoEntrada;
    }
}
