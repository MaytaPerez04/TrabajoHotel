package Administrador.usuarios;

import javax.swing.*;
import java.awt.*;
import modelo.Administrador;

public class CrearUsuariosFrame extends JFrame {

    public CrearUsuariosFrame() {
        setTitle("Crear Usuario");
        setSize(450, 450); // un poco más alto
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));

        // --- HEADER ---
        JPanel header = new JPanel();
        header.setBackground(new Color(192, 57, 43)); // rojo elegante
        header.setPreferredSize(new Dimension(450, 60));
        JLabel titulo = new JLabel("Crear Nuevo Usuario");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.add(titulo);
        add(header, BorderLayout.NORTH);

        // --- FORM PANEL ---
        JPanel form = new JPanel();
        form.setBackground(new Color(245, 245, 245));
        form.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
        form.setLayout(new GridLayout(8, 1, 10, 10));

        JTextField tfNombre = new JTextField();
        JTextField tfUsuario = new JTextField();
        JPasswordField tfPass = new JPasswordField();
        String[] roles = {"Administrador", "Recepcion", "Limpieza"};
        JComboBox<String> cbRol = new JComboBox<>(roles);

        // Hacer campos de texto más altos
        Dimension campoGrande = new Dimension(0, 35);
        tfNombre.setPreferredSize(campoGrande);
        tfUsuario.setPreferredSize(campoGrande);
        tfPass.setPreferredSize(campoGrande);
        cbRol.setPreferredSize(campoGrande);

        form.add(crearEtiqueta("Nombre:"));
        form.add(tfNombre);
        form.add(crearEtiqueta("Usuario:"));
        form.add(tfUsuario);
        form.add(crearEtiqueta("Contraseña:"));
        form.add(tfPass);
        form.add(crearEtiqueta("Rol:"));
        form.add(cbRol);

        add(form, BorderLayout.CENTER);

        // --- BOTONES ---
        JPanel botones = new JPanel();
        botones.setBackground(new Color(245, 245, 245));
        botones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnCrear = new JButton("Crear");
        JButton btnCancelar = new JButton("Cancelar");

        estiloBotonRojo(btnCrear, new Color(192, 57, 43));
        estiloBotonRojo(btnCancelar, new Color(231, 76, 60));

        btnCrear.addActionListener(e -> {
            String nombre = tfNombre.getText().trim();
            String usuario = tfUsuario.getText().trim();
            String pass = new String(tfPass.getPassword()).trim();
            String rol = (String) cbRol.getSelectedItem();

            try {
                Administrador.crearUsuario(nombre, usuario, pass, rol);
                JOptionPane.showMessageDialog(this, "Usuario creado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        botones.add(btnCrear);
        botones.add(btnCancelar);

        add(botones, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;
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

