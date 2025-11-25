package Administrador.usuarios;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controlador.ConexionBD;

import java.awt.*;
import modelo.Administrador;
import java.util.Vector;
import java.sql.*;

public class ListarUsuariosFrame extends JFrame {

    public ListarUsuariosFrame() {
        setTitle("Lista de Usuarios");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // --- HEADER ---
        JPanel header = new JPanel();
        header.setBackground(new Color(192, 57, 43));
        header.setPreferredSize(new Dimension(600, 60));
        JLabel titulo = new JLabel("Lista de Usuarios");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.add(titulo);
        add(header, BorderLayout.NORTH);

        // --- TABLA ---
        String[] columnas = {"ID", "Nombre", "Usuario", "Rol"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.setRowHeight(25);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabla.getTableHeader().setBackground(new Color(192, 57, 43));
        tabla.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scroll, BorderLayout.CENTER);

        // --- CARGAR DATOS ---
        try {
            Connection conn = ConexionBD.conectar();
            if (conn == null) throw new Exception("No se pudo conectar a la base de datos.");

            String sql = "SELECT * FROM usuario";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Vector<Object> fila = new Vector<>();
                fila.add(rs.getInt("idUsuario"));
                fila.add(rs.getString("nombre"));
                fila.add(rs.getString("usuario"));
                fila.add(rs.getString("rol"));
                modelo.addRow(fila);
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // --- BOTON CERRAR ---
        JPanel botones = new JPanel();
        botones.setBackground(new Color(245, 245, 245));
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(192, 57, 43));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnCerrar.addActionListener(e -> dispose());

        botones.add(btnCerrar);
        add(botones, BorderLayout.SOUTH);

        setVisible(true);
    }
}

