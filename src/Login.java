import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Login {

	public static Usuario iniciarSesion() {
		Connection conn = null;
		String nombreUsuario;
		String contrasena;

		while (true) { // Ciclo infinito hasta que el login sea exitoso o el usuario cancele
			// Pedir el nombre de usuario
			nombreUsuario = JOptionPane.showInputDialog("Ingrese su nombre de usuario:");
			// Si el usuario presiona Cancelar, se termina el programa
			if (nombreUsuario == null) {
				System.exit(0); // Cierra el programa
			}

			// Pedir la contraseña
			contrasena = JOptionPane.showInputDialog("Ingrese su contraseña:");
			// Si el usuario presiona Cancelar, se termina el programa
			if (contrasena == null) {

				System.exit(0); // Cierra el programa
			}

			// Intentar conectar con la base de datos
			conn = conexionBD.conectar();
			if (conn == null) {
				JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.");
				return null; // Salimos si no se pudo conectar a la base de datos
			}

			try {
				String sql = "SELECT * FROM usuario WHERE usuario = ? AND contrasena = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, nombreUsuario);
				ps.setString(2, contrasena);

				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					String rol = rs.getString("rol"); // Obtener el rol del usuario
					return new Usuario(nombreUsuario, rol); // Si los datos son correctos, retornamos el usuario
				} else {
					JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
					// El ciclo vuelve a pedir los datos
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
