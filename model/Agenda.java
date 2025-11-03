package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import utils.Menu;

public class Agenda {

    /*
        CRUD:
        agendarCita()                   ok!
        modificarCita()
        cancelarCita() / cancelar
        mostrarCitasPorPaciente()       ok!
        mostrarCitas()                  ok!
        --
        buscarCita()                    ok!
     */

    private ArrayList<Cita> citas;
    private final GestorPacientes gestorPacientes;

    public Agenda (GestorPacientes gestorPacientes) {
        this.citas = new ArrayList<>();
        this.gestorPacientes = gestorPacientes;
    }

    private Cita buscarCita(int id) {
        for (Cita c : citas) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public void mostrarCitas() {
        if (citas.isEmpty()) {
            Menu.mostrarMensajeError("⚠️ Aun no hay citas registradas.");
            return;
        }

        Menu.mostrarMensaje("\tCITAS MEDICAS 🩺", 25);

        for (Cita c : citas) {
            c.mostrarCita();
        }
    }

    public void mostrarCitasPorPaciente() {
        if (citas.isEmpty()) {
            Menu.mostrarMensajeError("⚠️ Aun no hay citas registradas.");
            return;
        }

        Menu.mostrarMensaje("\tCITAS POR PACIENTE‍️ 👨🏻‍", 30);

        Scanner sc = new Scanner(System.in);
        boolean pacienteEncontrado = false;

        Paciente paciente = null;
        do {
            String id;
            System.out.print("\nID del Paciente (0 para buscar paciente): ");
            id = sc.nextLine();

            if (id.equals("*")) return;
            if (id.equals("0")) {
                gestorPacientes.mostrarPacientes(); // TODO: Implementacion pendiente (preguntar si quiere buscarlo por nombre, etc
                continue;
            }

            paciente = gestorPacientes.buscarPacientePorID(id);
            if (paciente == null) {
                Menu.mostrarMensajeError("❌ No se encontro el paciente. Intenta de nuevo.");
            } else {
                break;
            }
        } while (true);


        for (Cita c : citas) {
            if (Objects.equals(c.getPaciente().getId(), paciente.getId())) {
                c.mostrarCita();
                pacienteEncontrado = true;
            }
        }

        if (!pacienteEncontrado) {
            Menu.mostrarMensajeError("⚠️ No se encontraron citas para " + paciente.getNombre());
        }
    }

    public boolean agendarCita() {
        Scanner sc = new Scanner(System.in);
        boolean citaMatutina = false;
        boolean citaVespertina = false;

        Menu.mostrarMensaje("\tNUEVA CITA 🗒️", 22);
        System.out.println("Pulsa '*' para salir en cualquier momento.");

        Paciente paciente;
        do {
            String id;
            System.out.print("\nID del Paciente (0 para buscar paciente): ");
            id = sc.nextLine();

            if (id.equals("*")) return false;
            if (id.equals("0")) {
                gestorPacientes.mostrarPacientes(); // TODO: Implementacion pendiente (preguntar si quiere buscarlo por nombre, etc
                continue;
            }

            paciente = gestorPacientes.buscarPacientePorID(id);
            if (paciente == null) {
                Menu.mostrarMensajeError("❌ No se encontro el paciente. Intenta de nuevo.");
            } else {
                System.out.println("\n" + paciente + "\n");
                break;
            }
        } while (true);

        // pedir fecha y hora
        LocalDateTime fechaHora = preguntarFechaYHora(sc);
        if (fechaHora == null) return false;

        LocalTime horaCambioTurno = LocalTime.of(14, 0);
        if (fechaHora.toLocalTime().isBefore(horaCambioTurno)) {
            citaMatutina = true;
        } else {
            citaVespertina = true;
        }

        // motivo
        Cita cita = null;
        int opcion;
        if (citaMatutina) cita = new CitaMatutina(paciente, fechaHora);
        if (citaVespertina) cita = new CitaVespertina(paciente, fechaHora);

        System.out.println("\nSelecciona el motivo:");
        cita.mostrarMotivosDisponibles();
        do {
            System.out.print("\tOpcion: ");
            opcion = sc.nextInt();
            if (opcion <= 0 || opcion > cita.getTotalMotivos()){
                Menu.mostrarMensajeError("Opcion invalida. Intenta de nuevo.");
            } else {
                String motivo = cita.getMotivoPorIndice(opcion - 1);
                cita.setMotivo(motivo);
                System.out.println("\nOpcion seleccionada: " + motivo);
                break;
            }
        } while (true);

        System.out.print("\nDuracion (min): ");
        int duracion = sc.nextInt();
        cita.setDuracionMinutos(duracion);

        String mensajeCitaCreada = "\tCita creada para " + cita.getPaciente().getNombre() +
                " el dia " + cita.getFecha() + " a las " + cita.getHora() + " hrs.";

        if (citas.isEmpty()) {
            citas.add(cita);
            Menu.mostrarMensaje(mensajeCitaCreada, 65);
            return true;
        }

        boolean citaAceptada = false;
        for (Cita c : citas) {
            if ( ( cita.getFechaHora().isEqual(c.getFechaHora()) ) || ( cita.getFechaHora().isAfter(c.getFechaHora()) && cita.getFechaHora().isBefore(c.terminaEn()) ) ) {
                Menu.mostrarMensajeError("❌ Error. No hay disponibilidad en ese horario. Intenta de nuevo.");
                citaAceptada = false;
            } else {
                citaAceptada = true;
            }
        }
        if (citaAceptada) {
            citas.add(cita);
            Menu.mostrarMensaje(mensajeCitaCreada, 65);
        }

        return citaAceptada;
    }

    private LocalDateTime preguntarFechaYHora(Scanner sc) {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
        do {
            try {
                System.out.print("Fecha (dd/MM/yyyy): ");
                String fechaInput = sc.nextLine();
                if (fechaInput.equals("*")) return null;
                LocalDate fecha = LocalDate.parse(fechaInput, formatoFecha);

                System.out.print("Hora (HH:mm): ");
                String horaInput = sc.nextLine();
                if (horaInput.equals("*")) return null;
                LocalTime hora = LocalTime.parse(horaInput, formatoHora);

                return LocalDateTime.of(fecha, hora); // Combinar fecha y hora en un LocalDateTime
            } catch (Exception e) {
                System.out.println("❌ Error: formato incorrecto. Ejemplo correcto -> Fecha: 05/10/2025, Hora: 14:30");
            }
        } while (true);
    }



}
