package model.cita;

public class MotivoCita {
    private String motivo;
    private int duracion;

    MotivoCita(String motivo, int duracion) {
        this.motivo = motivo;
        this.duracion = duracion;
    }

    public String getMotivo() {
        return motivo;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
}
