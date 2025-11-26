package Limpieza;

import javax.swing.JOptionPane;

public class LimpiezaController {

    public static void mostrarMenuLimpieza() {

        String[] opciones = {
                "Pedidos de limpieza",
                "Marcar como en limpieza",
                "Marcar habitación como limpia",
                "Ver habitaciones por estado",
                "Cerrar"
        };

        int opcion;

        do {
            opcion = JOptionPane.showOptionDialog(
                    null,
                    "Seleccione una opción",
                    "Limpieza",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            switch (opcion) {

                case 0 -> new VerHabitacionesFrame();  // ← ahora abre el JFrame

                case 1 -> new MarcarLimpiezaFrame();           // ← JFrame para pasar de limpiar → limpiando

                case 2 -> new MarcarLimpiezaRealizadaFrame();   // ← JFrame para pasar de limpiando → limpia

                case 3 -> new VerHabitacionesFrame();           // ← muestra todas las habitaciones
            }

        } while (opcion != 4);
    }
}

