import javax.swing.*;
import java.awt.*;

public class main extends JFrame {

    public main() {

        setTitle("HotelGama - Menú Principal");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // -------- PANEL SUPERIOR --------
        JPanel header = new JPanel();
        header.setBackground(new Color(33, 47, 60));
        header.setPreferredSize(new Dimension(500, 70));

        JLabel titulo = new JLabel("HotelGama");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.add(titulo);

        add(header, BorderLayout.NORTH);

        // -------- PANEL CENTRAL --------
        JPanel center = new JPanel();
        center.setBackground(new Color(236, 240, 241));
        center.setLayout(new GridLayout(2, 1, 20, 20));
        center.setBorder(BorderFactory.createEmptyBorder(25, 60, 25, 60));

        JButton btnReserva = crearBoton("Quiero hacer una reserva");
        JButton btnLogin = crearBoton("Iniciar sesión");

        center.add(btnReserva);
        center.add(btnLogin);

        add(center, BorderLayout.CENTER);

        // -------- ACCIONES --------

        // Abrir ventana de reservas (JFrame nuevo)
        btnReserva.addActionListener(e -> new Reserva());

        // Llama al login que ya existe en tu proyecto
        btnLogin.addActionListener(e -> {

            Usuario u = Login.iniciarSesion(); // usa tu sistema actual

            if (u != null) {
                // abrir menú según rol (próximos archivos)
                Hotelgama.mostrarMenuSegunRol(u, this);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo iniciar sesión");
            }
        });

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
