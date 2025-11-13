package model.cita;

import model.Paciente;

import java.time.LocalDateTime;

public class CitaVespertina extends Cita {
    public CitaVespertina(Paciente paciente, LocalDateTime fechaHora) {
        // constructor encadenado
        this(paciente, fechaHora, "");
    }

    public CitaVespertina(Paciente paciente, LocalDateTime fechaHora, String motivo) {
        super(paciente, fechaHora);
        motivosDisponibles.put("Consulta general de psicologia", 60);
        motivosDisponibles.put("Crisis nerviosa", 90);
        motivosDisponibles.put("Cita infantil", 30);
        this.setMotivo(motivo);
    }
}
