package Administrador.finanzas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import controlador.ConexionBD;

public class GestionFinancieraFrame extends JFrame {

    public GestionFinancieraFrame() {
        setTitle("Gestión Financiera");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 240, 240));

        // ---------------- HEADER ----------------
        JPanel header = new JPanel();
        header.setBackground(new Color(150, 0, 0));
        header.setPreferredSize(new Dimension(500, 60));
        JLabel title = new JLabel("Ingresos por Mes");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.add(title);
        add(header, BorderLayout.NORTH);

        // ---------------- TABLA ----------------
        String[] columnas = { "Mes", "Año", "Total Ingresos" };
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // ---------------- BOTON CERRAR ----------------
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(200, 0, 0));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCerrar.setFocusPainted(false);
        btnCerrar.addActionListener(e -> dispose());
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnCerrar);
        add(panelBoton, BorderLayout.SOUTH);

        // ---------------- CARGAR DATOS ----------------
        cargarDatos(modelo);

        setVisible(true);
    }

    private void cargarDatos(DefaultTableModel modelo) {
        try (Connection conn = ConexionBD.conectar()) {
            if (conn == null) throw new Exception("No se pudo conectar a la base de datos.");

            String sql = """
                    SELECT YEAR(fechaEntrada) AS anio, MONTH(fechaEntrada) AS mes, SUM(montoTotal) AS total
                    FROM reserva
                    GROUP BY YEAR(fechaEntrada), MONTH(fechaEntrada)
                    ORDER BY anio, mes
                    """;
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    int anio = rs.getInt("anio");
                    int mes = rs.getInt("mes");
                    double total = rs.getDouble("total");
                    String mesNombre = switch (mes) {
                        case 1 -> "Enero"; case 2 -> "Febrero"; case 3 -> "Marzo"; case 4 -> "Abril";
                        case 5 -> "Mayo"; case 6 -> "Junio"; case 7 -> "Julio"; case 8 -> "Agosto";
                        case 9 -> "Septiembre"; case 10 -> "Octubre"; case 11 -> "Noviembre"; case 12 -> "Diciembre";
                        default -> "Desconocido";
                    };
                    Object[] fila = { mesNombre, anio, String.format("$%.2f", total) };
                    modelo.addRow(fila);
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al generar reporte financiero: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

