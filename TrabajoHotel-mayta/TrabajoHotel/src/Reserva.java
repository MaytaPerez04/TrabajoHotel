import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JOptionPane;

public class Reserva {

	public static void crearReservaIndividual() {
		boolean reservaConfirmada = false;

		while (!reservaConfirmada) {
			// 1. Tipo de reserva
			String[] opcTipoRes = { "Individuo", "Delegación" };
			int tipoRes = JOptionPane.showOptionDialog(null, "Seleccione el tipo de reserva:", "Reserva",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcTipoRes, opcTipoRes[0]);

			if (tipoRes == -1)
				return; // si cierra el diálogo

			String tipoReserva = opcTipoRes[tipoRes];
			int cantHuespedes = 0;
			String tipoHabitacion = "";
			String[] nombresHuespedes = null;
			String titRes = "";
			String tipoIdentificacion = "";
			String numIdentificacion = "";

			// 2. Datos según tipo de reserva
			if (tipoReserva.equals("Individuo")) {
				// Cantidad de huéspedes
				do {
					try {
						cantHuespedes = Integer
								.parseInt(JOptionPane.showInputDialog("¿Cuántos huéspedes van a hospedarse? (máx 2)"));
						if (cantHuespedes < 1 || cantHuespedes > 2) {
							JOptionPane.showMessageDialog(null, "Debe ser entre 1 y 2 huéspedes.");
						}
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Ingrese un número válido.");
						cantHuespedes = 0;
					}
				} while (cantHuespedes < 1 || cantHuespedes > 2);

				// Tipo de habitación
				String[] tiposHab = { "SIMPLE", "SUITE" };
				int tipoHab = JOptionPane.showOptionDialog(null, "Seleccione tipo de habitación:", "Habitación",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, tiposHab, tiposHab[0]);
				if (tipoHab == -1)
					return;
				tipoHabitacion = tiposHab[tipoHab];

			} else if (tipoReserva.equals("Delegación")) {
				// Delegación: habitaciones siempre tipo DOBLE
				tipoHabitacion = "DOBLE";

				// Cantidad de huéspedes por habitación (3 a 4)
				do {
					try {
						cantHuespedes = Integer.parseInt(JOptionPane
								.showInputDialog("¿Cuántos huéspedes van a hospedarse? (3 a 4 por habitación)"));
						if (cantHuespedes < 3 || cantHuespedes > 4) {
							JOptionPane.showMessageDialog(null, "Debe ser entre 3 y 4 huéspedes por habitación.");
						}
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Ingrese un número válido.");
						cantHuespedes = 0;
					}
				} while (cantHuespedes < 3 || cantHuespedes > 4);
			}

			// 3. Nombres de los huéspedes
			nombresHuespedes = new String[cantHuespedes];
			for (int i = 0; i < cantHuespedes; i++) {
				nombresHuespedes[i] = JOptionPane.showInputDialog("Ingrese nombre del huésped #" + (i + 1));
				if (nombresHuespedes[i] == null || nombresHuespedes[i].isEmpty()) {
					JOptionPane.showMessageDialog(null, "Debe completar todos los nombres.");
					i--; // repetir este índice
				}
			}

			// 4. Nombre del titular de la reserva
			titRes = JOptionPane.showInputDialog("Nombre del titular de la reserva:");
			if (titRes == null || titRes.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Debe completar el nombre del titular.");
				continue;
			}

			// 5. Tipo de identificación
			String[] tiposIdent = { "DNI", "Pasaporte" };
			int tipoIdent = JOptionPane.showOptionDialog(null, "Seleccione tipo de identificación:", "Identificación",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, tiposIdent, tiposIdent[0]);
			if (tipoIdent == -1)
				return;
			tipoIdentificacion = tiposIdent[tipoIdent];

			// 6. Número de identificación con validación
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

			// 7. Fecha de inicio
			LocalDate fechaIniRes = null;
			try {
				int diaRes = Integer.parseInt(JOptionPane.showInputDialog("Día de inicio de la reserva:"));
				int mesRes = Integer.parseInt(JOptionPane.showInputDialog("Mes de inicio de la reserva:"));
				int anoRes = Integer.parseInt(JOptionPane.showInputDialog("Año de inicio de la reserva:"));
				fechaIniRes = LocalDate.of(anoRes, mesRes, diaRes);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Fecha inválida.");
				continue;
			}

			// 8. Cantidad de días
			int cantDias = 0;
			try {
				cantDias = Integer.parseInt(JOptionPane.showInputDialog("Cantidad de días de la estadía:"));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Cantidad de días inválida.");
				continue;
			}

			LocalDate fechaFinRes = fechaIniRes.plusDays(cantDias);

			// 9. Confirmación final
			StringBuilder resumen = new StringBuilder();
			resumen.append("Tipo de reserva: ").append(tipoReserva).append("\n");
			resumen.append("Titular: ").append(titRes).append("\n");
			resumen.append("Tipo de identificación: ").append(tipoIdentificacion).append("\n");
			resumen.append("Número de identificación: ").append(numIdentificacion).append("\n");
			resumen.append("Cantidad de huéspedes: ").append(cantHuespedes).append("\n");
			resumen.append("Tipo de habitación: ").append(tipoHabitacion).append("\n");
			resumen.append("Huéspedes: ");
			for (String h : nombresHuespedes) {
				resumen.append(h).append(" ");
			}
			resumen.append("\n");
			resumen.append("Fecha inicio: ").append(fechaIniRes).append("\n");
			resumen.append("Fecha fin: ").append(fechaFinRes).append("\n");

			int confirm = JOptionPane.showOptionDialog(null, resumen.toString() + "\n¿Desea confirmar la reserva?",
					"Confirmar Reserva", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
					new String[] { "No", "Sí" }, "Sí");

			if (confirm == 1) {
				// Guardar en base de datos
				guardarReservaBD(tipoReserva, titRes, tipoIdentificacion, numIdentificacion, fechaIniRes, fechaFinRes,
						tipoHabitacion, nombresHuespedes);
				reservaConfirmada = true;
			}
		}
	}

