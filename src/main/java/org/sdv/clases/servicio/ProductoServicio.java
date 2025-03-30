package org.sdv.clases.servicio;

import org.sdv.clases.modelo.Producto;
import org.sdv.clases.repositorio.Repositorio;

import java.sql.SQLException;
import java.util.List;

public class ProductoServicio {

    private final Repositorio<Producto> repositorio;

    public ProductoServicio(Repositorio<Producto> repositorio) {
        this.repositorio = repositorio;
    }

    public void insertar(Producto producto) throws SQLException {
        repositorio.insertar(producto);
    }

    public void actualizar(Producto producto) throws SQLException {
        repositorio.actualizar(producto);
    }

    public void eliminar(Long id) throws SQLException {
        repositorio.eliminar(id);
    }

    public List<Producto> listar() throws SQLException {
        return repositorio.listar();
    }

}
