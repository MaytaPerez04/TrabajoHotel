import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexionBD {

    // Cambiá estos datos según tu configuración de MySQL/MariaDB
    private static final String URL = "jdbc:mysql://localhost:3306/hotelgama";
    private static final String USER = "root";       // tu usuario de MySQL
    private static final String PASS = "";           // tu contraseña de MySQL

    // Método para conectarse a la base de datos
    public static Connection conectar() {
        try {
            Connection conexion = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conexión exitosa a la base de datos.");
            return conexion;
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            return null;
        }
    }
}

