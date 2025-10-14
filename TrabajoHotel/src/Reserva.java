
import java.time.LocalDate;

import javax.swing.JOptionPane;

public class Reserva {
	public static void crearReservaIndividual() {
		LocalDate fechaIniRes = null;
		int ver = 0;

		while (ver == 0) {
			int diaRes = Integer.parseInt(JOptionPane.showInputDialog("Número del día de la reserva"));
			int mesRes = Integer.parseInt(JOptionPane.showInputDialog("Número del mes de la reserva"));
			int anoRes = Integer.parseInt(JOptionPane.showInputDialog("Número del año de la reserva"));

			fechaIniRes = LocalDate.of(anoRes, mesRes, diaRes);

			String[] opc2 = { "Rehacer", "Si" };
			ver = JOptionPane.showOptionDialog(null,
					"Anotó como fecha de reserva: " + fechaIniRes + ". ¿Le parece bien?", "Hotel la Perla",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opc2, opc2[1]);
		}

		int cantDias = Integer.parseInt(JOptionPane.showInputDialog(null, "¿Cuántos días se quedarán?",
				"Hotel la Perla", JOptionPane.PLAIN_MESSAGE));

		LocalDate fechaFinRes = fechaIniRes.plusDays(cantDias);

		String titRes = JOptionPane.showInputDialog(null, "Nombre del titular de la reserva:", "Hotel la Perla",
				JOptionPane.PLAIN_MESSAGE);

		String[] opcTipoRes = { "Individuo", "Delegación" };
		int tipoRes = JOptionPane.showOptionDialog(null, "¿Qué desea hacer?", "Hotel La Perla",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcTipoRes, opcTipoRes[0]);

		if (tipoRes == 0) {
			int cantHab;
			do {
				cantHab = Integer.parseInt(JOptionPane.showInputDialog(null, "¿Cuántas habitaciones van a querer?",
						"Hotel La Perla", JOptionPane.PLAIN_MESSAGE));
				if (cantHab > 4) {
					JOptionPane.showMessageDialog(null, "No se pueden realizar reservas por más de 4 habitaciones.",
							"Hotel La Perla", JOptionPane.WARNING_MESSAGE);
				}
			} while (cantHab > 4);

			JOptionPane.showMessageDialog(null,
					"Resumen:\n" + "Se quedan desde el " + fechaIniRes + " hasta el " + fechaFinRes + "\nRequerirán de "
							+ cantHab + " habitaciones" + "\nTitular de la reserva: " + titRes,
					"Hotel La Perla", JOptionPane.PLAIN_MESSAGE);
		}

		// Acá podrías guardar la reserva en una lista o archivo si querés persistencia
	}
}