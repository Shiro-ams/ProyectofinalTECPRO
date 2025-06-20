package Conexion;

import java.sql.Connection;

public class Conexion {
    public static void main(String[] args) {
        try {
            Connection conn = getConexion();
            if (conn != null) {
                System.out.println("Conexión exitosa");
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConexion() throws Exception {
        String url = "jdbc:mysql://localhost:3306/mi_basedatos";
        String user = "root";
        String password = "123456";
        Class.forName("com.mysql.cj.jdbc.Driver"); // O el driver que uses
        return java.sql.DriverManager.getConnection(url, user, password);
    }
}
