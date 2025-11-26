package controllers;

import application.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.Agenda;
import model.GestorPacientes;
import utils.Paths;

public class MenuCitasController {

    private Agenda agenda;
    private GestorPacientes gestorPacientes;

    public void setDependencies(Agenda agenda, GestorPacientes gestorPacientes) {
        this.agenda = agenda;
        this.gestorPacientes = gestorPacientes;
    }

    @FXML
    private Button btnCrearCita;

    @FXML
    private Button btnMostrarCitas;

    @FXML
    private Button btnRegresarMenu;

    @FXML
    void crearCita(ActionEvent event) {
        App.app.setScene(Paths.CREAR_CITA);
    }

    @FXML
    void mostrarCitas(ActionEvent event) {
        App.app.setScene(Paths.MOSTRAR_CITAS);
    }

    @FXML
    void regresarMenuPrincipal(ActionEvent event) {
        App.app.setScene(Paths.MENU);
    }

}
