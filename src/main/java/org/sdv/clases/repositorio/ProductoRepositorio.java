package org.sdv.clases.repositorio;

import org.sdv.clases.configuracion.ConexionBD;
import org.sdv.clases.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorio implements Repositorio<Producto>{

    private Connection getConnection() throws SQLException {
        return ConexionBD.getConexion();
    }

    @Override
    public List<Producto> listar() throws SQLException {

        List<Producto> productos = new ArrayList<>();

        try(Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCTOS ORDER BY CODIGO")){

            while(rs.next()){
                Producto producto = new Producto();
                producto.setCodigo(rs.getLong("CODIGO"));
                producto.setNombre(rs.getString("NOMBRE"));
                producto.setPrecio(rs.getDouble("PRECIO"));
                producto.setStock(rs.getInt("STOCK"));
                productos.add(producto);
            }

            return productos;

        }
    }

    @Override
    public void insertar(Producto producto) throws SQLException {
        try(PreparedStatement stmt = getConnection().prepareStatement("INSERT INTO PRODUCTOS(CODIGO, NOMBRE, PRECIO, STOCK) VALUES(?,?,?,?)")){

            stmt.setLong(1, producto.getCodigo());
            stmt.setString(2, producto.getNombre());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());

            stmt.executeUpdate();

        }
    }

    @Override
    public void actualizar(Producto producto) throws SQLException {
        try(PreparedStatement stmt = getConnection().prepareStatement("UPDATE PRODUCTOS SET NOMBRE = ?, PRECIO = ?, STOCK = ? WHERE CODIGO = ?")){

            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setInt(3, producto.getStock());
            stmt.setLong(4, producto.getCodigo());

            stmt.executeUpdate();

        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try(PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM PRODUCTOS WHERE CODIGO = ?")){

            stmt.setLong(1, id);
            stmt.executeUpdate();

        }
    }
}
