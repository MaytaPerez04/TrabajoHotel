package modelo;

import java.sql.*;

import javax.swing.SwingUtilities;

import Administrador.AdminFrame;
import modelo.Usuario;
import controlador.ConexionBD;
public class Administrador {

	// Crear usuario
	public static void crearUsuario(String nombre, String usuario, String contrasena, String rol) throws Exception {
		Connection conn = ConexionBD.conectar();
		if (conn == null)
			throw new Exception("No se pudo conectar a la base de datos.");

		String sql = "INSERT INTO usuario (nombre, usuario, contrasena, rol) VALUES (?, ?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, nombre);
			ps.setString(2, usuario);
			ps.setString(3, contrasena);
			ps.setString(4, rol);
			ps.executeUpdate();
		} finally {
			conn.close();
		}
	}

	// Editar usuario
	public static void editarUsuario(int id, String nombre, String usuario, String contrasena, String rol)
			throws Exception {
		Connection conn = ConexionBD.conectar();
		if (conn == null)
			throw new Exception("No se pudo conectar a la base de datos.");

		String sql = "UPDATE usuario SET nombre = ?, usuario = ?, contrasena = ?, rol = ? WHERE idUsuario = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, nombre);
			ps.setString(2, usuario);
			ps.setString(3, contrasena);
			ps.setString(4, rol);
			ps.setInt(5, id);
			int rows = ps.executeUpdate();
			if (rows == 0)
				throw new Exception("Usuario no encontrado con ID: " + id);
		} finally {
			conn.close();
		}
	}

	// Eliminar usuario
	public static void eliminarUsuario(int id) throws Exception {
		Connection conn = ConexionBD.conectar();
		if (conn == null)
			throw new Exception("No se pudo conectar a la base de datos.");

		String sql = "DELETE FROM usuario WHERE idUsuario = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			int rows = ps.executeUpdate();
			if (rows == 0)
				throw new Exception("Usuario no encontrado con ID: " + id);
		} finally {
			conn.close();
		}
	}

	// Listar usuarios
	public static String getListaUsuarios() throws Exception {
		Connection conn = ConexionBD.conectar();
		if (conn == null)
			throw new Exception("No se pudo conectar a la base de datos.");

		StringBuilder lista = new StringBuilder();
		String sql = "SELECT * FROM usuario";
		try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			boolean hasUsers = false;
			while (rs.next()) {
				hasUsers = true;
				lista.append("ID: ").append(rs.getInt("idUsuario")).append(" | Nombre: ").append(rs.getString("nombre"))
						.append(" | Usuario: ").append(rs.getString("usuario")).append(" | Rol: ")
						.append(rs.getString("rol")).append("\n");
			}
			return hasUsers ? lista.toString() : "No hay usuarios registrados.";
		} finally {
			conn.close();
		}
	}



//---------------- MENU ADMINISTRADOR ----------------

// Método para abrir el panel de administración
	public static void mostrarMenuAdministrador(Usuario u) {
		SwingUtilities.invokeLater(() -> new AdminFrame(u));
	}

// ---------------- HABITACIONES ----------------
// Puedes agregar aquí métodos como agregarHabitacion, modificarHabitacion, etc.
	public static void agregarHabitacion() {
		// Implementación
	}

	public static void modificarHabitacion() {
		// Implementación
	}

	public static void eliminarHabitacion() {
		// Implementación
	}

	public static void listarHabitaciones() {
		// Implementación
	}

// ---------------- FINANZAS ----------------
	public static void gestionFinanciera() {
		// Implementación
	}
}
