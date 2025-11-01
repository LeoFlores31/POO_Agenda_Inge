package model;//TODO Arreglar Date & Time

import java.time.LocalDateTime;
import java.util.ArrayList;

abstract class Cita {
    private Paciente paciente;
    private LocalDateTime fechaHora;
    String motivo;

    public Cita(Paciente paciente, LocalDateTime fechaHora) {
        this.paciente = paciente;
        this.fechaHora = fechaHora;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
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

    public boolean setMotivo(String motivo) {
        return false;
    }

    public void mostrarCita() {
        System.out.println("Id del paciente: " + paciente.getId());
        System.out.println("Nombre paciente: " + paciente.getNombre());
        System.out.println("Telefono: " + paciente.getTelefono());
        System.out.println("Email: " + paciente.getEmail());
        System.out.println("Fecha y Hora: " + this.getFechaHora());
    }
}

class CitaMatutina extends Cita {

    private ArrayList<String> listaMotivos = new ArrayList<>();

    public CitaMatutina(Paciente paciente, LocalDateTime fechaHora, String motivo) {
        super(paciente, fechaHora);
        listaMotivos.add("Consulta nutricional");
        listaMotivos.add("Chequeo de glucosa");
        listaMotivos.add("Pesaje mensual");
        if (!this.setMotivo(motivo)) {
            this.motivo = null;
        }
    }

    @Override
    public boolean setMotivo(String motivo) {
        for (String m : listaMotivos) {
            if (m.equals(motivo)) {
                this.motivo = motivo;
                return true;
            }
        }
        return false;
    }
}

class CitaVespertina extends Cita {

    private ArrayList<String> listaMotivos = new ArrayList<>();

    public CitaVespertina(Paciente paciente, LocalDateTime fechaHora, String motivo) {
        super(paciente, fechaHora);
        listaMotivos.add("Consulta general de psicologia");
        listaMotivos.add("Crisis nerviosa");
        listaMotivos.add("Cita infantil");
        if (!this.setMotivo(motivo)) {
            this.motivo = null;
        }
    }

    @Override
    public boolean setMotivo(String motivo) {
        for (String m : listaMotivos) {
            if (m.equals(motivo)) {
                this.motivo = motivo;
                return true;
            }
        }
        return false;
    }

}