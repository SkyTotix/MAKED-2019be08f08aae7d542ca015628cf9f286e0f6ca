package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExpedientesDoctorController {

    @FXML
    private TableView<Expediente> tablaExpedientes;
    
    @FXML
    private TableColumn<Expediente, String> colFolio;
    
    @FXML
    private TableColumn<Expediente, String> colFecha;
    
    @FXML
    private TableColumn<Expediente, String> colPaciente;
    
    @FXML
    private TableColumn<Expediente, String> colCurp;
    
    @FXML
    private TableColumn<Expediente, String> colDiagnostico;
    
    @FXML
    private TableColumn<Expediente, String> colTratamiento;
    
    @FXML
    private TableColumn<Expediente, String> colMotivo;
    
    @FXML
    private Button btnVisualizar;
    
    @FXML
    private TextField txtBuscar;
    
    @FXML
    private Button btnPerfil;
    
    @FXML
    private Button btnCitas;
    
    @FXML
    private Button btnExpedientes;
    
    @FXML
    private Button btnSalir;
    
    @FXML
    private Button btnRegresarMenu;

    // Datos simulados de expedientes
    private ObservableList<Expediente> todosLosExpedientes;
    private ObservableList<Expediente> expedientesFiltrados;

    @FXML
    private void initialize() {
        System.out.println("ExpedientesDoctorController inicializando...");
        
        // Configurar la tabla de expedientes
        configurarTablaExpedientes();
        
        // Cargar datos simulados
        cargarTodosLosExpedientes();
        
        // Configurar búsqueda
        configurarBusqueda();
        
        System.out.println("ExpedientesDoctorController inicializado correctamente");
    }
    
    private void configurarTablaExpedientes() {
        // Configurar las columnas de la tabla
        colFolio.setCellValueFactory(new PropertyValueFactory<>("folio"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colPaciente.setCellValueFactory(new PropertyValueFactory<>("paciente"));
        colCurp.setCellValueFactory(new PropertyValueFactory<>("curp"));
        colDiagnostico.setCellValueFactory(new PropertyValueFactory<>("diagnostico"));
        colTratamiento.setCellValueFactory(new PropertyValueFactory<>("tratamiento"));
        colMotivo.setCellValueFactory(new PropertyValueFactory<>("motivo"));
        
        // Configurar la tabla para permitir selección
        tablaExpedientes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        // Agregar evento de doble click para visualizar expediente
        tablaExpedientes.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Expediente expedienteSeleccionado = tablaExpedientes.getSelectionModel().getSelectedItem();
                if (expedienteSeleccionado != null) {
                    visualizarExpedienteCompleto(expedienteSeleccionado);
                }
            }
        });
    }
    
    private void cargarTodosLosExpedientes() {
        // Crear datos simulados de expedientes médicos
        todosLosExpedientes = FXCollections.observableArrayList();
        
        // Expedientes de diferentes pacientes
        todosLosExpedientes.add(new Expediente("FOL-2024-001", "15/01/2024", "María González López", "GONL850315MDFXXX01", 
            "Migraña con aura", "Sumatriptán 50mg cada 8 horas por 3 días", "Dolor de cabeza intenso"));
        
        todosLosExpedientes.add(new Expediente("FOL-2024-002", "18/01/2024", "Juan Pérez Martínez", "PEMJ920410HDFXXX02", 
            "Gastritis aguda", "Omeprazol 20mg cada 12 horas por 7 días", "Dolor abdominal y náuseas"));
        
        todosLosExpedientes.add(new Expediente("FOL-2024-003", "20/01/2024", "Ana Rodríguez Silva", "ROSA880625MDFXXX03", 
            "Hipertensión arterial", "Losartán 50mg diario, control de presión", "Control de presión arterial"));
        
        todosLosExpedientes.add(new Expediente("FOL-2024-004", "22/01/2024", "Carlos Mendoza Vega", "MEVC870812HDFXXX04", 
            "Artritis reumatoide", "Metotrexato 10mg semanal, fisioterapia", "Dolor en las articulaciones"));
        
        todosLosExpedientes.add(new Expediente("FOL-2024-005", "25/01/2024", "Laura Torres Jiménez", "TOJL910315MDFXXX05", 
            "Diabetes tipo 2", "Metformina 500mg cada 12 horas", "Control de diabetes"));
        
        todosLosExpedientes.add(new Expediente("FOL-2024-006", "28/01/2024", "Roberto Sánchez Díaz", "SADR890720HDFXXX06", 
            "Faringitis aguda", "Amoxicilina 500mg cada 8 horas por 7 días", "Dolor de garganta y fiebre"));
        
        todosLosExpedientes.add(new Expediente("FOL-2024-007", "30/01/2024", "Carmen Vega Ruiz", "VARC860425MDFXXX07", 
            "Depresión moderada", "Sertralina 50mg diario, terapia psicológica", "Estado de ánimo bajo"));
        
        todosLosExpedientes.add(new Expediente("FOL-2024-008", "02/02/2024", "Miguel Ángel Flores", "FLAM840630HDFXXX08", 
            "Lumbalgia crónica", "Ibuprofeno 400mg cada 8 horas, ejercicios", "Dolor de espalda baja"));
        
        todosLosExpedientes.add(new Expediente("FOL-2024-009", "05/02/2024", "Patricia Morales", "MORP920815MDFXXX09", 
            "Control post-operatorio", "Antibióticos profilácticos, curación", "Revisión post-cirugía"));
        
        todosLosExpedientes.add(new Expediente("FOL-2024-010", "08/02/2024", "Fernando Castro", "CAFJ881225HDFXXX10", 
            "Alergias estacionales", "Loratadina 10mg diario, evitar alérgenos", "Estornudos y congestión"));
        
        // Asignar los datos a la tabla
        expedientesFiltrados = FXCollections.observableArrayList(todosLosExpedientes);
        tablaExpedientes.setItems(expedientesFiltrados);
        
        System.out.println("Todos los expedientes cargados: " + todosLosExpedientes.size() + " expedientes");
    }
    
    private void configurarBusqueda() {
        // La búsqueda se activa con el evento onKeyReleased en el FXML
        txtBuscar.setPromptText("🔍 Buscar por CURP o FOLIO");
    }
    
    @FXML
    private void buscarExpediente() {
        String terminoBusqueda = txtBuscar.getText().toLowerCase().trim();
        
        if (terminoBusqueda.isEmpty()) {
            // Si no hay término de búsqueda, mostrar todos
            expedientesFiltrados.clear();
            expedientesFiltrados.addAll(todosLosExpedientes);
        } else {
            // Filtrar expedientes
            expedientesFiltrados.clear();
            for (Expediente expediente : todosLosExpedientes) {
                if (expediente.getCurp().toLowerCase().contains(terminoBusqueda) ||
                    expediente.getFolio().toLowerCase().contains(terminoBusqueda) ||
                    expediente.getPaciente().toLowerCase().contains(terminoBusqueda)) {
                    expedientesFiltrados.add(expediente);
                }
            }
        }
        
        System.out.println("Búsqueda realizada. Resultados: " + expedientesFiltrados.size() + " expedientes");
    }
    
    @FXML
    private void visualizarExpediente(ActionEvent event) {
        Expediente expedienteSeleccionado = tablaExpedientes.getSelectionModel().getSelectedItem();
        
        if (expedienteSeleccionado == null) {
            mostrarAlerta("Advertencia", "Por favor seleccione un expediente para visualizar", Alert.AlertType.WARNING);
            return;
        }
        
        visualizarExpedienteCompleto(expedienteSeleccionado);
    }
    
    private void visualizarExpedienteCompleto(Expediente expediente) {
        System.out.println("Visualizando expediente: " + expediente.getFolio());
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/consulta.fxml"));
            Parent consultaRoot = loader.load();
            
            // Pasar la información del expediente al controlador de consulta
            ConsultaController consultaController = loader.getController();
            consultaController.cargarDatosCita(expediente.getPaciente(), expediente.getMotivo());
            
            // Aquí se podrían pasar más datos del expediente si fuera necesario
            // consultaController.cargarDatosExpediente(expediente);
            
            Scene nuevaEscena = new Scene(consultaRoot, 1200, 800);
            Stage currentStage = (Stage) btnVisualizar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Expediente Médico - " + expediente.getPaciente() + " (" + expediente.getFolio() + ")");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el expediente médico", Alert.AlertType.ERROR);
        }
    }
    
    // Métodos para el menú lateral
    @FXML
    private void abrirPerfil(ActionEvent event) {
        try {
            System.out.println("Abriendo perfil del médico...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/perfilDocMod.fxml"));
            Parent perfilRoot = loader.load();
            
            Scene nuevaEscena = new Scene(perfilRoot, 1200, 800);
            Stage currentStage = (Stage) btnPerfil.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Mi Perfil - Médico");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el perfil del médico", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirCitas(ActionEvent event) {
        try {
            System.out.println("Abriendo gestión de citas...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/citasDoctor.fxml"));
            Parent citasRoot = loader.load();
            
            Scene nuevaEscena = new Scene(citasRoot, 1200, 800);
            Stage currentStage = (Stage) btnCitas.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Gestión de Citas - Médico");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la gestión de citas", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirExpedientes(ActionEvent event) {
        // Ya estamos en la ventana de expedientes, no hacer nada
        System.out.println("Ya estamos en la gestión de expedientes");
    }
    
    @FXML
    private void regresarAlMenuPrincipal(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/mainDoctor.fxml"));
            Parent mainRoot = loader.load();
            
            Scene nuevaEscena = new Scene(mainRoot, 1200, 800);
            Stage currentStage = (Stage) btnRegresarMenu.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Panel Principal - Médico");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el panel principal", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void salir(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Salida");
        alert.setHeaderText(null);
        alert.setContentText("¿Está seguro de que desea salir del sistema?");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/login.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 759, 422));
                    stage.setTitle("Bienvenido a tu gestor de citas medicas");
                    stage.show();

                    Stage currentStage = (Stage) btnSalir.getScene().getWindow();
                    currentStage.hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    // Clase interna para representar un expediente médico
    public static class Expediente {
        private String folio;
        private String fecha;
        private String paciente;
        private String curp;
        private String diagnostico;
        private String tratamiento;
        private String motivo;
        
        public Expediente(String folio, String fecha, String paciente, String curp, 
                         String diagnostico, String tratamiento, String motivo) {
            this.folio = folio;
            this.fecha = fecha;
            this.paciente = paciente;
            this.curp = curp;
            this.diagnostico = diagnostico;
            this.tratamiento = tratamiento;
            this.motivo = motivo;
        }
        
        // Getters y Setters
        public String getFolio() { return folio; }
        public void setFolio(String folio) { this.folio = folio; }
        
        public String getFecha() { return fecha; }
        public void setFecha(String fecha) { this.fecha = fecha; }
        
        public String getPaciente() { return paciente; }
        public void setPaciente(String paciente) { this.paciente = paciente; }
        
        public String getCurp() { return curp; }
        public void setCurp(String curp) { this.curp = curp; }
        
        public String getDiagnostico() { return diagnostico; }
        public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
        
        public String getTratamiento() { return tratamiento; }
        public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }
        
        public String getMotivo() { return motivo; }
        public void setMotivo(String motivo) { this.motivo = motivo; }
    }
}
