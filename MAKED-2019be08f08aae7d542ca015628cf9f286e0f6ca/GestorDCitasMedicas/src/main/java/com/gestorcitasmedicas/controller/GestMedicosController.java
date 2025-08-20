package com.gestorcitasmedicas.controller;

import com.gestorcitasmedicas.model.Medico;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestMedicosController {

    @FXML
    private Button btnPerfil;
    
    @FXML
    private Button btnAgregar;
    
    @FXML
    private VBox menuLateral;
    
    @FXML
    private VBox menuItemUsuarios;
    
    @FXML
    private VBox menuItemMedicos;
    
    @FXML
    private VBox menuItemCitas;
    
    @FXML
    private VBox menuItemSalir;
    
    @FXML
    private TableView<Medico> tablaMedicos;
    
    @FXML
    private TableColumn<Medico, String> colNombre;
    
    @FXML
    private TableColumn<Medico, String> colEspecialidad;
    
    @FXML
    private TableColumn<Medico, String> colCedula;
    
    @FXML
    private TableColumn<Medico, String> colConsultorio;
    
    @FXML
    private TableColumn<Medico, String> colAcciones;
    
    @FXML
    private TableColumn<Medico, String> colTelefono;
    
    @FXML
    private TableColumn<Medico, String> colEstatus;

    // Variables para el menú expandible
    private boolean menuExpandido = false;
    private Timeline timelineExpansion;
    
    // Lista de médicos
    private ObservableList<Medico> medicos;

    @FXML
    private void initialize() {
        // Configurar la tabla
        configurarTabla();
        
        // Cargar datos simulados
        cargarDatosSimulados();
        
        // Configurar menú expandible
        configurarMenuExpandible();
        
        // Configurar eventos del menú lateral
        configurarEventosMenuLateral();
        
        // Configurar eventos de botones
        configurarEventosBotones();
    }
    
    private void configurarTabla() {
        // Configurar las columnas
        colNombre.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getNombre()));
        
        colEspecialidad.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getEspecialidad()));
        
        colCedula.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getCedula()));
        
        colConsultorio.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getConsultorio()));
        
        colTelefono.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getTelefono()));
        
        colEstatus.setCellValueFactory(cellData -> 
            new SimpleStringProperty("Activo")); // Por defecto activo
    }
    
    private void cargarDatosSimulados() {
        medicos = FXCollections.observableArrayList();
        
        // Cargar médicos reales desde la memoria
        List<Medico> listaMedicos = Medico.obtenerTodos();
        medicos.addAll(listaMedicos);
        
        tablaMedicos.setItems(medicos);
    }
    
    // Método para refrescar la tabla
    public void refrescarTabla() {
        medicos.clear();
        List<Medico> listaMedicos = Medico.obtenerTodos();
        medicos.addAll(listaMedicos);
    }
    
    private void configurarMenuExpandible() {
        // Crear timeline para la animación
        timelineExpansion = new Timeline();
        
        // Configurar eventos del mouse
        menuLateral.setOnMouseEntered(event -> {
            if (!menuExpandido) {
                expandirMenu();
            }
        });
        
        menuLateral.setOnMouseExited(event -> {
            javafx.application.Platform.runLater(() -> {
                if (menuExpandido && !menuLateral.isHover()) {
                    contraerMenu();
                }
            });
        });
    }
    
    private void expandirMenu() {
        if (!menuExpandido && timelineExpansion.getStatus() != javafx.animation.Animation.Status.RUNNING) {
            menuExpandido = true;
            
            // Animar el ancho del menú
            KeyValue kvWidth = new KeyValue(menuLateral.prefWidthProperty(), 160);
            KeyFrame kfWidth = new KeyFrame(Duration.millis(300), kvWidth);
            
            // Mostrar etiquetas
            mostrarEtiquetasMenu(true);
            
            timelineExpansion.getKeyFrames().clear();
            timelineExpansion.getKeyFrames().add(kfWidth);
            timelineExpansion.play();
        }
    }
    
    private void contraerMenu() {
        if (menuExpandido && timelineExpansion.getStatus() != javafx.animation.Animation.Status.RUNNING) {
            menuExpandido = false;
            
            // Animar el ancho del menú
            KeyValue kvWidth = new KeyValue(menuLateral.prefWidthProperty(), 60);
            KeyFrame kfWidth = new KeyFrame(Duration.millis(300), kvWidth);
            
            // Ocultar etiquetas
            mostrarEtiquetasMenu(false);
            
            timelineExpansion.getKeyFrames().clear();
            timelineExpansion.getKeyFrames().add(kfWidth);
            timelineExpansion.play();
        }
    }
    
    private void mostrarEtiquetasMenu(boolean mostrar) {
        for (VBox menuItem : List.of(menuItemUsuarios, menuItemMedicos, menuItemCitas, menuItemSalir)) {
            for (javafx.scene.Node child : menuItem.getChildren()) {
                if (child instanceof Label) {
                    child.setVisible(mostrar);
                    child.setManaged(mostrar);
                }
            }
        }
    }
    
    private void configurarEventosMenuLateral() {
        // Configurar eventos de clic para cada elemento del menú
        menuItemUsuarios.setOnMouseClicked(event -> abrirGestionUsuarios());
        menuItemMedicos.setOnMouseClicked(event -> abrirGestionMedicos());
        menuItemCitas.setOnMouseClicked(event -> abrirGestionCitas());
        menuItemSalir.setOnMouseClicked(event -> salir());
    }
    
    private void configurarEventosBotones() {
        btnAgregar.setOnAction(event -> abrirRegistroMedico());
    }
    
    @FXML
    private void abrirPerfil(ActionEvent event) {
        mostrarAlerta("Mi Perfil", "Función de perfil en desarrollo", Alert.AlertType.INFORMATION);
    }
    
    private void abrirGestionUsuarios() {
        try {
            // Cargar la nueva ventana completa
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/gestUsuarios.fxml"));
            Parent gestUsuariosRoot = loader.load();
            
            // Crear nueva escena
            Scene nuevaEscena = new Scene(gestUsuariosRoot);
            
            // Obtener la ventana actual y reemplazarla
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Gestión de Usuarios - Gestor de Citas Médicas");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la gestión de usuarios", Alert.AlertType.ERROR);
        }
    }
    
    private void abrirGestionMedicos() {
        // Ya estamos en gestión de médicos
        mostrarAlerta("Gestión de Médicos", "Ya estás en la gestión de médicos", Alert.AlertType.INFORMATION);
    }
    
    private void abrirGestionCitas() {
        try {
            // Cargar la ventana de agendar citas
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/agendarCitaAdm.fxml"));
            Parent agendarCitaRoot = loader.load();
            
            // Crear nueva escena
            Scene nuevaEscena = new Scene(agendarCitaRoot);
            
            // Obtener la ventana actual y reemplazarla
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Agendar Cita Médica - Gestor de Citas Médicas");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de agendar citas", Alert.AlertType.ERROR);
        }
    }
    
    private void salir() {
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
                    stage.show();

                    Stage currentStage = (Stage) menuLateral.getScene().getWindow();
                    currentStage.hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    @FXML
    private void volverAlMenuPrincipal(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/mainRecepcionista.fxml"));
            Parent mainRecepcionistaRoot = loader.load();
            
            Scene nuevaEscena = new Scene(mainRecepcionistaRoot);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Panel Principal - Gestor de Citas Médicas");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo volver al menú principal", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void agregarMedico(ActionEvent event) {
        abrirRegistroMedico();
    }
    
    private void abrirRegistroMedico() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/agregarMedico.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle("Agregar Nuevo Médico");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();
            
            // Recargar la tabla después de agregar
            cargarDatosSimulados();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir el formulario de agregar médico", Alert.AlertType.ERROR);
        }
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    

}
