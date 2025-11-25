package Administrador.habitaciones;

import javax.swing.*;
import java.awt.*;
import modelo.AdministradorHabitaciones;

public class EliminarHabitacionFrame extends JFrame {

	public EliminarHabitacionFrame() {
		setTitle("Eliminar Habitación");
		setSize(350, 200);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		// ---------------- HEADER ----------------
		JPanel header = new JPanel();
		header.setBackground(new Color(200, 0, 0));
		JLabel titulo = new JLabel("Eliminar Habitación");
		titulo.setForeground(Color.WHITE);
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
		header.add(titulo);
		add(header, BorderLayout.NORTH);

		// ---------------- FORM ----------------
		JPanel form = new JPanel(new GridLayout(1, 2, 10, 10));
		form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		form.setBackground(new Color(240, 240, 240));

		JLabel lId = new JLabel("ID:");
		JTextField tfId = new JTextField();
		tfId.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		form.add(lId);
		form.add(tfId);
		add(form, BorderLayout.CENTER);

		// ---------------- BOTONES ----------------
		JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		botones.setBackground(new Color(240, 240, 240));

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBackground(new Color(200, 0, 0));
		btnEliminar.setForeground(Color.WHITE);
		btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnEliminar.setFocusPainted(false);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(new Color(150, 150, 150));
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnCancelar.setFocusPainted(false);

		botones.add(btnEliminar);
		botones.add(btnCancelar);
		add(botones, BorderLayout.SOUTH);

		// ---------------- ACCIONES ----------------
		btnEliminar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(tfId.getText().trim());
				AdministradorHabitaciones.eliminarHabitacion(id);
				JOptionPane.showMessageDialog(this, "Habitación eliminada correctamente");
				dispose();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID inválido");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage());
			}
		});

		btnCancelar.addActionListener(e -> dispose());

		setVisible(true);
	}
}
