package com.co.colector.model;

/**
 * Created by User on 24/08/2015.
 */
public class Enterprise {

    private int id;
    private String name;
    private String descriptionEnterprise;
    private String system_id;
    private String db_system;

    public Enterprise(int id, String name, String descriptionEnterprise){
        this.id = id;
        this.name = name;
        this.descriptionEnterprise = descriptionEnterprise;
    }

    public Enterprise(int id, String name, String descriptionEnterprise, String system_id, String db_system){
        this.id = id;
        this.name = name;
        this.descriptionEnterprise = descriptionEnterprise;
        this.setSystem_id(system_id);
        this.setDb_system(db_system);
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

    public String getSystem_id() {
        return system_id;
    }

    public void setSystem_id(String system_id) {
        this.system_id = system_id;
    }

    public String getDb_system() {
        return db_system;
    }

    public void setDb_system(String db_system) {
        this.db_system = db_system;
    }
}