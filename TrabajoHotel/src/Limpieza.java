import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Limpieza {

	public static void mostrarMenuLimpieza() {
		String[] opciones = { "Pedidos de limpieza", // habitaciones en 'limpiar'
				"Marcar como en limpieza", // cambiar 'limpiar' → 'limpiando'
				"Marcar habitación como limpia", // cambiar 'limpiando' → 'limpia'
				"Ver habitaciones por estado", // mostrar todas las habitaciones y su estado de limpieza
				"Cerrar" };
		int opcion;

		do {
			opcion = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Limpieza", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

			switch (opcion) {
			case 0 -> mostrarPedidosLimpieza();
			case 1 -> marcarHabitacionComoEnProceso();
			case 2 -> marcarHabitacionComoLimpia();
			case 3 -> mostrarHabitacionesPorEstado();
			}
		} while (opcion != 4);
	}

	// Mostrar habitaciones pendientes de limpieza (estadoLimpieza='limpiar')
	private static void mostrarPedidosLimpieza() {
		Connection conn = conexionBD.conectar();
		if (conn == null) {
			JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.");
			return;
		}

		try {
			String sql = "SELECT idHabitacion, tipo FROM habitacion WHERE estadoLimpieza='limpiar'";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			StringBuilder habitaciones = new StringBuilder();
			while (rs.next()) {
				habitaciones.append("ID: ").append(rs.getInt("idHabitacion")).append(" - Tipo: ")
						.append(rs.getString("tipo")).append("\n");
			}

			JOptionPane.showMessageDialog(null,
					habitaciones.length() == 0 ? "No hay habitaciones pendientes de limpieza."
							: "Habitaciones pendientes de limpieza:\n" + habitaciones.toString());

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al consultar habitaciones: " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// Cambiar estado de limpieza: 'limpiar' → 'limpiando'
	private static void marcarHabitacionComoEnProceso() {
		Connection conn = conexionBD.conectar();
		if (conn == null)
			return;

		try {
			// Primero mostramos las habitaciones en 'limpiar'
			String sql = "SELECT idHabitacion, tipo FROM habitacion WHERE estadoLimpieza='limpiar'";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			StringBuilder habitaciones = new StringBuilder();
			while (rs.next()) {
				habitaciones.append("ID: ").append(rs.getInt("idHabitacion")).append(" - Tipo: ")
						.append(rs.getString("tipo")).append("\n");
			}

			if (habitaciones.length() == 0) {
				JOptionPane.showMessageDialog(null, "No hay habitaciones pendientes de limpieza.");
				return;
			}

			String input = JOptionPane.showInputDialog(
					"Habitaciones pendientes:\n" + habitaciones + "\nIngrese ID a poner en 'limpiando':");
			if (input != null && !input.isEmpty()) {
				try {
					int idSeleccionado = Integer.parseInt(input);
					String updateSql = "UPDATE habitacion SET estadoLimpieza='limpiando' WHERE idHabitacion=? AND estadoLimpieza='limpiar'";
					PreparedStatement psUpdate = conn.prepareStatement(updateSql);
					psUpdate.setInt(1, idSeleccionado);
					int rows = psUpdate.executeUpdate();

					JOptionPane.showMessageDialog(null, rows > 0 ? "Habitación en proceso de limpieza."
							: "ID no encontrado o no estaba en estado 'limpiar'.");
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "ID inválido.");
				}
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// Cambiar estado de limpieza: 'limpiando' → 'limpia'
	private static void marcarHabitacionComoLimpia() {
		Connection conn = conexionBD.conectar();
		if (conn == null)
			return;

		try {
			String sql = "SELECT idHabitacion, tipo FROM habitacion WHERE estadoLimpieza='limpiando'";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			StringBuilder habitaciones = new StringBuilder();
			while (rs.next()) {
				habitaciones.append("ID: ").append(rs.getInt("idHabitacion")).append(" - Tipo: ")
						.append(rs.getString("tipo")).append("\n");
			}

			if (habitaciones.length() == 0) {
				JOptionPane.showMessageDialog(null, "No hay habitaciones en proceso de limpieza.");
				return;
			}

			String input = JOptionPane.showInputDialog(
					"Habitaciones en limpieza:\n" + habitaciones + "\nIngrese ID a marcar como limpia:");
			if (input != null && !input.isEmpty()) {
				try {
					int idSeleccionado = Integer.parseInt(input);
					String updateSql = "UPDATE habitacion SET estadoLimpieza='limpia' WHERE idHabitacion=? AND estadoLimpieza='limpiando'";
					PreparedStatement psUpdate = conn.prepareStatement(updateSql);
					psUpdate.setInt(1, idSeleccionado);
					int rows = psUpdate.executeUpdate();

					JOptionPane.showMessageDialog(null, rows > 0 ? "Habitación marcada como limpia."
							: "ID no encontrado o no estaba en estado 'limpiando'.");
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "ID inválido.");
				}
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// Mostrar todas las habitaciones y su estado de limpieza
	private static void mostrarHabitacionesPorEstado() {
		Connection conn = conexionBD.conectar();
		if (conn == null)
			return;

		try {
			String sql = "SELECT idHabitacion, tipo, estado, estadoLimpieza FROM habitacion";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			StringBuilder habitaciones = new StringBuilder();
			while (rs.next()) {
				habitaciones.append("ID: ").append(rs.getInt("idHabitacion")).append(" - Tipo: ")
						.append(rs.getString("tipo")).append(" - Estado: ").append(rs.getString("estado"))
						.append(" - Limpieza: ").append(rs.getString("estadoLimpieza")).append("\n");
			}

			JOptionPane.showMessageDialog(null,
					habitaciones.length() == 0 ? "No hay habitaciones registradas." : habitaciones.toString());

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
