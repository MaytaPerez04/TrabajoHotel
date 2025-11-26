package controlador;

import Recepcion.RecepcionFrame;

import javax.swing.*;

import Limpieza.LimpiezaFrame;
import modelo.Administrador;
import modelo.Usuario;

import java.awt.*;

public class Hotelgama {

	public static void mostrarMenuSegunRol(Usuario u, Object ignore) {

		String rol = u.getRol();

		// Abrir menú según rol
		if (rol.equalsIgnoreCase("Recepcion")) {
			new RecepcionFrame(); // abre el JFrame con el menú de recepción
		} else if (rol.equalsIgnoreCase("Limpieza")) {
			new LimpiezaFrame(); // abre el JFrame con el menú de limpieza

		} else if (rol.equalsIgnoreCase("Administrador")) {
			Administrador.mostrarMenuAdministrador(u);

		} else {
			// En caso de rol desconocido, mostrar mensaje en JFrame también
			JFrame desconocido = new JFrame("Rol no reconocido");
			desconocido.setSize(350, 150);
			desconocido.setLocationRelativeTo(null);
			desconocido.setResizable(false);
			desconocido.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			JLabel msg = new JLabel("Rol no reconocido para " + u.getNombre());
			msg.setHorizontalAlignment(SwingConstants.CENTER);
			msg.setFont(new Font("Segoe UI", Font.BOLD, 16));

			desconocido.add(msg);
			desconocido.setVisible(true);
		}
	}
}
