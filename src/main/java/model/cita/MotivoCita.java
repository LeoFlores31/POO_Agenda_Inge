package model.cita;

import java.io.Serial;

public class MotivoCita implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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
