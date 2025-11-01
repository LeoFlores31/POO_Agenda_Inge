package utils;

import java.util.Scanner;

public class Menu {

    public void mostrarTitulo() {
        int repeat = 45;
        System.out.println("-".repeat(repeat));
        System.out.println("\tAgenda de Citas Medicas - El Inge 👨‍💻");
        System.out.println("-".repeat(repeat));
    }

    public void mostrarMenuPrincipal() {
        System.out.println("\n--- Menú Principal ---");
        System.out.println("1. Pacientes");
        System.out.println("2. Citas");
        System.out.println("3. Salir del sistema");
        System.out.print("\n\tOpcion: ");
    }
}
