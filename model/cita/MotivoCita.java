package model.cita;

public class MotivoCita implements java.io.Serializable {
    private final String motivo;
    private final int duracion;

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
}
