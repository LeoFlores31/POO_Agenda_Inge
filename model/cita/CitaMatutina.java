package model.cita;

import model.Paciente;

import java.time.LocalDateTime;

public class CitaMatutina extends Cita {
    public CitaMatutina(Paciente paciente, LocalDateTime fechaHora) {
        // constructor encadenado
        this(paciente, fechaHora, 1); // se asgina el motivo por default
    }

    public CitaMatutina(Paciente paciente, LocalDateTime fechaHora, int idMotivo) {
        super(paciente, fechaHora, idMotivo);
        motivosDisponibles.put(1, new MotivoCita("Consulta nutricional", 60));
        motivosDisponibles.put(2, new MotivoCita("Chequeo de glucosa", 40));
        motivosDisponibles.put(3, new MotivoCita("Pesaje mensual", 15));
        this.setIdMotivo(idMotivo);
    }
}