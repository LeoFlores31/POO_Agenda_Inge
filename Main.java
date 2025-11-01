import java.time.LocalDateTime;
import java.util.Scanner;

import utils.Menu;
import utils.SubMenus;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();

        Scanner sc = new Scanner(System.in);
        int opcion;

        menu.mostrarTitulo();

        do {
            menu.mostrarMenuPrincipal();
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    SubMenus.ejecutarMenuPaciente(sc);
                    break;
                case 2:
                    SubMenus.ejecutarMenuAgenda(sc);
                    break;
                case 3:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }

        } while (opcion != 3);

        sc.close();

    }
}
