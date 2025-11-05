package model.cita;

import model.Paciente;

import java.time.LocalDateTime;

public class CitaVespertina extends Cita {
    public CitaVespertina(Paciente paciente, LocalDateTime fechaHora) {
        super(paciente, fechaHora);
        listaMotivos.add("Consulta general de psicologia");
        listaMotivos.add("Crisis nerviosa");
        listaMotivos.add("Cita infantil");
    }

    public CitaVespertina(Paciente paciente, LocalDateTime fechaHora, String motivo, int duracionMinutos) {
        super(paciente, fechaHora);
        listaMotivos.add("Consulta general de psicologia");
        listaMotivos.add("Crisis nerviosa");
        listaMotivos.add("Cita infantil");
        this.setMotivo(motivo);
        this.setDuracionMinutos(duracionMinutos);
    }
}
