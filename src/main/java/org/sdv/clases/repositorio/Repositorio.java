package org.sdv.clases.repositorio;

import java.sql.SQLException;
import java.util.List;

public interface Repositorio <T>{
    void insertar(T t) throws SQLException;
    void  actualizar(T t) throws SQLException;
    void eliminar(Long id) throws SQLException;
    List<T> listar() throws SQLException;
}
