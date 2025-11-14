import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Reserva extends JFrame {

    public Reserva() {

        setTitle("HotelGama - Nueva Reserva");
        setSize(600, 480);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ================= HEADER =================
        JPanel header = new JPanel();
        header.setBackground(new Color(33, 47, 60));
        header.setPreferredSize(new Dimension(600, 70));

        JLabel titulo = new JLabel("Crear Reserva");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.add(titulo);

        add(header, BorderLayout.NORTH);

        // ================= FORMULARIO =================
        JPanel form = new JPanel();
        form.setBackground(new Color(236, 240, 241));
        form.setLayout(new GridLayout(8, 2, 12, 12));
        form.setBorder(BorderFactory.createEmptyBorder(20, 35, 20, 35));

        JTextField dia = new JTextField();
        JTextField mes = new JTextField();
        JTextField ano = new JTextField();

        JTextField diasEstadia = new JTextField();
        JTextField titular = new JTextField();

        String[] tipos = { "Individuo", "Delegación" };
        JComboBox<String> tipoReserva = new JComboBox<>(tipos);

        // HABITACIONES
        ArrayList<Integer> idsHabitaciones = new ArrayList<>();
        JComboBox<String> comboHabitaciones = new JComboBox<>();

        cargarHabitacionesLibres(idsHabitaciones, comboHabitaciones);

        JTextField cantHabitacionesDelegacion = new JTextField();
        cantHabitacionesDelegacion.setEnabled(false);

        // Activar o desactivar campos según tipo
        tipoReserva.addActionListener(e -> {
            boolean esDelegacion = tipoReserva.getSelectedItem().equals("Delegación");
            cantHabitacionesDelegacion.setEnabled(esDelegacion);
            comboHabitaciones.setEnabled(!esDelegacion);
        });

        // --------- AÑADIR CAMPOS ----------
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

        form.add(new JLabel("Tipo de reserva:"));
        form.add(tipoReserva);

        form.add(new JLabel("Habitación (Individuo):"));
        form.add(comboHabitaciones);

        form.add(new JLabel("Cantidad de habitaciones (Delegación):"));
        form.add(cantHabitacionesDelegacion);

        add(form, BorderLayout.CENTER);

        // ================= BOTONES =================
        JPanel botones = new JPanel();
        botones.setBackground(new Color(236, 240, 241));
        botones.setLayout(new FlowLayout());

        JButton aceptar = new JButton("Confirmar");
        JButton cancelar = new JButton("Cancelar");

        estiloBoton(aceptar, new Color(39, 174, 96));
        estiloBoton(cancelar, new Color(192, 57, 43));

        cancelar.addActionListener(e -> dispose());

        aceptar.addActionListener(e -> {

            try {
                // -------- LEER FECHAS --------
                int d = Integer.parseInt(dia.getText());
                int m = Integer.parseInt(mes.getText());
                int a = Integer.parseInt(ano.getText());

                LocalDate fechaInicio = LocalDate.of(a, m, d);
                int dias = Integer.parseInt(diasEstadia.getText());
                LocalDate fechaFin = fechaInicio.plusDays(dias);

                String nombreTitular = titular.getText().trim();
                if (nombreTitular.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ingrese nombre del titular.");
                    return;
                }

                // ========== INSERTAR HUESPED ==========
                int idHuesped = insertarHuesped(nombreTitular);

                // ========== INSERTAR RESERVA(S) ==========
                if (tipoReserva.getSelectedItem().equals("Individuo")) {

                    int index = comboHabitaciones.getSelectedIndex();
                    if (index < 0) {
                        JOptionPane.showMessageDialog(this, "Seleccione una habitación.");
                        return;
                    }

                    int idHab = idsHabitaciones.get(index);
                    insertarReserva(idHuesped, fechaInicio, fechaFin, "Individuo", idHab);

                    // marcar ocupada
                    ocuparHabitacion(idHab);

                    JOptionPane.showMessageDialog(this,
                            "Reserva creada correctamente.\nHabitación: " + idHab);

                } else { // Delegación

                    int cantidad = Integer.parseInt(cantHabitacionesDelegacion.getText());

                    if (cantidad <= 0) {
                        JOptionPane.showMessageDialog(this, "Cantidad inválida.");
                        return;
                    }

                    if (cantidad > idsHabitaciones.size()) {
                        JOptionPane.showMessageDialog(this, "No hay tantas habitaciones libres.");
                        return;
                    }

                    for (int i = 0; i < cantidad; i++) {
                        int idHab = idsHabitaciones.get(i);
                        insertarReserva(idHuesped, fechaInicio, fechaFin, "Delegación", idHab);
                        ocuparHabitacion(idHab);
                    }

                    JOptionPane.showMessageDialog(this,
                            "Reserva de delegación creada correctamente.\nHabitaciones asignadas: " + cantidad);
                }

                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos: " + ex.getMessage());
            }
        });

        botones.add(aceptar);
        botones.add(cancelar);

        add(botones, BorderLayout.SOUTH);

        setVisible(true);
    }

    // ================== ESTILOS ==================
    private void estiloBoton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    // ================== CARGAR HABITACIONES ==================
    private void cargarHabitacionesLibres(ArrayList<Integer> ids, JComboBox<String> combo) {
        try {
            Connection cn = conexionBD.conectar();
            PreparedStatement ps = cn.prepareStatement(
                    "SELECT idHabitacion, tipo, precio FROM habitacion WHERE estado = 'libre'"
            );
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idHabitacion");
                String tipo = rs.getString("tipo");
                int precio = rs.getInt("precio");

                combo.addItem("Hab " + id + " - " + tipo + " ($" + precio + ")");
                ids.add(id);
            }

            cn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando habitaciones: " + e.getMessage());
        }
    }

    // ================== INSERTAR HUESPED ==================
    private int insertarHuesped(String nombre) throws Exception {
        Connection cn = conexionBD.conectar();
        PreparedStatement ps = cn.prepareStatement(
                "INSERT INTO huesped (nombre) VALUES (?)",
                Statement.RETURN_GENERATED_KEYS
        );
        ps.setString(1, nombre);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        int id = rs.getInt(1);

        cn.close();
        return id;
    }

    // ================== INSERTAR RESERVA ==================
    private void insertarReserva(int idHuesped, LocalDate ini, LocalDate fin, String tipo, int idHab) throws Exception {

        Connection cn = conexionBD.conectar();
        PreparedStatement ps = cn.prepareStatement(
                "INSERT INTO reserva (fecha_ini_res, fecha_fin_res, tipo, fk_idHuesped, fk_idHabitacion) " +
                        "VALUES (?, ?, ?, ?, ?)"
        );
        ps.setDate(1, Date.valueOf(ini));
        ps.setDate(2, Date.valueOf(fin));
        ps.setString(3, tipo);
        ps.setInt(4, idHuesped);
        ps.setInt(5, idHab);

        ps.executeUpdate();
        cn.close();
    }

    // ================== CAMBIAR ESTADO HABITACIÓN ==================
    private void ocuparHabitacion(int idHabitacion) throws Exception {
        Connection cn = conexionBD.conectar();
        PreparedStatement ps = cn.prepareStatement(
                "UPDATE habitacion SET estado = 'ocupada' WHERE idHabitacion = ?"
        );
        ps.setInt(1, idHabitacion);
        ps.executeUpdate();
        cn.close();
    }
}
