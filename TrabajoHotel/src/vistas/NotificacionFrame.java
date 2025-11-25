package vistas;

import javax.swing.*;
import java.awt.*;

public class NotificacionFrame extends JFrame {

	public NotificacionFrame(String mensaje, String titulo) {
		setTitle(titulo);
		setSize(400, 200);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(0, 180, 150)); // verde agua

		JLabel lblMensaje = new JLabel("<html><div style='text-align: center;'>" + mensaje + "</div></html>");
		lblMensaje.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensaje.setVerticalAlignment(SwingConstants.CENTER);

		add(lblMensaje, BorderLayout.CENTER);

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBackground(new Color(0, 120, 100));
		btnAceptar.setForeground(Color.WHITE);
		btnAceptar.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnAceptar.setFocusPainted(false);
		btnAceptar.addActionListener(e -> dispose());

		JPanel panelBoton = new JPanel();
		panelBoton.setBackground(new Color(0, 180, 150));
		panelBoton.add(btnAceptar);

		add(panelBoton, BorderLayout.SOUTH);

		setVisible(true);
	}

	// Método estático para usar directamente
	public static void mostrar(String mensaje, String titulo) {
		new NotificacionFrame(mensaje, titulo);
	}
}