	private static void guardarReservaBD(String tipoReserva, String titRes, String tipoIdent, String numIdent,
			LocalDate fechaIni, LocalDate fechaFin, String tipoHab, String[] nombresHuespedes) {

		Connection conn = conexionBD.conectar();
		if (conn == null)
			return;

		try {
			// Guardar huéspedes
			int idHuesped = 0;
			PreparedStatement psHuesped = conn.prepareStatement(
					"INSERT INTO huesped (nombre, tit_res, tipo_identificacion, num_identificacion) VALUES (?, ?, ?, ?)",
					PreparedStatement.RETURN_GENERATED_KEYS);
			for (String h : nombresHuespedes) {
				psHuesped.setString(1, h);
				psHuesped.setString(2, titRes);
				psHuesped.setString(3, tipoIdent);
				psHuesped.setString(4, numIdent);
				psHuesped.executeUpdate();
				var rs = psHuesped.getGeneratedKeys();
				if (rs.next()) {
					idHuesped = rs.getInt(1);
				}
			}

			// Asignar habitación (Individuo o Delegación)
			int idHabitacion = obtenerHabitacionLibre(conn, tipoHab);
			if (idHabitacion == 0) {
				JOptionPane.showMessageDialog(null, "No hay habitaciones libres del tipo seleccionado.");
				return;
			}

			// Marcar habitación como ocupada
			PreparedStatement psUpdateHab = conn
					.prepareStatement("UPDATE habitacion SET estado='ocupada' WHERE idHabitacion=?");
			psUpdateHab.setInt(1, idHabitacion);
			psUpdateHab.executeUpdate();

			// Guardar reserva incluyendo la habitación
			PreparedStatement psRes = conn.prepareStatement(
					"INSERT INTO reserva (fecha_ini_res, fecha_fin_res, tipo, fk_idhuesped, fk_idhabitacion) VALUES (?, ?, ?, ?, ?)");
			psRes.setDate(1, java.sql.Date.valueOf(fechaIni));
			psRes.setDate(2, java.sql.Date.valueOf(fechaFin));
			psRes.setString(3, tipoReserva);
			psRes.setInt(4, idHuesped);
			psRes.setInt(5, idHabitacion);
			psRes.executeUpdate();

			// Mostrar mensaje con número de habitación asignada
			JOptionPane.showMessageDialog(null, "Habitación asignada: " + idHabitacion);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al guardar la reserva: " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	static int obtenerHabitacionLibre(Connection conn, String tipoHab) throws SQLException {
		PreparedStatement ps = conn
				.prepareStatement("SELECT idHabitacion FROM habitacion WHERE tipo=? AND estado='libre' LIMIT 1");
		ps.setString(1, tipoHab);
		var rs = ps.executeQuery();
		if (rs.next()) {
			return rs.getInt("idHabitacion"); // devuelve la primera habitación libre del tipo
		}
		return 0; // si no hay habitación libre
	}
}
