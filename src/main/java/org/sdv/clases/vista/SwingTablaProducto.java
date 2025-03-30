package org.sdv.clases.vista;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.sdv.clases.modelo.Producto;
import org.sdv.clases.vista.tablamodelos.ProductoTablaModelo;
import org.sdv.clases.servicio.ProductoServicio;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SwingTablaProducto extends JFrame {

    private JPanel panel_tabla;
    private JTable tabla_productos;
    private JTextField txt_stock;
    private JTextField txt_precio;
    private JTextField txt_nombre;
    private JButton btn_editar;
    private JButton btn_borrar;
    private JScrollPane scroll;

    private final ProductoTablaModelo productoTablaModelo = new ProductoTablaModelo();
    private long codigo;
    private ProductoServicio productoServicio;

    public SwingTablaProducto() {
        initComponents();
        getContentPane().add(panel_tabla);

        setTitle("Producto agregados");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setSize(550, 500);
        setResizable(false);
        setLocationRelativeTo(null);

    }

    public SwingTablaProducto(ProductoServicio productoServicio) throws SQLException {
        this();
        this.productoServicio = productoServicio;
        productoTablaModelo.setFilas(productoServicio.listar());
        tabla_productos.setModel(productoTablaModelo);
    }


    //Metodos de evento
    private void btn_editarActionPerformed(ActionEvent evt) {
        try {
            if (validarCampos()) {
                String nombre = txt_nombre.getText();
                double precio = Double.parseDouble(txt_precio.getText());
                int stock = Integer.parseInt(txt_stock.getText());

                Producto producto = new Producto(codigo, nombre, precio, stock);
                productoServicio.actualizar(producto);
                productoTablaModelo.setFilas(productoServicio.listar());
                codigo = 0;
                JOptionPane.showMessageDialog(this, "Producto modificado exitosamente", "Producto", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la base de datos\n" + e.getMessage(), "Error base de datos", JOptionPane.ERROR_MESSAGE);
        }
        limpiarCampos();
    }

    private void btn_borrarActionPerformed(ActionEvent evt) {
        try {
            if (validarCampos()) {
                int confirmar = JOptionPane.showConfirmDialog(this, "¿Deseas eliminar este registro?", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirmar == JOptionPane.YES_OPTION) {
                    productoServicio.eliminar(codigo);
                    productoTablaModelo.setFilas(productoServicio.listar());
                    JOptionPane.showMessageDialog(this, "Producto eliminado exitosamente", "Producto eliminado", JOptionPane.INFORMATION_MESSAGE);
                }
                codigo = 0;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la base de datos\n" + e.getMessage(), "Error base de datos", JOptionPane.ERROR_MESSAGE);
        }

        limpiarCampos();
    }


    private void initComponents() {

        assert btn_editar != null;
        btn_editar.addActionListener(this::btn_editarActionPerformed);
        assert btn_borrar != null;
        btn_borrar.addActionListener(this::btn_borrarActionPerformed);

        assert tabla_productos != null;
        tabla_productos.addMouseListener(new MouseAdapter() {

            int columna = -1;
            int fila = -1;

            @Override
            public void mouseClicked(MouseEvent e) {
                fila = tabla_productos.rowAtPoint(e.getPoint());
                columna = tabla_productos.columnAtPoint(e.getPoint());

                if (fila > -1 && columna > -1) {
                    codigo = (Long) tabla_productos.getValueAt(fila, 0);
                    txt_nombre.setText(tabla_productos.getValueAt(fila, 1).toString());
                    txt_precio.setText(tabla_productos.getValueAt(fila, 2).toString());
                    txt_stock.setText(tabla_productos.getValueAt(fila, 3).toString());
                }

                fila = -1;
                columna = -1;

            }
        });
    }


    private void limpiarCampos() {
        txt_nombre.setText("");
        txt_precio.setText("");
        txt_stock.setText("");
    }

    private void despintarCampos() {
        txt_nombre.setBackground(new Color(243, 246, 249));
        txt_precio.setBackground(new Color(243, 246, 249));
        txt_stock.setBackground(new Color(243, 246, 249));
    }

    private boolean validarCampos() {
        try {
            String nombre = txt_nombre.getText();
            double precio = Double.parseDouble(txt_precio.getText());
            int stock = Integer.parseInt(txt_stock.getText());

            List<String> errores = new ArrayList<>();

            if (codigo <= 0) {
                errores.add("No hay un código seleccionado.");
            }

            if (nombre.isBlank()) {
                errores.add("El nombre no puede estar vacio.");
                txt_nombre.setBackground(new Color(192, 57, 32));
            }

            if (precio <= 0) {
                errores.add("El precio debe ser positivo.");
                txt_precio.setBackground(new Color(192, 57, 32));
            }

            if (stock <= 0) {
                errores.add("El stock debe ser positivo.");
                txt_stock.setBackground(new Color(192, 57, 32));
            }

            if (!errores.isEmpty()) {

                StringBuilder mensajesE = new StringBuilder();
                for (String error : errores) {
                    mensajesE.append(error).append("\n");
                }

                JOptionPane.showMessageDialog(this, mensajesE, "Producto", JOptionPane.ERROR_MESSAGE);
                despintarCampos();
                return false;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los campos", "Producto", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        despintarCampos();
        return true;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel_tabla = new JPanel();
        panel_tabla.setLayout(new GridLayoutManager(5, 4, new Insets(0, 0, 0, 0), -1, 10));
        panel_tabla.setBackground(new Color(-2301216));
        panel_tabla.setPreferredSize(new Dimension(500, 500));
        panel_tabla.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 16, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-16250872));
        label1.setText("Productos en la lista");
        panel_tabla.add(label1, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txt_stock = new JTextField();
        txt_stock.setBackground(new Color(-788743));
        Font txt_stockFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 14, txt_stock.getFont());
        if (txt_stockFont != null) txt_stock.setFont(txt_stockFont);
        txt_stock.setForeground(new Color(-16777216));
        panel_tabla.add(txt_stock, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txt_precio = new JTextField();
        txt_precio.setBackground(new Color(-788743));
        Font txt_precioFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 14, txt_precio.getFont());
        if (txt_precioFont != null) txt_precio.setFont(txt_precioFont);
        txt_precio.setForeground(new Color(-16777216));
        panel_tabla.add(txt_precio, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        btn_editar = new JButton();
        btn_editar.setBackground(new Color(-7239896));
        Font btn_editarFont = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 14, btn_editar.getFont());
        if (btn_editarFont != null) btn_editar.setFont(btn_editarFont);
        btn_editar.setForeground(new Color(-657413));
        btn_editar.setText("Editar");
        panel_tabla.add(btn_editar, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btn_borrar = new JButton();
        btn_borrar.setBackground(new Color(-4179680));
        Font btn_borrarFont = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 14, btn_borrar.getFont());
        if (btn_borrarFont != null) btn_borrar.setFont(btn_borrarFont);
        btn_borrar.setForeground(new Color(-657413));
        btn_borrar.setText("Borrar");
        panel_tabla.add(btn_borrar, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txt_nombre = new JTextField();
        txt_nombre.setBackground(new Color(-788743));
        Font txt_nombreFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 14, txt_nombre.getFont());
        if (txt_nombreFont != null) txt_nombre.setFont(txt_nombreFont);
        txt_nombre.setForeground(new Color(-16777216));
        panel_tabla.add(txt_nombre, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel_tabla.add(spacer1, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel_tabla.add(spacer2, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        scroll = new JScrollPane();
        scroll.setBackground(new Color(-2169870));
        Font scrollFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 14, scroll.getFont());
        if (scrollFont != null) scroll.setFont(scrollFont);
        scroll.setForeground(new Color(-16777216));
        scroll.setHorizontalScrollBarPolicy(30);
        panel_tabla.add(scroll, new GridConstraints(1, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tabla_productos = new JTable();
        tabla_productos.setCellSelectionEnabled(true);
        Font tabla_productosFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 14, tabla_productos.getFont());
        if (tabla_productosFont != null) tabla_productos.setFont(tabla_productosFont);
        tabla_productos.setGridColor(new Color(-15917790));
        tabla_productos.setIntercellSpacing(new Dimension(2, 2));
        tabla_productos.setRowHeight(25);
        tabla_productos.setRowMargin(2);
        tabla_productos.setSelectionBackground(new Color(-13210015));
        scroll.setViewportView(tabla_productos);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel_tabla;
    }

}
