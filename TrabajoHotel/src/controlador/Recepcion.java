package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import controlador.ConexionBD;

public class Recepcion {

    public static boolean editarReserva(int idReserva, LocalDate fechaInicio, LocalDate fechaFin) {
        Connection conn = ConexionBD.conectar();
        if (conn == null) return false;

        try {
            String sql = "UPDATE reserva SET fecha_ini_res = ?, fecha_fin_res = ? WHERE idreserva = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, java.sql.Date.valueOf(fechaInicio));
            ps.setDate(2, java.sql.Date.valueOf(fechaFin));
            ps.setInt(3, idReserva);

            int filas = ps.executeUpdate();
            return filas > 0; // true si se actualizó
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

}
