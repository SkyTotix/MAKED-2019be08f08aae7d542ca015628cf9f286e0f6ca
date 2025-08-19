package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MainDoctorController {

    @FXML
    private TableView<Cita> tablaCitas;
    
    @FXML
    private TableColumn<Cita, String> colHora;
    
    @FXML
    private TableColumn<Cita, String> colNumCita;
    
    @FXML
    private TableColumn<Cita, String> colNombre;
    
    @FXML
    private TableColumn<Cita, String> colSintomatologia;
    
    @FXML
    private TableColumn<Cita, String> colConsultorio;
    
    @FXML
    private Button btnPerfil;
    
    @FXML
    private Button btnCitas;
    
    @FXML
    private Button btnExpedientes;
    
    @FXML
    private Button btnSalir;

    // Datos simulados de citas del día
    private ObservableList<Cita> citasDelDia;

    @FXML
    private void initialize() {
        System.out.println("MainDoctorController inicializando...");
        
        // Configurar la tabla de citas
        configurarTablaCitas();
        
        // Cargar datos simulados
        cargarCitasDelDia();
        
        System.out.println("MainDoctorController inicializado correctamente");
    }
    
    private void configurarTablaCitas() {
        // Configurar las columnas de la tabla
        colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        colNumCita.setCellValueFactory(new PropertyValueFactory<>("numCita"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colSintomatologia.setCellValueFactory(new PropertyValueFactory<>("sintomatologia"));
        colConsultorio.setCellValueFactory(new PropertyValueFactory<>("consultorio"));
        
        // Configurar la tabla para permitir selección
        tablaCitas.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        // Agregar evento de doble click para iniciar consulta
        tablaCitas.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Cita citaSeleccionada = tablaCitas.getSelectionModel().getSelectedItem();
                if (citaSeleccionada != null) {
                    iniciarConsulta(citaSeleccionada);
                }
            }
        });
    }
    
    private void cargarCitasDelDia() {
        // Crear datos simulados de citas
        citasDelDia = FXCollections.observableArrayList();
        
        citasDelDia.add(new Cita("09:00", "C001", "María González López", "Dolor de cabeza y fiebre", "Consultorio 1"));
        citasDelDia.add(new Cita("10:30", "C002", "Juan Pérez Martínez", "Dolor abdominal", "Consultorio 1"));
        citasDelDia.add(new Cita("12:00", "C003", "Ana Rodríguez Silva", "Control de presión arterial", "Consultorio 1"));
        citasDelDia.add(new Cita("14:30", "C004", "Carlos Mendoza Vega", "Dolor en las articulaciones", "Consultorio 1"));
        citasDelDia.add(new Cita("16:00", "C005", "Laura Torres Jiménez", "Revisión de medicamentos", "Consultorio 1"));
        
        // Asignar los datos a la tabla
        tablaCitas.setItems(citasDelDia);
        
        System.out.println("Citas del día cargadas: " + citasDelDia.size() + " citas");
    }
    
    private void iniciarConsulta(Cita cita) {
        System.out.println("Iniciando consulta para: " + cita.getNombre());
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/consulta.fxml"));
            Parent consultaRoot = loader.load();
            // Pasar la información de la cita al controlador de consulta
            ConsultaController consultaController = loader.getController();
            consultaController.cargarDatosCita(cita.getNombre(), cita.getSintomatologia());
            
            Scene nuevaEscena = new Scene(consultaRoot);
            Stage currentStage = (Stage) tablaCitas.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Consulta Médica - " + cita.getNombre());
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de consulta", Alert.AlertType.ERROR);
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
        try {
            System.out.println("Abriendo expedientes médicos...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/expedientesDoctor.fxml"));
            Parent expedientesRoot = loader.load();
            
            Scene nuevaEscena = new Scene(expedientesRoot, 1200, 800);
            Stage currentStage = (Stage) btnExpedientes.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Expedientes Médicos");
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
    
    // Clase interna para representar una cita
    public static class Cita {
        private String hora;
        private String numCita;
        private String nombre;
        private String sintomatologia;
        private String consultorio;
        
        public Cita(String hora, String numCita, String nombre, String sintomatologia, String consultorio) {
            this.hora = hora;
            this.numCita = numCita;
            this.nombre = nombre;
            this.sintomatologia = sintomatologia;
            this.consultorio = consultorio;
        }
        
        // Getters y Setters
        public String getHora() { return hora; }
        public void setHora(String hora) { this.hora = hora; }
        
        public String getNumCita() { return numCita; }
        public void setNumCita(String numCita) { this.numCita = numCita; }
        
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        
        public String getSintomatologia() { return sintomatologia; }
        public void setSintomatologia(String sintomatologia) { this.sintomatologia = sintomatologia; }
        
        public String getConsultorio() { return consultorio; }
        public void setConsultorio(String consultorio) { this.consultorio = consultorio; }
    }
}
