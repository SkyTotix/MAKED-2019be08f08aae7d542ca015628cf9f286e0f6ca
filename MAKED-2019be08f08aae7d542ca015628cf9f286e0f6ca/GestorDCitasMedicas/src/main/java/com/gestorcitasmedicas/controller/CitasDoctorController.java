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

public class CitasDoctorController {

    @FXML
    private TableView<Cita> tablaCitas;
    
    @FXML
    private TableColumn<Cita, String> colHora;
    
    @FXML
    private TableColumn<Cita, String> colNumCita;
    
    @FXML
    private TableColumn<Cita, String> colPaciente;
    
    @FXML
    private TableColumn<Cita, String> colSintomatologia;
    
    @FXML
    private TableColumn<Cita, String> colConsultorio;
    
    @FXML
    private Button btnIniciarConsulta;
    
    @FXML
    private Button btnRegresar;
    
    @FXML
    private Button btnPerfil;
    
    @FXML
    private Button btnCitas;
    
    @FXML
    private Button btnExpedientes;
    
    @FXML
    private Button btnSalir;

    // Datos simulados de citas
    private ObservableList<Cita> todasLasCitas;

    @FXML
    private void initialize() {
        System.out.println("CitasDoctorController inicializando...");
        
        // Configurar la tabla de citas
        configurarTablaCitas();
        
        // Cargar datos simulados
        cargarTodasLasCitas();
        
        System.out.println("CitasDoctorController inicializado correctamente");
    }
    
