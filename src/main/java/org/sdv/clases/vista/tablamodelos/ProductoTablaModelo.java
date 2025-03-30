package org.sdv.clases.vista.tablamodelos;

import org.sdv.clases.modelo.Producto;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProductoTablaModelo extends AbstractTableModel {

    List<Producto> filas = new ArrayList<>();
    String[] columns = {"Código", "Nombre", "Precio", "Stock"};


    public List<Producto> getFilas() {
        return filas;
    }

    public void setFilas(List<Producto> filas) {
        this.filas.clear();
        this.filas = filas;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return filas.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Producto producto  = filas.get(rowIndex);
        return switch (columnIndex){
            case 0 -> producto.getCodigo();
            case 1 -> producto.getNombre();
            case 2 -> producto.getPrecio();
            case 3 -> producto.getStock();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}
