package Administrador;

import javax.swing.*;
import java.awt.*;
import Administrador.usuarios.GestionUsuariosFrame;
import modelo.Usuario;

public class AdminFrame extends JFrame {

	private Usuario admin;

	public AdminFrame(Usuario admin) {
		this.admin = admin;

		setTitle("Panel Administrador");
		setSize(600, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		// ------------------ HEADER ------------------
		JPanel header = new JPanel();
		header.setBackground(new Color(150, 0, 0)); // rojo oscuro
		header.setPreferredSize(new Dimension(600, 90));

		JLabel title = new JLabel("Panel de Administración");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Segoe UI", Font.BOLD, 30));
		header.add(title);

		add(header, BorderLayout.NORTH);

		// ------------------ CONTENIDO ------------------
		JPanel center = new JPanel();
		center.setBackground(new Color(240, 240, 240));
		center.setLayout(new GridLayout(4, 1, 15, 15));
		center.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

		JButton btnUsuarios = crearBoton("Gestionar usuarios");
		JButton btnHabitaciones = crearBoton("Gestionar habitaciones");
		JButton btnFinanzas = crearBoton("Gestión financiera");
		JButton btnVolver = crearBoton("Cerrar sesión");

		center.add(btnUsuarios);
		center.add(btnHabitaciones);
		center.add(btnFinanzas);
		center.add(btnVolver);

		add(center, BorderLayout.CENTER);

		// ------------------ ACCIONES ------------------
		btnUsuarios.addActionListener(e -> new GestionUsuariosFrame());

		btnHabitaciones.addActionListener(e -> new Administrador.habitaciones.GestionHabitacionesFrame());

		btnFinanzas.addActionListener(e -> new Administrador.finanzas.GestionFinancieraFrame());

		btnVolver.addActionListener(e -> {
			dispose();
			new Principal.main(); // Volver al menú principal
		});

		setVisible(true);
	}

	private JButton crearBoton(String txt) {
		JButton btn = new JButton(txt);
		btn.setBackground(new Color(200, 0, 0));
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
		btn.setFocusPainted(false);
		btn.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
		return btn;
	}
}
