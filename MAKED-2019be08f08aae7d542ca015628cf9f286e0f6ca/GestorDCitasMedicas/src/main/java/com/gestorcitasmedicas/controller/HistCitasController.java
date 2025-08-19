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
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HistCitasController {

    @FXML private TableView<Cita> tablaHistorialCitas;
    @FXML private TableColumn<Cita, String> colNumCita;
    @FXML private TableColumn<Cita, String> colFecha;
    @FXML private TableColumn<Cita, String> colMotivo;
    @FXML private TableColumn<Cita, String> colMedico;
    @FXML private TableColumn<Cita, String> colDiagnostico;
    @FXML private TableColumn<Cita, String> colTratamiento;
    @FXML private TableColumn<Cita, String> colEstado;
    

    @FXML private Button btnMiPerfilMenu;
    @FXML private Button btnHistorialCitas;
    @FXML private Button btnEditarInformacion;
    @FXML private Button btnCancelarCita;
    @FXML private Button btnReprogramarCita;
    @FXML private Button btnSalir;
    @FXML private Button btnRegresar;
    
    // Elementos del menú expandible
    @FXML private VBox menuLateral;
    @FXML private VBox menuItemMiPerfil;
    @FXML private VBox menuItemHistorialCitas;
    @FXML private VBox menuItemEditarInformacion;
    @FXML private VBox menuItemCancelarCita;
    @FXML private VBox menuItemReprogramarCita;
    @FXML private VBox menuItemSalir;
    
    // Variables para el menú expandible
    private Timeline timelineExpansion;
    private boolean menuExpandido = false;

    private ObservableList<Cita> historialCitas;

    @FXML
    private void initialize() {
        System.out.println("HistCitasController inicializando...");
        
        // Configurar menú expandible
        configurarMenuExpandible();
        
        // Configurar la tabla de historial de citas
        configurarTablaHistorialCitas();
        
        // Cargar datos de ejemplo
        cargarHistorialCitas();
        
        // Configurar efectos hover para los botones del menú lateral
        configurarEfectosHover();
        
        System.out.println("HistCitasController inicializado correctamente");
    }
    
    private void configurarTablaHistorialCitas() {
        // Configurar las columnas de la tabla
        colNumCita.setCellValueFactory(new PropertyValueFactory<>("numCita"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colMotivo.setCellValueFactory(new PropertyValueFactory<>("motivo"));
        colMedico.setCellValueFactory(new PropertyValueFactory<>("medico"));
        colDiagnostico.setCellValueFactory(new PropertyValueFactory<>("diagnostico"));
        colTratamiento.setCellValueFactory(new PropertyValueFactory<>("tratamiento"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        
        // Configurar doble clic para ver detalles de la cita
        tablaHistorialCitas.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Cita citaSeleccionada = tablaHistorialCitas.getSelectionModel().getSelectedItem();
                if (citaSeleccionada != null) {
                    mostrarDetallesCita(citaSeleccionada);
                }
            }
        });
    }
    
    private void cargarHistorialCitas() {
        historialCitas = FXCollections.observableArrayList();
        
        // Agregar datos de ejemplo
        historialCitas.add(new Cita("001", "15/01/2024", "Dolor de cabeza", "Dr. Carlos Mendoza", 
                                   "Migraña tensional", "Paracetamol 500mg cada 8 horas", "Completada"));
        historialCitas.add(new Cita("002", "22/01/2024", "Revisión general", "Dr. Ana García", 
                                   "Saludable", "Mantener dieta balanceada", "Completada"));
        historialCitas.add(new Cita("003", "05/02/2024", "Dolor de espalda", "Dr. Roberto Silva", 
                                   "Contractura muscular", "Ejercicios de estiramiento", "Completada"));
        historialCitas.add(new Cita("004", "18/02/2024", "Control de presión", "Dr. María López", 
                                   "Presión arterial normal", "Continuar medicación", "Completada"));
        historialCitas.add(new Cita("005", "10/03/2024", "Consulta dental", "Dr. Juan Pérez", 
                                   "Caries leve", "Empaste dental", "Completada"));
        
        tablaHistorialCitas.setItems(historialCitas);
    }
    
    private void configurarMenuExpandible() {
        // Configurar animación del menú
        timelineExpansion = new Timeline();

        // Verificar que los elementos del menú no sean null antes de configurar eventos
        if (menuLateral != null) {
            // Eventos del menú lateral
            menuLateral.setOnMouseEntered(e -> expandirMenu());
            menuLateral.setOnMouseExited(e -> contraerMenu());

            // Mostrar etiquetas inicialmente
            mostrarEtiquetasMenu(false);
        }
    }

    private void expandirMenu() {
        if (!menuExpandido && menuLateral != null && timelineExpansion != null) {
            menuExpandido = true;

            // Detener animación anterior si está en curso
            timelineExpansion.stop();

            // Hacer que el menú aparezca por encima del contenido
            menuLateral.toFront();

            // Animación de expansión
            KeyValue keyValue = new KeyValue(menuLateral.prefWidthProperty(), 200);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue);
            timelineExpansion.getKeyFrames().clear();
            timelineExpansion.getKeyFrames().add(keyFrame);

            // Mostrar etiquetas durante la expansión
            mostrarEtiquetasMenu(true);

            timelineExpansion.play();
        }
    }

    private void contraerMenu() {
        if (menuExpandido && menuLateral != null && timelineExpansion != null) {
            menuExpandido = false;

            // Detener animación anterior si está en curso
            timelineExpansion.stop();

            // Animación de contracción
            KeyValue keyValue = new KeyValue(menuLateral.prefWidthProperty(), 60);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue);
            timelineExpansion.getKeyFrames().clear();
            timelineExpansion.getKeyFrames().add(keyFrame);

            // Ocultar etiquetas durante la contracción
            mostrarEtiquetasMenu(false);

            timelineExpansion.play();
        }
    }

    private void mostrarEtiquetasMenu(boolean mostrar) {
        // Mostrar u ocultar etiquetas de texto en los elementos del menú
        if (menuItemMiPerfil != null) {
            menuItemMiPerfil.getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> node.setVisible(mostrar));
        }
        if (menuItemHistorialCitas != null) {
            menuItemHistorialCitas.getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> node.setVisible(mostrar));
        }
        if (menuItemEditarInformacion != null) {
            menuItemEditarInformacion.getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> node.setVisible(mostrar));
        }
        if (menuItemCancelarCita != null) {
            menuItemCancelarCita.getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> node.setVisible(mostrar));
        }
        if (menuItemReprogramarCita != null) {
            menuItemReprogramarCita.getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> node.setVisible(mostrar));
        }
        if (menuItemSalir != null) {
            menuItemSalir.getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> node.setVisible(mostrar));
        }
    }
    
    private void configurarEfectosHover() {
        // Agregar efectos hover a los botones del menú lateral
        Button[] botonesMenu = {btnMiPerfilMenu, btnHistorialCitas, btnEditarInformacion, 
                               btnCancelarCita, btnReprogramarCita, btnSalir};
        
        for (Button boton : botonesMenu) {
            boton.setOnMouseEntered(e -> {
                boton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2); -fx-background-radius: 20; -fx-cursor: hand;");
            });
            
            boton.setOnMouseExited(e -> {
                boton.setStyle("-fx-background-color: transparent; -fx-background-radius: 20;");
            });
        }
    }
    
    private void mostrarDetallesCita(Cita cita) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalles de la Cita");
        alert.setHeaderText("Cita #" + cita.getNumCita());
        alert.setContentText(
            "Fecha: " + cita.getFecha() + "\n" +
            "Motivo: " + cita.getMotivo() + "\n" +
            "Médico: " + cita.getMedico() + "\n" +
            "Diagnóstico: " + cita.getDiagnostico() + "\n" +
            "Tratamiento: " + cita.getTratamiento() + "\n" +
            "Estado: " + cita.getEstado()
        );
        alert.showAndWait();
    }

    @FXML
    private void abrirMiPerfil(ActionEvent event) {
        try {
            System.out.println("Abriendo mi perfil...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/EditarInformacion.fxml"));
            Parent perfilRoot = loader.load();
            
            Scene nuevaEscena = new Scene(perfilRoot);
            Stage currentStage = (Stage) btnMiPerfilMenu.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Mi Perfil - Paciente");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar mi perfil", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirHistorialCitas(ActionEvent event) {
        // Ya estamos en el historial de citas, no hacer nada
        System.out.println("Ya estamos en el historial de citas");
    }
    
    @FXML
    private void abrirEditarInformacion(ActionEvent event) {
        try {
            System.out.println("Abriendo editar información...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/EditarInformacion.fxml"));
            Parent editarRoot = loader.load();
            
            Scene nuevaEscena = new Scene(editarRoot);
            Stage currentStage = (Stage) btnEditarInformacion.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Editar Información - Paciente");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la edición de información", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirCancelarCita(ActionEvent event) {
        try {
            System.out.println("Abriendo cancelar cita...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/CancelarCita.fxml"));
            Parent cancelarRoot = loader.load();
            
            Scene nuevaEscena = new Scene(cancelarRoot);
            Stage currentStage = (Stage) btnCancelarCita.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Cancelar Cita - Paciente");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la cancelación de cita", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirReprogramarCita(ActionEvent event) {
        try {
            System.out.println("Abriendo reprogramar cita...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/ReprogramarCita.fxml"));
            Parent reprogramarRoot = loader.load();
            
            Scene nuevaEscena = new Scene(reprogramarRoot);
            Stage currentStage = (Stage) btnReprogramarCita.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Reprogramar Cita - Paciente");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la reprogramación de cita", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void regresar(ActionEvent event) {
        try {
            System.out.println("Regresando al panel principal...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/VistaPaciente.fxml"));
            Parent pacienteRoot = loader.load();
            
            Scene nuevaEscena = new Scene(pacienteRoot);
            Stage currentStage = (Stage) btnRegresar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Panel Principal - Paciente");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo regresar al panel principal", Alert.AlertType.ERROR);
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
        private String numCita;
        private String fecha;
        private String motivo;
        private String medico;
        private String diagnostico;
        private String tratamiento;
        private String estado;

        public Cita(String numCita, String fecha, String motivo, String medico, 
                   String diagnostico, String tratamiento, String estado) {
            this.numCita = numCita;
            this.fecha = fecha;
            this.motivo = motivo;
            this.medico = medico;
            this.diagnostico = diagnostico;
            this.tratamiento = tratamiento;
            this.estado = estado;
        }

        // Getters y Setters
        public String getNumCita() { return numCita; }
        public void setNumCita(String numCita) { this.numCita = numCita; }

        public String getFecha() { return fecha; }
        public void setFecha(String fecha) { this.fecha = fecha; }

        public String getMotivo() { return motivo; }
        public void setMotivo(String motivo) { this.motivo = motivo; }

        public String getMedico() { return medico; }
        public void setMedico(String medico) { this.medico = medico; }

        public String getDiagnostico() { return diagnostico; }
        public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

        public String getTratamiento() { return tratamiento; }
        public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }

        public String getEstado() { return estado; }
        public void setEstado(String estado) { this.estado = estado; }
    }
}
