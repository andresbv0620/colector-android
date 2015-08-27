package com.co.colector.model;

/**
 * Created by User on 26/08/2015.
 */
public class Registry {

    private String name;
    private String registryLabel;
    private String updated;

    public Registry(String name, String registryLabel, String updated){
        this.setName(name);
        this.setRegistryLabel(registryLabel);
        this.setUpdated(updated);
    }

    public Registry(String name, String registryLabel){
        this.setName(name);
        this.setRegistryLabel(registryLabel);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistryLabel() {
        return registryLabel;
    }

    public void setRegistryLabel(String registryLabel) {
        this.registryLabel = registryLabel;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}
