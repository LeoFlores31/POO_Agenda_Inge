package utils;

public class Menu {

    public static void mostrarMensaje(String mensaje, int lineasSeparacion) {
        System.out.println('\n' + "-".repeat(lineasSeparacion));
        System.out.println(mensaje);
        System.out.println("-".repeat(lineasSeparacion) + '\n');
    }

    public static void mostrarMenuPrincipal() {
        System.out.println("\n--- Menú Principal ---");
        System.out.println("1. Pacientes");
        System.out.println("2. Citas");
        System.out.println("3. Salir del sistema");
        System.out.print("\n\tOpcion: ");
    }

    public static void mostrarMensajeError(String mensaje) {
        System.out.println("\n*" + mensaje + "*\n");
    }

}
