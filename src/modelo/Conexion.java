package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private Connection connection;     
    
    private final String url = "jdbc:mysql://localhost/iglesias";
 //private final String url = "jdbc:mysql://localhost:3306/bitacora?serverTimezone=UTC";
    private final String user = "root";
    private final String pass = "madeleine";
   
   
  
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void establecerConexion() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
                 e.printStackTrace();
        } catch (SQLException e) {
                 e.printStackTrace();
        }
    }

    public void cerrarConexion() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
