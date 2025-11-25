package Administrador.usuarios;

import javax.swing.*;
import java.awt.*;
import modelo.Administrador;

public class EliminarUsuariosFrame extends JFrame {

    public EliminarUsuariosFrame() {
        setTitle("Eliminar Usuario");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // --- HEADER ---
        JPanel header = new JPanel();
        header.setBackground(new Color(192, 57, 43));
        header.setPreferredSize(new Dimension(400, 60));
        JLabel titulo = new JLabel("Eliminar Usuario");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.add(titulo);
        add(header, BorderLayout.NORTH);

        // --- FORM PANEL ---
        JPanel form = new JPanel();
        form.setBackground(new Color(245, 245, 245));
        form.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        form.setLayout(new GridLayout(2, 1, 10, 10));

        JTextField tfId = new JTextField();
        tfId.setPreferredSize(new Dimension(0, 35));
        form.add(new JLabel("ID del Usuario a eliminar:"));
        form.add(tfId);

        add(form, BorderLayout.CENTER);

        // --- BOTONES ---
        JPanel botones = new JPanel();
        botones.setBackground(new Color(245, 245, 245));
        botones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnEliminar = new JButton("Eliminar");
        JButton btnCancelar = new JButton("Cancelar");

        estiloBotonRojo(btnEliminar, new Color(192, 57, 43));
        estiloBotonRojo(btnCancelar, new Color(231, 76, 60));

        btnEliminar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(tfId.getText().trim());
                Administrador.eliminarUsuario(id);
                JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        botones.add(btnEliminar);
        botones.add(btnCancelar);
        add(botones, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void estiloBotonRojo(JButton btn, Color colorFondo) {
        btn.setBackground(colorFondo);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
