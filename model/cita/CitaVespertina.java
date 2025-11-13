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
        mapMotivos.put("Consulta general de psicologia", 60);
        mapMotivos.put("Crisis nerviosa", 90);
        mapMotivos.put("Cita infantil", 30);
        this.setMotivo(motivo);
    }
}
