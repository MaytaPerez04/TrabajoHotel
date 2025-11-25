package Principal;

import javax.swing.*;
import java.awt.*;

// IMPORTS CORREGIDOS SEGÚN TU ESTRUCTURA
import vistas.LoginFrame;
import controlador.Hotelgama;
import modelo.Reserva;
import modelo.Usuario;  // <-- Agregar esto para que reconozca Usuario


public class main extends JFrame {

    public main() {

        setTitle("HotelGama - Menú Principal");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // LOGO Y TEXTO
        JPanel top = new JPanel();
        top.setBackground(new Color(236, 240, 241));
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // LOGO
        JLabel logo = new JLabel();
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            ImageIcon iconLogo = new ImageIcon(getClass().getResource("/img/logo.png"));
            Image imgLogo = iconLogo.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
            logo.setIcon(new ImageIcon(imgLogo));
        } catch (Exception e) {
            logo.setText("HotelGama"); // Fallback
            logo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        }
        
        top.add(logo);
        add(top, BorderLayout.NORTH);

        // PANEL CENTRAL CON BOTONES
        JPanel center = new JPanel();
        center.setBackground(new Color(236, 240, 241));
        center.setLayout(new GridLayout(2, 1, 20, 20));
        center.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        JButton btnReserva = crearBoton("Quiero hacer una reserva");
        JButton btnLogin = crearBoton("Iniciar sesión");

        center.add(btnReserva);
        center.add(btnLogin);

        // Acción botón login con callback
        btnLogin.addActionListener(e -> {
            new LoginFrame(u -> { // 'u' es el usuario logueado
                Hotelgama.mostrarMenuSegunRol(u, null); // abrir menú según rol
            });
        });


        // Acción botón reserva
        btnReserva.addActionListener(e -> new Reserva());

        add(center, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(52, 152, 219));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return btn;
    }

    public static void main(String[] args) {
        new main();
    }
}


