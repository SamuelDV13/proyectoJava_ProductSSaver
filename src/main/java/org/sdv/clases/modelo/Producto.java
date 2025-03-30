package org.sdv.clases.modelo;

import org.sdv.clases.anotaciones.Exportable;

import java.util.Objects;

public class Producto {

    @Exportable(encabezado = "ID")
    private Long codigo;
    @Exportable
    private String nombre;
    @Exportable
    private double precio;
    @Exportable(encabezado = "Disponible")
    private int stock;

    public Producto() {
    }

    public Producto(Long codigo, String nombre, double precio, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


    @Override
    public String toString() {
        return codigo + "\t | " + nombre + "\t | " + precio + "\t | " + stock;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Objects.equals(codigo, producto.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }
}
