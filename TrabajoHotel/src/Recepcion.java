import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

public class Recepcion {

	public static void mostrarMenuRecepcion() {
		String[] opciones = { "Editar reserva", "Cancelar reserva", "Ver disponibilidad de habitaciones",
				"Generar factura o recibo", "Cerrar" };
		int opcion;

		do {
			opcion = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Recepción",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

			switch (opcion) {
			case 0 -> editarReserva(); // Editar reserva
			case 1 -> cancelarReserva(); // Cancelar reserva
			case 2 -> verDisponibilidadHabitaciones();// Ver disponibilidad
			case 3 -> generarFactura(); // Generar factura o recibo
			}

		} while (opcion != 4); // 4 es "Cerrar"
	}

	public static void editarReserva() {
		JOptionPane.showMessageDialog(null, "Función editar reserva aún no implementada");
	}

	public static void cancelarReserva() {
		Connection conn = conexionBD.conectar();
		if (conn == null)
			return;

		try {
			// Pedir ID de la reserva
			String inputId = JOptionPane.showInputDialog("Ingrese el ID de la reserva a cancelar:");
			if (inputId == null || inputId.trim().isEmpty())
				return;

			int idReserva;
			try {
				idReserva = Integer.parseInt(inputId);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "ID inválido.");
				return;
			}

			// Consultar la reserva para obtener idHuesped y idHabitacion
			PreparedStatement psConsulta = conn
					.prepareStatement("SELECT fk_idhuesped, fk_idhabitacion FROM reserva WHERE idreserva = ?");
			psConsulta.setInt(1, idReserva);
			var rs = psConsulta.executeQuery();

			if (!rs.next()) {
				JOptionPane.showMessageDialog(null, "No se encontró la reserva con ese ID.");
				return;
			}

			int idHuesped = rs.getInt("fk_idhuesped");
			int idHabitacion = rs.getInt("fk_idhabitacion");

			// Confirmación de cancelación
			int confirm = JOptionPane.showConfirmDialog(null,
					"¿Está seguro que desea cancelar la reserva ID " + idReserva + "?", "Confirmar cancelación",
					JOptionPane.YES_NO_OPTION);
			if (confirm != JOptionPane.YES_OPTION)
				return;

			// 1. Eliminar la reserva
			PreparedStatement psEliminarRes = conn.prepareStatement("DELETE FROM reserva WHERE idreserva = ?");
			psEliminarRes.setInt(1, idReserva);
			psEliminarRes.executeUpdate();

			// 2. Eliminar el/los huéspedes asociados
			PreparedStatement psEliminarHuesped = conn.prepareStatement("DELETE FROM huesped WHERE idhuesped = ?");
			psEliminarHuesped.setInt(1, idHuesped);
			psEliminarHuesped.executeUpdate();

			// 3. Liberar la habitación
			PreparedStatement psLiberarHab = conn
					.prepareStatement("UPDATE habitacion SET estado = 'libre' WHERE idHabitacion = ?");
			psLiberarHab.setInt(1, idHabitacion);
			psLiberarHab.executeUpdate();

			JOptionPane.showMessageDialog(null, "Reserva cancelada y habitación liberada correctamente.");

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al cancelar la reserva: " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void verDisponibilidadHabitaciones() {
		Connection conn = conexionBD.conectar();
		if (conn == null)
			return;

		try {
			// Consulta todas las habitaciones con su estado y tipo
			PreparedStatement ps = conn
					.prepareStatement("SELECT idHabitacion, tipo, estado FROM habitacion ORDER BY tipo, idHabitacion");
			var rs = ps.executeQuery();

			StringBuilder disponibilidad = new StringBuilder();
			disponibilidad.append("Habitaciones disponibles:\n\n");

			while (rs.next()) {
				int idHab = rs.getInt("idHabitacion");
				String tipo = rs.getString("tipo");
				String estado = rs.getString("estado");
				disponibilidad.append("Habitación #").append(idHab).append(" - Tipo: ").append(tipo)
						.append(" - Estado: ").append(estado).append("\n");
			}

			JOptionPane.showMessageDialog(null, disponibilidad.toString());

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al consultar disponibilidad: " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void generarFactura() {
		Connection conn = conexionBD.conectar();
		if (conn == null)
			return;

		try {
			// Pedir al usuario el ID de la reserva
			String inputId = JOptionPane.showInputDialog("Ingrese el ID de la reserva para generar la factura:");
			if (inputId == null || inputId.trim().isEmpty())
				return;

			int idReserva;
			try {
				idReserva = Integer.parseInt(inputId);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "ID de reserva inválido.");
				return;
			}

			// Consulta de la reserva
			PreparedStatement ps = conn
					.prepareStatement("SELECT r.idreserva, r.fecha_ini_res, r.fecha_fin_res, r.tipo, "
							+ "h.idHabitacion, h.tipo AS tipoHab, h.precio, hu.nombre AS titular " + "FROM reserva r "
							+ "JOIN huesped hu ON r.fk_idhuesped = hu.idhuesped "
							+ "JOIN habitacion h ON r.fk_idhabitacion = h.idHabitacion " + "WHERE r.idreserva = ?");
			ps.setInt(1, idReserva);

			var rs = ps.executeQuery();
			if (rs.next()) {
				String titular = rs.getString("titular");
				LocalDate fechaIni = rs.getDate("fecha_ini_res").toLocalDate();
				LocalDate fechaFin = rs.getDate("fecha_fin_res").toLocalDate();
				String tipoHabitacion = rs.getString("tipoHab");
				double precioHabitacion = rs.getDouble("precio");

				long dias = java.time.temporal.ChronoUnit.DAYS.between(fechaIni, fechaFin);
				double total = dias * precioHabitacion;

				StringBuilder factura = new StringBuilder();
				factura.append("===== FACTURA =====\n");
				factura.append("Titular: ").append(titular).append("\n");
				factura.append("Reserva ID: ").append(idReserva).append("\n");
				factura.append("Fecha inicio: ").append(fechaIni).append("\n");
				factura.append("Fecha fin: ").append(fechaFin).append("\n");
				factura.append("Habitación: ").append(tipoHabitacion).append("\n");
				factura.append("Días: ").append(dias).append("\n");
				factura.append("Precio por día: $").append(precioHabitacion).append("\n");
				factura.append("Total a pagar: $").append(total).append("\n");
				factura.append("==================");

				JOptionPane.showMessageDialog(null, factura.toString());
			} else {
				JOptionPane.showMessageDialog(null, "No se encontró la reserva con ID: " + idReserva);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al generar la factura: " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
