package controllers;

import application.App;
import dao.AgendaDAO;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Agenda;
import model.GestorPacientes;
import model.Paciente;
import model.cita.Cita;

import javafx.fxml.FXML;
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

public class ModificarCitaController implements Initializable {

    private Cita citaAModificar;
    private Agenda agenda;
    private AgendaDAO agendaDAO;
    private GestorPacientes gestorPacientes;
    private ArrayList<String> opcionesMotivos;
    private int citaID;

    public void setDependencies(Agenda agenda, GestorPacientes gestorPacientes, Cita citaAModificar, AgendaDAO agendaDAO) {
        this.agenda = agenda;
        this.gestorPacientes = gestorPacientes;
        this.citaAModificar = citaAModificar;
        this.agendaDAO = agendaDAO;
        cargarCampos(citaAModificar);
        actualizarTablaCita(this.agenda.getCitas());
    }

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnMostrarCitas;

    @FXML
    private Button btnMostrarPacientes;

    @FXML
    private Button btnRegresar;

    @FXML
    private TableColumn<Cita, Integer> colCitaDuracion;

    @FXML
    private TableColumn<Cita, String> colCitaEmail;

    @FXML
    private TableColumn<Cita, LocalDate> colCitaFecha;

    @FXML
    private TableColumn<Cita, String> colCitaHorario;

    @FXML
    private TableColumn<Cita, Integer> colCitaID;

    @FXML
    private TableColumn<Cita, String> colCitaMotivo;

    @FXML
    private TableColumn<Cita, Paciente> colCitaPaciente;

    @FXML
    private TableColumn<Cita, String> colCitaTelefono;

    @FXML
    private TableColumn<Cita, String> colCitaTurno;

    @FXML
    private TableColumn<Paciente, String> colPacienteEmail;

    @FXML
    private TableColumn<Paciente, String> colPacienteID;

    @FXML
    private TableColumn<Paciente, String> colPacienteNombre;

    @FXML
    private TableColumn<Paciente, String> colPacienteTelefono;

    @FXML
    private TableView<Cita> tblCitas;

    @FXML
    private TableView<Paciente> tblPacientes;

    @FXML
    private TextField txtNombre;

    @FXML
    private DatePicker pickDate;

    @FXML
    private ComboBox<Integer> cbxHora;

    @FXML
    private ComboBox<Integer> cbxMinutos;

    @FXML
    private ChoiceBox<String> ddMotivo;

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

        return nuevaCita.getMotivosDisponibles();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbxHora.getItems().addAll(java.util.stream.IntStream.rangeClosed(0, 23).boxed().toList());
        cbxMinutos.getItems().addAll(0, 15, 30, 45);

        ddMotivo.getItems().addAll(setOpcionesMotivos());

