import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;


public class Main {
	public static void main(String[] args) {
		LocalDate fechaIniRes = null;
		
		String[] opc1= { "Quiero hacer una reserva", "Iniciar Sesión"};
		int res1 = JOptionPane.showOptionDialog(null, "¿Que desea hacer?", "Hotel la Perla", JOptionPane.DEFAULT_OPTION, 
		JOptionPane.INFORMATION_MESSAGE, null, opc1, opc1[0]);
		
		if (res1 == 0) {
			int ver = 0;
			while (ver == 0) {
				//hacer verificaci´øn de las fechas
				int diaRes = Integer.parseInt(JOptionPane.showInputDialog("Número del día de la reserva"));
				int mesRes = Integer.parseInt(JOptionPane.showInputDialog("Número del mes de la reserva"));
				int anoRes = Integer.parseInt(JOptionPane.showInputDialog("Número del año de la reserva"));
				
				fechaIniRes = LocalDate.of(anoRes, mesRes, diaRes);
				
				String[] opc2 = {"Rehacer", "Si"};
				ver = JOptionPane.showOptionDialog(null, "Anotó como fecha de reserva"
						+ ": " + fechaIniRes + ". ¿Le parece bien?", "Hotel la Perla", JOptionPane.DEFAULT_OPTION, 
						JOptionPane.INFORMATION_MESSAGE, null, opc2, opc2[1]);
				
			}
			// Hacer varificación del nombre
			int cantDias = Integer.parseInt(JOptionPane.showInputDialog(null, 
					"Cuántos días se quedarán?", 
					"Hotel la perla",
					JOptionPane.PLAIN_MESSAGE));
			
			LocalDate fechaFinRes = fechaIniRes.plusDays(cantDias);
			
			String titRes = JOptionPane.showInputDialog(null, 
					"Nombre del titular de la reserva:", 
					"Hotel la perla",
					JOptionPane.PLAIN_MESSAGE);
			
			//Hacer verificación de la cantidad
			String[] opcTipoRes = {"Individuo", "Delegación"};
			int tipoRes = JOptionPane.showOptionDialog(null, "¿Que desea hacer?", "Hotel la Perla", JOptionPane.DEFAULT_OPTION, 
					JOptionPane.INFORMATION_MESSAGE, null, opcTipoRes, opcTipoRes[0]);
			if (tipoRes==0) {
				int cantHab = 10;
				do {
					cantHab = Integer.parseInt(JOptionPane.showInputDialog(null, 
							"Cuantas habitaciones van a querer?", 
							"Hotel la perla",
							JOptionPane.PLAIN_MESSAGE));
					if (cantHab > 4) {
						JOptionPane.showMessageDialog(
							    null,
							    "No se pueden realizar reservas por más de 4 habitaciones, por favor ingresa un número mas pequeño.",
							    "Hotel la Perla",
							    JOptionPane.WARNING_MESSAGE);
					}
				
				} while(cantHab > 4);
				
				//agregar selección del tamaño de los cuartos
				
				JOptionPane.showMessageDialog(
					    null,
					    "Resumen:\n"
					    + "Se quedan desde el " + fechaIniRes + " hasta el " + fechaFinRes +
					    "\nRequerirán de " + cantHab + " habitaciones"
					    + "\nTitular de la reserva: " + titRes,
					    "Hotel la Perla",
					    JOptionPane.PLAIN_MESSAGE);
				
				//agregar botón de confirmación o cancelación.
				
				
			}
			
		} else if (res1==1) {
			String[] opc3= { "Recepcionista", "Personal de Limpieza", "Administrador", "Cancelar"};
			int res3 = JOptionPane.showOptionDialog(null, "¿Cómo desea ingresar?", "Hotel la Perla", JOptionPane.DEFAULT_OPTION, 
			JOptionPane.INFORMATION_MESSAGE, null, opc3, opc3[3]);
			
			if (res3 == 0) {
				//Agregar Login
				String[] opcRec= { "Check-in", "Check-Out", "Administrar reservas",
						"Ver pedidos de Room Service", "Cerrar"};
				int resRec = JOptionPane.showOptionDialog(null, "¿Que desea hacer?", "Hotel la Perla", JOptionPane.DEFAULT_OPTION, 
						JOptionPane.INFORMATION_MESSAGE, null, opcRec, opcRec[4]);
				//Agregar todas las opciones de arriba
			} else if (res3 == 1) {
				String[] opcLim= { "Pedidos de limpieza", "Habitaciones por limpiar", 
						"Marcar habitación como limpia", "Cerrar"};
				int resRec = JOptionPane.showOptionDialog(null, "¿Que desea hacer?", "Hotel la Perla", JOptionPane.DEFAULT_OPTION, 
						JOptionPane.INFORMATION_MESSAGE, null, opcLim, opcLim[3]);
				//Agregar todas las opciones de arriba

			}  else if (res3 == 1) {
				String[] opcAdmin= { "Gestionar usuarios", "Administrar futuras reservas", 
						"Ver reservas previas", "Cerrar"};
				int resRec = JOptionPane.showOptionDialog(null, "¿Que desea hacer?", "Hotel la Perla", JOptionPane.DEFAULT_OPTION, 
						JOptionPane.INFORMATION_MESSAGE, null, opcAdmin, opcAdmin[3]);
				//Agregar todas las opciones de arriba
			}
		}
		
		
	}
}
