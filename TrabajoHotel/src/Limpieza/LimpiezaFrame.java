package Limpieza;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import controlador.ConexionBD;

public class LimpiezaFrame extends JFrame {

	public LimpiezaFrame() {
		setTitle("Panel de Limpieza");
		setSize(600, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		// ------------------ HEADER ------------------
		JPanel header = new JPanel();
		header.setBackground(new Color(163, 198, 255)); // Azul claro
		header.setPreferredSize(new Dimension(600, 90));
		JLabel title = new JLabel("Panel de Limpieza");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Segoe UI", Font.BOLD, 30));
		header.add(title);
		add(header, BorderLayout.NORTH);

		// ------------------ CONTENIDO ------------------
		JPanel center = new JPanel();
		center.setBackground(new Color(240, 240, 240)); // Fondo gris claro
		center.setLayout(new GridLayout(6, 1, 15, 15));
		center.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

		JButton btnMarcarLimpieza = crearBoton("Marcar limpieza realizada");
		JButton btnVerHabitaciones = crearBoton("Ver habitaciones pendientes");
		JButton btnCambiarLimpiando = crearBoton("Cambiar estado a 'Limpiando'");
		JButton btnCambiarLimpia = crearBoton("Cambiar estado a 'Limpia'");
		JButton btnCerrar = crearBoton("Cerrar sesión");

		center.add(btnMarcarLimpieza);
		center.add(btnVerHabitaciones);
		center.add(btnCambiarLimpiando);
		center.add(btnCambiarLimpia);
		center.add(btnCerrar);

		add(center, BorderLayout.CENTER);

		// ------------------ ACCIONES ------------------
		btnMarcarLimpieza.addActionListener(e -> {
			// Aquí puedes agregar la lógica para marcar la limpieza
			JOptionPane.showMessageDialog(this, "Función de marcar limpieza aún no implementada.");
		});

		btnVerHabitaciones.addActionListener(e -> {
			// Mostrar habitaciones pendientes de limpieza (estado 'limpiar' o 'limpiando')
			mostrarPedidosLimpieza();
		});

		btnCambiarLimpiando.addActionListener(e -> cambiarEstadoHabitacion("limpiando"));
		btnCambiarLimpia.addActionListener(e -> cambiarEstadoHabitacion("limpia"));

		btnCerrar.addActionListener(e -> dispose());

		setVisible(true); // Hacer visible el JFrame
	}

	private JButton crearBoton(String texto) {
		JButton btn = new JButton(texto);
		btn.setBackground(Color.WHITE); // Fondo blanco para los botones
		btn.setForeground(new Color(70, 130, 180)); // Azul oscuro para el texto
		btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
		btn.setFocusPainted(false);
		btn.setBorder(BorderFactory.createLineBorder(new Color(163, 198, 255), 2)); // Borde azul claro
		return btn;
	}

	private void cambiarEstadoHabitacion(String estado) {
		// Pedir ID de la habitación a modificar
		String inputId = JOptionPane.showInputDialog(this, "Ingrese ID de la habitación a cambiar a '" + estado + "':");
		if (inputId == null || inputId.trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "ID inválido.");
			return;
		}

		try (Connection conn = ConexionBD.conectar()) {
			if (conn == null) {
				JOptionPane.showMessageDialog(this, "Error de conexión con la base de datos.");
				return;
			}

			int idHabitacion = Integer.parseInt(inputId.trim());

			// Verificar si la habitación existe y obtener su estado actual
			String sqlCheck = "SELECT estadoLimpieza FROM habitacion WHERE idHabitacion = ?";
			PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
			psCheck.setInt(1, idHabitacion);
			ResultSet rs = psCheck.executeQuery();

			if (!rs.next()) {
				JOptionPane.showMessageDialog(this, "No se encontró la habitación con ese ID.");
				return;
			}

			String estadoActual = rs.getString("estadoLimpieza");

			// Si ya está en el estado deseado, mostrar mensaje
			if (estadoActual.equals(estado)) {
				JOptionPane.showMessageDialog(this, "La habitación ya está en el estado '" + estado + "'.");
				return;
			}

			// Actualizar el estado de la habitación
			String sqlUpdate = "UPDATE habitacion SET estadoLimpieza = ? WHERE idHabitacion = ?";
			PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
			psUpdate.setString(1, estado);
			psUpdate.setInt(2, idHabitacion);
			psUpdate.executeUpdate();

			JOptionPane.showMessageDialog(this,
					"El estado de la habitación #" + idHabitacion + " ha sido cambiado a '" + estado + "'.");

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error al cambiar el estado de la habitación: " + ex.getMessage());
		}
	}

	// Mostrar habitaciones pendientes de limpieza
	public void mostrarPedidosLimpieza() {
		Connection conn = ConexionBD.conectar();
		if (conn == null) {
			JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
			return;
		}

		try {
			// Obtener habitaciones pendientes de limpieza (estado 'limpiar' o 'limpiando')
			String sql = "SELECT idHabitacion, tipo FROM habitacion WHERE estadoLimpieza IN ('limpiar', 'limpiando')";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			StringBuilder habitaciones = new StringBuilder();
			while (rs.next()) {
				habitaciones.append("ID: ").append(rs.getInt("idHabitacion")).append(" - Tipo: ")
						.append(rs.getString("tipo")).append("\n");
			}

			JOptionPane.showMessageDialog(this,
					habitaciones.length() == 0 ? "No hay habitaciones pendientes de limpieza."
							: "Habitaciones pendientes de limpieza:\n" + habitaciones.toString());

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error al consultar habitaciones: " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
