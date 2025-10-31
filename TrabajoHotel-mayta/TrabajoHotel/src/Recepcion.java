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
		Connection conn = conexionBD.conectar();
		if (conn == null)
			return;

		try {
			String dni= JOptionPane.showInputDialog("Ingrese el DNI del titular de la reserva a editar");
			
			PreparedStatement psConsulta = conn.prepareStatement("SELECT `idHuesped`, `num_identificacion`, "
					+ "`nombre` FROM `huesped` WHERE `num_identificacion` = ?");
			psConsulta.setString(1, dni);
			var rs = psConsulta.executeQuery();
			
			if (!rs.next()) {
				JOptionPane.showMessageDialog(null, "No se encontró la reserva con ese DNI.");
				return;
			}
			
			int idHuesped = rs.getInt("idHuesped");
			String nombre = rs.getString("nombre");
			
			PreparedStatement psReserva = conn.prepareStatement("SELECT * FROM `reserva` where `fk_idHuesped` = ?");
			psReserva.setInt(1, idHuesped);
			var dres= psReserva.executeQuery();
			
			
			int idReserva = dres.getInt("idReserva");
			int habitacion = dres.getInt("fk_idHabitacion");
			String fechai = dres.getString("fecha_ini_res");
			String fechaf = dres.getString("fecha_fin_res");
			
			String[] opciones = { "Editar fechas", "Cambiar titular" };

			int seleccion = JOptionPane.showOptionDialog(null, "¿Qué desea hacer?", "HotelGama", JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
			
			switch (seleccion) {
			case 0:
				LocalDate fechaIniRes = null;
				try {
					int diaRes = Integer.parseInt(JOptionPane.showInputDialog("Día de inicio de la reserva:"));
					int mesRes = Integer.parseInt(JOptionPane.showInputDialog("Mes de inicio de la reserva:"));
					int anoRes = Integer.parseInt(JOptionPane.showInputDialog("Año de inicio de la reserva:"));
					fechaIniRes = LocalDate.of(anoRes, mesRes, diaRes);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Fecha inválida.");	
				}
				
				int cantDias = 0;
				try {
					cantDias = Integer.parseInt(JOptionPane.showInputDialog("Cantidad de días de la estadía:"));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Cantidad de días inválida.");
				}
				
				LocalDate fechaFinRes = fechaIniRes.plusDays(cantDias);
				
				PreparedStatement psEditaFecha = conn.prepareStatement("UPDATE `reserva` SET `fecha_ini_res`=? WHERE `idReserva` = ?");
				psEditaFecha.setDate(1, java.sql.Date.valueOf(fechaIniRes));
				psEditaFecha.setDate(2, java.sql.Date.valueOf(fechaFinRes));
				psEditaFecha.setInt(3, idReserva);
				psEditaFecha.executeUpdate();

				
				break;
				
			case 1:
				String tipoIdentificacion = "";
				String numIdentificacion = "";

				String nNombre=null;
				
				while (nNombre.isEmpty()) {
					nNombre = JOptionPane.showInputDialog("Nombre del titular de la reserva:");
					if (nNombre == null || nNombre.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Debe completar el nombre del titular.");
					
					}
				}
				
				String[] tiposIdent = { "DNI", "Pasaporte" };
				int tipoIdent = JOptionPane.showOptionDialog(null, "Seleccione tipo de identificación:", "Identificación",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, tiposIdent, tiposIdent[0]);
				if (tipoIdent == -1)
					return;
				tipoIdentificacion = tiposIdent[tipoIdent];

				while (true) {
					numIdentificacion = JOptionPane.showInputDialog("Ingrese el número de identificación del titular:");
					if (numIdentificacion == null || numIdentificacion.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Debe completar el número de identificación.");
						continue;
					}
					if ((tipoIdentificacion.equals("DNI") && numIdentificacion.matches("\\d{8}"))
							|| (tipoIdentificacion.equals("Pasaporte") && numIdentificacion.matches("[A-Za-z0-9]{9}"))) {
						break;
					} else {
						JOptionPane.showMessageDialog(null, "Número de identificación inválido para el tipo seleccionado.");
					}
				}
				
				String titularViejo = null;
				PreparedStatement psEditarTitular = conn.prepareStatement("UPDATE `huesped` SET `tit_res`='?' WHERE idHuesped = ?");
				psEditarTitular.setString(1, titularViejo);
				psEditarTitular.setInt(2, idHuesped);
				psEditarTitular.executeQuery();
				
				PreparedStatement psNuevoTitular = conn.prepareStatement("INSERT INTO `huesped`"
						+ "(`nombre`, `tit_res`, `tipo_identificacion`, `num_identificacion`) "
						+ "VALUES ('?','TITULAR','?','?')");
				psNuevoTitular.setString(1, nNombre);
				psNuevoTitular.setString(2, tipoIdentificacion);
				psNuevoTitular.setString(3, numIdentificacion);
				psNuevoTitular.executeQuery();
				
				PreparedStatement encNuevoTitular = conn.prepareStatement("SELECT `idHuesped` FROM `huesped` WHERE  num_identificacion = '?'");
				encNuevoTitular.setString(1, numIdentificacion);
				var rnt = encNuevoTitular.executeQuery();
				
				int idHuespedN = rnt.getInt("idHuesped");
				
				PreparedStatement nIDTit = conn.prepareStatement("UPDATE `reserva` SET `fk_idHuesped`= ? WHERE fk_idHuesped = ?");
				nIDTit.setInt(1, idHuespedN);
				nIDTit.setInt(2, idHuesped);
				nIDTit.executeQuery();
				


				

			}

			
			
		} catch (SQLException e) {}
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
