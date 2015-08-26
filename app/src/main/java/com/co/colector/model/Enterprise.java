package com.co.colector.model;

/**
 * Created by User on 24/08/2015.
 */
public class Enterprise {

    private int id;
    private String name;
    private String descriptionEnterprise;

    public Enterprise(int id, String name, String descriptionEnterprise){
        this.id = id;
        this.name = name;
        this.descriptionEnterprise = descriptionEnterprise;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptionEnterprise() {
        return descriptionEnterprise;
    }

    public void setDescriptionEnterprise(String descriptionEnterprise) {
        this.descriptionEnterprise = descriptionEnterprise;
    }
}