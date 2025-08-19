package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;

import java.io.IOException;
import com.gestorcitasmedicas.controller.AgendarCitaPacienteController;

public class VistaPacienteController {

    @FXML
    private Button btnMiPerfil;
    

    
    @FXML
    private Button btnHistorialCitas;
    
    @FXML
    private Button btnEditarInformacion;
    
    @FXML
    private Button btnCancelarCita;
    
    @FXML
    private Button btnReprogramarCita;
    
    @FXML
    private Button btnAgendarCita;
    
    @FXML
    private Button btnSalir;
    
    // Elementos del menú expandible
    @FXML private VBox menuLateral;
    @FXML private VBox menuItemMiPerfil;

    @FXML private VBox menuItemHistorialCitas;
    @FXML private VBox menuItemEditarInformacion;
    @FXML private VBox menuItemCancelarCita;
    @FXML private VBox menuItemReprogramarCita;
    @FXML private VBox menuItemAgendarCita;
    @FXML private VBox menuItemSalir;
    
    // Variables para el menú expandible
    private Timeline timelineExpansion;
    private boolean menuExpandido = false;

    @FXML
    private void initialize() {
        System.out.println("VistaPacienteController inicializando...");
        
        // Configurar menú expandible
        configurarMenuExpandible();
        
        // Configurar efectos hover para los botones del menú lateral
        configurarEfectosHover();
        
        System.out.println("VistaPacienteController inicializado correctamente");
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
        if (menuItemAgendarCita != null) {
            menuItemAgendarCita.getChildren().stream()
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
        Button[] botonesMenu = {btnMiPerfil, btnHistorialCitas, btnEditarInformacion, 
                               btnCancelarCita, btnReprogramarCita, btnAgendarCita, btnSalir};
        
        for (Button boton : botonesMenu) {
            if (boton != null) {
                boton.setOnMouseEntered(e -> {
                    boton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2); -fx-background-radius: 20; -fx-cursor: hand;");
                });
                
                boton.setOnMouseExited(e -> {
                    boton.setStyle("-fx-background-color: transparent; -fx-background-radius: 20;");
                });
            }
        }
    }

    @FXML
    private void abrirMiPerfil(ActionEvent event) {
        try {
            System.out.println("Abriendo mi perfil...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/EditarInformacion.fxml"));
            Parent perfilRoot = loader.load();
            
            Scene nuevaEscena = new Scene(perfilRoot);
            Stage currentStage = (Stage) btnMiPerfil.getScene().getWindow();
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
        try {
            System.out.println("Abriendo historial de citas...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/HISTCITAS.fxml"));
            Parent historialRoot = loader.load();
            
            Scene nuevaEscena = new Scene(historialRoot);
            Stage currentStage = (Stage) btnHistorialCitas.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Historial de Citas - Paciente");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el historial de citas", Alert.AlertType.ERROR);
        }
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
            System.out.println("Abriendo selección de cita para cancelar...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/seleccionarCita.fxml"));
            Parent seleccionarRoot = loader.load();
            
            // Obtener el controlador y configurarlo para "cancelar"
            SeleccionarCitaController controller = loader.getController();
            controller.setAccion("cancelar");
            controller.setPacienteId(1); // TODO: Obtener ID real del paciente logueado
            controller.setVentanaActual((Stage) btnCancelarCita.getScene().getWindow());
            
            Scene nuevaEscena = new Scene(seleccionarRoot);
            Stage currentStage = (Stage) btnCancelarCita.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Seleccionar Cita para Cancelar");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la selección de citas", Alert.AlertType.ERROR);
        }
    }
    

    
    @FXML
    private void abrirReprogramarCita(ActionEvent event) {
        try {
            System.out.println("Abriendo selección de cita para reprogramar...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/seleccionarCita.fxml"));
            Parent seleccionarRoot = loader.load();
            
            // Obtener el controlador y configurarlo para "reprogramar"
            SeleccionarCitaController controller = loader.getController();
            controller.setAccion("reprogramar");
            controller.setPacienteId(1); // TODO: Obtener ID real del paciente logueado
            controller.setVentanaActual((Stage) btnReprogramarCita.getScene().getWindow());
            
            Scene nuevaEscena = new Scene(seleccionarRoot);
            Stage currentStage = (Stage) btnReprogramarCita.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Seleccionar Cita para Reprogramar");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la selección de citas", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirAgendarCita(ActionEvent event) {
        try {
            System.out.println("Abriendo agendar cita...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/agendarCitaPaciente.fxml"));
            Parent agendarRoot = loader.load();
            
            // Obtener el controlador y configurar el ID del paciente
            AgendarCitaPacienteController controller = loader.getController();
            controller.setPacienteId(1); // TODO: Obtener ID real del paciente logueado
            
            Scene nuevaEscena = new Scene(agendarRoot);
            Stage currentStage = (Stage) btnAgendarCita.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Agendar Cita - Paciente");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de agendar cita", Alert.AlertType.ERROR);
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
}
