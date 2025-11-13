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
//        listaMotivos.add("Consulta nutricional");
//        listaMotivos.add("Chequeo de glucosa");
//        listaMotivos.add("Pesaje mensual");
        mapMotivos.put("Consulta nutricional", 60);
        mapMotivos.put("Chequeo de glucosa", 40);
        mapMotivos.put("Pesaje mensual", 15);
        this.setMotivo(motivo);
    }
}