import javax.swing.JOptionPane;

public class Hotelgama {

    public static void mostrarMenuSegunRol(Usuario u) {
        String rol = u.getRol(); // obtiene el rol del usuario

        if (rol.equalsIgnoreCase("Recepcion")) {
            menuRecepcion();
        } else if (rol.equalsIgnoreCase("Limpieza")) {
            menuLimpieza();
        } else if (rol.equalsIgnoreCase("Administrador")) {
            menuAdministrador();
        } else {
            JOptionPane.showMessageDialog(null, "Rol no reconocido.");
        }
    }

    // Menú Recepción
    private static void menuRecepcion() {
        String[] opcRec = {"Check-in", "Check-Out", "Administrar reservas",
                "Ver pedidos de Room Service", "Cerrar"};
        int resRec;
        do {
            resRec = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Recepción",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcRec, opcRec[0]);

            switch (resRec) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Función Check-in aún no implementada");
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "Función Check-out aún no implementada");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Administrar reservas aún no implementado");
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Ver pedidos de Room Service aún no implementado");
                    break;
            }
        } while (resRec != 4); // Cierra cuando selecciona "Cerrar"
    }

    // Menú Limpieza
    private static void menuLimpieza() {
        String[] opcLim = {"Pedidos de limpieza", "Habitaciones por limpiar",
                "Marcar habitación como limpia", "Cerrar"};
        int resLim;
        do {
            resLim = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Limpieza",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcLim, opcLim[0]);

            switch (resLim) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Pedidos de limpieza aún no implementados");
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "Habitaciones por limpiar aún no implementadas");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Marcar habitación como limpia aún no implementado");
                    break;
            }
        } while (resLim != 3); // Cierra cuando selecciona "Cerrar"
    }

    // Menú Administrador
    private static void menuAdministrador() {
        String[] opcAdmin = {"Gestionar usuarios", "Administrar futuras reservas",
                "Ver reservas previas", "Cerrar"};
        int resAdmin;
        do {
            resAdmin = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Administrador",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcAdmin, opcAdmin[0]);

            switch (resAdmin) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Gestionar usuarios aún no implementado");
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "Administrar futuras reservas aún no implementado");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Ver reservas previas aún no implementado");
                    break;
            }
        } while (resAdmin != 3); // Cierra cuando selecciona "Cerrar"
    }
}

