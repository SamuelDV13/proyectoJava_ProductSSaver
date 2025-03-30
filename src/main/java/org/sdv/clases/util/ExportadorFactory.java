package org.sdv.clases.util;

import static org.sdv.clases.util.Extensiones.*;

public class ExportadorFactory<T> {

    public AbstractExportar<T> getExportar() {
        return switch (AbstractExportar.getEXTENSION()){
            case TXT -> new ExportarText<>();
            case XLSX -> new ExportarExcel<>();
            default -> null;
        };
    }

}
