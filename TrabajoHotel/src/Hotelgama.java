import javax.swing.JOptionPane;

public class Hotelgama {

    public static void mostrarMenuSegunRol(Usuario u) {
        String rol = u.getRol(); // obtiene el rol del usuario

        // Mostrar mensaje de bienvenida por rol antes de mostrar el menú
        if (rol.equalsIgnoreCase("Recepcion")) {
            JOptionPane.showMessageDialog(null, "¡Bienvenido/a al sistema de Recepción, " + u.getNombre() + "!");
            menuRecepcion();
        } else if (rol.equalsIgnoreCase("Limpieza")) {
            JOptionPane.showMessageDialog(null, "¡Bienvenido/a al sistema de Limpieza!");
            Limpieza.mostrarMenuLimpieza(); // Llamamos al menú de la clase Limpieza
        } else if (rol.equalsIgnoreCase("Administrador")) {
            JOptionPane.showMessageDialog(null, "¡Bienvenido/a al sistema de Administrador, " + u.getNombre() + "!");
            Administrador.mostrarMenuAdministrador();
        } else {
            JOptionPane.showMessageDialog(null, "Rol no reconocido.");
        }
    }

    // Menú Recepción
    private static void menuRecepcion() {
        String[] opcRec = { "Check-in", "Check-Out", "Administrar reservas", "Ver pedidos de Room Service", "Cerrar" };
        int resRec;
        do {
            resRec = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Recepción",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcRec, opcRec[0]);

            switch (resRec) {
                case 0 -> JOptionPane.showMessageDialog(null, "Función Check-in aún no implementada");
                case 1 -> JOptionPane.showMessageDialog(null, "Función Check-out aún no implementada");
                case 2 -> JOptionPane.showMessageDialog(null, "Administrar reservas aún no implementado");
                case 3 -> JOptionPane.showMessageDialog(null, "Ver pedidos de Room Service aún no implementado");
            }
        } while (resRec != 4); // Cierra cuando selecciona "Cerrar"
    }

    

    // Main para probar
    public static void main(String[] args) {
        Usuario usuario = Login.iniciarSesion();
        if (usuario != null) {
            mostrarMenuSegunRol(usuario);
        }
    }
}
