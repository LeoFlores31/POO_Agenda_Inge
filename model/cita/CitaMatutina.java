package model.cita;

import model.Paciente;

import java.time.LocalDateTime;

public class CitaMatutina extends Cita {
    public CitaMatutina(Paciente paciente, LocalDateTime fechaHora) {
        super(paciente, fechaHora);
        listaMotivos.add("Consulta nutricional");
        listaMotivos.add("Chequeo de glucosa");
        listaMotivos.add("Pesaje mensual");
    }

    public CitaMatutina(Paciente paciente, LocalDateTime fechaHora, String motivo ,int duracionMinutos) {
        super(paciente, fechaHora);
        listaMotivos.add("Consulta nutricional");
        listaMotivos.add("Chequeo de glucosa");
        listaMotivos.add("Pesaje mensual");
        this.setMotivo(motivo);
        this.setDuracionMinutos(duracionMinutos);
    }
}