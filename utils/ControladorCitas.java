package utils;

import model.Agenda;
import model.Paciente;
import model.cita.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ControladorCitas {

    public static void manejarModicarCita(Scanner sc, Agenda agenda) {
        String inputUsuario;
        do {
            System.out.print("\nIngresa el ID de la cita a modificar. Presiona '0' para buscar la cita o '-1' para regresar: ");
            inputUsuario = sc.nextLine();
            if (inputUsuario.equals("-1")) {
                break;
            }
            if (inputUsuario.equals("0")) {
                manejarBusquedaCitas(sc, agenda);
                continue;
            }

            int idCita;
            try {
                idCita = Integer.parseInt(inputUsuario);
            } catch ( NumberFormatException e) {
                System.err.println("ID en formato invalido: " + e.getMessage());
                continue;
            }

            Cita citaAModificar = agenda.getCitaPorId(idCita);
            if (citaAModificar == null) {
                Menu.mostrarMensajeError("❌ No se encontro la cita. Intenta de nuevo.");
                continue;
            }

            int opcion = -1;
            do {
                System.out.println("\nℹ️ Cita seleccionada:");
                citaAModificar.mostrarCita();

                System.out.println("\nℹ️ Que quieres modificar?");
                System.out.println("1) Fecha");
                System.out.println("2) Hora");
                System.out.println("3) Nombre del paciente");
                System.out.println("4) Motivo");
                System.out.println("5) Regresar al menu anterior");
                System.out.print("Opcion: ");
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
                        System.out.println("\nFecha actual: " + citaAModificar.getFecha());
                        LocalDate nuevaFecha = preguntarFecha(sc);
                        citaAModificar.setFecha(nuevaFecha);
                        System.out.println("\n✅ Fecha modificada con exito!");
                        break;
                    case 2:
                        String tipoCita = citaAModificar.getTipoCita();
                        System.out.println("\nHora actual: " + citaAModificar.getHora());
                        LocalTime nuevaHora = preguntarHora(sc);
                        citaAModificar.setHora(nuevaHora);
                        System.out.println("\n✅ Hora modificada con exito!");
                        if (!tipoCita.equals(citaAModificar.getTipoCita())){
                            Cita nuevaCita = cambiarInstanciaCita(citaAModificar, agenda);
                            if (nuevaCita != null) {
                                citaAModificar = agenda.getCitaPorId(nuevaCita.getId());
                                System.out.println("\nℹ️ Tu tipo de cita cambio, favor de seleccionar un nuevo motivo.");
                                // todo: crear metodos especificos para cada modificacion
                                String nuevoMotivo = preguntarMotivo(sc, citaAModificar);
                                citaAModificar.setMotivo(nuevoMotivo);
                                System.out.println("\n✅ Motivo de cita modificado con exito!");
                            }
                        }
                        break;
                    case 3:
                        System.out.println("\nNombre actual: " + citaAModificar.getPaciente().getNombre());
                        String nuevoNombre = preguntarNombre(sc);
                        citaAModificar.getPaciente().setNombre(nuevoNombre);
                        System.out.println("\n✅ Nombre modificado con exito!");
                        break;
                    case 4:
                        System.out.println("\nMotivo actual: " + citaAModificar.getMotivo());
                        String nuevoMotivo = preguntarMotivo(sc, citaAModificar);
                        citaAModificar.setMotivo(nuevoMotivo);
                        System.out.println("\n✅ Motivo de cita modificado con exito!");
                        break;
                    case 5:
                        break;
                    default:
                        Menu.mostrarMensajeError("❌ Opcion Incorrecta. Intenta de nuevo.");
                }
            } while (opcion != 5);

        } while (true);
    }

    private static Cita cambiarInstanciaCita (Cita citaAModificar, Agenda agenda){
        Paciente paciente = citaAModificar.getPaciente();
        LocalDateTime nuevaFechaHora = citaAModificar.getFechaHora();
        String motivo = citaAModificar.getMotivo();
        int duracion = citaAModificar.getDuracionMinutos();

        Cita nuevaCita = null;

        if (citaAModificar.getTipoCita().equals("MATUTINA")) {
            nuevaCita = new CitaMatutina(paciente, nuevaFechaHora, motivo, duracion);
        } else if (citaAModificar.getTipoCita().equals("VESPERTINA")) {
            nuevaCita = new CitaVespertina(paciente, nuevaFechaHora, motivo, duracion);
        }

        if (nuevaCita != null) {
            if (!agenda.reemplazarCita(citaAModificar, nuevaCita)) {
                Menu.mostrarMensajeError("❌ Error al cambiar la instnacia de la cita.");
                return null;
            }
        }
        return nuevaCita;
    }

    private static String preguntarMotivo(Scanner sc, Cita cita) {
        System.out.println("\nLos motivos disponibles para tu horario son:");
        cita.mostrarMotivosDisponibles();
        int opcion = -1;
        String nuevoMotivo = "";
        do {
            System.out.print("\tOpcion: ");
            try {
                opcion = sc.nextInt();
                sc.nextLine(); // limpiar el buffer
            } catch (InputMismatchException e) {
                Menu.mostrarMensajeError("❌ Opcion Incorrecta. Intenta de nuevo.");
                sc.nextLine(); // limpiar el buffer
                continue;
            }
            if (opcion <= 0 || opcion > cita.getTotalMotivos()){
                Menu.mostrarMensajeError("❌ Opcion Incorrecta. Intenta de nuevo.");
                continue;
            }
            nuevoMotivo = cita.getMotivoPorIndice(opcion - 1);
            System.out.println("\nOpcion seleccionada: " + nuevoMotivo);
        } while (opcion <= 0 || opcion > cita.getTotalMotivos());
        return nuevoMotivo;
    }

    private static String preguntarNombre(Scanner sc) {
        String nuevoNombre;
        do{
            System.out.print("Nuevo nombre: ");
            nuevoNombre = sc.nextLine().trim();
            if (nuevoNombre.isEmpty()) {
                Menu.mostrarMensajeError("❌ El nombre no puede estar vacío.");
            } else if (nuevoNombre.length() == 1) {
                Menu.mostrarMensajeError("❌ El nombre debe contener al menos 2 caracteres.");
            }
        } while (nuevoNombre.isEmpty() || nuevoNombre.length() == 1);
        return nuevoNombre;
    }

    private static LocalDate preguntarFecha(Scanner sc) {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        do {
            try {
                System.out.print("Nueva fecha (dd/MM/yyyy): ");
                String fechaInput = sc.nextLine();
                return LocalDate.parse(fechaInput, formatoFecha);
            } catch (Exception e) {
                System.out.println("❌ Error: formato incorrecto. Ejemplo correcto -> Fecha: 05/10/2025");
            }
        } while (true);
    }

    private static LocalTime preguntarHora(Scanner sc) {
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
        do {
            try {
                System.out.print("Nueva hora (HH:mm): ");
                String horaInput = sc.nextLine();
                return LocalTime.parse(horaInput, formatoHora);
            } catch (Exception e) {
                System.out.println("❌ Error: formato incorrecto. Ejemplo correcto -> Hora: 14:30");
            }
        } while (true);
    }

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
