package utils;

import model.Agenda;
import model.cita.Cita;

import java.util.Scanner;

public class SubMenus {

    // Submenú de Pacientes
    public static void ejecutarMenuPaciente(Scanner sc) {
        int opcion;
        do {
            mostrarMenuPaciente();
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Dar de alta paciente");
                    break;
                case 2:
                    System.out.println("Modificar paciente");
                    break;
                case 3:
                    System.out.println("Eliminar paciente");
                    break;
                case 4:
                    System.out.println("Mostrar lista de pacientes");
                    break;
                case 5:
                    System.out.println("Regresando al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }

        } while (opcion != 5);
    }

    public static void mostrarMenuPaciente() {
        System.out.println("\n--- Menú Pacientes ---");
        System.out.println("1. Dar de alta paciente");
        System.out.println("2. Modificar paciente");
        System.out.println("3. Eliminar paciente");
        System.out.println("4. Mostrar lista de pacientes");
        System.out.println("5. Regresar al menú anterior");
        System.out.print("\n\tOpcion: ");
    }

    // Submenú de Citas
    public static void mostrarMenuAgenda() {
        System.out.println("\n--- Menú Citas ---");
        System.out.println("1. Crear una cita");
        System.out.println("2. Modificar una cita");
        System.out.println("3. Cancelar una cita");
        System.out.println("4. Mostrar citas por paciente");
        System.out.println("5. Mostrar todas las citas");
        System.out.println("6. Regresar al menú anterior");
        System.out.print("\n\tOpcion: ");
    }

    public static void ejecutarMenuAgenda(Scanner sc, Agenda agenda) {
        int opcion;
        do {
            mostrarMenuAgenda();
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar el buffer

            switch (opcion) {
                case 1:
                    // todo: crear metodo en la clase controlador
                    if (agenda.agendarCita()) {
                        System.out.println("\nCita agregada con exito ✅");
                    }
                    break;

                case 2:
                    // todo: implementar metodo
                    System.out.println("Modificar una cita");
                    break;

                case 3:
                    ControladorCitas.manejarCancelacionCita(sc, agenda);
                    break;

                case 4:
                    ControladorCitas.manejarBusquedaCitas(sc, agenda);
                    break;

                case 5:
                    ControladorCitas.manejarMostrarCitas(agenda);
                    break;

                case 6:
                    System.out.println("Regresando al menú principal...");
                    break;

                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }

        } while (opcion != 6);
    }

}
