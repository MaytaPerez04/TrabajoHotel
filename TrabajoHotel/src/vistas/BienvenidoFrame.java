package vistas;

import javax.swing.*;

import modelo.Usuario;

import java.awt.*;
import java.util.function.Consumer;

public class BienvenidoFrame extends JFrame {

    public BienvenidoFrame(Usuario usuario, Consumer<Usuario> callback) {
        setTitle("Bienvenido");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Fondo gris claro
        getContentPane().setBackground(new Color(230, 230, 230));

        // Mensaje de bienvenida
        JLabel mensaje = new JLabel("¡Bienvenido, " + usuario.getNombre() + "!");
        mensaje.setFont(new Font("Segoe UI", Font.BOLD, 20));
        mensaje.setForeground(new Color(33, 33, 33)); // texto oscuro
        mensaje.setHorizontalAlignment(SwingConstants.CENTER);
        mensaje.setVerticalAlignment(SwingConstants.CENTER);
        add(mensaje, BorderLayout.CENTER);

        // Panel con botón “Siguiente”
        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(230, 230, 230)); // mismo gris
        JButton siguiente = new JButton("Siguiente");
        siguiente.setFont(new Font("Segoe UI", Font.BOLD, 16));
        siguiente.setBackground(new Color(100, 149, 237)); // azul suave
        siguiente.setForeground(Color.WHITE);
        siguiente.setFocusPainted(false);

        siguiente.addActionListener(e -> {
            dispose(); // cerrar bienvenida
            callback.accept(usuario); // abrir menú según rol
        });

        panelBoton.add(siguiente);
        add(panelBoton, BorderLayout.SOUTH);

        setVisible(true);
    }
}