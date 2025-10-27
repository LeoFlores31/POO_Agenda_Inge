import java.time.LocalDateTime;

import utils.Menu;
import utils.SubMenus;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        SubMenus.mostrarMenuAgenda();
        SubMenus.mostrarMenuPaciente();
    }
}
