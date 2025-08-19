package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.VBox;
import com.gestorcitasmedicas.model.Consulta;
import com.gestorcitasmedicas.model.Medico;
import com.gestorcitasmedicas.model.Paciente;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class SeleccionarCitaController {
    
    @FXML
    private Label lblTitulo;
    
    @FXML
    private DatePicker datePicker;
    
    @FXML
    private ComboBox<String> cboMedico;
    
    @FXML
    private ComboBox<String> cboPaciente;
    
    @FXML
    private TableView<Consulta> tablaCitas;
    
    @FXML
    private TableColumn<Consulta, String> colFecha;
    
    @FXML
    private TableColumn<Consulta, String> colHora;
    
    @FXML
    private TableColumn<Consulta, String> colPaciente;
    
    @FXML
    private TableColumn<Consulta, String> colMedico;
    
    @FXML
    private TableColumn<Consulta, String> colMotivo;
    
    @FXML
    private TableColumn<Consulta, String> colEstado;
    
    @FXML
    private VBox panelInfoCita;
    
    @FXML
    private Label lblInfoFecha;
    
    @FXML
    private Label lblInfoHora;
    
    @FXML
    private Label lblInfoPaciente;
    
    @FXML
    private Label lblInfoMedico;
    
    @FXML
    private Label lblInfoMotivo;
    
    @FXML
    private Button btnSeleccionar;
    
    private ObservableList<Consulta> todasLasCitas;
    private Consulta citaSeleccionada;
    private String accion; // "cancelar" o "reprogramar"
    
    @FXML
    private void initialize() {
        configurarTabla();
        cargarDatos();
        configurarEventos();
    }
    
    public void setAccion(String accion) {
        this.accion = accion;
        if ("cancelar".equals(accion)) {
            lblTitulo.setText("Seleccionar Cita para Cancelar");
            btnSeleccionar.setText("Cancelar Cita");
        } else if ("reprogramar".equals(accion)) {
            lblTitulo.setText("Seleccionar Cita para Reprogramar");
            btnSeleccionar.setText("Reprogramar Cita");
        }
    }
    
    private void configurarTabla() {
        // Configurar columnas
        colFecha.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getFecha().toString()));
        
        colHora.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getHora().toString()));
        
        colPaciente.setCellValueFactory(cellData -> 
            new SimpleStringProperty("Paciente " + cellData.getValue().getIdPaciente()));
        
        colMedico.setCellValueFactory(cellData -> 
            new SimpleStringProperty("Médico " + cellData.getValue().getIdMedico()));
        
        colMotivo.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getMotivo()));
        
        colEstado.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getEstado()));
        
        // Configurar selección
        tablaCitas.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    citaSeleccionada = newValue;
                    mostrarInformacionCita(newValue);
                    btnSeleccionar.setDisable(false);
                } else {
                    citaSeleccionada = null;
                    ocultarInformacionCita();
                    btnSeleccionar.setDisable(true);
                }
            }
        );
    }
    
    private void cargarDatos() {
        // Cargar citas simuladas
        todasLasCitas = FXCollections.observableArrayList();
        
        // Agregar citas de ejemplo
        Consulta cita1 = new Consulta(1, 1, LocalDate.now().plusDays(1), LocalTime.of(9, 0), "Consulta general");
        cita1.setEstado("Programada");
        todasLasCitas.add(cita1);
        
        Consulta cita2 = new Consulta(2, 2, LocalDate.now().plusDays(2), LocalTime.of(10, 30), "Revisión");
        cita2.setEstado("Programada");
        todasLasCitas.add(cita2);
        
        Consulta cita3 = new Consulta(3, 3, LocalDate.now().plusDays(3), LocalTime.of(14, 0), "Control");
        cita3.setEstado("Programada");
        todasLasCitas.add(cita3);
        
        tablaCitas.setItems(todasLasCitas);
        
        // Cargar médicos y pacientes en los combos
        cargarMedicos();
        cargarPacientes();
    }
    
    private void cargarMedicos() {
        List<Medico> medicos = Medico.obtenerTodos();
        ObservableList<String> nombresMedicos = FXCollections.observableArrayList();
        
        for (Medico medico : medicos) {
            nombresMedicos.add(medico.getNombre() + " - " + medico.getEspecialidad());
        }
        
        cboMedico.setItems(nombresMedicos);
    }
    
    private void cargarPacientes() {
        List<Paciente> pacientes = Paciente.obtenerTodos();
        ObservableList<String> nombresPacientes = FXCollections.observableArrayList();
        
        for (Paciente paciente : pacientes) {
            nombresPacientes.add(paciente.getNombre());
        }
        
        cboPaciente.setItems(nombresPacientes);
    }
    
    private void configurarEventos() {
        // Configurar fecha por defecto
        datePicker.setValue(LocalDate.now());
    }
    
    @FXML
    private void filtrarCitas() {
        LocalDate fecha = datePicker.getValue();
        String medico = cboMedico.getValue();
        String paciente = cboPaciente.getValue();
        
        List<Consulta> citasFiltradas = todasLasCitas.stream()
            .filter(cita -> fecha == null || cita.getFecha().equals(fecha))
            .filter(cita -> medico == null || ("Médico " + cita.getIdMedico()).contains(medico.split(" - ")[0]))
            .filter(cita -> paciente == null || ("Paciente " + cita.getIdPaciente()).equals(paciente))
            .collect(Collectors.toList());
        
        tablaCitas.setItems(FXCollections.observableArrayList(citasFiltradas));
    }
    
    @FXML
    private void limpiarFiltros() {
        datePicker.setValue(null);
        cboMedico.setValue(null);
        cboPaciente.setValue(null);
        tablaCitas.setItems(todasLasCitas);
    }
    
    private void mostrarInformacionCita(Consulta cita) {
        lblInfoFecha.setText(cita.getFecha().toString());
        lblInfoHora.setText(cita.getHora().toString());
        lblInfoPaciente.setText("Paciente " + cita.getIdPaciente());
        lblInfoMedico.setText("Médico " + cita.getIdMedico());
        lblInfoMotivo.setText(cita.getMotivo());
        
        panelInfoCita.setVisible(true);
        panelInfoCita.setManaged(true);
    }
    
    private void ocultarInformacionCita() {
        panelInfoCita.setVisible(false);
        panelInfoCita.setManaged(false);
    }
    
    @FXML
    private void seleccionarCita() {
        if (citaSeleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una cita", Alert.AlertType.ERROR);
            return;
        }
        
        // Aquí se abriría la ventana correspondiente según la acción
        if ("cancelar".equals(accion)) {
            abrirCancelarCita(citaSeleccionada);
        } else if ("reprogramar".equals(accion)) {
            abrirReprogramarCita(citaSeleccionada);
        }
        
        cerrarVentana();
    }
    
    private void abrirCancelarCita(Consulta cita) {
        // TODO: Implementar apertura de ventana de cancelar cita
        mostrarAlerta("Información", "Abriendo ventana de cancelar cita para: Paciente " + cita.getIdPaciente(), Alert.AlertType.INFORMATION);
    }
    
    private void abrirReprogramarCita(Consulta cita) {
        // TODO: Implementar apertura de ventana de reprogramar cita
        mostrarAlerta("Información", "Abriendo ventana de reprogramar cita para: Paciente " + cita.getIdPaciente(), Alert.AlertType.INFORMATION);
    }
    
    @FXML
    private void cancelar() {
        cerrarVentana();
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    private void cerrarVentana() {
        Stage stage = (Stage) tablaCitas.getScene().getWindow();
        stage.close();
    }
}
