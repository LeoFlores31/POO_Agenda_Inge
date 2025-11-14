package model.cita;

import model.Paciente;

import java.time.LocalDateTime;

public class CitaVespertina extends Cita {
    public CitaVespertina(Paciente paciente, LocalDateTime fechaHora) {
        // constructor encadenado
        this(paciente, fechaHora, 1); // se asgina el motivo por default
    }

    public CitaVespertina(Paciente paciente, LocalDateTime fechaHora, int idMotivo) {
        super(paciente, fechaHora, idMotivo);
        motivosDisponibles.put(1, new MotivoCita("Consulta general de psicologia", 60));
        motivosDisponibles.put(2, new MotivoCita("Crisis nerviosa", 90));
        motivosDisponibles.put(3, new MotivoCita("Cita infantil", 30));
        this.setMotivo(motivosDisponibles.get(idMotivo).getMotivo());
        this.setDuracionMinutos(motivosDisponibles.get(idMotivo).getDuracion());
    }
}
