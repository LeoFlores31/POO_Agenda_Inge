package controllers;

import application.App;
import dao.AgendaDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Agenda;
import model.GestorPacientes;
import model.Paciente;
import model.cita.Cita;
import model.cita.CitaMatutina;
import model.cita.CitaVespertina;
import utils.Paths;
import utils.Alertas;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CrearCitaController implements Initializable {

    private Agenda agenda;
    private AgendaDAO agendaDAO;
    private GestorPacientes gestorPacientes;
    private Paciente pacienteCita = null;
    private Cita nuevaCita = null;

    public void setDependencies(Agenda agenda, GestorPacientes gestorPacientes, AgendaDAO agendaDAO) {
        this.agenda = agenda;
        this.gestorPacientes = gestorPacientes;
        this.agendaDAO = agendaDAO;
        actualizarTablaPacientes(this.gestorPacientes.getListaPacientes());
    }

    @FXML
    private Button btnCrearCita;

    @FXML
    private Button btnIrACitas;

    @FXML
    private Button btnMostrarPacientes;

    @FXML
    private Button btnRegresar;

    @FXML
    private ComboBox<Integer> cbxHora;

    @FXML
    private ComboBox<Integer> cbxMinutos;

    @FXML
    private TableColumn<Paciente, String> colPacienteEmail;

    @FXML
    private TableColumn<Paciente, String> colPacienteID;

    @FXML
    private TableColumn<Paciente, String> colPacienteNombre;

    @FXML
    private TableColumn<Paciente, String> colPacienteTelefono;

    @FXML
    private ChoiceBox<String> ddMotivo;

    @FXML
    private DatePicker pickDate;

    @FXML
    private TableView<Paciente> tblPacientes;

    @FXML
    private TextField txtNombre;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbxHora.getItems().addAll(java.util.stream.IntStream.rangeClosed(7, 23).boxed().toList());
        cbxMinutos.getItems().addAll(0, 15, 30, 45);

        ddMotivo.getItems().addAll(setOpcionesMotivos());

        colPacienteID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPacienteNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPacienteTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colPacienteEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tblPacientes.setOnMouseClicked( mouseEvent -> {
            if (tblPacientes.getSelectionModel().getSelectedItem() != null) {
                cargarCampos();
            }
        });

        cbxHora.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                ddMotivo.getItems().clear();
                ddMotivo.getItems().addAll(setOpcionesMotivos());
            }
        });
    }

    public void cargarCampos() {
        Paciente paciente = tblPacientes.getSelectionModel().getSelectedItem();
        pacienteCita = paciente;
        txtNombre.setText(paciente.getNombre());
    }

    private ArrayList<String> setOpcionesMotivos() {
        LocalTime nuevaHora = obtenerNuevaHora();

        LocalTime horaCambioTurno = LocalTime.of(12, 0);

        boolean citaMatutina = false;
        boolean citaVespertina = false;

        if (nuevaHora.isBefore(horaCambioTurno)) {
            citaMatutina = true;
        } else {
            citaVespertina = true;
        }
        Cita nuevaCita = null;
        if (citaMatutina) nuevaCita = new CitaMatutina();
        if (citaVespertina) nuevaCita = new CitaVespertina();

        this.nuevaCita = nuevaCita;

        return this.nuevaCita.getMotivosDisponibles();
    }

    public LocalTime obtenerNuevaHora() {
        int hora = cbxHora.getValue() != null ? cbxHora.getValue() : 0;
        int minuto = cbxMinutos.getValue() != null ? cbxMinutos.getValue() : 0;
        return LocalTime.of(hora, minuto);
    }

    private void actualizarTablaPacientes(ArrayList<Paciente> pacientes) {
        tblPacientes.getItems().clear();
        tblPacientes.getItems().addAll(pacientes);
        tblPacientes.refresh();
    }

    private boolean validarCamposParaActualizar() {
        if (txtNombre.getText().trim().isEmpty()) {
            Alertas.mostarWarning("El campo Nombre del paciente no puede estar vacío.", "Campo Vacío");
            return false;
        }

        if (pickDate.getValue() == null) {
            Alertas.mostarWarning("Debes seleccionar una Fecha válida.", "Selección Incompleta");
            return false;
        }

        if (cbxHora.getValue() == null) {
            Alertas.mostarWarning("Debes seleccionar la Hora de inicio de la cita.", "Selección Incompleta");
            return false;
        }

        if (cbxMinutos.getValue() == null) {
            Alertas.mostarWarning("Debes seleccionar los Minutos de inicio de la cita.", "Selección Incompleta");
            return false;
        }

        if (ddMotivo.getValue() == null) {
            Alertas.mostarWarning("Debes seleccionar el Motivo de la cita.", "Selección Incompleta");
            return false;
        }

        return true;
    }


    @FXML
    void crearCita(ActionEvent event) {
        if (!validarCamposParaActualizar()) return;

        if (pacienteCita == null) {
            Alertas.mostarWarning("Paciente Incorrecto.", "Por favor, selecciona un paciente de la lista.");
            return;
        }

        nuevaCita.setPaciente(pacienteCita);

        LocalDate fecha = pickDate.getValue();
        LocalTime hora = obtenerNuevaHora();
        nuevaCita.setFechaHora(LocalDateTime.of(fecha, hora));

        int nuevoIdMotivo = ddMotivo.getSelectionModel().getSelectedIndex() + 1;
        nuevaCita.setIdMotivo(nuevoIdMotivo);

        if (agenda.agendarCita(nuevaCita)) {
            Alertas.mostarSuccess("Actualización Exitosa", "La cita ha sido creada correctamente.");
            agendaDAO.guardarCitas(agenda.getCitas());
            btnIrACitas.setVisible(true);
            limpiarCampos();
        } else {
            Alertas.mostarError("Error Inesperado.", "Se produjo un error al crear la cita. Intenta de nuevo.");
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        pickDate.setValue(null);
        cbxHora.setValue(null);
        cbxMinutos.setValue(null);
        ddMotivo.setValue(null);
    }

    @FXML
    void irAVerCitas(ActionEvent event) {
        App.app.setScene(Paths.MOSTRAR_CITAS);
    }

    @FXML
    void regresar(ActionEvent event) {
        App.app.setScene(Paths.MENU_CITAS);
    }

}
