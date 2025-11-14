import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

import model.Agenda;
import model.GestorPacientes;
import model.Paciente;
import model.cita.*; // TODO: Eliminar, solo es para el ejemplo
import utils.Menu;
import utils.SubMenus;

public class Main {
    public static void main(String[] args) {
        Agenda agenda = new Agenda();

        Scanner sc = new Scanner(System.in);
        int opcion;

        //////////// Bloque de prueba ////////////
        Paciente p1 = new Paciente("Fer", "33 1212 5555", "fer@email.com");
        Paciente p2 = new Paciente("Leo", "33 1508 2345", "leo@email.com");

        GestorPacientes.agregarPaciente(p1);
        GestorPacientes.agregarPaciente(p2);

        LocalTime horaCambioTurno = LocalTime.of(12, 0);
        LocalDateTime fechaHora = LocalDateTime.now();
        boolean citaMatutina = false;
        boolean citaVespertina = false;

        if (fechaHora.toLocalTime().isBefore(horaCambioTurno)) {
            citaMatutina = true;
        } else {
            citaVespertina = true;
        }
        Cita c1 = null;
        if (citaMatutina) c1 = new CitaMatutina(p1, fechaHora);
        if (citaVespertina) c1 = new CitaVespertina(p1, fechaHora);

        Cita c2 = null;
        if (citaMatutina) c2 = new CitaMatutina(p2, fechaHora.plusHours(1));
        if (citaVespertina) c2 = new CitaVespertina(p2, fechaHora.plusHours(1));

        agenda.agendarCita(c1);
        agenda.agendarCita(c2);
        ///////////////////r/////////////////////

        Menu.mostrarMensaje("\tAgenda de Citas Medicas - El Inge 👨‍💻", 45);

        do {
            Menu.mostrarMenuPrincipal();
            opcion = sc.nextInt();
            // todo: validar entradad de datos
            switch (opcion) {
                case 1:
                    SubMenus.ejecutarMenuPaciente(sc);
                    break;
                case 2:
                    SubMenus.ejecutarMenuAgenda(sc, agenda);
                    break;
                case 3:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }

        } while (opcion != 3);

        sc.close();

    }
}
