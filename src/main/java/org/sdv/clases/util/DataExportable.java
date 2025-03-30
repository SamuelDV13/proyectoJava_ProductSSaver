package org.sdv.clases.util;

import org.sdv.clases.modelo.Producto;

import java.io.IOException;
import java.util.List;

public interface DataExportable <T> {

    void exportarDatos(List<T> datos) throws exportableException, IOException;

}
