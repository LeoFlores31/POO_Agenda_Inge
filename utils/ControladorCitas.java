package utils;

import model.Agenda;
import model.cita.Cita;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ControladorCitas {

    public static void manejarMostrarCitas(Agenda agenda) {
        ArrayList<Cita> resultadoCitas = agenda.getCitas();
        if (resultadoCitas.isEmpty()) {
            Menu.mostrarMensajeError("⚠️ Aun no hay citas registradas.");
        } else {
            Menu.mostrarMensaje("\tCITAS MEDICAS 🩺👨🏻‍", 25);
            for (Cita c : resultadoCitas) {
                c.mostrarCita();
            }
        }
    }

    public static void manejarCancelacionCita(Scanner sc, Agenda agenda) {
        String inputUsuario;
        do {
            System.out.print("\nIngresa el ID de la cita a cancelar. Presiona '0' para buscar la cita o '-1' para regresar: ");
            inputUsuario = sc.nextLine();
            if (inputUsuario.equals("-1")) {
                break;
            }
            if (inputUsuario.equals("0")) {
                manejarBusquedaCitas(sc, agenda);
                continue;
            }
            try {
                int id = Integer.parseInt(inputUsuario);
                Cita c = agenda.getCitaPorId(id);
                if (c == null) {
                    Menu.mostrarMensajeError("❌ No se encontro la cita. Intenta de nuevo.");
                    continue;
                }
                System.out.println("\nℹ️ La cita a cancelar es:");
                c.mostrarCita();
                System.out.print("\n⚠️ Estas seguro de cancelarla? (y/n): ");
                inputUsuario = sc.nextLine();
                if (inputUsuario.equalsIgnoreCase("y")) {
                    if (agenda.cancelarCita(id)) {
                        System.out.println("\n✅ Cita cancelada con exito!");
                        break;
                    } else {
                        Menu.mostrarMensajeError("❌ Ocurrio un error. Intenta de nuevo.");
                    }
                }
            } catch ( NumberFormatException e) {
                System.err.println("ID en formato invalido: " + e.getMessage());
            }
        } while (true);
    }

    public static void manejarBusquedaCitas(Scanner sc, Agenda agenda) {

        Menu.mostrarMensaje("\tBUSCAR CITA 👨🏽‍💻", 25);

        int opcion;
        String inputUsuario;
        boolean ejecutarMenu;
        ArrayList<Cita> resultadoCitas;

        do {
            ejecutarMenu = true;
            System.out.println("\n\tBuscar cita por:");
            System.out.println("1) Nombre");
            System.out.println("2) Telefono");
            System.out.println("3) Email");
            System.out.println("4) Regresar");
            System.out.print("\tOpcion: ");
            try {
                opcion = sc.nextInt();
                sc.nextLine(); // limpiar el buffer
            } catch (InputMismatchException e) {
                Menu.mostrarMensajeError("❌ Opcion Incorrecta. Intenta de nuevo.");
                sc.nextLine(); // limpiar el buffer
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.print("\nNombre: ");
                    inputUsuario = sc.nextLine();

                    resultadoCitas = agenda.buscarCitaPorNombre(inputUsuario);

                    if (resultadoCitas.isEmpty()) {
                        Menu.mostrarMensajeError("⚠️ No se encontraron citas para '" + inputUsuario + "'. Intenta de nuevo.");
                    } else {
                        ejecutarMenu = false;
                        for (Cita c : resultadoCitas) {
                            c.mostrarCita();
                        }
                    }
                    break;
                case 2:
                    System.out.print("\nTelefono: ");
                    inputUsuario = sc.nextLine();

                    resultadoCitas = agenda.buscarCitaPorTelefono(inputUsuario);

                    if (resultadoCitas.isEmpty()) {
                        Menu.mostrarMensajeError("⚠️ No se encontraron citas con el telefono '" + inputUsuario + "'. Intenta de nuevo.");
                    } else {
                        ejecutarMenu = false;
                        for (Cita c : resultadoCitas) {
                            c.mostrarCita();
                        }
                    }
                    break;
                case 3:
                    System.out.print("\nEmail: ");
                    inputUsuario = sc.nextLine();

                    resultadoCitas = agenda.buscarCitaPorEmail(inputUsuario);

                    if (resultadoCitas.isEmpty()) {
                        Menu.mostrarMensajeError("⚠️ No se encontraron citas para '" + inputUsuario + "'. Intenta de nuevo.");
                    } else {
                        ejecutarMenu = false;
                        for (Cita c : resultadoCitas) {
                            c.mostrarCita();
                        }
                    }
                    break;
                case 4:
                    ejecutarMenu = false; // salir del menu
                    break;
                default:
                    Menu.mostrarMensajeError("❌ Opcion Incorrecta. Intenta de nuevo.");
            }
        } while (ejecutarMenu);
    }


}
