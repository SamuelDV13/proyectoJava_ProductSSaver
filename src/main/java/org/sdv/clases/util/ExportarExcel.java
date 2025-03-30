package org.sdv.clases.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sdv.clases.anotaciones.Exportable;
import org.sdv.clases.modelo.Producto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ExportarExcel<T> extends AbstractExportar<T> {

    @Override
    public void exportarDatos(List<T> datos) throws exportableException, IOException {
       try(Workbook wk = new XSSFWorkbook()){
           Sheet hoja = wk.createSheet("Datos");

           Field[] atributos = Producto.class.getDeclaredFields();

           List<Field> atributosAnotacion = Arrays.stream(atributos).
                   filter(atributo -> atributo.isAnnotationPresent(Exportable.class)).toList();

           AtomicInteger iHeader = new AtomicInteger();
           Row header = hoja.createRow(0);
           atributosAnotacion.forEach(atributo -> {
               String encabezado = (!atributo.getDeclaredAnnotation(Exportable.class).encabezado().isEmpty()) ? atributo.getDeclaredAnnotation(Exportable.class).encabezado() : atributo.getName();

               Cell celda = header.createCell(iHeader.getAndIncrement());
               celda.setCellValue(encabezado);
           });

           AtomicInteger numFila = new AtomicInteger(1);
           AtomicInteger iDatos = new AtomicInteger();
           datos.forEach(t -> {
               Row filaDato = hoja.createRow(numFila.getAndIncrement());
                atributosAnotacion.forEach(field -> {
                    field.setAccessible(true);
                    try {
                        Object valor = field.get(t);

                        Cell celda = filaDato.createCell(iDatos.getAndIncrement());

                        if(valor instanceof String) celda.setCellValue((String) valor);
                        if(valor instanceof Integer) celda.setCellValue((Integer) valor);
                        if(valor instanceof Double) celda.setCellValue((Double) valor);
                        if(valor instanceof Long) celda.setCellValue((Long) valor);

                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
                iDatos.set(0);
           });

           try(FileOutputStream fos = new FileOutputStream(RUTA + "\\" + ARCHIVO + ".xlsx")){
                wk.write(fos);
           }

       }
    }
}
