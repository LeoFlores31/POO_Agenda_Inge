package controllers;

import application.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import utils.Paths;
import utils.Alertas;
import java.util.Optional;

public class MenuPrincipalController {

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
        App.app.setScene(Paths.MENU_PACIENTES);
    }

    @FXML
    void salirSistema(ActionEvent event) {
        String title = "Confirmación de Salida";
        String header = "¿Estás seguro que deseas salir de la aplicación?";
        String context = "Asegúrate de haber guardado todos los cambios recientes.";

        Optional<ButtonType> resultado = Alertas.mostarConfirmation(title, header, context);

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            // todo: validar si es necesario llamar al metodo: agendaDAO.guardarCitas(agenda.getCitas());
            System.out.println("Saliendo del sistema");
            Platform.exit();
            System.exit(0);
        }
    }

}
