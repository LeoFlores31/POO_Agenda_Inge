package controllers;

import application.App;
import dao.GestorPacientesDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.GestorPacientes;
import model.Paciente;
import utils.Alertas;
import utils.Paths;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MostrarPacientesController implements Initializable {

    private GestorPacientes gestorPacientes;
    private GestorPacientesDAO gestorPacientesDAO;
    private Paciente pacienteTemp = null;

    public void setDependencies(GestorPacientes gestorPacientes, GestorPacientesDAO gestorPacientesDAO) {
        this.gestorPacientes = gestorPacientes;
        this.gestorPacientesDAO = gestorPacientesDAO;
        actualizarTabla(this.gestorPacientes.getListaPacientes());
    }

    @FXML
    private Button btnBuscarPaciente;

    @FXML
    private Button btnCrearPaciente;

    @FXML
    private Button btnEliminarPaciente;

    @FXML
    private Button btnModificarPaciente;

    @FXML
    private Button btnRegresar;

    @FXML
    private TableColumn<Paciente, String> colEmail;

    @FXML
    private TableColumn<Paciente, String> colID;

    @FXML
    private TableColumn<Paciente, String> colNombre;

    @FXML
    private TableColumn<Paciente, String> colTelefono;

    @FXML
    private ChoiceBox<String> ddBuscarPor;
    private final String[] opcionesBuscarPor = {"Mostar todo", "ID", "Nombre", "Telefono", "Email"};

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblID;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblTelefono;

    @FXML
    private Label lblEmailBuscar;

    @FXML
    private Label lblIDBuscar;

    @FXML
    private Label lblNombreBuscar;

    @FXML
    private Label lblTelefonoBuscar;

    @FXML
    private TableView<Paciente> tblPacientes;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEmailBuscar;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtIDBuscar;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtNombreBuscar;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtTelefonoBuscar;

    private void actualizarTabla(ArrayList<Paciente> pacientes) {
        tblPacientes.getItems().clear();
        tblPacientes.getItems().addAll(pacientes);
        tblPacientes.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ddBuscarPor.getItems().addAll(opcionesBuscarPor);
        ddBuscarPor.setOnAction(this::mostrarCamposBusqueda);

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tblPacientes.setOnMouseClicked( mouseEvent -> {
            if (tblPacientes.getSelectionModel().getSelectedItem() != null) {
                pacienteTemp = getDatosPaciente();
                mostrarBotonesModificarEliminar();
                mostrarCamposModificarPaciente();
                habilitarCamposModificar();
                cargarDatosModificarPaciente(pacienteTemp);
            }
        });
    }

    private Paciente getDatosPaciente() {
        return tblPacientes.getSelectionModel().getSelectedItem();
    }

    private void mostrarBotonesModificarEliminar() {
        btnModificarPaciente.setVisible(true);
        btnEliminarPaciente.setVisible(true);
    }

    private void ocultarBotonesModificarEliminar() {
        btnModificarPaciente.setVisible(false);
        btnEliminarPaciente.setVisible(false);
    }

    private void mostrarCamposModificarPaciente() {
        lblID.setVisible(true);
        lblNombre.setVisible(true);
        lblEmail.setVisible(true);
        lblTelefono.setVisible(true);

        txtID.setVisible(true);
        txtNombre.setVisible(true);
        txtEmail.setVisible(true);
        txtTelefono.setVisible(true);
    }

    private void cargarDatosModificarPaciente(Paciente pacienteTemp) {
        txtID.setText(pacienteTemp.getId());
        txtNombre.setText(pacienteTemp.getNombre());
        txtEmail.setText(pacienteTemp.getEmail());
        txtTelefono.setText(pacienteTemp.getTelefono());
    }

    private void ocultarCamposModificarPaciente() {
        lblID.setVisible(false);
        lblNombre.setVisible(false);
        lblEmail.setVisible(false);
        lblTelefono.setVisible(false);

        txtID.setVisible(false);
        txtNombre.setVisible(false);
        txtEmail.setVisible(false);
        txtTelefono.setVisible(false);
    }

    private void mostrarCamposBusqueda(ActionEvent event) {
        String opcion = ddBuscarPor.getValue();

        ocultarCamposBusqueda();
        limpiarCamposBusqueda();

        switch (opcion) {
            case "ID" -> {
                lblIDBuscar.setVisible(true);
                txtIDBuscar.setVisible(true);
            }
            case "Nombre" -> {
                lblNombreBuscar.setVisible(true);
                txtNombreBuscar.setVisible(true);
            }
            case "Telefono" -> {
                lblTelefonoBuscar.setVisible(true);
                txtTelefonoBuscar.setVisible(true);
            }
            case "Email" -> {
                lblEmailBuscar.setVisible(true);
                txtEmailBuscar.setVisible(true);
            }
        }
    }

    private void limpiarCamposBusqueda() {
        txtIDBuscar.setText("");
        txtNombreBuscar.setText("");
        txtTelefonoBuscar.setText("");
        txtEmailBuscar.setText("");
    }

    private void limpiarCamposModificar() {
        txtID.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
    }

    private void deshabilitarCamposModificar() {
        txtNombre.setDisable(true);
        txtTelefono.setDisable(true);
        txtEmail.setDisable(true);
    }

    private void habilitarCamposModificar() {
        txtNombre.setDisable(false);
        txtTelefono.setDisable(false);
        txtEmail.setDisable(false);
    }

    private void ocultarCamposBusqueda() {
        lblIDBuscar.setVisible(false);
        lblNombreBuscar.setVisible(false);
        lblEmailBuscar.setVisible(false);
        lblTelefonoBuscar.setVisible(false);

        txtIDBuscar.setVisible(false);
        txtNombreBuscar.setVisible(false);
        txtEmailBuscar.setVisible(false);
        txtTelefonoBuscar.setVisible(false);
    }

    @FXML
    void buscarPaciente(ActionEvent event) {
        ArrayList<Paciente> pacientes = new ArrayList<>();

        if (ddBuscarPor.getValue() == null) {
            Alertas.mostarWarning("Campo Requerido Vacío", "Por favor, ingresa el valor de busqueda correcto.");
            return;
        }

        if (ddBuscarPor.getValue().equals("Mostar todo")) {
            pacientes = gestorPacientes.getListaPacientes();

        } else if (ddBuscarPor.getValue().equals("Nombre")) {
            String nombreBuscado = txtNombreBuscar.getText().trim();

            if (nombreBuscado.isEmpty()) {
                Alertas.mostarWarning("Campo Requerido Vacío", "Por favor, ingresa el valor de busqueda correcto.");
                return;
            } else {
                pacientes = gestorPacientes.getPacientesPorNombre(nombreBuscado);
            }

        } else if (ddBuscarPor.getValue().equals("Telefono")) {
            String telefonoBuscado = txtTelefonoBuscar.getText().trim();

            if (telefonoBuscado.isEmpty()) {
                Alertas.mostarWarning("Campo Requerido Vacío", "Por favor, ingresa el valor de busqueda correcto.");
                return;
            } else {
                pacientes = gestorPacientes.getPacientesPorTelefono(telefonoBuscado);
            }

        } else if (ddBuscarPor.getValue().equals("Email")) {
            String emailBuscado = txtEmailBuscar.getText().trim();

            if (emailBuscado.isEmpty()) {
                Alertas.mostarWarning("Campo Requerido Vacío", "Por favor, ingresa el valor de busqueda correcto.");
                return;
            } else {
                pacientes = gestorPacientes.getPacientesPorEmail(emailBuscado);
            }

        } else if (ddBuscarPor.getValue().equals("ID")) {
            String idBuscado = txtIDBuscar.getText().trim();

            if (idBuscado.isEmpty()) {
                Alertas.mostarWarning("Campo Requerido Vacío", "Por favor, ingresa el valor de busqueda correcto.");
                return;
            } else {
                pacientes = gestorPacientes.getPacientesPorID(idBuscado);
            }
        }

        ocultarBotonesModificarEliminar();
        limpiarCamposModificar();
        mostrarCamposModificarPaciente();
        deshabilitarCamposModificar();
        actualizarTabla(pacientes);
    }

    @FXML
    void eliminarPaciente(ActionEvent event) {

    }

    @FXML
    void irACrearPaciente(ActionEvent event) {
        App.app.setScene(Paths.CREAR_PACIENTE);
    }

    @FXML
    void modificarPaciente(ActionEvent event) {

    }

    @FXML
    void regresarMenuPacientes(ActionEvent event) {
        App.app.setScene(Paths.MENU_PACIENTES);
    }

}
