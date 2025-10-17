import javax.swing.JOptionPane;

public class Administrador {

	public static void mostrarMenuAdministrador() {
		// Opciones del menú
		String[] opciones = { "Gestionar usuarios", "Gestionar habitaciones", "Gestionar productos y servicios",
				"Generar reportes financieros", "Cerrar sesión" };

		int opcion;

		do {
			// Muestra el menú
			opcion = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Administrador",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

			switch (opcion) {
			case 0 -> gestionarUsuarios();
			case 1 -> gestionarHabitaciones();
			case 2 -> gestionarProductosServicios();
			case 3 -> generarReportesFinancieros();
			case 4 -> {
				
				JOptionPane.showMessageDialog(null, "Cerrando sesión. ¡Hasta luego!");
				
			}
			}
		} while (opcion != 4); 
	}

	// Métodos para las opciones del menú (por completar)
	private static void gestionarUsuarios() {
		// TODO: Lógica para gestionar usuarios
		JOptionPane.showMessageDialog(null, "Función gestionar usuarios aún no implementada.");
	}

	private static void gestionarHabitaciones() {
		// TODO: Lógica para gestionar habitaciones
		JOptionPane.showMessageDialog(null, "Función gestionar habitaciones aún no implementada.");
	}

	private static void gestionarProductosServicios() {
		// TODO: Lógica para gestionar productos y servicios
		JOptionPane.showMessageDialog(null, "Función gestionar productos y servicios aún no implementada.");
	}

	private static void generarReportesFinancieros() {
		// TODO: Lógica para generar reportes financieros
		JOptionPane.showMessageDialog(null, "Función generar reportes financieros aún no implementada.");
	}
}
