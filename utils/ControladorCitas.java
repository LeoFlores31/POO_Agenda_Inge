package utils;

import model.Agenda;
import model.Paciente;
import model.cita.*;
import model.GestorPacientes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ControladorCitas {

    public static void manejarAgregarCita(Scanner sc, Agenda agenda) {
        boolean citaMatutina = false;
        boolean citaVespertina = false;

        Menu.mostrarMensaje("\tNUEVA CITA 🗒️", 22);

        // pedir paciente
        Paciente paciente = preguntarPaciente(sc, agenda);
        if (paciente == null) return;

        // pedir fecha y hora
        LocalDateTime fechaHora = preguntarFechaYHora(sc);

        LocalTime horaCambioTurno = LocalTime.of(12, 0);
        if (fechaHora.toLocalTime().isBefore(horaCambioTurno)) {
            citaMatutina = true;
        } else {
            citaVespertina = true;
        }
        Cita nuevaCita = null;
        if (citaMatutina) nuevaCita = new CitaMatutina(paciente, fechaHora);
        if (citaVespertina) nuevaCita = new CitaVespertina(paciente, fechaHora);

        // pedir motivo
        nuevaCita.setMotivo(preguntarMotivo(sc, nuevaCita));

        // pedir duracion - todo: no pedir duracion, crear HashMap para obtener la duracion dinamicamente.
        int duracion;
        do {
            System.out.print("\nDuracion (min): ");
            try {
                duracion = sc.nextInt();
                sc.nextLine(); // limpiar el buffer
                break;
            } catch (InputMismatchException e) {
                Menu.mostrarMensajeError("❌ Error. Ingresa un numero entero valido.");
                sc.nextLine(); // limpiar el buffer
            }
        } while (true);

        nuevaCita.setDuracionMinutos(duracion);

        String mensajeCitaCreada = "\tCita creada para " + nuevaCita.getPaciente().getNombre() +
                " el dia " + nuevaCita.getFecha() + " a las " + nuevaCita.getHora() + " hrs.";

        do {
            if (agenda.agendarCita(nuevaCita)) {
                System.out.println("\n✅ Cita agregada con exito");
                Menu.mostrarMensaje(mensajeCitaCreada, 65);
                break;
            } else {
                Menu.mostrarMensajeError("⚠️ No hay disponibilidad. Intenta en otro horario.");
                nuevaCita = modificarHora(sc, nuevaCita, agenda);
            }
        } while (true);
    }

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
                        modificarFecha(sc, citaAModificar);
                        break;
                    case 2:
                        citaAModificar = modificarHora(sc, citaAModificar, agenda);
                        break;
                    case 3:
                        modificarNombre(sc, citaAModificar);
                        break;
                    case 4:
                        modificarMotivo(sc, citaAModificar);
                        break;
                    case 5:
                        break;
                    default:
                        Menu.mostrarMensajeError("❌ Opcion Incorrecta. Intenta de nuevo.");
                }
            } while (opcion != 5);

        } while (true);
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

    private static void modificarFecha(Scanner sc, Cita citaAModificar) {
        System.out.println("\nFecha actual: " + citaAModificar.getFecha());
        LocalDate nuevaFecha = preguntarFecha(sc, true);
        citaAModificar.setFecha(nuevaFecha);
        System.out.println("\n✅ Fecha modificada con exito!");
    }

    private static Cita modificarHora(Scanner sc, Cita citaAModificar, Agenda agenda) {
        String tipoCita = citaAModificar.getTipoCita();
        System.out.println("\nHora actual: " + citaAModificar.getHora());
        do {
            LocalTime nuevaHora = preguntarHora(sc, true);
            citaAModificar.setHora(nuevaHora);
            if (agenda.validarDisponibilidadCita(citaAModificar)){
                System.out.println("\n✅ Hora modificada con exito!");
                if (!tipoCita.equals(citaAModificar.getTipoCita())){
                    Cita nuevaCita = cambiarInstanciaCita(citaAModificar, agenda);
                    if (nuevaCita != null) {
                        citaAModificar = agenda.getCitaPorId(nuevaCita.getId());
                        System.out.println("\nℹ️ Tu tipo de cita cambio, favor de seleccionar un nuevo motivo.");
                        modificarMotivo(sc, citaAModificar);
                    }
                }
                break;
            } else {
                Menu.mostrarMensajeError("⚠️ No hay disponibilidad. Intenta en otro horario.");
            }
        } while (true);

        return citaAModificar;
    }

    private static void modificarNombre(Scanner sc, Cita citaAModificar) {
        System.out.println("\nNombre actual: " + citaAModificar.getPaciente().getNombre());
        String nuevoNombre = preguntarNombre(sc);
        citaAModificar.getPaciente().setNombre(nuevoNombre);
        System.out.println("\n✅ Nombre modificado con exito!");
    }

    private static void modificarMotivo(Scanner sc, Cita citaAModificar) {
        System.out.println("\nMotivo actual: " + citaAModificar.getMotivo());
        String nuevoMotivo = preguntarMotivo(sc, citaAModificar);
        citaAModificar.setMotivo(nuevoMotivo);
        System.out.println("\n✅ Motivo de cita modificado con exito!");
    }

    private static Cita cambiarInstanciaCita (Cita citaAModificar, Agenda agenda){
        Paciente paciente = citaAModificar.getPaciente();
        LocalDateTime nuevaFechaHora = citaAModificar.getFechaHora();
        String motivo = citaAModificar.getMotivo();

        Cita nuevaCita = null;

        if (citaAModificar.getTipoCita().equals("MATUTINA")) {
            nuevaCita = new CitaMatutina(paciente, nuevaFechaHora, motivo);
        } else if (citaAModificar.getTipoCita().equals("VESPERTINA")) {
            nuevaCita = new CitaVespertina(paciente, nuevaFechaHora, motivo);
        }

        if (nuevaCita != null) {
            if (!agenda.reemplazarCita(citaAModificar, nuevaCita)) {
                Menu.mostrarMensajeError("❌ Error al cambiar la instancia de la cita.");
                return null;
            } else {
                nuevaCita.setId(citaAModificar.getId());
            }
        }
        return nuevaCita;
    }

    private static String preguntarMotivo(Scanner sc, Cita cita) {
        System.out.println("\nLos motivos disponibles para tu horario son:");
        ArrayList<String> motivos = cita.getMotivosDisponibles();
        mostrarMotivosCita(motivos);
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
            nuevoMotivo = cita.getMotivoPorKey(motivos.get(opcion - 1));
            System.out.println("\nOpcion seleccionada: " + nuevoMotivo);
        } while (opcion <= 0 || opcion > cita.getTotalMotivos());
        return nuevoMotivo;
    }

    private static void mostrarMotivosCita(ArrayList<String> motivos) {
        for (int i = 0; i < motivos.size(); i++) {
            System.out.println(i + 1 + ") " + motivos.get(i));
        }
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

    private static LocalDateTime preguntarFechaYHora(Scanner sc) {
        LocalDate fecha = preguntarFecha(sc, false);
        LocalTime hora = preguntarHora(sc, false);
        return LocalDateTime.of(fecha, hora); // Combinar fecha y hora en un LocalDateTime
    }

    private static LocalDate preguntarFecha(Scanner sc, boolean esNuevaFecha) {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        do {
            try {
                if (esNuevaFecha){
                    System.out.print("Nueva Fecha (dd/MM/yyyy): ");
                } else {
                    System.out.print("Fecha (dd/MM/yyyy): ");
                }
                String fechaInput = sc.nextLine();
                return LocalDate.parse(fechaInput, formatoFecha);
            } catch (Exception e) {
                System.out.println("❌ Error: formato incorrecto. Ejemplo correcto -> Fecha: 05/10/2025");
            }
        } while (true);
    }

    private static LocalTime preguntarHora(Scanner sc, boolean esNuevaHora) {
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
        do {
            try {
                if (esNuevaHora){
                    System.out.print("Nueva Hora (HH:mm): ");
                } else {
                    System.out.print("Hora (HH:mm): ");
                }
                String horaInput = sc.nextLine();
                return LocalTime.parse(horaInput, formatoHora);
            } catch (Exception e) {
                System.out.println("❌ Error: formato incorrecto. Ejemplo correcto -> Hora: 14:30");
            }
        } while (true);
    }

    private static Paciente preguntarPaciente(Scanner sc, Agenda agenda) {
        Paciente paciente = null;
        do {
            String id;
            System.out.print("\nIngresa el ID del Paciente. Presiona '0' para buscar el paciente o '-1' para regresar: ");
            id = sc.nextLine();

            if (id.equals("-1")) return paciente;
            if (id.equals("0")) {
                System.out.print("\n");
                GestorPacientes.mostrarListaPacientes();
                continue;
            }

            paciente = GestorPacientes.buscarPacientePorId(id);
            if (paciente == null) {
                Menu.mostrarMensajeError("❌ No se encontro el paciente. Intenta de nuevo.");
            } else {
                System.out.println("\n" + paciente + "\n");
                break;
            }
        } while (true);
        return paciente;
    }

}
