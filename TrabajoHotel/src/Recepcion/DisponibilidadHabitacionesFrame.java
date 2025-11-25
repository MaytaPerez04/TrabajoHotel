package Recepcion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import controlador.ConexionBD;

public class DisponibilidadHabitacionesFrame extends JFrame {

	public DisponibilidadHabitacionesFrame() {
		setTitle("Disponibilidad de Habitaciones");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new BorderLayout(10, 10));
		getContentPane().setBackground(new Color(0, 204, 153)); // fondo verde agua

		// ---------------- HEADER ----------------
		JPanel header = new JPanel();
		header.setBackground(new Color(0, 153, 128));
		header.setPreferredSize(new Dimension(600, 60));
		JLabel title = new JLabel("Disponibilidad de Habitaciones");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Segoe UI", Font.BOLD, 24));
		header.add(title);
		add(header, BorderLayout.NORTH);

		// ---------------- TABLA ----------------
		String[] columnas = { "ID", "Tipo", "Estado" };
		DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
		JTable tabla = new JTable(modelo);
		tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		tabla.setRowHeight(25);
		JScrollPane scroll = new JScrollPane(tabla);
		add(scroll, BorderLayout.CENTER);

		// ---------------- BOTON CERRAR ----------------
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setBackground(new Color(0, 102, 85));
		btnCerrar.setForeground(Color.WHITE);
		btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnCerrar.setFocusPainted(false);
		btnCerrar.addActionListener(e -> dispose());

		JPanel panelBoton = new JPanel();
		panelBoton.setBackground(new Color(0, 204, 153));
		panelBoton.add(btnCerrar);
		add(panelBoton, BorderLayout.SOUTH);

		cargarDisponibilidad(modelo);

		setVisible(true);
	}

	private void cargarDisponibilidad(DefaultTableModel modelo) {
		try (Connection conn = ConexionBD.conectar()) {
			if (conn == null)
				return;

			String sql = "SELECT idHabitacion, tipo, estado FROM habitacion ORDER BY tipo, idHabitacion";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Object[] fila = { rs.getInt("idHabitacion"), rs.getString("tipo"), rs.getString("estado") };
				modelo.addRow(fila);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al cargar disponibilidad: " + e.getMessage());
		}
	}
}
