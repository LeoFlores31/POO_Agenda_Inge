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
        App.app.setScene(Paths.CREAR_PACIENTE);
    }

    @FXML
    void mostrarPacientes(ActionEvent event) {
        App.app.setScene(Paths.MOSTRAR_PACIENTES);
    }

    @FXML
    void regresarMenuPrincipal(ActionEvent event) {
        App.app.setScene(Paths.MENU);
    }

}
