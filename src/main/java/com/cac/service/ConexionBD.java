package com.cac.service;

import java.sql.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // Configuración de la conexión a tu base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/db_hotel";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "71-42";

    // Método para obtener una conexión a la base de datos
    public static Connection obtenerConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error al cargar el controlador de MySQL", e);
        }
    }

    // Método para cerrar la conexión
    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                // Manejo de errores al cerrar la conexión
                e.printStackTrace();
            }
        }
    }
}