    private void configurarTablaCitas() {
        // Configurar las columnas de la tabla
        colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        colNumCita.setCellValueFactory(new PropertyValueFactory<>("numCita"));
        colPaciente.setCellValueFactory(new PropertyValueFactory<>("paciente"));
        colSintomatologia.setCellValueFactory(new PropertyValueFactory<>("sintomatologia"));
        colConsultorio.setCellValueFactory(new PropertyValueFactory<>("consultorio"));
        
        // Configurar la tabla para permitir selección
        tablaCitas.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        // Agregar evento de doble click para iniciar consulta
        tablaCitas.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Cita citaSeleccionada = tablaCitas.getSelectionModel().getSelectedItem();
                if (citaSeleccionada != null) {
                    iniciarConsultaConCita(citaSeleccionada);
                }
            }
        });
    }
    
    private void cargarTodasLasCitas() {
        // Crear datos simulados de citas (más extensos que en el panel principal)
        todasLasCitas = FXCollections.observableArrayList();
        
        // Citas de hoy
        todasLasCitas.add(new Cita("09:00", "C001", "María González López", "Dolor de cabeza y fiebre", "Consultorio 1"));
        todasLasCitas.add(new Cita("10:30", "C002", "Juan Pérez Martínez", "Dolor abdominal", "Consultorio 1"));
        todasLasCitas.add(new Cita("12:00", "C003", "Ana Rodríguez Silva", "Control de presión arterial", "Consultorio 1"));
        todasLasCitas.add(new Cita("14:30", "C004", "Carlos Mendoza Vega", "Dolor en las articulaciones", "Consultorio 1"));
        todasLasCitas.add(new Cita("16:00", "C005", "Laura Torres Jiménez", "Revisión de medicamentos", "Consultorio 1"));
        
        // Citas de mañana
        todasLasCitas.add(new Cita("08:30", "C006", "Roberto Sánchez Díaz", "Dolor de garganta", "Consultorio 1"));
        todasLasCitas.add(new Cita("11:00", "C007", "Carmen Vega Ruiz", "Control de diabetes", "Consultorio 1"));
        todasLasCitas.add(new Cita("13:30", "C008", "Miguel Ángel Flores", "Dolor de espalda", "Consultorio 1"));
        todasLasCitas.add(new Cita("15:00", "C009", "Patricia Morales", "Revisión post-operatoria", "Consultorio 1"));
        todasLasCitas.add(new Cita("17:30", "C010", "Fernando Castro", "Alergias estacionales", "Consultorio 1"));
        
        // Asignar los datos a la tabla
        tablaCitas.setItems(todasLasCitas);
        
        System.out.println("Todas las citas cargadas: " + todasLasCitas.size() + " citas");
    }
    
    @FXML
    private void iniciarConsulta(ActionEvent event) {
        Cita citaSeleccionada = tablaCitas.getSelectionModel().getSelectedItem();
        
        if (citaSeleccionada == null) {
            mostrarAlerta("Advertencia", "Por favor seleccione una cita para iniciar la consulta", Alert.AlertType.WARNING);
            return;
        }
        
        iniciarConsultaConCita(citaSeleccionada);
    }
    
    private void iniciarConsultaConCita(Cita citaSeleccionada) {
        System.out.println("Iniciando consulta para: " + citaSeleccionada.getPaciente());
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/consulta.fxml"));
            Parent consultaRoot = loader.load();
            
            // Pasar la información de la cita al controlador de consulta
            ConsultaController consultaController = loader.getController();
            consultaController.cargarDatosCita(citaSeleccionada.getPaciente(), citaSeleccionada.getSintomatologia());
            
            Scene nuevaEscena = new Scene(consultaRoot);
            Stage currentStage = (Stage) btnIniciarConsulta.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Consulta Médica - " + citaSeleccionada.getPaciente());
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de consulta", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void regresar(ActionEvent event) {
        try {
            System.out.println("Regresando al panel principal del médico...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/mainDoctor.fxml"));
            Parent mainDoctorRoot = loader.load();
            
            Scene nuevaEscena = new Scene(mainDoctorRoot);
            Stage currentStage = (Stage) btnRegresar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Panel Principal - Médico");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo regresar al panel principal", Alert.AlertType.ERROR);
        }
    }
    
    // Métodos para el menú lateral
    @FXML
    private void abrirPerfil(ActionEvent event) {
        try {
            System.out.println("Abriendo perfil del médico...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/perfilDocMod.fxml"));
            Parent perfilRoot = loader.load();
            
            Scene nuevaEscena = new Scene(perfilRoot);
            Stage currentStage = (Stage) btnPerfil.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Mi Perfil - Médico");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el perfil del médico", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirCitas(ActionEvent event) {
        // Ya estamos en la ventana de citas, no hacer nada
        System.out.println("Ya estamos en la gestión de citas");
    }
    
    @FXML
    private void abrirExpedientes(ActionEvent event) {
        try {
            System.out.println("Abriendo expedientes médicos...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/expedientesDoctor.fxml"));
            Parent expedientesRoot = loader.load();
            
            Scene nuevaEscena = new Scene(expedientesRoot);
            Stage currentStage = (Stage) btnExpedientes.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Expedientes Médicos");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar los expedientes médicos", Alert.AlertType.ERROR);
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
                    stage.setScene(new Scene(root));
                    stage.setTitle("Bienvenido a tu gestor de citas medicas");
                    stage.setMaximized(true);
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
    
    // Clase interna para representar una cita
    public static class Cita {
        private String hora;
        private String numCita;
        private String paciente;
        private String sintomatologia;
        private String consultorio;
        
        public Cita(String hora, String numCita, String paciente, String sintomatologia, String consultorio) {
            this.hora = hora;
            this.numCita = numCita;
            this.paciente = paciente;
            this.sintomatologia = sintomatologia;
            this.consultorio = consultorio;
        }
        
        // Getters y Setters
        public String getHora() { return hora; }
        public void setHora(String hora) { this.hora = hora; }
        
        public String getNumCita() { return numCita; }
        public void setNumCita(String numCita) { this.numCita = numCita; }
        
        public String getPaciente() { return paciente; }
        public void setPaciente(String paciente) { this.paciente = paciente; }
        
        public String getSintomatologia() { return sintomatologia; }
        public void setSintomatologia(String sintomatologia) { this.sintomatologia = sintomatologia; }
        
        public String getConsultorio() { return consultorio; }
        public void setConsultorio(String consultorio) { this.consultorio = consultorio; }
    }
}
