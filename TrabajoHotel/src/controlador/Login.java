package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import modelo.Usuario;

public class Login {

    // Valida usuario y contraseña
    public static Usuario iniciarSesion(String user, String pass) throws Exception {
        Connection conn = ConexionBD.conectar();
        if (conn == null) throw new Exception("No se pudo conectar a la base de datos.");

        try {
            String sql = "SELECT * FROM usuario WHERE usuario=? AND contrasena=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("idUsuario"),
                        rs.getString("nombre"),
                        user,
                        pass,
                        rs.getString("rol")
                );
            } else {
                throw new Exception("Usuario o contraseña incorrectos.");
            }

        } catch (Exception e) {
            throw new Exception("" + e.getMessage());
        }
    }
}