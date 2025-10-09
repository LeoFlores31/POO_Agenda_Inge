public class Citas {
    private Paciente paciente;
    private String fecha;
    private String hora;
    private String motivo;

    public Citas(Paciente paciente, String fecha, String hora, String motivo) {
        this.paciente = paciente;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void mostrarCita() {
        System.out.println("Id del paciente: " + paciente.getId());
        System.out.println("Cita para el paciente: " + paciente.getNombre());
        System.out.println("Telefono: " + paciente.getTelefono());
        System.out.println("Email: " + paciente.getEmail());
        System.out.println("Fecha: " + fecha);
        System.out.println("Hora: " + hora);
    }
}
