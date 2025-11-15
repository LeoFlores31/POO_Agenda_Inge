package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import model.cita.*;

public class Agenda {

    private ArrayList<Cita> citas;

    public Agenda () {
        this.citas = new ArrayList<>();
    }

    public ArrayList<Cita> buscarCitaPorNombre(String nombre) {
        ArrayList<Cita> citasEncontradas = new ArrayList<>();
        for (Cita c : citas) {
            if (c.getPaciente().getNombre().equalsIgnoreCase(nombre)) {
                citasEncontradas.add(c);
            }
        }
        return citasEncontradas;
    }

    public ArrayList<Cita> buscarCitaPorTelefono(String telefono) {
        ArrayList<Cita> citasEncontradas = new ArrayList<>();
        for (Cita c : citas) {
            if (c.getPaciente().getTelefono().equals(telefono)) {
                citasEncontradas.add(c);
            }
        }
        return citasEncontradas;
    }

    public ArrayList<Cita> buscarCitaPorEmail(String email) {
        ArrayList<Cita> citasEncontradas = new ArrayList<>();
        for (Cita c : citas) {
            if (c.getPaciente().getEmail().equalsIgnoreCase(email)) {
                citasEncontradas.add(c);
            }
        }
        return citasEncontradas;
    }

    public boolean cancelarCita(int id) {
        for (Cita c : citas) {
            if (c.getId() == id) {
                return citas.remove(c);
            }
        }
        return false;
    }

    public ArrayList<Cita> getCitas() {
        return citas;
    }

    public Cita getCitaPorId(int id) {
        Cita cita = null;
        for (Cita c : citas) {
            if (c.getId() == id) {
                cita = c;
            }
        }
        return cita;
    }

    public boolean agendarCita(Cita nuevaCita) {
        if (validarDisponibilidadCita(nuevaCita)){
            citas.add(nuevaCita);
            return true;
        }
        return false;
    }

    public boolean validarDisponibilidadCita(Cita nuevaCita) {
        if (citas.isEmpty()) {
            return true;
        }

        LocalDateTime finNuevaCita = nuevaCita.terminaEn();

        for (Cita c : citas) {
            LocalDateTime inicioExistente = c.getFechaHora();
            LocalDateTime finExistente = c.terminaEn();

            boolean seSuperpone = nuevaCita.getFechaHora().isBefore(finExistente) &&
                    inicioExistente.isBefore(finNuevaCita);

            if (seSuperpone) {
                return false;
            }
        }

        return true;
    }

    public boolean reemplazarCita(Cita antigua, Cita nueva) {
        int indice = this.citas.indexOf(antigua);
        if (indice >= 0) {
            this.citas.set(indice, nueva);
            return true;
        }
        return false;
    }

}
