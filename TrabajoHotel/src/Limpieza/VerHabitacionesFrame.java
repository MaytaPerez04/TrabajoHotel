package Limpieza;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import controlador.ConexionBD;

public class VerHabitacionesFrame extends JFrame {

	public VerHabitacionesFrame() {
		setTitle("Habitaciones Pendientes de Limpieza");
		setSize(550, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		getContentPane().setBackground(new Color(255, 224, 130)); // amarillo pastel
		setLayout(new BorderLayout(10, 10));

		// ------------------ TABLA ------------------
		String[] columnas = { "ID", "Tipo", "Estado", "Estado Limpieza" };
		DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
		JTable tabla = new JTable(modelo);
		tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		tabla.setRowHeight(26);

		JScrollPane scroll = new JScrollPane(tabla);
		add(scroll, BorderLayout.CENTER);

		// ------------------ BOTÓN CERRAR ------------------
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setBackground(new Color(255, 183, 77));
		btnCerrar.setForeground(Color.BLACK);
		btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnCerrar.addActionListener(e -> dispose());

		JPanel panelBoton = new JPanel();
		panelBoton.setBackground(new Color(255, 224, 130));
		panelBoton.add(btnCerrar);
		add(panelBoton, BorderLayout.SOUTH);

		// Cargar data real
		cargarHabitacionesPendientes(modelo);

		setVisible(true);
	}

	private void cargarHabitacionesPendientes(DefaultTableModel modelo) {
		try (Connection conn = ConexionBD.conectar()) {

			String sql = "SELECT idHabitacion, tipo, estado, estadoLimpieza " + "FROM habitacion "
					+ "WHERE estadoLimpieza IN ('limpiar', 'limpiando') " + "ORDER BY tipo, idHabitacion";

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			boolean hayDatos = false;

			while (rs.next()) {
				hayDatos = true;

				modelo.addRow(new Object[] { rs.getInt("idHabitacion"), rs.getString("tipo"), rs.getString("estado"),
						rs.getString("estadoLimpieza") });
			}

			if (!hayDatos) {
				JOptionPane.showMessageDialog(this, "No hay habitaciones pendientes.");
				dispose();
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
		}
	}
}
