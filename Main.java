import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // Crear paciente
        Paciente paciente1 = new Paciente(1, "Leonel F", "3334564092", "leo@mail.com");
        // Crear cita para ese paciente
        Cita citaMatutina = new CitaMatutina(paciente1, LocalDateTime.now(), "Revision general");
        if (citaMatutina.getMotivo() == null) {
            System.out.println("Motivo incorrecto. Intenta de nuevo.");
        } else {
            citaMatutina.mostrarCita();
        }


        System.out.println("\n\n");

        // Crear paciente
        Paciente paciente2 = new Paciente(2, "Fer S", "1112223344", "fer@mail.com");
        // Crear cita para ese paciente
        Cita citaVespertina = new CitaVespertina(paciente2, LocalDateTime.now(), "Cita infantil");
        if (citaVespertina.getMotivo() == null) {
            System.out.println("Motivo " + citaVespertina.motivo +" incorrecto. Intenta de nuevo.");
        } else {
            citaVespertina.mostrarCita();
        }


    }
}
