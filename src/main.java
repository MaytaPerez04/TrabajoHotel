import javax.swing.JOptionPane;
import java.time.LocalDate;

public class main {
    public static void main(String[] args) {
        String[] opc1 = {"Quiero hacer una reserva", "Iniciar Sesión"};
        int res1 = JOptionPane.showOptionDialog(null, "¿Qué desea hacer?", "Hotel la Perla",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opc1, opc1[0]);

        if (res1 == 0) {
            // Lógica de reservas individuales
            hacerReservaIndividual();
        } else if (res1 == 1) {
            // Lógica de login
            Usuario usuario = Login.iniciarSesion();
            if (usuario != null) {
                // Llama a los menús según rol
                Hotelgama.mostrarMenuSegunRol(usuario);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo iniciar sesión.");
            }
        }
    }

    // Método para la reserva individual (puedes mover tu código actual aquí)
    public static void hacerReservaIndividual() {
        LocalDate fechaIniRes = null;
        int ver = 0;

        while (ver == 0) {
            int diaRes = Integer.parseInt(JOptionPane.showInputDialog("Número del día de la reserva"));
            int mesRes = Integer.parseInt(JOptionPane.showInputDialog("Número del mes de la reserva"));
            int anoRes = Integer.parseInt(JOptionPane.showInputDialog("Número del año de la reserva"));

            fechaIniRes = LocalDate.of(anoRes, mesRes, diaRes);

            String[] opc2 = {"Rehacer", "Si"};
            ver = JOptionPane.showOptionDialog(null, "Anotó como fecha de reserva: " + fechaIniRes
                            + ". ¿Le parece bien?", "Hotel la Perla",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opc2, opc2[1]);
        }

        int cantDias = Integer.parseInt(JOptionPane.showInputDialog(null, "Cuántos días se quedarán?", "Hotel la perla",
                JOptionPane.PLAIN_MESSAGE));

        LocalDate fechaFinRes = fechaIniRes.plusDays(cantDias);

        String titRes = JOptionPane.showInputDialog(null, "Nombre del titular de la reserva:", "Hotel la perla",
                JOptionPane.PLAIN_MESSAGE);

        String[] opcTipoRes = {"Individuo", "Delegación"};
        int tipoRes = JOptionPane.showOptionDialog(null, "¿Qué desea hacer?", "Hotel la Perla",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcTipoRes, opcTipoRes[0]);

        if (tipoRes == 0) {
            int cantHab;
            do {
                cantHab = Integer.parseInt(JOptionPane.showInputDialog(null, "Cuántas habitaciones van a querer?", "Hotel la perla",
                        JOptionPane.PLAIN_MESSAGE));
                if (cantHab > 4) {
                    JOptionPane.showMessageDialog(null,
                            "No se pueden realizar reservas por más de 4 habitaciones, ingrese un número más pequeño.",
                            "Hotel la Perla",
                            JOptionPane.WARNING_MESSAGE);
                }
            } while (cantHab > 4);

            JOptionPane.showMessageDialog(null, "Resumen:\n"
                    + "Se quedan desde el " + fechaIniRes + " hasta el " + fechaFinRes
                    + "\nRequerirán de " + cantHab + " habitaciones"
                    + "\nTitular de la reserva: " + titRes,
                    "Hotel la Perla", JOptionPane.PLAIN_MESSAGE);
        }
    }
}



