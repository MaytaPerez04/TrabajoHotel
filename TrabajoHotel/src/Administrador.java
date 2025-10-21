import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Administrador {
	// Menú principal del Administrador
	public static void mostrarMenuAdministrador(Usuario admin) {
		String[] opciones = { "Gestionar usuarios", "Gestionar habitaciones", "Gestión financiera", "Cerrar sesión" };
		int opcion;
		do {
			opcion = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Menú Administrador",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

			switch (opcion) {
			case 0 -> menuGestionUsuarios();
			case 1 -> menuGestionHabitaciones();
			case 2 -> gestionFinanciera();
			case 3 -> JOptionPane.showMessageDialog(null, "Sesión cerrada");
			default -> JOptionPane.showMessageDialog(null, "Opción no válida");
			}
		} while (opcion != 3);
	}

	// Submenú para gestión de usuarios
	private static void menuGestionUsuarios() {
		String[] opcUsuarios = { "Crear usuario", "Editar usuario", "Eliminar usuario", "Ver lista de usuarios",
				"Volver" };
		int resUsuarios;
		do {
			resUsuarios = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Gestión de usuarios",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcUsuarios, opcUsuarios[0]);

			switch (resUsuarios) {
			case 0 -> crearUsuario();
			case 1 -> editarUsuario();
			case 2 -> eliminarUsuario();
			case 3 -> listarUsuarios();
			}
		} while (resUsuarios != 4);
	}

	// Crear usuario
	private static void crearUsuario() {
		Connection conn = conexionBD.conectar();
		if (conn == null) {
			JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.");
			return;
		}
		try {
			String nombre = JOptionPane.showInputDialog("Ingrese el nombre del usuario:");
			if (nombre == null)
				return;
			String usuario = JOptionPane.showInputDialog("Ingrese el nombre de usuario (login):");
			if (usuario == null)
				return;
			String contrasena = JOptionPane.showInputDialog("Ingrese la contraseña:");
			if (contrasena == null)
				return;
			String[] roles = { "Administrador", "Recepcion", "Limpieza" };
			String rol = (String) JOptionPane.showInputDialog(null, "Seleccione el rol:", "Rol",
					JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);
			if (rol == null)
				return;

			String sql = "INSERT INTO usuario (nombre, usuario, contrasena, rol) VALUES (?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, nombre);
			ps.setString(2, usuario);
			ps.setString(3, contrasena);
			ps.setString(4, rol);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				JOptionPane.showMessageDialog(null,
						"Usuario creado: " + nombre + " (ID: " + id + ", Rol: " + rol + ")");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al crear usuario: " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// Editar usuario
	private static void editarUsuario() {
		Connection conn = conexionBD.conectar();
		if (conn == null) {
			JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.");
			return;
		}
		try {
			String idStr = JOptionPane.showInputDialog("Ingrese el ID del usuario a editar:");
			if (idStr == null)
				return;
			int id = Integer.parseInt(idStr);
			String sql = "SELECT * FROM usuario WHERE idUsuario = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				JOptionPane.showMessageDialog(null, "Usuario no encontrado con ID: " + id);
				return;
			}
			Usuario usuario = new Usuario(id, rs.getString("nombre"), rs.getString("usuario"),
					rs.getString("contrasena"), rs.getString("rol"));

			String nombre = JOptionPane.showInputDialog("Nuevo nombre (deje vacío para no cambiar):",
					usuario.getNombre());
			String usuarioLogin = JOptionPane.showInputDialog("Nuevo nombre de usuario (deje vacío para no cambiar):",
					usuario.getUsuario());
			String contrasena = JOptionPane.showInputDialog("Nueva contraseña (deje vacío para no cambiar):",
					usuario.getContrasena());
			String[] roles = { "Administrador", "Recepcion", "Limpieza" };
			String rol = (String) JOptionPane.showInputDialog(null, "Seleccione nuevo rol:", "Rol",
					JOptionPane.QUESTION_MESSAGE, null, roles, usuario.getRol());

			String sqlUpdate = "UPDATE usuario SET nombre = ?, usuario = ?, contrasena = ?, rol = ? WHERE idUsuario = ?";
			ps = conn.prepareStatement(sqlUpdate);
			ps.setString(1, nombre != null && !nombre.isEmpty() ? nombre : usuario.getNombre());
			ps.setString(2, usuarioLogin != null && !usuarioLogin.isEmpty() ? usuarioLogin : usuario.getUsuario());
			ps.setString(3, contrasena != null && !contrasena.isEmpty() ? contrasena : usuario.getContrasena());
			ps.setString(4, rol != null ? rol : usuario.getRol());
			ps.setInt(5, id);
			ps.executeUpdate();

			JOptionPane.showMessageDialog(null, "Usuario actualizado: ID " + id);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "ID inválido. Debe ser un número.");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al editar usuario: " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// Eliminar usuario
	private static void eliminarUsuario() {
		Connection conn = conexionBD.conectar();
		if (conn == null) {
			JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.");
			return;
		}
		try {
			String idStr = JOptionPane.showInputDialog("Ingrese el ID del usuario a eliminar:");
			if (idStr == null)
				return;
			int id = Integer.parseInt(idStr);
			String sql = "DELETE FROM usuario WHERE idUsuario = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			int rows = ps.executeUpdate();
			if (rows > 0) {
				JOptionPane.showMessageDialog(null, "Usuario eliminado: ID " + id);
			} else {
				JOptionPane.showMessageDialog(null, "Usuario no encontrado con ID: " + id);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "ID inválido. Debe ser un número.");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// Ver lista de usuarios
	private static void listarUsuarios() {
		Connection conn = conexionBD.conectar();
		if (conn == null) {
			JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.");
			return;
		}
		try {
			String sql = "SELECT * FROM usuario";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			StringBuilder lista = new StringBuilder("Lista de usuarios:\n");
			boolean hasUsers = false;
			while (rs.next()) {
				hasUsers = true;
				lista.append("ID: ").append(rs.getInt("idUsuario")).append(" | Nombre: ").append(rs.getString("nombre"))
						.append(" | Usuario: ").append(rs.getString("usuario")).append(" | Rol: ")
						.append(rs.getString("rol")).append("\n");
			}
			JOptionPane.showMessageDialog(null, hasUsers ? lista.toString() : "No hay usuarios registrados.");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al listar usuarios: " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// Submenú para gestión de habitaciones
	private static void menuGestionHabitaciones() {
		String[] opcHabitaciones = { "Agregar habitación", "Modificar habitación", "Eliminar habitación",
				"Ver lista de habitaciones", "Volver" };
		int resHabitaciones;
		do {
			resHabitaciones = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Gestión de habitaciones",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcHabitaciones,
					opcHabitaciones[0]);

			switch (resHabitaciones) {
			case 0 -> agregarHabitacion();
			case 1 -> modificarHabitacion();
			case 2 -> eliminarHabitacion();
			case 3 -> listarHabitaciones();
			}
		} while (resHabitaciones != 4);
	}

	// Agregar habitación
	private static void agregarHabitacion() {
		Connection conn = conexionBD.conectar();
		if (conn == null) {
			JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.");
			return;
		}
		try {
			String idStr = JOptionPane.showInputDialog("Ingrese el ID de la habitación (ej. 101):");
			if (idStr == null)
				return;
			int idHabitacion = Integer.parseInt(idStr);
			String sqlCheck = "SELECT * FROM habitacion WHERE idHabitacion = ?";
			PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
			psCheck.setInt(1, idHabitacion);
			if (psCheck.executeQuery().next()) {
				JOptionPane.showMessageDialog(null, "Ya existe una habitación con ID " + idHabitacion);
				return;
			}

			Habitacion.TipoHabitacion[] tipos = Habitacion.TipoHabitacion.values();
			Habitacion.TipoHabitacion tipo = (Habitacion.TipoHabitacion) JOptionPane.showInputDialog(null,
					"Seleccione el tipo:", "Tipo de habitación", JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);
			if (tipo == null)
				return;

			String precioStr = JOptionPane.showInputDialog("Ingrese el precio por noche:");
			if (precioStr == null)
				return;
			double precio = Double.parseDouble(precioStr);

			Habitacion.EstadoHabitacion[] estados = Habitacion.EstadoHabitacion.values();
			Habitacion.EstadoHabitacion estado = (Habitacion.EstadoHabitacion) JOptionPane.showInputDialog(null,
					"Seleccione el estado:", "Estado de habitación", JOptionPane.QUESTION_MESSAGE, null, estados,
					Habitacion.EstadoHabitacion.LIBRE);
			if (estado == null)
				return;

			String[] estadosLimpieza = { "limpia", "limpiar", "limpiando" };
			String estadoLimpieza = (String) JOptionPane.showInputDialog(null, "Seleccione el estado de limpieza:",
					"Estado de limpieza", JOptionPane.QUESTION_MESSAGE, null, estadosLimpieza, "limpia");
			if (estadoLimpieza == null)
				return;

			String sql = "INSERT INTO habitacion (idHabitacion, tipo, estado, precio, estadoLimpieza) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, idHabitacion);
			ps.setString(2, tipo.toString());
			ps.setString(3, estado.toString());
			ps.setDouble(4, precio);
			ps.setString(5, estadoLimpieza);
			ps.executeUpdate();

			JOptionPane.showMessageDialog(null, "Habitación creada: " + idHabitacion + " (" + tipo + ")");
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "ID o precio inválido. Deben ser números.");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al crear habitación: " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// Modificar habitación
	private static void modificarHabitacion() {
	    Connection conn = null;
	    PreparedStatement psCheck = null;
	    ResultSet rs = null;
	    PreparedStatement psUpdate = null;

	    try {
	        conn = conexionBD.conectar();
	        if (conn == null) {
	            JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.");
	            return;
	        }

	        String idStr = JOptionPane.showInputDialog("Ingrese el ID de la habitación que desea modificar:");
	        if (idStr == null) return;

	        int idHabitacion;
	        try {
	            idHabitacion = Integer.parseInt(idStr);
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(null, "ID inválido. Debe ingresar un número entero.");
	            return;
	        }

	        // Verificar si existe la habitación
	        String sqlCheck = "SELECT * FROM habitacion WHERE idHabitacion = ?";
	        psCheck = conn.prepareStatement(sqlCheck);
	        psCheck.setInt(1, idHabitacion);
	        rs = psCheck.executeQuery();

	        if (!rs.next()) {
	            JOptionPane.showMessageDialog(null, "No existe habitación con ID " + idHabitacion);
	            return;
	        }

	        // Obtener datos actuales
	        String tipoActual = rs.getString("tipo");
	        String estadoActual = rs.getString("estado");
	        double precioActual = rs.getDouble("precio");
	        String estadoLimpiezaActual = rs.getString("estadoLimpieza");

	        // Pedir nuevos valores (con los actuales como predeterminados)
	        Habitacion.TipoHabitacion[] tipos = Habitacion.TipoHabitacion.values();
	        Habitacion.TipoHabitacion tipoNuevo = (Habitacion.TipoHabitacion) JOptionPane.showInputDialog(
	                null,
	                "Seleccione el nuevo tipo:",
	                "Tipo de habitación",
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                tipos,
	                Habitacion.TipoHabitacion.valueOf(tipoActual.toUpperCase()) // CORREGIDO
	        );
	        if (tipoNuevo == null) return;

	        String precioStr = JOptionPane.showInputDialog("Ingrese el nuevo precio por noche:", precioActual);
	        if (precioStr == null) return;

	        double precioNuevo;
	        try {
	            precioNuevo = Double.parseDouble(precioStr);
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(null, "Precio inválido. Debe ser un número.");
	            return;
	        }

	        Habitacion.EstadoHabitacion[] estados = Habitacion.EstadoHabitacion.values();
	        Habitacion.EstadoHabitacion estadoNuevo = (Habitacion.EstadoHabitacion) JOptionPane.showInputDialog(
	                null,
	                "Seleccione el nuevo estado:",
	                "Estado de habitación",
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                estados,
	                Habitacion.EstadoHabitacion.valueOf(estadoActual.toUpperCase()) // CORREGIDO
	        );
	        if (estadoNuevo == null) return;

	        String[] estadosLimpieza = { "limpia", "limpiar", "limpiando" };
	        String estadoLimpiezaNuevo = (String) JOptionPane.showInputDialog(
	                null,
	                "Seleccione el nuevo estado de limpieza:",
	                "Estado de limpieza",
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                estadosLimpieza,
	                estadoLimpiezaActual
	        );
	        if (estadoLimpiezaNuevo == null) return;

	        // Confirmar cambios antes de actualizar
	        int confirm = JOptionPane.showConfirmDialog(
	                null,
	                "¿Está seguro que desea guardar los cambios?",
	                "Confirmar modificación",
	                JOptionPane.YES_NO_OPTION
	        );
	        if (confirm != JOptionPane.YES_OPTION) return;

	        // Actualizar en base de datos
	        String sqlUpdate = "UPDATE habitacion SET tipo = ?, precio = ?, estado = ?, estadoLimpieza = ? WHERE idHabitacion = ?";
	        psUpdate = conn.prepareStatement(sqlUpdate);
	        psUpdate.setString(1, tipoNuevo.toString());
	        psUpdate.setDouble(2, precioNuevo);
	        psUpdate.setString(3, estadoNuevo.toString());
	        psUpdate.setString(4, estadoLimpiezaNuevo);
	        psUpdate.setInt(5, idHabitacion);

	        int filas = psUpdate.executeUpdate();
	        if (filas > 0) {
	            JOptionPane.showMessageDialog(null, "Habitación modificada correctamente.");
	        } else {
	            JOptionPane.showMessageDialog(null, "No se pudo modificar la habitación.");
	        }

	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Error en la base de datos: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
	        try { if (psCheck != null) psCheck.close(); } catch (SQLException e) { e.printStackTrace(); }
	        try { if (psUpdate != null) psUpdate.close(); } catch (SQLException e) { e.printStackTrace(); }
	        try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
	    }
	}



	// Eliminar habitación
	private static void eliminarHabitacion() {
		Connection conn = conexionBD.conectar();
		if (conn == null) {
			JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.");
			return;
		}
		try {
			String idStr = JOptionPane.showInputDialog("Ingrese el ID de la habitación a eliminar:");
			if (idStr == null)
				return;
			int idHabitacion = Integer.parseInt(idStr);
			String sql = "DELETE FROM habitacion WHERE idHabitacion = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, idHabitacion);
			int rows = ps.executeUpdate();
			if (rows > 0) {
				JOptionPane.showMessageDialog(null, "Habitación eliminada: ID " + idHabitacion);
			} else {
				JOptionPane.showMessageDialog(null, "Habitación no encontrada con ID: " + idHabitacion);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "ID inválido. Debe ser un número.");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al eliminar habitación: " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// Ver lista de habitaciones
	private static void listarHabitaciones() {
		Connection conn = conexionBD.conectar();
		if (conn == null) {
			JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.");
			return;
		}
		try {
			String sql = "SELECT * FROM habitacion";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			StringBuilder lista = new StringBuilder("Lista de habitaciones:\n");
			boolean hasHabitaciones = false;
			while (rs.next()) {
				hasHabitaciones = true;
				lista.append("ID: ").append(rs.getInt("idHabitacion")).append(" | Tipo: ").append(rs.getString("tipo"))
						.append(" | Precio: $").append(rs.getDouble("precio")).append(" | Estado: ")
						.append(rs.getString("estado")).append(" | Estado Limpieza: ")
						.append(rs.getString("estadoLimpieza")).append("\n");
			}
			JOptionPane.showMessageDialog(null,
					hasHabitaciones ? lista.toString() : "No hay habitaciones registradas.");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al listar habitaciones: " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// Gestión financiera (ingresos por mes)
	private static void gestionFinanciera() {
		Connection conn = conexionBD.conectar();
		if (conn == null) {
			JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.");
			return;
		}
		try {
			String sql = "SELECT YEAR(fechaEntrada) AS anio, MONTH(fechaEntrada) AS mes, SUM(montoTotal) AS total "
					+ "FROM reserva GROUP BY YEAR(fechaEntrada), MONTH(fechaEntrada) ORDER BY anio, mes";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			StringBuilder reporte = new StringBuilder("Ingresos por mes:\n");
			boolean hasReservas = false;
			while (rs.next()) {
				hasReservas = true;
				int anio = rs.getInt("anio");
				int mes = rs.getInt("mes");
				double total = rs.getDouble("total");
				String mesNombre = switch (mes) {
				case 1 -> "Enero";
				case 2 -> "Febrero";
				case 3 -> "Marzo";
				case 4 -> "Abril";
				case 5 -> "Mayo";
				case 6 -> "Junio";
				case 7 -> "Julio";
				case 8 -> "Agosto";
				case 9 -> "Septiembre";
				case 10 -> "Octubre";
				case 11 -> "Noviembre";
				case 12 -> "Diciembre";
				default -> "Desconocido";
				};
				reporte.append(mesNombre).append(" ").append(anio).append(": $").append(String.format("%.2f", total))
						.append("\n");
			}
			JOptionPane.showMessageDialog(null, hasReservas ? reporte.toString() : "No hay reservas registradas.");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al generar reporte financiero: " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
