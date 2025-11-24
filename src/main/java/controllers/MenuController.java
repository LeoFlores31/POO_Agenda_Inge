package controllers;

import application.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import utils.Paths;

import java.util.Optional;

public class MenuController {

    @FXML
    private Button btnAgenda;

    @FXML
    private Button btnPacientes;

    @FXML
    private Button btnSalir;

    @FXML
    void mostrarMenuAgenda(ActionEvent event) {
        System.out.println("Mostrando Menu Agenda");
        App.app.setScene(Paths.MENU_CITAS);
    }

    @FXML
    void mostrarMenuPacientes(ActionEvent event) {
        System.out.println("Mostrando Menu Paciente");
    }

    @FXML
    void salirSistema(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de Salida");
        alert.setHeaderText("¿Estás seguro que deseas salir de la aplicación?");
        alert.setContentText("Asegúrate de haber guardado todos los cambios recientes.");

        Optional<ButtonType> resultado = alert.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            // todo: validar si es necesario llamar al metodo: agendaDAO.guardarCitas(agenda.getCitas());
            System.out.println("Saliendo del sistema");
            Platform.exit();
            System.exit(0);
        }
    }

}
