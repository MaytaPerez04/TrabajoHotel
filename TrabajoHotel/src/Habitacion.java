import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Habitacion {
	Connection conn = conexionBD.conectar();
	public int idHabitacion;
	public Enum tipoHabitacion;
	public Enum estadoHabitacion;

	public Habitacion(int idHabitacion, Enum tipoHabitacion, Enum estadoHabitacion) {
		super();
		this.idHabitacion = idHabitacion;
		this.tipoHabitacion = tipoHabitacion;
		this.estadoHabitacion = estadoHabitacion;
	}

	public int getIdHabitacion() {
		return idHabitacion;
	}

	public void setIdHabitacion(int idHabitacion) {
		this.idHabitacion = idHabitacion;
	}

	public Enum getTipoHabitacion() {
		return tipoHabitacion;
	}

	public void setTipoHabitacion(Enum tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}

	public Enum getEstadoHabitacion() {
		return estadoHabitacion;
	}

	public void setEstadoHabitacion(Enum estadoHabitacion) {
		this.estadoHabitacion = estadoHabitacion;
	}

	public void liberaHabitacion(int idHabitacion) throws SQLException {
		String sql = "UPDATE habitacion SET estado = ? WHERE idHabitacion = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, "Libre");
			ps.setInt(2, idHabitacion);
			ps.executeUpdate();
		}
	}

}