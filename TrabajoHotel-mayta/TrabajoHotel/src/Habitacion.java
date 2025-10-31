import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Habitacion {
	// Enum interno para tipo de habitación
	public enum TipoHabitacion {
		SIMPLE(1), DOBLE(2), SUITE(4);

		private final int capacidad;

		TipoHabitacion(int capacidad) {
			this.capacidad = capacidad;
		}

		public int getCapacidad() {
			return capacidad;
		}
	}

	// Enum interno para estado de habitación
	public enum EstadoHabitacion {
		LIBRE, OCUPADA
	}

	private int idHabitacion;
	private TipoHabitacion tipoHabitacion;
	private EstadoHabitacion estadoHabitacion;
	private double precio;
	private String estadoLimpieza; // Nuevo campo para reflejar la tabla

	public Habitacion(int idHabitacion, TipoHabitacion tipoHabitacion, EstadoHabitacion estadoHabitacion, double precio,
			String estadoLimpieza) {
		this.idHabitacion = idHabitacion;
		this.tipoHabitacion = tipoHabitacion;
		this.estadoHabitacion = estadoHabitacion;
		this.precio = precio;
		this.estadoLimpieza = estadoLimpieza;
	}

	// Getters y setters
	public int getIdHabitacion() {
		return idHabitacion;
	}

	public void setIdHabitacion(int idHabitacion) {
		this.idHabitacion = idHabitacion;
	}

	public TipoHabitacion getTipoHabitacion() {
		return tipoHabitacion;
	}

	public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}

	public EstadoHabitacion getEstadoHabitacion() {
		return estadoHabitacion;
	}

	public void setEstadoHabitacion(EstadoHabitacion estadoHabitacion) {
		this.estadoHabitacion = estadoHabitacion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getEstadoLimpieza() {
		return estadoLimpieza;
	}

	public void setEstadoLimpieza(String estadoLimpieza) {
		this.estadoLimpieza = estadoLimpieza;
	}

	// Método para liberar habitación
	public void liberaHabitacion(Connection conn) throws SQLException {
		String sql = "UPDATE habitacion SET estado = ?, estadoLimpieza = ? WHERE idHabitacion = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, EstadoHabitacion.LIBRE.toString());
			ps.setString(2, "limpia");
			ps.setInt(3, idHabitacion);
			ps.executeUpdate();
		}
	}

	@Override
	public String toString() {
		return "Habitación " + idHabitacion + " | Tipo: " + tipoHabitacion + " | Precio: $" + precio + " | Estado: "
				+ estadoHabitacion + " | Estado Limpieza: " + estadoLimpieza;
	}
}