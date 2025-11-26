package Limpieza;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import controlador.ConexionBD;

public class MarcarLimpiezaFrame extends JFrame {

	private JTable tabla;
	private DefaultTableModel modelo;

	public MarcarLimpiezaFrame() {

		setTitle("Marcar Habitación como Limpia");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout(10, 10));
		getContentPane().setBackground(new Color(255, 224, 130));

		// ---------------- TABLA ----------------
		String[] columnas = { "ID", "Tipo", "Estado", "Limpieza" };
		modelo = new DefaultTableModel(columnas, 0);
		tabla = new JTable(modelo);
		tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		tabla.setRowHeight(25);
		add(new JScrollPane(tabla), BorderLayout.CENTER);

		// ---------------- BOTONES ----------------
		JButton btnMarcar = new JButton("Marcar como limpia");
		btnMarcar.setBackground(new Color(255, 143, 0));
		btnMarcar.setForeground(Color.WHITE);
		btnMarcar.setFont(new Font("Segoe UI", Font.BOLD, 16));

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setBackground(new Color(255, 183, 77));
		btnCerrar.setForeground(Color.BLACK);
		btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnCerrar.addActionListener(e -> dispose());

		JPanel panelBoton = new JPanel();
		panelBoton.setBackground(new Color(255, 224, 130));
		panelBoton.add(btnMarcar);
		panelBoton.add(btnCerrar);
		add(panelBoton, BorderLayout.SOUTH);

		btnMarcar.addActionListener(e -> marcarLimpieza());

		cargarHabitaciones();
		setVisible(true);
	}

	private void cargarHabitaciones() {
		try (Connection conn = ConexionBD.conectar()) {

			String sql = "SELECT idHabitacion, tipo, estado, estadoLimpieza " + "FROM habitacion "
					+ "WHERE estadoLimpieza = 'limpiando' " + "ORDER BY tipo, idHabitacion";

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			modelo.setRowCount(0);

			while (rs.next()) {
				modelo.addRow(new Object[] { rs.getInt("idHabitacion"), rs.getString("tipo"), rs.getString("estado"),
						rs.getString("estadoLimpieza") });
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
		}
	}

	private void marcarLimpieza() {

		int fila = tabla.getSelectedRow();

		if (fila == -1) {
			JOptionPane.showMessageDialog(this, "Seleccione una habitación");
			return;
		}

		int idHabitacion = (int) modelo.getValueAt(fila, 0);

		try (Connection conn = ConexionBD.conectar()) {

			String sql = "UPDATE habitacion SET estadoLimpieza = 'limpia' WHERE idHabitacion = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, idHabitacion);

			if (ps.executeUpdate() > 0) {
				JOptionPane.showMessageDialog(this, "Habitación marcada como limpia");
				cargarHabitaciones();
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
		}
	}
}
