package utils;

import dao.AgendaDAO;
import dao.GestorPacientesDAO;
import model.Agenda;
import model.GestorPacientes;
import java.util.Scanner;


public class SubMenus {

    // Submenú de Pacientes

    public static void ejecutarMenuPaciente(Scanner sc, GestorPacientes gestorPacientes, GestorPacientesDAO gestorPacientesDAO) { // ejecuta menu paciente recibe
                                                          // parametr para no crear scanner
        int opcion;
        boolean datosModificados = false;

        do {
            mostrarMenuPaciente();

            while (!sc.hasNextInt()) { // valida si no es un numero entero lo que se ingreso
                System.out.println("Error: Ingresa solo números."); // imprime el error
                sc.next(); // ignora lo que no sea numeros para evitar bucle inf
            }
            opcion = sc.nextInt(); // Después de la validación asigna el entero a opcion
            sc.nextLine(); // Ignora el espacio vacio de enter

            switch (opcion) {
                case 1:
                    System.out.println("--- Dar de alta paciente ---");
                    gestorPacientes.darDeAltaPaciente(sc);
                    datosModificados = true;
                    break;
                case 2:
                    System.out.println("--- Modificar paciente ---");
                    gestorPacientes.modificarPaciente(sc);
                    datosModificados = true;
                    break;
                case 3:
                    System.out.println("--- Eliminar paciente ---");
                    gestorPacientes.eliminarPaciente(sc);
                    datosModificados = true;
                    break;
                case 4:
                    System.out.println("--- Mostrar lista de pacientes ---");
                    gestorPacientes.mostrarListaPacientes();
                    break;
                case 5:
                    if (datosModificados) {
                        System.out.println("💾 Guardando datos...");
                        gestorPacientesDAO.guardarPaciente(gestorPacientes.getListaPacientes());
                    }
                    System.out.println("Regresando al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
            System.out.println();

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

    public static void ejecutarMenuAgenda(Scanner sc, Agenda agenda, AgendaDAO agendaDAO, GestorPacientes gestorPacientes) {
        int opcion;
        boolean datosModificados = false;

        if (gestorPacientes.getListaPacientes().isEmpty()) {
            Menu.mostrarMensajeError("⚠️ Favor de registrar primero a los pacientes.");
            return;
        }

        do {
            mostrarMenuAgenda();
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar el buffer

            switch (opcion) {
                case 1:
                    // todo: hacer que retorne booleanos los cases
                    if (ControladorCitas.manejarAgregarCita(sc, agenda, gestorPacientes)) {
                        datosModificados = true;
                    }
                    break;

                case 2:
                    if (ControladorCitas.manejarModicarCita(sc, agenda)) {
                        datosModificados = true;
                    }
                    break;

                case 3:
                    if (ControladorCitas.manejarCancelacionCita(sc, agenda)) {
                        datosModificados = true;
                    }
                    break;

                case 4:
                    ControladorCitas.manejarBusquedaCitas(sc, agenda);
                    break;

                case 5:
                    ControladorCitas.manejarMostrarCitas(agenda);
                    break;

                case 6:
                    if (datosModificados) {
                        System.out.println("💾 Guardando datos...");
                        agendaDAO.guardarCitas(agenda.getCitas());
                    }
                    System.out.println("Regresando al menú principal...");
                    break;

                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }

        } while (opcion != 6);
    }

}
