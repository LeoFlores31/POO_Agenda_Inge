package model.cita;

import model.Paciente;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class Cita {
    private static int totalCitas = 0;
    private int id;
    private Paciente paciente;
    private LocalDateTime fechaHora;
    private int motivoId;
    // todo: usar motivo y duracionMinutos en lugar de modificar el HashMap
//    private String motivo;
//    private int duracionMinutos;

    protected HashMap<Integer, MotivoCita> motivosDisponibles = new HashMap<>();

    public Cita() {}

    public Cita(Paciente paciente, LocalDateTime fechaHora, int motivoId) {
        this.id = ++totalCitas;
        this.paciente = paciente;
        this.fechaHora = fechaHora;
        this.motivoId = motivoId;
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
        return this.motivosDisponibles.get(motivoId).getMotivo();
    }

    public void setMotivo(String motivo) {
        this.motivosDisponibles.get(motivoId).setMotivo(motivo);
    }

    public ArrayList<String> getMotivosDisponibles() {
        ArrayList<String> motivos = new ArrayList<>();
        for (Map.Entry<Integer, MotivoCita> entry : motivosDisponibles.entrySet()) {
            motivos.add(entry.getValue().getMotivo());
        }
        return motivos;
    }

    public int motivoCitaId() {
        return this.motivoId;
    }

    public MotivoCita getMotivoPorID(Integer id) {
        for (Map.Entry<Integer, MotivoCita> entry : motivosDisponibles.entrySet()) {
            if (Objects.equals(entry.getKey(), id)) {
                return entry.getValue();
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
        return this.motivosDisponibles.get(motivoId).getDuracion();
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.motivosDisponibles.get(motivoId).setDuracion(duracionMinutos);
    }

    public LocalDateTime terminaEn() {
        return this.fechaHora.plusMinutes(this.motivosDisponibles.get(motivoId).getDuracion());
    }
}