package model;

import utils.Menu;

import java.util.ArrayList;
import java.util.Optional;

public class GestorPacientes {
    private ArrayList<Paciente> pacientes = new ArrayList<>();

    public void mostrarPacientes() {
        System.out.println("\n");
        if (pacientes.isEmpty()) {
            Menu.mostrarMensajeError("⚠️ Aun no hay pacientes registrados.");
        } else {
            for (Paciente p : pacientes){
                System.out.println(p);
            }
        }
    }

    public Paciente buscarPacientePorID(String id) {
        for (Paciente p : pacientes){
            if (p.getId().equalsIgnoreCase(id)) return p;
        }
        return null;
    }

    public void agregarPaciente(Paciente p){
        pacientes.add(p);
    }

}
