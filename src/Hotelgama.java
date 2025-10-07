import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Hotelgama {

	public static void mostrarMenuSegunRol(Usuario u) {
		String rol = u.getRol(); // obtiene el rol del usuario

		// Mostrar mensaje de bienvenida por rol antes de mostrar el menú
		if (rol.equalsIgnoreCase("Recepcion")) {
			JOptionPane.showMessageDialog(null, "¡Bienvenido/a al sistema de Recepción, " + u.getNombre() + "!",
					"Recepción", JOptionPane.INFORMATION_MESSAGE);
			menuRecepcion();
		} else if (rol.equalsIgnoreCase("Limpieza")) {
			JOptionPane.showMessageDialog(null, "¡Bienvenido/a al sistema de Limpieza, " + u.getNombre() + "!",
					"Limpieza", JOptionPane.INFORMATION_MESSAGE);
			menuLimpieza();
		} else if (rol.equalsIgnoreCase("Administrador")) {
			JOptionPane.showMessageDialog(null, "¡Bienvenido/a al sistema de Administrador, " + u.getNombre() + "!",
					"Administrador", JOptionPane.INFORMATION_MESSAGE);
			menuAdministrador();
		} else {
			JOptionPane.showMessageDialog(null, "Rol no reconocido.");
		}
	}

	// Menú Recepción
	private static void menuRecepcion() {
		String[] opcRec = { "Check-in", "Check-Out", "Administrar reservas", "Ver pedidos de Room Service", "Cerrar" };
		int resRec;
		do {
			resRec = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Recepción",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcRec, opcRec[0]);

			switch (resRec) {
			case 0:
				JOptionPane.showMessageDialog(null, "Función Check-in aún no implementada");
				break;
			case 1:
				JOptionPane.showMessageDialog(null, "Función Check-out aún no implementada");
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "Administrar reservas aún no implementado");
				break;
			case 3:
				JOptionPane.showMessageDialog(null, "Ver pedidos de Room Service aún no implementado");
				break;
			}
		} while (resRec != 4); // Cierra cuando selecciona "Cerrar"
	}

	// menu limpieza //
	private static void menuLimpieza() {
		String[] opcLim = { "Pedidos de limpieza", "Habitaciones por limpiar", "Marcar habitación como limpia",
				"Cerrar" };
		int resLim;
		do {
			resLim = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Limpieza", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, opcLim, opcLim[0]);

			switch (resLim) {
			case 0:
				mostrarPedidosLimpieza(); // Llamamos a la nueva función para manejar pedidos de limpieza
				break;
			case 1:
				JOptionPane.showMessageDialog(null, "Habitaciones por limpiar aún no implementadas");
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "Marcar habitación como limpia aún no implementado");
				break;
			}
		} while (resLim != 3); // Cierra cuando selecciona "Cerrar"
	}

	private static void mostrarPedidosLimpieza() {
		Connection conn = conexionBD.conectar();
		if (conn == null) {
			JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.");
			return;
		}

		try {
			// Consulta SQL para obtener las habitaciones en estado "limpieza"
			String sql = "SELECT idHabitacion, tipo, estado FROM habitacion WHERE estado = 'limpieza'";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			// Crear un array de cadenas para mostrar en el JOptionPane
			StringBuilder habitaciones = new StringBuilder();
			while (rs.next()) {
				int idHabitacion = rs.getInt("idHabitacion");
				String tipo = rs.getString("tipo");
				habitaciones.append("ID: ").append(idHabitacion).append(" - Tipo: ").append(tipo).append("\n");
			}

			if (habitaciones.length() == 0) {
				JOptionPane.showMessageDialog(null, "No hay habitaciones pendientes de limpieza.");
			} else {
				// Mostrar las habitaciones en un JOptionPane
				String selected = JOptionPane.showInputDialog(null, "Habitaciones pendientes de limpieza:\n"
						+ habitaciones.toString() + "\nSeleccione el ID de la habitación que quiere limpiar:");

				if (selected != null && !selected.isEmpty()) {
					try {
						int idSeleccionada = Integer.parseInt(selected);
						// Actualizar el estado de la habitación a "limpiando"
						actualizarEstadoHabitacion(idSeleccionada, conn);
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "ID incorrecto.");
					}
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al consultar las habitaciones: " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static void actualizarEstadoHabitacion(int idHabitacion, Connection conn) {
		try {
			// Consulta SQL para actualizar el estado de la habitación a "limpiando"
			String updateSql = "UPDATE habitacion SET estado = 'limpiando' WHERE idHabitacion = ?";
			PreparedStatement psUpdate = conn.prepareStatement(updateSql);
			psUpdate.setInt(1, idHabitacion);

			int rowsAffected = psUpdate.executeUpdate();
			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null,
						"La habitación " + idHabitacion + " ya tiene encargados para su limpieza.");
			} else {
				JOptionPane.showMessageDialog(null, "No se encontró la habitación con ID " + idHabitacion);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al actualizar el estado de la habitación: " + e.getMessage());
		}
	}

	// Menú Administrador
	private static void menuAdministrador() {
		String[] opcAdmin = { "Gestionar usuarios", "Administrar futuras reservas", "Ver reservas previas", "Cerrar" };
		int resAdmin;
		do {
			resAdmin = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Administrador",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcAdmin, opcAdmin[0]);

			switch (resAdmin) {
			case 0:
				JOptionPane.showMessageDialog(null, "Gestionar usuarios aún no implementado");
				break;
			case 1:
				JOptionPane.showMessageDialog(null, "Administrar futuras reservas aún no implementado");
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "Ver reservas previas aún no implementado");
				break;
			}
		} while (resAdmin != 3); // Cierra cuando selecciona "Cerrar"
	}
}

