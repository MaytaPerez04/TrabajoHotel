package Recepcion;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import controlador.ConexionBD;

public class GenerarFacturaFrame extends JFrame {

	private JTextField tfId;
	private JTextArea taFactura;

	public GenerarFacturaFrame() {
		setTitle("Generar Factura");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new BorderLayout(10, 10));
		getContentPane().setBackground(new Color(204, 255, 229)); // verde agua

		// HEADER
		JPanel header = new JPanel();
		header.setBackground(new Color(0, 153, 102));
		header.setPreferredSize(new Dimension(500, 60));
		JLabel title = new JLabel("Generar Factura");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Segoe UI", Font.BOLD, 24));
		header.add(title);
		add(header, BorderLayout.NORTH);

		// PANEL DE INGRESO DE ID
		JPanel panelInput = new JPanel(new GridLayout(1, 3, 10, 10));
		panelInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelInput.setBackground(new Color(204, 255, 229));

		tfId = new JTextField();
		JButton btnGenerar = new JButton("Generar");
		btnGenerar.setBackground(new Color(0, 153, 102));
		btnGenerar.setForeground(Color.WHITE);
		btnGenerar.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnGenerar.setFocusPainted(false);

		panelInput.add(new JLabel("ID de Reserva:"));
		panelInput.add(tfId);
		panelInput.add(btnGenerar);

		add(panelInput, BorderLayout.NORTH);

		// ÁREA DE FACTURA
		taFactura = new JTextArea();
		taFactura.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		taFactura.setEditable(false);
		JScrollPane scroll = new JScrollPane(taFactura);
		add(scroll, BorderLayout.CENTER);

		// BOTÓN CERRAR
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setBackground(new Color(150, 150, 150));
		btnCerrar.setForeground(Color.WHITE);
		btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnCerrar.setFocusPainted(false);
		btnCerrar.addActionListener(e -> dispose());

		JPanel panelBoton = new JPanel();
		panelBoton.setBackground(new Color(204, 255, 229));
		panelBoton.add(btnCerrar);
		add(panelBoton, BorderLayout.SOUTH);

		// ACCIÓN DEL BOTÓN GENERAR
		btnGenerar.addActionListener(e -> generarFactura());

		setVisible(true);
	}

	private void generarFactura() {
		try (Connection conn = ConexionBD.conectar()) {
			if (conn == null)
				return;

			int idReserva = Integer.parseInt(tfId.getText().trim());
			String sql = "SELECT r.idreserva, r.fecha_ini_res, r.fecha_fin_res, "
					+ "h.tipo AS tipoHab, h.precio, hu.nombre AS titular " + "FROM reserva r "
					+ "JOIN huesped hu ON r.fk_idhuesped = hu.idhuesped "
					+ "JOIN habitacion h ON r.fk_idhabitacion = h.idHabitacion " + "WHERE r.idreserva = ?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, idReserva);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String titular = rs.getString("titular");
				LocalDate fechaIni = rs.getDate("fecha_ini_res").toLocalDate();
				LocalDate fechaFin = rs.getDate("fecha_fin_res").toLocalDate();
				String tipoHabitacion = rs.getString("tipoHab");
				double precioHabitacion = rs.getDouble("precio");
				long dias = java.time.temporal.ChronoUnit.DAYS.between(fechaIni, fechaFin);
				double total = dias * precioHabitacion;

				StringBuilder factura = new StringBuilder();
				factura.append("===== FACTURA =====\n").append("Titular: ").append(titular).append("\n")
						.append("Reserva ID: ").append(idReserva).append("\n").append("Fecha inicio: ").append(fechaIni)
						.append("\n").append("Fecha fin: ").append(fechaFin).append("\n").append("Habitación: ")
						.append(tipoHabitacion).append("\n").append("Días: ").append(dias).append("\n")
						.append("Precio por día: $").append(precioHabitacion).append("\n").append("Total a pagar: $")
						.append(String.format("%.2f", total)).append("\n").append("==================");

				taFactura.setText(factura.toString());

			} else {
				taFactura.setText("No se encontró la reserva con ID: " + idReserva);
			}

		} catch (NumberFormatException e) {
			taFactura.setText("ID inválido");
		} catch (SQLException e) {
			taFactura.setText("Error: " + e.getMessage());
		}
	}
}
