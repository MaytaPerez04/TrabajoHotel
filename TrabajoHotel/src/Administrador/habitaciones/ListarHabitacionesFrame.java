package Administrador.habitaciones;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import controlador.ConexionBD;
import java.awt.*;
import java.sql.*;

public class ListarHabitacionesFrame extends JFrame {

	public ListarHabitacionesFrame() {
		setTitle("Listado de Habitaciones");
		setSize(650, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		// ---------------- HEADER ----------------
		JPanel header = new JPanel();
		header.setBackground(new Color(200, 0, 0));
		header.setPreferredSize(new Dimension(650, 60));
		JLabel title = new JLabel("Listado de Habitaciones");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Segoe UI", Font.BOLD, 24));
		header.add(title);
		add(header, BorderLayout.NORTH);

		// ---------------- TABLA ----------------
		String[] columnas = { "ID", "Tipo", "Estado", "Limpieza", "Precio" };
		DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
		JTable tabla = new JTable(modelo);
		tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		tabla.setRowHeight(28);

		// Centrar contenido de ciertas columnas
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tabla.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tabla.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		tabla.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		tabla.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		tabla.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(scroll, BorderLayout.CENTER);

		// ---------------- BOTON CERRAR ----------------
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setBackground(new Color(200, 0, 0));
		btnCerrar.setForeground(Color.WHITE);
		btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnCerrar.setFocusPainted(false);
		btnCerrar.addActionListener(e -> dispose());

		JPanel panelBoton = new JPanel();
		panelBoton.setBackground(new Color(240, 240, 240));
		panelBoton.add(btnCerrar);
		add(panelBoton, BorderLayout.SOUTH);

		// ---------------- CARGAR DATOS ----------------
		cargarDatos(modelo);

		setVisible(true);
	}

	private void cargarDatos(DefaultTableModel modelo) {
		try (Connection conn = ConexionBD.conectar()) {
			if (conn == null)
				throw new Exception("No se pudo conectar a la base de datos.");

			String sql = "SELECT * FROM habitacion";
			try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					Object[] fila = { rs.getInt("idHabitacion"), rs.getString("tipo"), rs.getString("estado"),
							rs.getString("estadoLimpieza"), rs.getDouble("precio") };
					modelo.addRow(fila);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al listar habitaciones: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
