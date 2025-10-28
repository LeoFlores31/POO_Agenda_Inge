package utils;

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
    public static void ejecutarMenuAgenda(Scanner sc) {
        int opcion;
        do {
            mostrarMenuAgenda();
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Crear una cita");
                    break;
                case 2:
                    System.out.println("Modificar una cita");
                    break;
                case 3:
                    System.out.println("Cancelar una cita");
                    break;
                case 4:
                    System.out.println("Mostrar citas por paciente");
                    break;
                case 5:
                    System.out.println("Mostrar lista de citas");
                    break;
                case 6:
                    System.out.println("Regresando al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }

        } while (opcion != 6);
    }

    public static void mostrarMenuAgenda() {
        System.out.println("\n--- Menú Citas ---");
        System.out.println("1. Crear una cita");
        System.out.println("2. Modificar una cita");
        System.out.println("3. Cancelar una cita");
        System.out.println("4. Mostrar citas por paciente");
        System.out.println("5. Mostrar lista de citas");
        System.out.println("6. Regresar al menú anterior");
        System.out.print("\n\tOpcion: ");
    }
}
