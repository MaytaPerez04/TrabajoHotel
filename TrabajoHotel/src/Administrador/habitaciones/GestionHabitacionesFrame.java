package Administrador.habitaciones;

import javax.swing.*;
import java.awt.*;

public class GestionHabitacionesFrame extends JFrame {

	public GestionHabitacionesFrame() {
		setTitle("Gestión de Habitaciones");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new GridLayout(5, 1, 10, 10));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setBackground(new Color(240, 240, 240));

		String[] opciones = { "Agregar Habitación", "Editar Habitación", "Eliminar Habitación", "Ver Lista", "Cerrar" };
		for (String op : opciones) {
			JButton btn = new JButton(op);
			btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
			btn.setBackground(new Color(150, 0, 0));
			btn.setForeground(Color.WHITE);
			btn.setFocusPainted(false);
			btn.addActionListener(e -> manejarAccion(op));
			add(btn);
		}

		setVisible(true);
	}

	private void manejarAccion(String opcion) {
		switch (opcion) {
		case "Agregar Habitación" -> new AgregarHabitacionFrame();
		case "Editar Habitación" -> new EditarHabitacionFrame();
		case "Eliminar Habitación" -> new EliminarHabitacionFrame();
		case "Ver Lista" -> new ListarHabitacionesFrame();
		case "Cerrar" -> dispose();
		}
	}
}
