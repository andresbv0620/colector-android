package com.co.colector.model;

/**
 * Created by User on 31/08/2015.
 */
public class FormRegistry {
    private String id;
    private String catalogoId;
    private String sistemaDb;
    private String sistemaId;
    private String tabletId;
    private String grupoEntrada;
    private String tabId;
    private String respuesta;
    private String usuarioId;
    private String entradaId;
    private String directoryPhoto;
    private String registroFormId;
    private String typeEntry;

    public FormRegistry(String id, String catalogoId, String sistemaDb, String sistemaId, String tabletId, String grupoEntrada, String tabId, String respuesta, String usuarioId, String entradaId, String directoryPhoto, String registroFormId, String typeEntry) {
        this.id = id;
        this.catalogoId = catalogoId;
        this.sistemaDb = sistemaDb;
        this.sistemaId = sistemaId;
        this.tabletId = tabletId;
        this.grupoEntrada = grupoEntrada;
        this.tabId = tabId;
        this.respuesta = respuesta;
        this.usuarioId = usuarioId;
        this.entradaId = entradaId;
        this.directoryPhoto = directoryPhoto;
        this.registroFormId = registroFormId;
        this.typeEntry = typeEntry;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatalogoId() {
        return catalogoId;
    }

    public void setCatalogoId(String catalogoId) {
        this.catalogoId = catalogoId;
    }

    public String getSistemaDb() {
        return sistemaDb;
    }

    public void setSistemaDb(String sistemaDb) {
        this.sistemaDb = sistemaDb;
    }

    public String getSistemaId() {
        return sistemaId;
    }

    public void setSistemaId(String sistemaId) {
        this.sistemaId = sistemaId;
    }

    public String getTabletId() {
        return tabletId;
    }

    public void setTabletId(String tabletId) {
        this.tabletId = tabletId;
    }

    public String getGrupoEntrada() {
        return grupoEntrada;
    }

    public void setGrupoEntrada(String grupoEntrada) {
        this.grupoEntrada = grupoEntrada;
    }

    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEntradaId() {
        return entradaId;
    }

    public void setEntradaId(String entradaId) {
        this.entradaId = entradaId;
    }

    public String getDirectoryPhoto() {
        return directoryPhoto;
    }

    public void setDirectoryPhoto(String directoryPhoto) {
        this.directoryPhoto = directoryPhoto;
    }

    public String getRegistroFormId() {
        return registroFormId;
    }

    public void setRegistroFormId(String registroFormId) {
        this.registroFormId = registroFormId;
    }

    public String getTypeEntry() {
        return typeEntry;
    }

    public void setTypeEntry(String typeEntry) {
        this.typeEntry = typeEntry;
    }
}
