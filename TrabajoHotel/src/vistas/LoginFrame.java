package vistas;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

import modelo.Usuario;
import controlador.Login;
import vistas.BienvenidoFrame;

public class LoginFrame extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoPass;

    // Recibe un callback para abrir el menú según el rol
    public LoginFrame(Consumer<Usuario> callback) {

        setTitle("Iniciar sesión");
        setSize(420, 360);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // PANEL FORMULARIO
        JPanel form = new JPanel(new GridLayout(4, 1, 12, 12));
        form.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
        form.setBackground(Color.WHITE);

        JLabel l1 = new JLabel("Usuario:");
        l1.setFont(new Font("Segoe UI", Font.BOLD, 16));
        campoUsuario = new JTextField();
        campoUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoUsuario.setPreferredSize(new Dimension(0, 35));

        JLabel l2 = new JLabel("Contraseña:");
        l2.setFont(new Font("Segoe UI", Font.BOLD, 16));
        campoPass = new JPasswordField();
        campoPass.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoPass.setPreferredSize(new Dimension(0, 35));

        form.add(l1);
        form.add(campoUsuario);
        form.add(l2);
        form.add(campoPass);

        add(form, BorderLayout.CENTER);

        // PANEL DE BOTONES
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bottom.setBackground(Color.WHITE);

        JButton btnLogin = new JButton("Iniciar sesión");
        btnLogin.setBackground(new Color(52, 152, 219));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        btnLogin.addActionListener(e -> {
            try {
                Usuario usuarioLogueado = Login.iniciarSesion(
                        campoUsuario.getText().trim(),
                        new String(campoPass.getPassword()).trim()
                );

                // Muestra la bienvenida con botón "Siguiente"
                new BienvenidoFrame(usuarioLogueado, u -> {
                    // Callback al presionar "Siguiente"
                    if (callback != null) callback.accept(u);
                });

                dispose(); // cerrar el login
            } catch (Exception ex) {
                mostrarErrorFrame(ex.getMessage());
            }
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(192, 57, 43));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnCancelar.addActionListener(e -> dispose());

        bottom.add(btnLogin);
        bottom.add(btnCancelar);

        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Muestra mensaje de error con botón "Volver"
    private void mostrarErrorFrame(String mensaje) {
        JFrame errorFrame = new JFrame("Error de login");
        errorFrame.setSize(400, 180);
        errorFrame.setLocationRelativeTo(null);
        errorFrame.setResizable(false);
        errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        errorFrame.setLayout(new BorderLayout());

        JLabel msg = new JLabel(mensaje);
        msg.setFont(new Font("Segoe UI", Font.BOLD, 16));
        msg.setHorizontalAlignment(SwingConstants.CENTER);
        msg.setVerticalAlignment(SwingConstants.CENTER);
        errorFrame.add(msg, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel();
        JButton volver = new JButton("Volver");
        volver.setFont(new Font("Segoe UI", Font.BOLD, 14));
        volver.setBackground(new Color(52, 152, 219));
        volver.setForeground(Color.WHITE);
        volver.setFocusPainted(false);
        volver.addActionListener(ev -> errorFrame.dispose());
        panelBoton.add(volver);

        errorFrame.add(panelBoton, BorderLayout.SOUTH);
        errorFrame.setVisible(true);
    }
}
