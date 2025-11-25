package Recepcion;

import javax.swing.*;
import java.awt.*;

public class RecepcionFrame extends JFrame {

	public RecepcionFrame() {
		setTitle("Panel de Recepción");
		setSize(600, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		// ------------------ HEADER ------------------
		JPanel header = new JPanel();
		header.setBackground(new Color(0, 204, 153)); // verde agua
		header.setPreferredSize(new Dimension(600, 90));

		JLabel title = new JLabel("Panel de Recepción");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Segoe UI", Font.BOLD, 30));
		header.add(title);
		add(header, BorderLayout.NORTH);

		// ------------------ CONTENIDO ------------------
		JPanel center = new JPanel();
		center.setBackground(new Color(240, 240, 240));
		center.setLayout(new GridLayout(5, 1, 15, 15));
		center.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

		JButton btnEditar = crearBoton("Editar reserva");
		JButton btnCancelar = crearBoton("Cancelar reserva");
		JButton btnDisponibilidad = crearBoton("Ver disponibilidad de habitaciones");
		JButton btnFactura = crearBoton("Generar factura o recibo");
		JButton btnCerrar = crearBoton("Cerrar sesión");

		center.add(btnEditar);
		center.add(btnCancelar);
		center.add(btnDisponibilidad);
		center.add(btnFactura);
		center.add(btnCerrar);

		add(center, BorderLayout.CENTER);

		// ------------------ ACCIONES ------------------
		btnEditar.addActionListener(e -> new EditarReservaFrame());
		btnCancelar.addActionListener(e -> new CancelarReservaFrame());
		btnDisponibilidad.addActionListener(e -> new DisponibilidadHabitacionesFrame());
		btnFactura.addActionListener(e -> new GenerarFacturaFrame()); // Abre directamente el JFrame
		btnCerrar.addActionListener(e -> dispose());

		setVisible(true);
	}

	private JButton crearBoton(String texto) {
		JButton btn = new JButton(texto);
		btn.setBackground(new Color(0, 204, 153)); // verde agua
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
		btn.setFocusPainted(false);
		btn.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
		return btn;
	}
}