        colCitaID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCitaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colCitaHorario.setCellValueFactory(new PropertyValueFactory<>("horarioCita"));
        colCitaTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));
        colCitaPaciente.setCellValueFactory(new PropertyValueFactory<>("nombrePaciente"));
        colCitaTelefono.setCellValueFactory(new PropertyValueFactory<>("telefonoPaciente"));
        colCitaEmail.setCellValueFactory(new PropertyValueFactory<>("emailPaciente"));
        colCitaMotivo.setCellValueFactory(new PropertyValueFactory<>("motivo"));
        colCitaDuracion.setCellValueFactory(new PropertyValueFactory<>("duracionMinutos"));

        colPacienteID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPacienteNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPacienteTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colPacienteEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tblCitas.setOnMouseClicked( mouseEvent -> {
            if (tblCitas.getSelectionModel().getSelectedItem() != null) {
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
        Cita cita = tblCitas.getSelectionModel().getSelectedItem();
        citaID = cita.getId();
        txtNombre.setText(cita.getNombrePaciente());
        pickDate.setValue(cita.getFecha());
        cbxHora.setValue(cita.getHora().getHour());
        cbxMinutos.setValue(cita.getHora().getMinute());
        ddMotivo.setValue(cita.getMotivo());
    }

    public void cargarCampos(Cita cita) {
        citaID = cita.getId();
        txtNombre.setText(cita.getNombrePaciente());
        pickDate.setValue(cita.getFecha());
        cbxHora.setValue(cita.getHora().getHour());
        cbxMinutos.setValue(cita.getHora().getMinute());
        ddMotivo.setValue(cita.getMotivo());
    }


    @FXML
    void actualizarCita(ActionEvent event) {
        if (!validarCamposParaActualizar()) return;

        int nuevoIdMotivo = ddMotivo.getSelectionModel().getSelectedIndex() + 1;
        Cita citaOriginal = agenda.getCitaPorId(citaID);
        if (citaOriginal == null) {
            Alertas.mostarWarning("Error", "La cita a modificar no fue encontrada en la base de datos.");
            return;
        }

        Paciente nuevoPaciente = validarYObtenerNombrePaciente(txtNombre.getText());
        if (nuevoPaciente == null){
            Alertas.mostarWarning("Nombre Invalido", "Por favor, ingresa un nombre correcto.");
            return;
        }

        Cita citaTemporal = getInstanciaCita(citaOriginal);
        citaTemporal.setPaciente(nuevoPaciente);
        citaTemporal.setFecha(pickDate.getValue());
        citaTemporal.setHora(obtenerNuevaHora());
        citaTemporal.setIdMotivo(nuevoIdMotivo);

        if (agenda.validarDisponibilidadCita(citaTemporal, citaOriginal.getId())){

            String tipoCitaOriginal = citaOriginal.getTipoCita();
            citaOriginal.setPaciente(nuevoPaciente);
            citaOriginal.setFecha(pickDate.getValue());
            citaOriginal.setHora(obtenerNuevaHora());

            if (!tipoCitaOriginal.equals(citaOriginal.getTipoCita())){
                Cita nuevaInstancia = cambiarInstanciaCita(citaOriginal, agenda);
                if (nuevaInstancia != null) {
                    citaOriginal = nuevaInstancia;
                }
            }

            citaOriginal.setIdMotivo(nuevoIdMotivo);

            actualizarTablaCita(this.agenda.getCitas());
            Alertas.mostarSuccess("Actualización Exitosa", "La cita ha sido modificada correctamente.");
        } else {
            Alertas.mostarWarning("No hay disponibilidad", "Por favor, selecciona otro horario.");
        }
    }

    private Cita cambiarInstanciaCita (Cita citaAModificar, Agenda agenda){
        final Cita nuevaCita = getInstanciaCita(citaAModificar);
        if (nuevaCita != null) {
            if (!agenda.reemplazarCita(citaAModificar, nuevaCita)) {
                Alertas.mostarWarning("Error al modificar la Cita", "No se pudo cambiar el tipo de Cita. Por favor, intenta de nuevo.");
                return null;
            } else {
                nuevaCita.setId(citaAModificar.getId());
            }
        }
        return nuevaCita;
    }

    private static Cita getInstanciaCita(Cita citaAModificar) {
        Paciente paciente = citaAModificar.getPaciente();
        LocalDateTime nuevaFechaHora = citaAModificar.getFechaHora();
        int motivoCitaId = citaAModificar.getIdMotivo();

        Cita citaTemp = null;

        if (citaAModificar.getTipoCita().equals("MATUTINA")) {
            citaTemp = new CitaMatutina(paciente, nuevaFechaHora, motivoCitaId);
        } else if (citaAModificar.getTipoCita().equals("VESPERTINA")) {
            citaTemp = new CitaVespertina(paciente, nuevaFechaHora, motivoCitaId);
        }
        return citaTemp;
    }

    private Paciente validarYObtenerNombrePaciente(String nombre) {
        for (Paciente p : gestorPacientes.getListaPacientes()) {
            if (p.getNombre().equalsIgnoreCase(nombre)){
                return p;
            }
        }
        return null;
    }

    @FXML
    void mostrarCitas(ActionEvent event) {
        tblPacientes.setVisible(false);
        tblCitas.setVisible(true);
        actualizarTablaCita(this.agenda.getCitas());
    }

    @FXML
    void mostrarPacientes(ActionEvent event) {
        tblCitas.setVisible(false);
        tblPacientes.setVisible(true);
        actualizarTablaPacientes(this.gestorPacientes.getListaPacientes());
    }

    @FXML
    void regresar(ActionEvent event) {
        App.app.setScene(Paths.MOSTRAR_CITAS);
    }

    public LocalTime obtenerNuevaHora() {
        int hora = cbxHora.getValue() != null ? cbxHora.getValue() : 0;
        int minuto = cbxMinutos.getValue() != null ? cbxMinutos.getValue() : 0;
        return LocalTime.of(hora, minuto);
    }

    private void actualizarTablaCita(ArrayList<Cita> citas) {
        tblCitas.getItems().clear();
        tblCitas.getItems().addAll(citas);
        tblCitas.refresh();
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
    void guardarCita(ActionEvent event) {
        agendaDAO.guardarCitas(agenda.getCitas());
        Alertas.mostarSuccess("Operacion exitosa", "Las citas han sido guardadas correctamente.");
    }

}
