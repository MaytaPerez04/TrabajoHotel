package modelo;

import javax.swing.*;

import Principal.main;
import controlador.ConexionBD;
import vistas.ResumenReserva;

import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Reserva extends JFrame {

	// --- estado del formulario ---
	private String diaStr = "", mesStr = "", anoStr = "";
	private String diasEstadiaStr = "", titularStr = "";
	private String tipoIdStr = "DNI", numIdStr = "";
	private String tipoReservaStr = "Individuo";
	private String cantDelegacionStr = "";

	private ArrayList<Integer> idsHabitaciones = new ArrayList<>();
	private ArrayList<String> descHabitaciones = new ArrayList<>();

	// componentes
	private JTextField dia;
	private JTextField mes;
	private JTextField ano;
	private JTextField diasEstadia;
	private JTextField titular;
	private JComboBox<String> tipoIdentificacion;
	private JTextField numIdentificacion;
	private JComboBox<String> tipoReserva;
	private JComboBox<String> comboHabitaciones;
	private JTextField cantHabitacionesDelegacion;

	public Reserva() {
		this(null);
	}

	public Reserva(Reserva passed) {
		if (passed != null) {
			this.diaStr = passed.diaStr;
			this.mesStr = passed.mesStr;
			this.anoStr = passed.anoStr;
			this.diasEstadiaStr = passed.diasEstadiaStr;
			this.titularStr = passed.titularStr;
			this.tipoIdStr = passed.tipoIdStr;
			this.numIdStr = passed.numIdStr;
			this.tipoReservaStr = passed.tipoReservaStr;
			this.cantDelegacionStr = passed.cantDelegacionStr;
			this.idsHabitaciones = new ArrayList<>(passed.idsHabitaciones);
			this.descHabitaciones = new ArrayList<>(passed.descHabitaciones);
		}

		setTitle("HotelGama - Nueva Reserva");
		setSize(600, 540);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		// HEADER
		JPanel header = new JPanel();
		header.setBackground(new Color(33, 47, 60));
		header.setPreferredSize(new Dimension(600, 70));
		JLabel titulo = new JLabel("Crear Reserva");
		titulo.setForeground(Color.WHITE);
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
		header.add(titulo);
		add(header, BorderLayout.NORTH);

		// FORM
		JPanel form = new JPanel();
		form.setBackground(new Color(236, 240, 241));
		form.setLayout(new GridLayout(10, 2, 12, 12));
		form.setBorder(BorderFactory.createEmptyBorder(20, 35, 20, 35));

		dia = new JTextField();
		mes = new JTextField();
		ano = new JTextField();
		diasEstadia = new JTextField();
		titular = new JTextField();

		String[] tiposId = { "DNI", "Pasaporte" };
		tipoIdentificacion = new JComboBox<>(tiposId);
		numIdentificacion = new JTextField();

		String[] tipos = { "Individuo", "Delegación" };
		tipoReserva = new JComboBox<>(tipos);

		comboHabitaciones = new JComboBox<>();

		if (idsHabitaciones.isEmpty()) {
			cargarHabitacionesLibres(idsHabitaciones, descHabitaciones, comboHabitaciones);
		} else {
			for (String s : descHabitaciones)
				comboHabitaciones.addItem(s);
		}

		cantHabitacionesDelegacion = new JTextField();
		cantHabitacionesDelegacion.setEnabled(false);

		tipoReserva.addActionListener(e -> {
			boolean esDelegacion = tipoReserva.getSelectedItem().equals("Delegación");
			cantHabitacionesDelegacion.setEnabled(esDelegacion);
			comboHabitaciones.setEnabled(!esDelegacion);
		});

		form.add(new JLabel("Día de inicio:"));
		form.add(dia);
		form.add(new JLabel("Mes de inicio:"));
		form.add(mes);
		form.add(new JLabel("Año de inicio:"));
		form.add(ano);
		form.add(new JLabel("Días de estadía:"));
		form.add(diasEstadia);
		form.add(new JLabel("Titular:"));
		form.add(titular);
		form.add(new JLabel("Tipo de identificación:"));
		form.add(tipoIdentificacion);
		form.add(new JLabel("Número de identificación:"));
		form.add(numIdentificacion);
		form.add(new JLabel("Tipo de reserva:"));
		form.add(tipoReserva);
		form.add(new JLabel("Habitación (Individuo):"));
		form.add(comboHabitaciones);
		form.add(new JLabel("Cantidad de habitaciones (Delegación):"));
		form.add(cantHabitacionesDelegacion);

		add(form, BorderLayout.CENTER);

		// rellenar si venimos con valores
		if (!diaStr.isEmpty())
			dia.setText(diaStr);
		if (!mesStr.isEmpty())
			mes.setText(mesStr);
		if (!anoStr.isEmpty())
			ano.setText(anoStr);
		if (!diasEstadiaStr.isEmpty())
			diasEstadia.setText(diasEstadiaStr);
		if (!titularStr.isEmpty())
			titular.setText(titularStr);
		if (tipoIdStr != null)
			tipoIdentificacion.setSelectedItem(tipoIdStr);
		if (!numIdStr.isEmpty())
			numIdentificacion.setText(numIdStr);
		if (tipoReservaStr != null)
			tipoReserva.setSelectedItem(tipoReservaStr);
		if (!cantDelegacionStr.isEmpty())
			cantHabitacionesDelegacion.setText(cantDelegacionStr);

		// BOTONES
		JPanel botones = new JPanel();
		botones.setBackground(new Color(236, 240, 241));
		botones.setLayout(new FlowLayout());

		JButton aceptar = new JButton("Confirmar");
		JButton cancelar = new JButton("Cancelar");

		estiloBoton(aceptar, new Color(39, 174, 96));
		estiloBoton(cancelar, new Color(192, 57, 43));

		cancelar.addActionListener(e -> {
			dispose();
			new main();
		});

		aceptar.addActionListener(e -> {
			try {
				int d = Integer.parseInt(dia.getText().trim());
				int m = Integer.parseInt(mes.getText().trim());
				int a = Integer.parseInt(ano.getText().trim());

				if (d < 1 || d > 31) {
					JOptionPane.showMessageDialog(this, "El día debe estar entre 1 y 31.");
					return;
				}
				if (m < 1 || m > 12) {
					JOptionPane.showMessageDialog(this, "El mes debe estar entre 1 y 12.");
					return;
				}
				if (a < 1900 || a > 3000) {
					JOptionPane.showMessageDialog(this, "Año inválido.");
					return;
				}

				LocalDate fechaInicio = LocalDate.of(a, m, d);

				int dias = Integer.parseInt(diasEstadia.getText().trim());
				if (dias <= 0) {
					JOptionPane.showMessageDialog(this, "Los días de estadía deben ser mayor que 0.");
					return;
				}
				LocalDate fechaFin = fechaInicio.plusDays(dias);

				String nombreTitular = titular.getText().trim();
				if (nombreTitular.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Ingrese nombre del titular.");
					return;
				}

				String tipoId = (String) tipoIdentificacion.getSelectedItem();
				String numId = numIdentificacion.getText().trim();
				if (tipoId.equals("DNI")) {
					if (!numId.matches("\\d{8}")) {
						JOptionPane.showMessageDialog(this, "El DNI debe tener exactamente 8 dígitos numéricos.");
						return;
					}
				} else {
					if (!numId.matches("[A-Za-z0-9]{9}")) {
						JOptionPane.showMessageDialog(this,
								"El pasaporte debe tener exactamente 9 caracteres alfanuméricos.");
						return;
					}
				}

				// guardar estado en la instancia
				this.diaStr = String.valueOf(d);
				this.mesStr = String.valueOf(m);
				this.anoStr = String.valueOf(a);
				this.diasEstadiaStr = String.valueOf(dias);
				this.titularStr = nombreTitular;
				this.tipoIdStr = tipoId;
				this.numIdStr = numId;
				this.tipoReservaStr = (String) tipoReserva.getSelectedItem();
				this.cantDelegacionStr = cantHabitacionesDelegacion.getText().trim();

				// preparar habitaciones seleccionadas
				ArrayList<Integer> habitacionesSeleccionadas = new ArrayList<>();
				ArrayList<String> habitacionesDescSeleccionadas = new ArrayList<>();

				if (this.tipoReservaStr.equals("Individuo")) {
					int index = comboHabitaciones.getSelectedIndex();
					if (index < 0) {
						JOptionPane.showMessageDialog(this, "Seleccione una habitación.");
						return;
					}
					habitacionesSeleccionadas.add(idsHabitaciones.get(index));
					habitacionesDescSeleccionadas.add(descHabitaciones.get(index));
				} else {
					int cantidad = Integer.parseInt(this.cantDelegacionStr.isEmpty() ? "0" : this.cantDelegacionStr);
					if (cantidad <= 0) {
						JOptionPane.showMessageDialog(this, "Cantidad inválida.");
						return;
					}
					if (cantidad > idsHabitaciones.size()) {
						JOptionPane.showMessageDialog(this, "No hay tantas habitaciones libres.");
						return;
					}
					for (int i = 0; i < cantidad; i++) {
						habitacionesSeleccionadas.add(idsHabitaciones.get(i));
						habitacionesDescSeleccionadas.add(descHabitaciones.get(i));
					}
				}

				new ResumenReserva(this, // parent JFrame
						this, // instancia Reserva para volver al editar
						fechaInicio, fechaFin, Integer.parseInt(diasEstadia.getText().trim()), nombreTitular, tipoId,
						numId, this.tipoReservaStr, habitacionesSeleccionadas, habitacionesDescSeleccionadas);

				this.setVisible(false);

			} catch (NumberFormatException nf) {
				JOptionPane.showMessageDialog(this, "Complete todos los campos numéricos correctamente.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Datos inválidos: " + ex.getMessage());
			}
		});

		botones.add(aceptar);
		botones.add(cancelar);
		add(botones, BorderLayout.SOUTH);

		setVisible(true);
	}

	private void estiloBoton(JButton btn, Color bg) {
		btn.setBackground(bg);
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btn.setFocusPainted(false);
		btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
	}

	private void cargarHabitacionesLibres(ArrayList<Integer> ids, ArrayList<String> descs, JComboBox<String> combo) {
		ids.clear();
		descs.clear();
		combo.removeAllItems();
		try {
			Connection cn = ConexionBD.conectar();
			PreparedStatement ps = cn
					.prepareStatement("SELECT idHabitacion, tipo, precio FROM habitacion WHERE estado = 'libre'");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("idHabitacion");
				String tipo = rs.getString("tipo");
				int precio = rs.getInt("precio");
				String descripcion = "Hab " + id + " - " + tipo + " ($" + precio + ")";

				combo.addItem(descripcion);
				ids.add(id);
				descs.add(descripcion);
			}

			cn.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error cargando habitaciones: " + e.getMessage());
		}
	}

	/**
	 * confirmarYGuardar: inserta huesped + reservas + marca habitaciones como
	 * ocupadas en transacción
	 */
	public boolean confirmarYGuardar(ArrayList<Integer> habIdsSeleccionadas, ArrayList<String> habDescSeleccionadas,
			LocalDate fechaInicio, LocalDate fechaFin, int diasEstadia) {
		Connection cn = null;
		try {
			cn = ConexionBD.conectar();
			cn.setAutoCommit(false);

			PreparedStatement psh = cn.prepareStatement(
					"INSERT INTO huesped (nombre, tipo_identificacion, num_identificacion) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			psh.setString(1, this.titularStr);
			psh.setString(2, this.tipoIdStr);
			psh.setString(3, this.numIdStr);
			psh.executeUpdate();

			ResultSet rsKeys = psh.getGeneratedKeys();
			int idHuesped;
			if (rsKeys.next()) {
				idHuesped = rsKeys.getInt(1);
			} else {
				Statement st = cn.createStatement();
				ResultSet r2 = st.executeQuery("SELECT LAST_INSERT_ID()");
				if (r2.next())
					idHuesped = r2.getInt(1);
				else
					throw new SQLException("No se pudo obtener idHuesped.");
			}

			PreparedStatement psr = cn.prepareStatement(
					"INSERT INTO reserva (fecha_ini_res, fecha_fin_res, tipo, fk_idHuesped, fk_idHabitacion) VALUES (?, ?, ?, ?, ?)");
			PreparedStatement pshu = cn
					.prepareStatement("UPDATE habitacion SET estado = 'ocupada' WHERE idHabitacion = ?");

			for (Integer idHab : habIdsSeleccionadas) {
				psr.setDate(1, Date.valueOf(fechaInicio));
				psr.setDate(2, Date.valueOf(fechaFin));
				psr.setString(3, this.tipoReservaStr);
				psr.setInt(4, idHuesped);
				psr.setInt(5, idHab);
				psr.executeUpdate();

				pshu.setInt(1, idHab);
				pshu.executeUpdate();
			}

			cn.commit();
			cn.setAutoCommit(true);
			cn.close();
			return true;

		} catch (Exception ex) {
			try {
				if (cn != null)
					cn.rollback();
			} catch (Exception e2) {
			}
			JOptionPane.showMessageDialog(this, "Error guardando reserva: " + ex.getMessage());
			try {
				if (cn != null)
					cn.setAutoCommit(true);
				if (cn != null)
					cn.close();
			} catch (Exception e3) {
			}
			return false;
		}
	}
}
