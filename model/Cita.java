package model;//TODO Arreglar Date & Time

import utils.Menu;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

abstract class Cita {
    private static int id = 0;
    private Paciente paciente;
    private LocalDateTime fechaHora;
    private String motivo;
    private int duracionMinutos;

    protected ArrayList<String> listaMotivos = new ArrayList<>();

    public Cita(Paciente paciente, LocalDateTime fechaHora) {
        id++;
        this.paciente = paciente;
        this.fechaHora = fechaHora;
        this.duracionMinutos = 0;
    }

    public int getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public LocalDate getFecha() {
        return fechaHora.toLocalDate();
    }

    public LocalTime getHora() {
        return fechaHora.toLocalTime();
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void mostrarMotivosDisponibles() {
        for (int i = 0; i < listaMotivos.size(); i++) {
            System.out.println(i + 1 + ") " + listaMotivos.get(i));
        }
    }

    public String getMotivoPorIndice(int i) {
        try{
            return listaMotivos.get(i);
        } catch (IndexOutOfBoundsException e) {
            Menu.mostrarMensajeError("Error: " + e.getMessage());
            throw e;
        }
    }

    public int getTotalMotivos() {
        return listaMotivos.size();
    }

    public void mostrarCita() {
        System.out.println("-".repeat(40));
        System.out.println("Fecha: " + this.getFecha());
        System.out.println("De: " + this.getHora() + " a " + this.terminaEn().toLocalTime());
        System.out.println("Paciente: " + paciente.getNombre());
        System.out.println("Motivo: " + this.getMotivo());
        System.out.println("-".repeat(40));

    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public LocalDateTime terminaEn() {
        return this.fechaHora.plusMinutes(this.duracionMinutos);
    }
}

class CitaMatutina extends Cita {
    public CitaMatutina(Paciente paciente, LocalDateTime fechaHora) {
        super(paciente, fechaHora);
        listaMotivos.add("Consulta nutricional");
        listaMotivos.add("Chequeo de glucosa");
        listaMotivos.add("Pesaje mensual");
    }
}

class CitaVespertina extends Cita {
    public CitaVespertina(Paciente paciente, LocalDateTime fechaHora) {
        super(paciente, fechaHora);
        listaMotivos.add("Consulta general de psicologia");
        listaMotivos.add("Crisis nerviosa");
        listaMotivos.add("Cita infantil");
    }
}