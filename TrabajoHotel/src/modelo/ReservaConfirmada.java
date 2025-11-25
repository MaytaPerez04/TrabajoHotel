package modelo;

import javax.swing.*;

import Principal.main;

import java.awt.*;


public class ReservaConfirmada extends JDialog {

	public ReservaConfirmada(JFrame parent) {
		super(parent, true);

		setTitle("Reserva Confirmada");
		setSize(420, 250);
		setLocationRelativeTo(parent);
		setResizable(false);
		setLayout(new BorderLayout());

		JPanel header = new JPanel();
		header.setBackground(new Color(33, 47, 60));
		header.setPreferredSize(new Dimension(420, 70));
		JLabel titulo = new JLabel("Reserva Confirmada");
		titulo.setForeground(Color.WHITE);
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		header.add(titulo);
		add(header, BorderLayout.NORTH);

		JPanel content = new JPanel();
		content.setBackground(Color.WHITE);
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// ICONO PNG
		JLabel icono = new JLabel();
		icono.setAlignmentX(Component.CENTER_ALIGNMENT);
		ImageIcon iconoOk = new ImageIcon(getClass().getResource("/img/check.png"));
		Image img = iconoOk.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
		icono.setIcon(new ImageIcon(img));

		JLabel msg = new JLabel("La reserva fue creada exitosamente");
		msg.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		msg.setAlignmentX(Component.CENTER_ALIGNMENT);

		content.add(icono);
		content.add(Box.createVerticalStrut(10));
		content.add(msg);

		add(content, BorderLayout.CENTER);

		JButton ok = new JButton("Aceptar");
		ok.setBackground(new Color(52, 152, 219));
		ok.setForeground(Color.WHITE);
		ok.setFont(new Font("Segoe UI", Font.BOLD, 16));
		ok.setFocusPainted(false);
		ok.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		ok.addActionListener(e -> {
			dispose();
			new main();
		});

		JPanel pie = new JPanel();
		pie.setBackground(Color.WHITE);
		pie.add(ok);

		add(pie, BorderLayout.SOUTH);

		setVisible(true);
	}
}
