package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.VBox;
import com.gestorcitasmedicas.model.Consulta;
import com.gestorcitasmedicas.model.Medico;
import com.gestorcitasmedicas.model.Paciente;

import java.io.IOException;
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
    private int pacienteId; // ID del paciente logueado
    private Stage ventanaActual; // Ventana actual para navegación
    
    @FXML
    private void initialize() {
        configurarTabla();
        configurarEventos();
        // No cargar datos aquí, esperar a que se establezca el pacienteId
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
    
    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
        System.out.println("SeleccionarCitaController - setPacienteId llamado con valor: " + this.pacienteId);
        
        // Cargar datos después de establecer el pacienteId
        cargarDatos();
    }
    
    public void setVentanaActual(Stage ventanaActual) {
        this.ventanaActual = ventanaActual;
    }
    
    private void configurarTabla() {
        // Configurar columnas
        colFecha.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getFecha().toString()));
        
        colHora.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getHora().toString()));
        
        colPaciente.setCellValueFactory(cellData -> 
            new SimpleStringProperty("Paciente " + cellData.getValue().getIdPaciente()));
        
        colMedico.setCellValueFactory(cellData -> {
            Medico medico = Medico.obtenerTodos().stream()
                .filter(m -> m.getId() == cellData.getValue().getIdMedico())
                .findFirst()
                .orElse(null);
            return new SimpleStringProperty(medico != null ? medico.getNombre() + " - " + medico.getEspecialidad() : "Médico " + cellData.getValue().getIdMedico());
        });
        
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
        // Cargar citas simuladas del paciente logueado
        todasLasCitas = FXCollections.observableArrayList();
        
        // Obtener todas las citas y filtrar por el paciente actual
        List<Consulta> todasLasConsultas = Consulta.obtenerTodas();
        
        System.out.println("SeleccionarCitaController - Total de consultas en memoria: " + todasLasConsultas.size());
        System.out.println("SeleccionarCitaController - Paciente ID buscado: " + pacienteId);
        
        for (Consulta cita : todasLasConsultas) {
            System.out.println("SeleccionarCitaController - Revisando cita ID=" + cita.getId() + 
                             ", Paciente=" + cita.getIdPaciente() + 
                             ", Fecha=" + cita.getFecha() + 
                             ", Estado=" + cita.getEstado());
            
            if (cita.getIdPaciente() == pacienteId) {
                System.out.println("SeleccionarCitaController - Cita pertenece al paciente actual");
                
                // Solo mostrar citas programadas para el futuro (no canceladas)
                if ((cita.getFecha().isAfter(LocalDate.now()) || cita.getFecha().equals(LocalDate.now())) 
                    && !cita.getEstado().equals("cancelada")) {
                    System.out.println("SeleccionarCitaController - Cita es futura o actual y no cancelada, agregando a la lista");
                    todasLasCitas.add(cita);
                } else {
                    System.out.println("SeleccionarCitaController - Cita es pasada o cancelada, no se agrega");
                }
            } else {
                System.out.println("SeleccionarCitaController - Cita no pertenece al paciente actual");
            }
        }
        
        System.out.println("SeleccionarCitaController - Total de citas filtradas: " + todasLasCitas.size());
        
        tablaCitas.setItems(todasLasCitas);
        
        // Cargar médicos en el combo (no necesitamos cargar pacientes)
        cargarMedicos();
        
        // Ocultar el combo de pacientes ya que solo mostramos las citas del paciente actual
        if (cboPaciente != null) {
            cboPaciente.setVisible(false);
            cboPaciente.setManaged(false);
        }
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
        
        List<Consulta> citasFiltradas = todasLasCitas.stream()
            .filter(cita -> fecha == null || cita.getFecha().equals(fecha))
            .filter(cita -> {
                if (medico == null) return true;
                Medico medicoCita = Medico.obtenerTodos().stream()
                    .filter(m -> m.getId() == cita.getIdMedico())
                    .findFirst()
                    .orElse(null);
                return medicoCita != null && (medicoCita.getNombre() + " - " + medicoCita.getEspecialidad()).contains(medico.split(" - ")[0]);
            })
            .collect(Collectors.toList());
        
        tablaCitas.setItems(FXCollections.observableArrayList(citasFiltradas));
    }
    
    @FXML
    private void limpiarFiltros() {
        datePicker.setValue(null);
        cboMedico.setValue(null);
        tablaCitas.setItems(todasLasCitas);
    }
    
    private void mostrarInformacionCita(Consulta cita) {
        // Obtener información del médico
        Medico medico = Medico.obtenerTodos().stream()
            .filter(m -> m.getId() == cita.getIdMedico())
            .findFirst()
            .orElse(null);
        
        // Obtener información del paciente
        Paciente paciente = Paciente.obtenerTodos().stream()
            .filter(p -> p.getId() == cita.getIdPaciente())
            .findFirst()
            .orElse(null);
        
        lblInfoFecha.setText(cita.getFecha().toString());
        lblInfoHora.setText(cita.getHora().toString());
        lblInfoPaciente.setText(paciente != null ? paciente.getNombre() : "Paciente " + cita.getIdPaciente());
        lblInfoMedico.setText(medico != null ? medico.getNombre() + " - " + medico.getEspecialidad() : "Médico " + cita.getIdMedico());
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
        try {
            System.out.println("SeleccionarCitaController - Abriendo ventana de cancelar cita para cita ID: " + cita.getId());
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/CancelarCita.fxml"));
            Parent cancelarRoot = loader.load();
            
            // Obtener el controlador y configurar la cita seleccionada
            CancelarCitaController controller = loader.getController();
            controller.setCitaSeleccionada(cita);
            
            // Usar la ventana actual si está disponible, sino crear una nueva
            Stage stage = (ventanaActual != null) ? ventanaActual : new Stage();
            stage.setTitle("Cancelar Cita - Paciente");
            stage.setScene(new Scene(cancelarRoot));
            stage.setResizable(false);
            
            if (ventanaActual == null) {
                stage.showAndWait();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana de cancelar cita", Alert.AlertType.ERROR);
        }
    }
    
    private void abrirReprogramarCita(Consulta cita) {
        try {
            System.out.println("SeleccionarCitaController - Abriendo ventana de reprogramar cita para cita ID: " + cita.getId());
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/ReprogramarCita.fxml"));
            Parent reprogramarRoot = loader.load();
            
            // Obtener el controlador y configurar la cita seleccionada
            ReprogramarCitaController controller = loader.getController();
            controller.setCitaSeleccionada(cita);
            
            // Usar la ventana actual si está disponible, sino crear una nueva
            Stage stage = (ventanaActual != null) ? ventanaActual : new Stage();
            stage.setTitle("Reprogramar Cita - Paciente");
            stage.setScene(new Scene(reprogramarRoot));
            stage.setResizable(false);
            
            if (ventanaActual == null) {
                stage.showAndWait();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana de reprogramar cita", Alert.AlertType.ERROR);
        }
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
