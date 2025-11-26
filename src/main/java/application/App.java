package application;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import controllers.MenuCitasController;
import controllers.MenuController;
import controllers.ModificarCitaController;
import controllers.MostrarCitaController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import dao.AgendaDAO;
import dao.GestorPacientesDAO;
import model.Agenda;
import model.GestorPacientes;
import model.Paciente;
import model.cita.*;
import utils.Menu;
import utils.Paths;
import utils.SubMenus;

public class App extends Application {

    public static App app;
    private Stage stageWindow;

    private Agenda agenda;
    private GestorPacientes gestorPacientes;
    private AgendaDAO agendaDAO;
    private GestorPacientesDAO gestorPacientesDAO;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        app = this;
        stageWindow = stage;

        inicializarGestores();

        setScene(Paths.MENU);
    }

    private void inicializarGestores() {
        this.gestorPacientesDAO = new GestorPacientesDAO();
        ArrayList<Paciente> pacientes = gestorPacientesDAO.cargarPacientes();

        this.gestorPacientes = new GestorPacientes();
        this.gestorPacientes.setListaPacientes(pacientes);
        this.gestorPacientes.inicializarContador(pacientes);

        this.agendaDAO = new AgendaDAO();
        ArrayList<Cita> citasArchivo = agendaDAO.cargarCitas();

        this.agenda = new Agenda();
        this.agenda.setCitas(citasArchivo);
        this.agenda.inicializarContador(citasArchivo);

        //////////// Bloque de prueba ////////////
//        Paciente p1 = new Paciente("Fer", "33 1212 5555", "fer@email.com");
//        Paciente p2 = new Paciente("Leo", "33 1508 2345", "leo@email.com");
//
//        this.gestorPacientes.agregarPaciente(p1);
//        this.gestorPacientes.agregarPaciente(p2);
//        gestorPacientesDAO.guardarPaciente(gestorPacientes.getListaPacientes());
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
//
//        LocalDateTime horaMatutina = LocalDateTime.parse("12/12/2025 11:00", formatter);
//        LocalDateTime horaVespertina = LocalDateTime.parse("10/12/2025 16:00", formatter);
//        Cita c1 = new CitaMatutina(p1, horaMatutina);
//        Cita c2 = new CitaVespertina(p2, horaVespertina);
//        this.agenda.agendarCita(c1);
//        this.agenda.agendarCita(c2);
//        agendaDAO.guardarCitas(this.agenda.getCitas());
        /////////////////////////////////////////
    }

    public void setScene(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            AnchorPane pane = loader.load();

            Object controller = loader.getController();

            if (controller instanceof MenuCitasController) {
                ((MenuCitasController) controller).setDependencies(this.agenda, this.gestorPacientes);
            }

            if (controller instanceof MostrarCitaController) {
                ((MostrarCitaController) controller).setDependencies(this.agenda, this.gestorPacientes, this.agendaDAO);
            }

            stageWindow.setScene(new Scene(pane));
            stageWindow.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setScene(String path, Cita citaAEditar) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            AnchorPane pane = loader.load();

            Object controller = loader.getController();

            if (controller instanceof ModificarCitaController) {
                ((ModificarCitaController) controller).setDependencies(this.agenda, this.gestorPacientes, citaAEditar, this.agendaDAO);
            }

            stageWindow.setScene(new Scene(pane));
            stageWindow.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
