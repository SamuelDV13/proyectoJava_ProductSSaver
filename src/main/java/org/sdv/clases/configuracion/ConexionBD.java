package org.sdv.clases.configuracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String url = "jdbc:oracle:thin:@//localhost:1521/XEPDB1";
    private static final String user = "PRODUCTSAVER";
    private static final String password = "ps";

    private static Connection con = null;


    public static Connection getConexion() throws SQLException {
        if(con == null){
            con = DriverManager.getConnection(url, user, password);
        }
        return con;
    }
}
