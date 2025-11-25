package Recepcion;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import controlador.ConexionBD;

public class CancelarReservaFrame extends JFrame {

	public CancelarReservaFrame() {
		setTitle("Cancelar Reserva");
		setSize(400, 200);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new GridLayout(2, 2, 10, 10));
		getContentPane().setBackground(new Color(240, 240, 240));

		JLabel lId = new JLabel("ID de Reserva:");
		JTextField tfId = new JTextField();

		JButton btnCancelar = new JButton("Cancelar Reserva");
		btnCancelar.setBackground(new Color(0, 204, 153));
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnCancelar.setFocusPainted(false);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setBackground(new Color(150, 150, 150));
		btnCerrar.setForeground(Color.WHITE);
		btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnCerrar.setFocusPainted(false);

		btnCancelar.addActionListener(e -> {
			try (Connection conn = ConexionBD.conectar()) {
				if (conn == null)
					return;

				int id = Integer.parseInt(tfId.getText().trim());

				// Buscar reserva
				String sqlCheck = "SELECT fk_idhuesped, fk_idhabitacion FROM reserva WHERE idreserva = ?";
				PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
				psCheck.setInt(1, id);
				ResultSet rs = psCheck.executeQuery();
				if (!rs.next()) {
					JOptionPane.showMessageDialog(this, "No se encontró la reserva con ese ID");
					return;
				}

				int idHuesped = rs.getInt("fk_idhuesped");
				int idHabitacion = rs.getInt("fk_idhabitacion");

				// **Confirmación en JFrame**
				boolean confirmado = mostrarConfirmacion("¿Está seguro que desea cancelar la reserva ID " + id + "?");
				if (!confirmado)
					return;

				// Eliminar reserva
				PreparedStatement psDelRes = conn.prepareStatement("DELETE FROM reserva WHERE idreserva = ?");
				psDelRes.setInt(1, id);
				psDelRes.executeUpdate();

				// Eliminar huesped
				PreparedStatement psDelHuesped = conn.prepareStatement("DELETE FROM huesped WHERE idhuesped = ?");
				psDelHuesped.setInt(1, idHuesped);
				psDelHuesped.executeUpdate();

				// Liberar habitación
				PreparedStatement psHab = conn
						.prepareStatement("UPDATE habitacion SET estado = 'libre' WHERE idHabitacion = ?");
				psHab.setInt(1, idHabitacion);
				psHab.executeUpdate();

				// Notificación final en JFrame
				mostrarNotificacion("Éxito", "Reserva cancelada y habitación liberada correctamente");
				dispose();

			} catch (Exception ex) {
				mostrarNotificacion("Error", ex.getMessage());
			}
		});

		btnCerrar.addActionListener(e -> dispose());

		add(lId);
		add(tfId);
		add(btnCancelar);
		add(btnCerrar);

		setVisible(true);
	}

	// ---------------- FUNCIONES AUXILIARES ----------------
	private boolean mostrarConfirmacion(String mensaje) {
		JDialog dialog = new JDialog(this, "Confirmación", true);
		dialog.setSize(350, 150);
		dialog.setLocationRelativeTo(this);
		dialog.setLayout(new BorderLayout());
		dialog.getContentPane().setBackground(new Color(0, 204, 153));

		JLabel lbl = new JLabel("<html><center>" + mensaje + "</center></html>");
		lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lbl.setForeground(Color.WHITE);
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		dialog.add(lbl, BorderLayout.CENTER);

		JPanel panelBotones = new JPanel();
		panelBotones.setBackground(new Color(0, 204, 153));

		final boolean[] resultado = { false };

		JButton si = new JButton("Sí");
		si.setBackground(new Color(0, 153, 120));
		si.setForeground(Color.WHITE);
		si.setFont(new Font("Segoe UI", Font.BOLD, 14));
		si.addActionListener(ev -> {
			resultado[0] = true;
			dialog.dispose();
		});

		JButton no = new JButton("No");
		no.setBackground(new Color(100, 100, 100));
		no.setForeground(Color.WHITE);
		no.setFont(new Font("Segoe UI", Font.BOLD, 14));
		no.addActionListener(ev -> dialog.dispose());

		panelBotones.add(si);
		panelBotones.add(no);
		dialog.add(panelBotones, BorderLayout.SOUTH);

		dialog.setVisible(true);
		return resultado[0];
	}

	private void mostrarNotificacion(String titulo, String mensaje) {
		JDialog dialog = new JDialog(this, titulo, true);
		dialog.setSize(350, 150);
		dialog.setLocationRelativeTo(this);
		dialog.setLayout(new BorderLayout());
		dialog.getContentPane().setBackground(new Color(0, 204, 153));

		JLabel lbl = new JLabel("<html><center>" + mensaje + "</center></html>");
		lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lbl.setForeground(Color.WHITE);
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		dialog.add(lbl, BorderLayout.CENTER);

		JButton ok = new JButton("Aceptar");
		ok.setBackground(new Color(0, 153, 120));
		ok.setForeground(Color.WHITE);
		ok.setFont(new Font("Segoe UI", Font.BOLD, 14));
		ok.addActionListener(e -> dialog.dispose());

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 204, 153));
		panel.add(ok);
		dialog.add(panel, BorderLayout.SOUTH);

		dialog.setVisible(true);
	}
}
