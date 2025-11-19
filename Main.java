import java.util.ArrayList;
import java.util.Scanner;

import dao.AgendaDAO;
import dao.GestorPacientesDAO;
import model.Agenda;
import model.GestorPacientes;
import model.Paciente;
import model.cita.*;
import utils.Menu;
import utils.SubMenus;

public class Main {
    public static void main(String[] args) {
        GestorPacientesDAO gestorPacientesDAO = new GestorPacientesDAO();
        ArrayList<Paciente> pacientes = gestorPacientesDAO.cargarPacientes();

        GestorPacientes gestorPacientes = new GestorPacientes();
        gestorPacientes.setListaPacientes(pacientes);
        gestorPacientes.inicializarContador(pacientes);

        AgendaDAO agendaDAO = new AgendaDAO();
        ArrayList<Cita> citasArchivo = agendaDAO.cargarCitas();

        Agenda agenda = new Agenda();
        agenda.setCitas(citasArchivo);
        agenda.inicializarContador(citasArchivo);

        Scanner sc = new Scanner(System.in);
        int opcion;

        //////////// Bloque de prueba ////////////
//        Paciente p1 = new Paciente("Fer", "33 1212 5555", "fer@email.com");
//        Paciente p2 = new Paciente("Leo", "33 1508 2345", "leo@email.com");
//
//        gestorPacientes.agregarPaciente(p1);
//        gestorPacientes.agregarPaciente(p2);
//        gestorPacientesDAO.guardarPaciente(gestorPacientes.getListaPacientes());
        /////////////////////////////////////////

        Menu.mostrarMensaje("\tAgenda de Citas Medicas - El Inge 👨‍💻", 45);

        do {
            Menu.mostrarMenuPrincipal();
            opcion = sc.nextInt();
            // todo: validar entradad de datos
            switch (opcion) {
                case 1:
                    SubMenus.ejecutarMenuPaciente(sc, gestorPacientes, gestorPacientesDAO);
                    break;
                case 2:
                    SubMenus.ejecutarMenuAgenda(sc, agenda, agendaDAO, gestorPacientes);
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
