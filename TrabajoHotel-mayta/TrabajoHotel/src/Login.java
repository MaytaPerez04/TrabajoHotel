import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Login {

    public static Usuario iniciarSesion() {
        String user = JOptionPane.showInputDialog("Ingrese su usuario:");
        String pass = JOptionPane.showInputDialog("Ingrese su contraseña:");

        Connection conn = conexionBD.conectar();
        if (conn == null) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.");
            return null;
        }

        try {
            String sql = "SELECT * FROM usuario WHERE usuario=? AND contrasena=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int idUsuario = rs.getInt("idUsuario");
                String nombre = rs.getString("nombre");
                String rol = rs.getString("rol");

                return new Usuario(idUsuario, nombre, user, pass, rol);
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la consulta: " + e.getMessage());
        }

        return null;
    }
}

