import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.SQLException;

public class Hotelgama {
	public static void mostrarMenuSegunRol(Usuario u, Connection conn) {
		String rol = u.getRol();

		if (rol.equalsIgnoreCase("Recepcion")) {
			JOptionPane.showMessageDialog(null, "¡Bienvenido/a al sistema de Recepción, " + u.getNombre() + "!");
			Recepcion.mostrarMenuRecepcion(); 
		} else if (rol.equalsIgnoreCase("Limpieza")) {
			JOptionPane.showMessageDialog(null, "¡Bienvenido/a al sistema de Limpieza, " + u.getNombre() + "!");
			Limpieza.mostrarMenuLimpieza();
		} else if (rol.equalsIgnoreCase("Administrador")) {
			JOptionPane.showMessageDialog(null, "¡Bienvenido/a al sistema de Administrador, " + u.getNombre() + "!");
			Administrador.mostrarMenuAdministrador(u);
		} else {
			JOptionPane.showMessageDialog(null, "Rol no reconocido.");
		}
	}

	public static void main(String[] args) {
		Connection conn = conexionBD.conectar();
		if (conn == null) {
			JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
			return;
		}
		Usuario usuario = Login.iniciarSesion();
		if (usuario != null) {
			mostrarMenuSegunRol(usuario, conn);
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
