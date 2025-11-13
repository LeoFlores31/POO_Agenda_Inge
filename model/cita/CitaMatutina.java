package model.cita;

import model.Paciente;

import java.time.LocalDateTime;

public class CitaMatutina extends Cita {
    public CitaMatutina(Paciente paciente, LocalDateTime fechaHora) {
        // constructor encadenado
        this(paciente, fechaHora, "");
    }

    public CitaMatutina(Paciente paciente, LocalDateTime fechaHora, String motivo) {
        super(paciente, fechaHora);
        motivosDisponibles.put("Consulta nutricional", 60);
        motivosDisponibles.put("Chequeo de glucosa", 40);
        motivosDisponibles.put("Pesaje mensual", 15);
        this.setMotivo(motivo);
    }
}