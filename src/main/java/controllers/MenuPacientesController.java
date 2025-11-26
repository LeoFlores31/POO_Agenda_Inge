package controllers;

import application.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utils.Paths;

public class MenuPacientesController {

    @FXML
    private Button btnCrearPaciente;

    @FXML
    private Button btnMostrarPacientes;

    @FXML
    private Button btnRegresarMenu;

    @FXML
    void crearPaciente(ActionEvent event) {

    }

    @FXML
    void mostrarPacientes(ActionEvent event) {
        App.app.setScene(Paths.MOSTRAR_PACIENTES);
        // todo: se debe de mostar la lista actual de pacientes
        // todo: opcion a filtrar por los diferentes campos
        // todo: se autocompletaran los campos al seleccionar.
        // todo: al seleccionar a un paciente se hablitara la opcion de modificar o eliminar

    }

    @FXML
    void regresarMenuPrincipal(ActionEvent event) {
        App.app.setScene(Paths.MENU);
    }

}
