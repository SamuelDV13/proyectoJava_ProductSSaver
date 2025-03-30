package org.sdv.clases.util;

public abstract class AbstractExportar<T> implements DataExportable<T>{

    protected static String RUTA;
    protected static  String ARCHIVO;
    protected static Extensiones EXTENSION = Extensiones.TXT;

    public AbstractExportar() {

    }

    public static String getRUTA() {
        return RUTA;
    }

    public static Extensiones getEXTENSION() {
        return EXTENSION;
    }

    public static String getARCHIVO() {
        return ARCHIVO;
    }

    public static void setDatos(String ruta, String archivo, Extensiones extension) {
        RUTA = ruta;
        ARCHIVO = archivo;
        EXTENSION = extension;
    }
}
