import javax.swing.JOptionPane;

public class main {
	public static void main(String[] args) {
		
		String[] opciones = { "Quiero hacer una reserva", "Iniciar sesión", "Cerrar" };

		int seleccion = JOptionPane.showOptionDialog(null, "¿Qué desea hacer?", "HotelGama", JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        
		switch (seleccion) {
		case 0:
			// Entra a reservas
			Reserva.crearReservaIndividual();
			break;

		case 1:
			// Inicia sesión
			Usuario usuario = Login.iniciarSesion();
			if (usuario != null) {
				Hotelgama.mostrarMenuSegunRol(usuario, null);
			} else {
				JOptionPane.showMessageDialog(null, "No se pudo iniciar sesión.", "HotelGama",
						JOptionPane.WARNING_MESSAGE);
			}
			break;

		case 2: // Cerrar
		case JOptionPane.CLOSED_OPTION:
			JOptionPane.showMessageDialog(null, "Gracias por usar el sistema. ¡Hasta pronto!");
			System.exit(0);
			break;

		default:
			JOptionPane.showMessageDialog(null, "Programa cerrado.");
			System.exit(0);
			break;
		}
	}
}



