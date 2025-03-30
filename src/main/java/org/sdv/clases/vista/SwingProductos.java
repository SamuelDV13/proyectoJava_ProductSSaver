package org.sdv.clases.vista;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.sdv.clases.modelo.Producto;
import org.sdv.clases.repositorio.ProductoRepositorio;
import org.sdv.clases.servicio.ProductoServicio;
import org.sdv.clases.util.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SwingProductos extends JFrame {
    private JPanel panel_principal;
    private JTextField txt_codigo;
    private JLabel lbl_codigo;
    private JButton btn_generar;
    private JLabel lbl_titulo;
    private JLabel lbl_nombre;
    private JTextField txt_nombre;
    private JLabel lbl_precio;
    private JTextField txt_precio;
    private JLabel lbl_stock;
    private JTextField txt_stock;
    private JButton btn_agregar;
    private JButton btn_visualizar;
    private final JMenuBar menuBar;
    private JMenu menu_conf;
    private JMenuItem menuitem_ruta;

    private final ProductoServicio productoServicio;

    public SwingProductos() {
        //Codigo de creacion
        getContentPane().add(panel_principal);

        setTitle("Productos CRUD");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setSize(443, 524);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        //Codigo extra
        productoServicio = new ProductoServicio(new ProductoRepositorio());

        menuBar = new JMenuBar();
        menu_conf = new JMenu("Configuracion");
        menuitem_ruta = new JMenuItem("Ruta de archivo");
        menuBar.add(menu_conf);
        menu_conf.add(menuitem_ruta);
        setJMenuBar(menuBar);

        initEvent();
    }


    private void initEvent() {
        assert btn_agregar != null;
        btn_agregar.addActionListener(this::btn_agregarActionPerformed);
        assert btn_generar != null;
        btn_generar.addActionListener(this::btn_generarActionPerformed);
        assert btn_visualizar != null;
        btn_visualizar.addActionListener(this::btn_visualizarActionPerformed);
        assert menuitem_ruta != null;
        menuitem_ruta.addActionListener(this::menuitem_rutaActionPerformed);
    }


    //Metodos de eventos
    private void btn_agregarActionPerformed(ActionEvent evt) {

        try {

            if (validarCampos()) {
                long codigo = Long.parseLong(txt_codigo.getText());
                String nombre = txt_nombre.getText();
                double precio = Double.parseDouble(txt_precio.getText());
                int stock = Integer.parseInt(txt_stock.getText());

                Producto producto = new Producto(codigo, nombre, precio, stock);
                productoServicio.insertar(producto);
                JOptionPane.showMessageDialog(this, "Producto  agregado exitosamente", "Producto", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la base de datos\n" + e.getMessage(), "Error base de datos", JOptionPane.ERROR_MESSAGE);
        }
        limpiarCampos();
    }


    private void btn_visualizarActionPerformed(ActionEvent evt) {
        try {
            SwingTablaProducto swingTablaProducto = new SwingTablaProducto(productoServicio);
            swingTablaProducto.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la base de datos\n" + e.getMessage(), "Error base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_generarActionPerformed(ActionEvent evt) {

        try {
            ExportadorFactory<Producto> exportadorFactory = new ExportadorFactory<>();
            DataExportable<Producto> exportable = exportadorFactory.getExportar();
            exportable.exportarDatos(productoServicio.listar());

            JOptionPane.showMessageDialog(this, "Archivo  generado exitosamente", "Producto", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la base de datos\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (exportableException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "Error al exportar los datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
        limpiarCampos();
    }

    private void menuitem_rutaActionPerformed(ActionEvent evt) {
        SwingOpciones swingOpciones = new SwingOpciones();
        swingOpciones.setVisible(true);
    }

    //Metodos internos

    private void limpiarCampos() {
        txt_codigo.setText("");
        txt_nombre.setText("");
        txt_precio.setText("");
        txt_stock.setText("");
    }

    private void despintarCampos() {
        txt_codigo.setBackground(new Color(243, 246, 249));
        txt_nombre.setBackground(new Color(243, 246, 249));
        txt_precio.setBackground(new Color(243, 246, 249));
        txt_stock.setBackground(new Color(243, 246, 249));
    }

    private boolean validarCampos() {

        try {

            long codigo = Long.parseLong(txt_codigo.getText());
            String nombre = txt_nombre.getText();
            double precio = Double.parseDouble(txt_precio.getText());
            int stock = Integer.parseInt(txt_stock.getText());

            List<String> errores = new ArrayList<>();

            if (codigo <= 0) {
                errores.add("El código no es valido.");
                txt_codigo.setBackground(new Color(192, 57, 32));
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


    public static void main(String[] args) {
        new SwingProductos().setVisible(true);
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
        panel_principal = new JPanel();
        panel_principal.setLayout(new GridLayoutManager(15, 3, new Insets(0, 0, 0, 0), 5, 8));
        panel_principal.setBackground(new Color(-2301216));
        panel_principal.setEnabled(true);
        panel_principal.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        lbl_codigo = new JLabel();
        Font lbl_codigoFont = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 14, lbl_codigo.getFont());
        if (lbl_codigoFont != null) lbl_codigo.setFont(lbl_codigoFont);
        lbl_codigo.setForeground(new Color(-16777216));
        lbl_codigo.setText("Código:");
        panel_principal.add(lbl_codigo, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel_principal.add(spacer1, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel_principal.add(spacer2, new GridConstraints(14, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        lbl_nombre = new JLabel();
        Font lbl_nombreFont = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 14, lbl_nombre.getFont());
        if (lbl_nombreFont != null) lbl_nombre.setFont(lbl_nombreFont);
        lbl_nombre.setForeground(new Color(-16777216));
        lbl_nombre.setText("Nombre:");
        panel_principal.add(lbl_nombre, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txt_nombre = new JTextField();
        txt_nombre.setBackground(new Color(-788743));
        Font txt_nombreFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 14, txt_nombre.getFont());
        if (txt_nombreFont != null) txt_nombre.setFont(txt_nombreFont);
        txt_nombre.setForeground(new Color(-16777216));
        panel_principal.add(txt_nombre, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lbl_precio = new JLabel();
        Font lbl_precioFont = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 14, lbl_precio.getFont());
        if (lbl_precioFont != null) lbl_precio.setFont(lbl_precioFont);
        lbl_precio.setForeground(new Color(-16777216));
        lbl_precio.setText("Precio:");
        panel_principal.add(lbl_precio, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txt_precio = new JTextField();
        txt_precio.setBackground(new Color(-788743));
        Font txt_precioFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 14, txt_precio.getFont());
        if (txt_precioFont != null) txt_precio.setFont(txt_precioFont);
        txt_precio.setForeground(new Color(-16777216));
        panel_principal.add(txt_precio, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lbl_stock = new JLabel();
        Font lbl_stockFont = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 14, lbl_stock.getFont());
        if (lbl_stockFont != null) lbl_stock.setFont(lbl_stockFont);
        lbl_stock.setForeground(new Color(-16777216));
        lbl_stock.setText("Stock:");
        panel_principal.add(lbl_stock, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txt_stock = new JTextField();
        txt_stock.setBackground(new Color(-788743));
        Font txt_stockFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 14, txt_stock.getFont());
        if (txt_stockFont != null) txt_stock.setFont(txt_stockFont);
        txt_stock.setForeground(new Color(-16777216));
        panel_principal.add(txt_stock, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel_principal.add(spacer3, new GridConstraints(10, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel_principal.add(spacer4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        btn_generar = new JButton();
        btn_generar.setBackground(new Color(-8529762));
        Font btn_generarFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 14, btn_generar.getFont());
        if (btn_generarFont != null) btn_generar.setFont(btn_generarFont);
        btn_generar.setForeground(new Color(-16777216));
        btn_generar.setText("GENERAR");
        panel_principal.add(btn_generar, new GridConstraints(13, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btn_agregar = new JButton();
        btn_agregar.setBackground(new Color(-10640936));
        Font btn_agregarFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 14, btn_agregar.getFont());
        if (btn_agregarFont != null) btn_agregar.setFont(btn_agregarFont);
        btn_agregar.setForeground(new Color(-16777216));
        btn_agregar.setText("AGREGAR");
        panel_principal.add(btn_agregar, new GridConstraints(11, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbl_titulo = new JLabel();
        Font lbl_tituloFont = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 18, lbl_titulo.getFont());
        if (lbl_tituloFont != null) lbl_titulo.setFont(lbl_tituloFont);
        lbl_titulo.setForeground(new Color(-16777216));
        lbl_titulo.setText("Productos CRUD");
        panel_principal.add(lbl_titulo, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel_principal.add(spacer5, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        btn_visualizar = new JButton();
        btn_visualizar.setBackground(new Color(-2579641));
        Font btn_visualizarFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 14, btn_visualizar.getFont());
        if (btn_visualizarFont != null) btn_visualizar.setFont(btn_visualizarFont);
        btn_visualizar.setForeground(new Color(-16777216));
        btn_visualizar.setText("VER PRODUCTOS");
        panel_principal.add(btn_visualizar, new GridConstraints(12, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txt_codigo = new JTextField();
        txt_codigo.setBackground(new Color(-788743));
        Font txt_codigoFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 14, txt_codigo.getFont());
        if (txt_codigoFont != null) txt_codigo.setFont(txt_codigoFont);
        txt_codigo.setForeground(new Color(-16777216));
        panel_principal.add(txt_codigo, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
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
        return panel_principal;
    }

}
