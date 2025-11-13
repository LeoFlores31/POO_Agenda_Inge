package model.cita;

import model.Paciente;
import utils.Menu;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Cita {
    private static int totalCitas = 0;
    private int id;
    private Paciente paciente;
    private LocalDateTime fechaHora;
    private String motivo;
    private int duracionMinutos;

    // todo: crear un HashMap<String, int> para almacenar el motivo y su duracion
//    protected ArrayList<String> listaMotivos = new ArrayList<>();
    protected HashMap<String, Integer> motivosDisponibles = new HashMap<>();

    public Cita(Paciente paciente, LocalDateTime fechaHora) {
        this.id = ++totalCitas;
        this.paciente = paciente;
        this.fechaHora = fechaHora;
        this.duracionMinutos = 0;
    }

    public String getTipoCita() {
        return this.fechaHora.getHour() < 12 ? "MATUTINA" : "VESPERTINA";
    }

    public void setId(int id) {
        this.id = id;
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

    public void setFecha(LocalDate fecha){
        this.fechaHora = LocalDateTime.of(fecha, this.getHora());
    }

    public void setHora(LocalTime hora){
        this.fechaHora = LocalDateTime.of(this.getFecha(), hora);
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public ArrayList<String> getMotivosDisponibles() {
        return new ArrayList<>(motivosDisponibles.keySet());
    }

    public String getMotivoPorKey(String motivo) {
        for (Map.Entry<String, Integer> entry : motivosDisponibles.entrySet()) {
            if (entry.getKey().equals(motivo)) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("No se encontró ningún motivo.");
    }

    public int getTotalMotivos() {
        return motivosDisponibles.size();
    }

    public void mostrarCita() {
        System.out.println("-".repeat(40));
        System.out.println("ID Cita: " + this.getId());
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