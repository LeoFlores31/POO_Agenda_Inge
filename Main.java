import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import model.Agenda;
import model.GestorPacientes;
import model.Paciente;
import model.cita.*; // TODO: Eliminar, solo es para el ejemplo
import utils.Menu;
import utils.SubMenus;

public class Main {
    public static void main(String[] args) {
        GestorPacientes gestorPacientes = new GestorPacientes();
        Agenda agenda = new Agenda(gestorPacientes);

        Scanner sc = new Scanner(System.in);
        int opcion;

        //////////// Bloque de prueba ////////////
        Paciente p1 = new Paciente("Fer", "33 1212 5555", "fer@email.com");
        Paciente p2 = new Paciente("Leo", "33 1508 2345", "leo@email.com");

        gestorPacientes.agregarPaciente(p1);
        gestorPacientes.agregarPaciente(p2);

        Cita c1 = new CitaMatutina(p1, LocalDateTime.now(), "Consulta nutricional",60);
        Cita c2 = new CitaMatutina(p2, LocalDateTime.now().plusHours(1), "Consulta general de psicologia",20);

        agenda.agendarCita(c1);
        agenda.agendarCita(c2);
        ////////////////////////////////////////

        Menu.mostrarMensaje("\tAgenda de Citas Medicas - El Inge 👨‍💻", 45);

        do {
            Menu.mostrarMenuPrincipal();
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    SubMenus.ejecutarMenuPaciente(sc); // TODO: Crear clase [GestorPacientes] para la logica de negocio
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
