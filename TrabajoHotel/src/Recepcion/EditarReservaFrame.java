package Recepcion;

import javax.swing.*;
import java.awt.*;
import vistas.NotificacionFrame;
import java.time.LocalDate;
import controlador.Recepcion;

public class EditarReservaFrame extends JFrame {

	public EditarReservaFrame() {
		setTitle("Editar Reserva");
		setSize(450, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new GridLayout(5, 2, 10, 10));
		getContentPane().setBackground(new Color(0, 180, 150)); // verde agua
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel lId = new JLabel("ID Reserva:");
		JTextField tfId = new JTextField();
		tfId.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		JLabel lFechaIni = new JLabel("Fecha inicio (YYYY-MM-DD):");
		JTextField tfFechaIni = new JTextField();
		tfFechaIni.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		JLabel lFechaFin = new JLabel("Fecha fin (YYYY-MM-DD):");
		JTextField tfFechaFin = new JTextField();
		tfFechaFin.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		JButton btnEditar = new JButton("Editar");
		btnEditar.setBackground(new Color(0, 120, 100));
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnEditar.setFocusPainted(false);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(new Color(150, 150, 150));
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnCancelar.setFocusPainted(false);

		add(lId);
		add(tfId);
		add(lFechaIni);
		add(tfFechaIni);
		add(lFechaFin);
		add(tfFechaFin);
		add(btnEditar);
		add(btnCancelar);

		// Acción botón Editar
		btnEditar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(tfId.getText().trim());
				LocalDate fechaIni = LocalDate.parse(tfFechaIni.getText().trim());
				LocalDate fechaFin = LocalDate.parse(tfFechaFin.getText().trim());

				// Lógica para editar la reserva en la base de datos
				boolean exito = Recepcion.editarReserva(id, fechaIni, fechaFin);

				if (exito) {
					NotificacionFrame.mostrar("Reserva editada correctamente", "Éxito");
					dispose();
				} else {
					NotificacionFrame.mostrar("No se encontró la reserva con ese ID", "Error");
				}
			} catch (NumberFormatException ex) {
				NotificacionFrame.mostrar("ID inválido", "Error");
			} catch (Exception ex) {
				NotificacionFrame.mostrar("Error: " + ex.getMessage(), "Error");
			}
		});

		btnCancelar.addActionListener(e -> dispose());

		setVisible(true);
	}
}
