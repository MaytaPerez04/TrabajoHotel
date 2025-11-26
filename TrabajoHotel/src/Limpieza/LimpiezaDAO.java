package Limpieza;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import controlador.ConexionBD;

public class LimpiezaDAO {

	// Obtener habitaciones con estado 'limpiar' o 'limpiando'
	public static List<String> getHabitacionesPendientes() {
		List<String> lista = new ArrayList<>();

		try (Connection conn = ConexionBD.conectar()) {
			String sql = "SELECT idHabitacion, tipo FROM habitacion WHERE estadoLimpieza IN ('limpiar', 'limpiando')";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				lista.add("ID: " + rs.getInt("idHabitacion") + " - Tipo: " + rs.getString("tipo"));
			}

		} catch (SQLException e) {
			System.out.println("Error al obtener habitaciones pendientes: " + e.getMessage());
		}

		return lista;
	}

	// Obtener habitaciones en estado 'limpiar'
	public static List<String> getHabitacionesParaLimpiando() {
		List<String> lista = new ArrayList<>();

		try (Connection conn = ConexionBD.conectar()) {
			String sql = "SELECT idHabitacion, tipo FROM habitacion WHERE estadoLimpieza='limpiar'";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				lista.add("ID: " + rs.getInt("idHabitacion") + " - Tipo: " + rs.getString("tipo"));
			}

		} catch (SQLException e) {
			System.out.println("Error al obtener habitaciones para limpiando: " + e.getMessage());
		}

		return lista;
	}

	// Obtener habitaciones en estado 'limpiando'
	public static List<String> getHabitacionesParaLimpia() {
		List<String> lista = new ArrayList<>();

		try (Connection conn = ConexionBD.conectar()) {
			String sql = "SELECT idHabitacion, tipo FROM habitacion WHERE estadoLimpieza='limpiando'";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				lista.add("ID: " + rs.getInt("idHabitacion") + " - Tipo: " + rs.getString("tipo"));
			}

		} catch (SQLException e) {
			System.out.println("Error al obtener habitaciones en limpieza: " + e.getMessage());
		}

		return lista;
	}

	// Cambiar estado de la habitación
	public static boolean actualizarEstado(int id, String nuevoEstado) {
		try (Connection conn = ConexionBD.conectar()) {
			String sql = "UPDATE habitacion SET estadoLimpieza = ? WHERE idHabitacion = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, nuevoEstado);
			ps.setInt(2, id);

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			System.out.println("Error al actualizar estado de habitación: " + e.getMessage());
		}
		return false;
	}

	// Obtener TODAS las habitaciones con su estado de limpieza
	public static List<String> getTodasHabitaciones() {
		List<String> lista = new ArrayList<>();

		try (Connection conn = ConexionBD.conectar()) {
			String sql = "SELECT idHabitacion, tipo, estado, estadoLimpieza FROM habitacion";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				lista.add("ID: " + rs.getInt("idHabitacion") + " - Tipo: " + rs.getString("tipo") + " - Estado: "
						+ rs.getString("estado") + " - Limpieza: " + rs.getString("estadoLimpieza"));
			}

		} catch (SQLException e) {
			System.out.println("Error al obtener todas las habitaciones: " + e.getMessage());
		}

		return lista;
	}
}
