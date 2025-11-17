package model.cita;

import model.Paciente;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Cita implements java.io.Serializable {
    private static int totalCitas = 0;
    private int id;
    private Paciente paciente;
    private LocalDateTime fechaHora;
    private int idMotivo;

    protected HashMap<Integer, MotivoCita> motivosDisponibles = new HashMap<>();

    public Cita(Paciente paciente, LocalDateTime fechaHora, int idMotivo) {
        this.id = ++totalCitas;
        this.paciente = paciente;
        this.fechaHora = fechaHora;
        this.idMotivo = idMotivo;
    }

    public static void setMaxId(int maxId) {
        totalCitas = maxId;
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
        return this.motivosDisponibles.get(idMotivo).getMotivo();
    }

    public int getDuracionMinutos() {
        return this.motivosDisponibles.get(idMotivo).getDuracion();
    }

    public ArrayList<String> getMotivosDisponibles() {
        ArrayList<String> motivos = new ArrayList<>();
        for (Map.Entry<Integer, MotivoCita> entry : motivosDisponibles.entrySet()) {
            motivos.add(entry.getValue().getMotivo());
        }
        return motivos;
    }

    public int getIdMotivo() {
        return this.idMotivo;
    }

    public void setIdMotivo(int id) {
        this.idMotivo = id;
    }

    public MotivoCita getMotivoPorId(int id) {
        MotivoCita motivo = motivosDisponibles.get(id);
        if (motivo == null) {
            throw new IllegalArgumentException("ID de motivo inválido: " + id);
        }
        return motivo;
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

    public LocalDateTime terminaEn() {
        return this.fechaHora.plusMinutes(this.motivosDisponibles.get(idMotivo).getDuracion());
    }
}