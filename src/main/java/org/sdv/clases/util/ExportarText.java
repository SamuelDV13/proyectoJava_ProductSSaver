package org.sdv.clases.util;

import org.sdv.clases.anotaciones.Exportable;
import org.sdv.clases.modelo.Producto;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ExportarText<T> extends AbstractExportar<T> {

    @Override
    public void exportarDatos(List<T> datos) throws exportableException, IOException {

        if (RUTA == null || ARCHIVO == null) {
            throw new exportableException("Parametros de archivos invalidos");
        }

        File archivo = new File(RUTA + "\\" + ARCHIVO + ".txt");

        try (Writer escritor = new FileWriter(archivo);
             BufferedWriter buffer = new BufferedWriter(escritor)) {

            Field[] atributos = Producto.class.getDeclaredFields();

            List<Field> atributosAnotacion = Arrays.stream(atributos).
                    filter(atributo -> atributo.isAnnotationPresent(Exportable.class)).toList();

            atributosAnotacion.forEach(atributo -> {
                try {
                    String encabezado = (!atributo.getDeclaredAnnotation(Exportable.class).encabezado().isEmpty()) ? atributo.getDeclaredAnnotation(Exportable.class).encabezado() : atributo.getName();
                    buffer.append(encabezado.toUpperCase()).append(formato(encabezado)).append("| ");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            buffer.newLine();
            datos.forEach(t -> {
                try {
                    atributosAnotacion.forEach(field -> {
                        try {
                            field.setAccessible(true);
                            String valor = field.get(t).toString();
                            buffer.append(valor).append(formato(valor)).append("| ");
                        } catch (IOException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    buffer.newLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });


        }
    }

    private String formato(String cadena){
        int espacio = 20;
        int numCaracteres =  cadena.length();
        String caracterSeparador = " ";
        return caracterSeparador.repeat(espacio - numCaracteres);
    }

}
