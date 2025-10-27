package utils;

import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {
        ejecutarMenuPrincipal();
    }

    public static void ejecutarMenuPrincipal() {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenuPrincipal();
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    SubMenus.ejecutarMenuPaciente(sc);
                    break;
                case 2:
                    SubMenus.ejecutarMenuAgenda(sc);
                    break;
                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }

        } while (opcion != 4);

        sc.close();
    }

    public static void mostrarMenuPrincipal() {
        System.out.println("\n--- Menú Principal ---");
        System.out.println("1. Pacientes");
        System.out.println("2. Citas");
        System.out.println("4. Salir del sistema");
        System.out.print("\n\tOpcion: ");
    }
}
