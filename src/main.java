import javax.swing.JOptionPane;

public class main {
    public static void main(String[] args) {
        String[] opc1 = {"Quiero hacer una reserva", "Iniciar Sesión"};
        int res1 = JOptionPane.showOptionDialog(null, "¿Qué desea hacer?", "Hotelgama",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opc1, opc1[0]);

        if (res1 == 0) {
            // Lógica de reservas individuales
            Reserva.crearReservaIndividual(); // 👈 Ahora llamás al método de otra clase
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
}



