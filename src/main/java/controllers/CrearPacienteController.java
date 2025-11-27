package controllers;

import application.App;
import dao.GestorPacientesDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.GestorPacientes;
import model.Paciente;
import utils.Alertas;
import utils.Paths;

import java.net.URL;
import java.util.ResourceBundle;

public class CrearPacienteController implements Initializable {

    private GestorPacientes gestorPacientes;
    private GestorPacientesDAO gestorPacientesDAO;

    public void setDependencies(GestorPacientes gestorPacientes, GestorPacientesDAO gestorPacientesDAO) {
        this.gestorPacientes = gestorPacientes;
        this.gestorPacientesDAO = gestorPacientesDAO;
    }

    @FXML
    private Button btnCrearPaciente;

    @FXML
    private Button btnRegresar;

    @FXML
    private Label lblErrorEmail;

    @FXML
    private Label lblEmailValido;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @FXML
    void crearPaciente(ActionEvent event) {
        if (!validarCampos()) return;

        String email = txtEmail.getText().trim();

        if (gestorPacientes.existeEmail(email)){
            Alertas.mostarWarning("Correo Invalido", "El correo " + email + " ya esta registrado. Intenta de nuevo");
        } else if (gestorPacientes.validarFormatoEmail(email)) {
            Paciente paciente = new Paciente();
            paciente.setNombre(txtNombre.getText().trim());
            paciente.setTelefono(txtTelefono.getText().trim());
            paciente.setEmail(txtEmail.getText().trim());

            gestorPacientes.agregarPaciente(paciente);
            gestorPacientesDAO.guardarPaciente(gestorPacientes.getListaPacientes());
            Alertas.mostarSuccess("Operacion exitosa.", "El paciente se creo correcetamente.");
            limpiarCampos();
        } else {
            Alertas.mostarWarning("Correo Invalido", "El correo " + email + " no tiene el formato correcto.");
        }
    }

    @FXML
    void regresar(ActionEvent event) {
        App.app.setScene(Paths.MENU_PACIENTES);
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            Alertas.mostarWarning("El campo Nombre del paciente no puede estar vacío.", "Campo Vacío");
            return false;
        }

        if (txtTelefono.getText().trim().isEmpty()) {
            Alertas.mostarWarning("El campo Telefono del paciente no puede estar vacío.", "Campo Vacío");
            return false;
        }

        if (txtEmail.getText().trim().isEmpty()) {
            Alertas.mostarWarning("El campo Email del paciente no puede estar vacío.", "Campo Vacío");
            return false;
        }

        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtEmail.textProperty().addListener((obs, oldValue, newValue) -> {
            String emailActual = newValue != null ? newValue.trim() : "";

            if (emailActual.isEmpty()) {
                lblErrorEmail.setVisible(false);
                lblEmailValido.setVisible(false);

            } else if (gestorPacientes.existeEmail(emailActual)) {
                lblErrorEmail.setText("❌ El email ya está registrado.");
                lblErrorEmail.setVisible(true);
                lblEmailValido.setVisible(false);

            } else if (gestorPacientes.validarFormatoEmail(emailActual)) {
                lblEmailValido.setText("✅ Email válido.");
                lblEmailValido.setVisible(true);
                lblErrorEmail.setVisible(false);
            } else {
                lblErrorEmail.setText("⚠️ formato invalido.");
                lblErrorEmail.setVisible(true);
                lblEmailValido.setVisible(false);
            }
        });
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
    }

}
