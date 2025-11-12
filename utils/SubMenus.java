package utils;

import model.Agenda;
import model.Paciente;
import model.cita.Cita;
import model.GestorPacientes;

import java.util.Scanner;

import javax.print.attribute.Size2DSyntax;

public class SubMenus {

    // Submenú de Pacientes

    public static void ejecutarMenuPaciente(Scanner sc) { // ejecuta menu paciente recibe
                                                          // parametr para no crear scanner
        int opcion;
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
                    GestorPacientes.darDeAltaPaciente(sc);
                    break;
                case 2:
                    System.out.println("--- Modificar paciente ---");
                    GestorPacientes.modificarPaciente(sc);
                    break;
                case 3:
                    System.out.println("--- Eliminar paciente ---");
                    GestorPacientes.eliminarPaciente(sc);
                    break;
                case 4:
                    System.out.println("--- Mostrar lista de pacientes ---");
                    GestorPacientes.mostrarListaPacientes();
                    break;
                case 5:
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
        System.out.println("5. Mostrar lista de citas");
        System.out.println("6. Regresar al menú anterior");
        System.out.print("\n\tOpcion: ");
    }

    public static void ejecutarMenuAgenda(Scanner sc, Agenda agenda) {
        int opcion;
        String inputUsuario;
        do {
            mostrarMenuAgenda();
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar el buffer

            switch (opcion) {
                // TODO: Implementar los metodos en la clase agenda y llamarlos aqui
                case 1:
                    if (agenda.agendarCita()) {
                        System.out.println("\nCita agregada con exito");
                    }
                    break;

                case 2:
                    System.out.println("Modificar una cita");
                    break;

                case 3:
                    boolean citaCancelada = false;
                    do {
                        System.out.print("Ingresa el ID de la cita a cancelar o presiona '0' para buscar la cita: ");
                        inputUsuario = sc.nextLine();
                        if (inputUsuario.equals("0")) {
                            agenda.buscarCita();
                            continue;
                        }
                        try {
                            int id = Integer.parseInt(inputUsuario);
                            citaCancelada = agenda.cancelarCita(id);
                        } catch (NumberFormatException e) {
                            System.err.println("ID en formato invalido: " + e.getMessage());
                        }

                        if (citaCancelada) {
                            System.out.println("\n✅ Cita cancelada con exito!");
                            break;
                        } else {
                            Menu.mostrarMensajeError("\n❌ No se encontro la cita. Intenta de nuevo.");
                        }
                    } while (true);

                    break;

                case 4:
                    agenda.mostrarCitasPorPaciente();
                    break;

                case 5:
                    agenda.mostrarCitas();
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
