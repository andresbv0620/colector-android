package com.co.colector.utils;

import com.co.colector.model.Catalog;

import java.util.ArrayList;

/**
 * Created by User on 24/08/2015.
 */
public class ColectorConstants {

    public static final String idTablet = "001";
    public static final String emailTag = "email";
    public static final String passwordTag = "password";
    public static final String idTabletTag = "tablet_id";
    public static final String jsonTagIdUsuario = "usuarioId";
    public static final String sistemasTag = "sistemas";
    public static final String sistemaIdJsonTag = "sistemaId";
    public static final String dbSistemaJsonTag = "dbSistema";
    public static final String nombreSistemaJsonTag = "nombreSistema";
    public static final String descripcionSistemaJsonTag = "descripcionSistema";
    public static final String catalogoNombreJsonTag = "catalogoNombre";
    public static final String catalogoDescripcionJsonTag = "catalogoDescripcion";
    public static final String catalogoIdJsonTag = "catalogoId";
    public static final String grupoEntradaJsonTag = "grupoEntrada";
    public static final String tabIdJsonTag = "tabId";
    public static final String catalogosJsonTag = "catalogos";
    public static final String tabsJsonTag = "tabs";
    public static final String tabNombreJsonTag = "tabNombre";
    public static final String entradasJsonTag = "entradas";
    public static final String entradaIdJsonTag = "entradaId";
    public static final String entradaNombreJsonTag = "entradaNombre";
    public static final String entradaTipoJsonTag = "entradaTipo";
    public static final String entradaObligatorioJsonTag = "entradaObligatorio";
    public static final String tabDescripcionJsonTag = "tabDescripcion";
    public static final String optionsJsonTag = "opciones";
    public static final String optionNameJsonTag = "opcionNombre";
    public static final String respuestaJsonTag = "respuesta";
    public static final String arrayTitles[] = { "Orden de ventas", "Inventario", "Servicio al cliente"};
    public static final String arraySubTitles[] = { "0 registros", "10 registros", "20 registros"};
    public static Catalog catalogSelected = null;
    public static String username = "";
    public static String password = "";
    public static ArrayList<Catalog> catalogArrayList;
    public static int indexCatalogSelected = 0;
    public static final int NUM_OF_COLUMNS = 3;
    public static final int GRID_PADDING = 8; // in dp

}
