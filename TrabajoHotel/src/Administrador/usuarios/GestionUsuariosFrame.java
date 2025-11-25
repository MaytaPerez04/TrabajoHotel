package Administrador.usuarios;

import javax.swing.*;
import java.awt.*;
import modelo.Administrador;

public class GestionUsuariosFrame extends JFrame {

    public GestionUsuariosFrame() {
        setTitle("Gestión de Usuarios");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new GridLayout(5, 1, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 240, 240));

        String[] opciones = { "Crear Usuario", "Editar Usuario", "Eliminar Usuario", "Ver Lista", "Cerrar" };
        for (String op : opciones) {
            JButton btn = new JButton(op);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
            btn.setBackground(new Color(150, 0, 0));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.addActionListener(e -> manejarAccion(op));
            add(btn);
        }

        setVisible(true);
    }

    private void manejarAccion(String opcion) {
        switch (opcion) {
            case "Crear Usuario" -> new CrearUsuariosFrame();
            case "Editar Usuario" -> new EditarUsuariosFrame();
            case "Eliminar Usuario" -> new EliminarUsuariosFrame();
            case "Ver Lista" -> new ListarUsuariosFrame();
            case "Cerrar" -> dispose();
        }
    }

    }

