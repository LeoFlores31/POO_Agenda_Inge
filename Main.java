public class Main {
    public static void main(String[] args) {
        // Crear paciente
        Paciente paciente1 = new Paciente(1, "Leonel F", "3334564092", "leo@mail.com");

        // Crear cita para ese paciente
        Citas cita1 = new Citas(paciente1, "2025-10-03", "10:30 AM");

        // Mostrar información de la cita
        cita1.mostrarCita();
    }
}
