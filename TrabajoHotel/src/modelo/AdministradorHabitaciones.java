package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import controlador.ConexionBD;

public class AdministradorHabitaciones {

    // Agregar habitación
    public static void agregarHabitacion(String numero, String tipo, double precio) throws Exception {
        Connection conn = ConexionBD.conectar();
        if (conn == null) throw new Exception("No se pudo conectar a la base de datos.");

        String sql = "INSERT INTO habitacion (numero, tipo, precio) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, numero);
            ps.setString(2, tipo);
            ps.setDouble(3, precio);
            ps.executeUpdate();
        } finally {
            conn.close();
        }
    }

    // Editar habitación
    public static void editarHabitacion(int id, String numero, String tipo, double precio) throws Exception {
        Connection conn = ConexionBD.conectar();
        if (conn == null) throw new Exception("No se pudo conectar a la base de datos.");

        String sql = "UPDATE habitacion SET numero = ?, tipo = ?, precio = ? WHERE idHabitacion = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, numero);
            ps.setString(2, tipo);
            ps.setDouble(3, precio);
            ps.setInt(4, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new Exception("Habitación no encontrada con ID: " + id);
        } finally {
            conn.close();
        }
    }

    // Eliminar habitación
    public static void eliminarHabitacion(int id) throws Exception {
        Connection conn = ConexionBD.conectar();
        if (conn == null) throw new Exception("No se pudo conectar a la base de datos.");

        String sql = "DELETE FROM habitacion WHERE idHabitacion = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new Exception("Habitación no encontrada con ID: " + id);
        } finally {
            conn.close();
        }
    }

    // Listar habitaciones
    public static String getListaHabitaciones() throws Exception {
        Connection conn = ConexionBD.conectar();
        if (conn == null) throw new Exception("No se pudo conectar a la base de datos.");

        StringBuilder lista = new StringBuilder();
        String sql = "SELECT * FROM habitacion";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean hasRooms = false;
            while (rs.next()) {
                hasRooms = true;
                lista.append("ID: ").append(rs.getInt("idHabitacion"))
                     .append(" | Número: ").append(rs.getString("numero"))
                     .append(" | Tipo: ").append(rs.getString("tipo"))
                     .append(" | Precio: ").append(rs.getDouble("precio"))
                     .append("\n");
            }
            return hasRooms ? lista.toString() : "No hay habitaciones registradas.";
        } finally {
            conn.close();
        }
    }
}
