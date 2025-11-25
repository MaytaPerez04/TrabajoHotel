package Administrador.habitaciones;

import javax.swing.*;
import java.awt.*;
import modelo.AdministradorHabitaciones;

public class AgregarHabitacionFrame extends JFrame {

    public AgregarHabitacionFrame() {
        setTitle("Agregar Habitación");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ---------------- HEADER ----------------
        JPanel header = new JPanel();
        header.setBackground(new Color(200, 0, 0));
        JLabel titulo = new JLabel("Agregar Habitación");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.add(titulo);
        add(header, BorderLayout.NORTH);

        // ---------------- FORM ----------------
        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        form.setBackground(new Color(240, 240, 240));

        JLabel lNumero = new JLabel("Número:");
        JTextField tfNumero = new JTextField();
        tfNumero.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel lTipo = new JLabel("Tipo:");
        JTextField tfTipo = new JTextField();
        tfTipo.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel lPrecio = new JLabel("Precio:");
        JTextField tfPrecio = new JTextField();
        tfPrecio.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        form.add(lNumero);
        form.add(tfNumero);
        form.add(lTipo);
        form.add(tfTipo);
        form.add(lPrecio);
        form.add(tfPrecio);

        add(form, BorderLayout.CENTER);

        // ---------------- BOTONES ----------------
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        botones.setBackground(new Color(240, 240, 240));

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBackground(new Color(200, 0, 0));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnAgregar.setFocusPainted(false);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(150, 150, 150));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCancelar.setFocusPainted(false);

        botones.add(btnAgregar);
        botones.add(btnCancelar);
        add(botones, BorderLayout.SOUTH);

        // ---------------- ACCIONES ----------------
        btnAgregar.addActionListener(e -> {
            try {
                String numero = tfNumero.getText().trim();
                String tipo = tfTipo.getText().trim();
                double precio = Double.parseDouble(tfPrecio.getText().trim());

                AdministradorHabitaciones.agregarHabitacion(numero, tipo, precio);
                JOptionPane.showMessageDialog(this, "Habitación agregada correctamente");
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Precio inválido");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        setVisible(true);
    }
}

