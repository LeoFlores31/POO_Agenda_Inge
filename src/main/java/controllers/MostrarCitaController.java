package controllers;

import application.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import model.Agenda;
import model.GestorPacientes;
import model.Paciente;
import model.cita.Cita;
import utils.Paths;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MostrarCitaController implements Initializable {

    private Agenda agenda;
    private GestorPacientes gestorPacientes;

    public void setDependencies(Agenda agenda, GestorPacientes gestorPacientes) {
        this.agenda = agenda;
        this.gestorPacientes = gestorPacientes;
        actualizarTabla(this.agenda.getCitas());
    }

    @FXML
    private Button btnBuscarCita;

    @FXML
    private Button btnCancelarCita;

    @FXML
    private Button btnModificarCita;

    @FXML
    private Button btnRegresar;

    @FXML
    private ChoiceBox<String> ddTurno;

    private final String[] opcionesTurno = {"Matutino", "Vespertino"};

    @FXML
    private ChoiceBox<String> ddBuscarPor;
    private final String[] opcionesBuscarPor = {"Mostar todo", "Nombre", "Telefono", "Email", "Turno"};

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblTelefono;

    @FXML
    private Label lblTurno;

    @FXML
    private TableView<Cita> tblCitas;

    @FXML
    private TableColumn<Cita, Integer> colID;

    @FXML
    private TableColumn<Cita, LocalDate> colFecha;

    @FXML
    private TableColumn<Cita, String> colHorario;

    @FXML
    private TableColumn<Cita, String> colTurno;

    @FXML
    private TableColumn<Cita, Paciente> colPaciente;

    @FXML
    private TableColumn<Cita, String> colTelefono;

    @FXML
    private TableColumn<Cita, String> colEmail;

    @FXML
    private TableColumn<Cita, String> colMotivo;

    @FXML
    private TableColumn<Cita, Integer> colDuracion;

    @FXML
    void buscarCita(ActionEvent event) {
        btnModificarCita.setVisible(false);
        btnCancelarCita.setVisible(false);
        ArrayList<Cita> citas = new ArrayList<>();

        if (ddBuscarPor.getValue() == null) {
            mostarWarningCampoRequerido();
            return;
        }

        if (ddBuscarPor.getValue().equals("Mostar todo")) {
            citas = agenda.getCitas();

        } else if (ddBuscarPor.getValue().equals("Nombre")) {
            String nombreBuscado = txtNombre.getText().trim();

            if (nombreBuscado.isEmpty()) {
                mostarWarningCampoRequerido();
                return;
            } else {
                citas = agenda.getCitaPorNombre(txtNombre.getText());
            }

        } else if (ddBuscarPor.getValue().equals("Telefono")) {
            String telefonoBuscado = txtTelefono.getText().trim();

            if (telefonoBuscado.isEmpty()) {
                mostarWarningCampoRequerido();
                return;
            } else {
                citas = agenda.getCitaPorTelefono(txtTelefono.getText());
            }

        } else if (ddBuscarPor.getValue().equals("Email")) {
            String emailBuscado = txtEmail.getText().trim();

            if (emailBuscado.isEmpty()) {
                mostarWarningCampoRequerido();
                return;
            } else {
                citas = agenda.getCitaPorEmail(txtEmail.getText());
            }

        } else if (ddBuscarPor.getValue().equals("Turno")) {
            String turnoBuscado = ddTurno.getValue();

            if (turnoBuscado.isEmpty()) {
                mostarWarningCampoRequerido();
                return;
            } else {
                citas = agenda.getCitaPorTurno(ddTurno.getValue());
            }
        }
        actualizarTabla(citas);
    }

    private void mostarWarningCampoRequerido() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Advertencia de Búsqueda");
        alert.setHeaderText("Campo Requerido Vacío");
        alert.setContentText("Por favor, ingresa el valor de busqueda correcto.");
        alert.showAndWait();
    }

    @FXML
    void cancelarCita(ActionEvent event) {
        limpiarCampos();
    }

    @FXML
    void modificarCita(ActionEvent event) {
        limpiarCampos();
        Cita citaAModificar = getDatosCita();
        App.app.setScene(Paths.MODIFICAR_CITAS, citaAModificar);
    }

    private Cita getDatosCita() {
        return tblCitas.getSelectionModel().getSelectedItem();
    }

    @FXML
    void regresarMenuCitas(ActionEvent event) {
        App.app.setScene(Paths.MENU_CITAS);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ddTurno.getItems().addAll(opcionesTurno);
        ddBuscarPor.getItems().addAll(opcionesBuscarPor);

        ddBuscarPor.setOnAction(this::mostrarCampoBusqueda);

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colHorario.setCellValueFactory(new PropertyValueFactory<>("horarioCita"));
        colTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));
        colPaciente.setCellValueFactory(new PropertyValueFactory<>("nombrePaciente"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefonoPaciente"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("emailPaciente"));
        colMotivo.setCellValueFactory(new PropertyValueFactory<>("motivo"));
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracionMinutos"));

        tblCitas.setOnMouseClicked( mouseEvent -> {
            if (tblCitas.getSelectionModel().getSelectedItem() != null) {
                mostrarBotonesModificarCancelar();
            }
        });
    }

    private void mostrarBotonesModificarCancelar() {
        btnModificarCita.setVisible(true);
        btnCancelarCita.setVisible(true);
    }

    private void ocultarCamposBusqueda() {
        lblNombre.setVisible(false);
        lblEmail.setVisible(false);
        lblTelefono.setVisible(false);
        lblTurno.setVisible(false);

        txtNombre.setVisible(false);
        txtEmail.setVisible(false);
        txtTelefono.setVisible(false);
        ddTurno.setVisible(false);
    }

    private void actualizarTabla(ArrayList<Cita> citas) {
        tblCitas.getItems().clear();
        tblCitas.getItems().addAll(citas);
        tblCitas.refresh();
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
    }

    private void mostrarCampoBusqueda(ActionEvent event) {
        String opcion = ddBuscarPor.getValue();

        ocultarCamposBusqueda();
        limpiarCampos();

        switch (opcion) {
            case "Nombre" -> {
                lblNombre.setVisible(true);
                txtNombre.setVisible(true);
            }
            case "Telefono" -> {
                lblTelefono.setVisible(true);
                txtTelefono.setVisible(true);
            }
            case "Email" -> {
                lblEmail.setVisible(true);
                txtEmail.setVisible(true);
            }
            case "Turno" -> {
                lblTurno.setVisible(true);
                ddTurno.setVisible(true);
            }
        }
    }
}
