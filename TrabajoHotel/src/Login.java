import java.sql.*;

import javax.swing.JOptionPane;

public class Login {

    public static Usuario iniciarSesion() {
        String nombreUsuario = JOptionPane.showInputDialog("Ingrese su nombre de usuario:");
        String contrasena = JOptionPane.showInputDialog("Ingrese su contraseña:");

        Connection conn = conexionBD.conectar();
        if (conn == null) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.");
            return null;
        }

        try {
            String sql = "SELECT * FROM usuario WHERE usuario = ? AND contrasena = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasena);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String rol = rs.getString("rol"); // o el nombre de la columna donde tengas el rol
                return new Usuario(nombreUsuario, rol);
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


