package vistas;

import javax.swing.*;

import modelo.Reserva;
import modelo.ReservaConfirmada;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ResumenReserva extends JDialog {

	public ResumenReserva(JFrame parent, Reserva reservaInstance, LocalDate fechaInicio, LocalDate fechaFin, int dias,
			String titular, String tipoId, String numId, String tipoReserva, ArrayList<Integer> habIdsSeleccionadas,
			ArrayList<String> habDescSeleccionadas) {

		super(parent, true);
		setTitle("Resumen de Reserva");
		setSize(600, 520);
		setLocationRelativeTo(parent);
		setResizable(false);
		setLayout(new BorderLayout());

		// HEADER
		JPanel header = new JPanel();
		header.setBackground(new Color(33, 47, 60));
		header.setPreferredSize(new Dimension(600, 70));
		JLabel titulo = new JLabel("Resumen de Reserva");
		titulo.setForeground(Color.WHITE);
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
		header.add(titulo);
		add(header, BorderLayout.NORTH);

		// CONTENIDO MINIMALISTA
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		content.setBackground(Color.WHITE);
		content.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

		// Helper para filas de 2 columnas
		content.add(fila("Titular:", titular));
		content.add(fila("Tipo identificación:", tipoId));
		content.add(fila("Número identificación:", numId));

		content.add(Box.createVerticalStrut(10));
		content.add(fila("Fecha inicio:", fechaInicio.toString()));
		content.add(fila("Fecha fin:", fechaFin.toString()));
		content.add(fila("Días:", String.valueOf(dias)));

		content.add(Box.createVerticalStrut(10));
		content.add(fila("Tipo de reserva:", tipoReserva));

		content.add(Box.createVerticalStrut(20));

		// Habitaciones asignadas
		JLabel habLabel = new JLabel("Habitaciones asignadas:");
		habLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
		content.add(habLabel);
		content.add(Box.createVerticalStrut(8));

		for (String desc : habDescSeleccionadas) {
			JLabel item = new JLabel("• " + desc);
			item.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			content.add(item);
		}

		add(content, BorderLayout.CENTER);

		// BOTONES
		JPanel botones = new JPanel(new FlowLayout());
		botones.setBackground(Color.WHITE);

		JButton editar = new JButton("Editar");
		JButton confirmar = new JButton("Confirmar");
		JButton cancelar = new JButton("Cancelar");

		estiloBoton(editar, new Color(52, 152, 219));
		estiloBoton(confirmar, new Color(39, 174, 96));
		estiloBoton(cancelar, new Color(192, 57, 43));

		editar.addActionListener(e -> {
			dispose();
			new Reserva(reservaInstance);
		});

		cancelar.addActionListener(e -> {
			dispose();
			parent.setVisible(true);
		});

		confirmar.addActionListener(e -> {
			boolean ok = reservaInstance.confirmarYGuardar(habIdsSeleccionadas, habDescSeleccionadas, fechaInicio,
					fechaFin, dias);

			if (ok) {
				dispose();
				new ReservaConfirmada(parent);
			}
		});

		botones.add(editar);
		botones.add(confirmar);
		botones.add(cancelar);

		add(botones, BorderLayout.SOUTH);

		setVisible(true);
	}

	private JPanel fila(String campo, String valor) {
		JPanel p = new JPanel(new GridLayout(1, 2));
		p.setBackground(Color.WHITE);

		JLabel l1 = new JLabel(campo);
		l1.setFont(new Font("Segoe UI", Font.BOLD, 16));

		JLabel l2 = new JLabel(valor);
		l2.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		p.add(l1);
		p.add(l2);

		return p;
	}

	private void estiloBoton(JButton b, Color c) {
		b.setBackground(c);
		b.setForeground(Color.WHITE);
		b.setFont(new Font("Segoe UI", Font.BOLD, 16));
		b.setFocusPainted(false);
		b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
	}
}
