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
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReprogramarCitaController {

    @FXML private ComboBox<String> cmbHora;
    @FXML private ComboBox<String> cmbFecha;
    @FXML private TextArea txtMotivo;
    @FXML private Button btnCancelar;
    @FXML private Button btnAceptar;
    
    // Elementos del menú expandible
    @FXML private VBox menuLateral;
    @FXML private VBox menuItemMiPerfil;
    @FXML private VBox menuItemHistorialCitas;
    @FXML private VBox menuItemEditarInformacion;
    @FXML private VBox menuItemCancelarCita;
    @FXML private VBox menuItemReprogramarCita;
    @FXML private VBox menuItemSalir;
    
    // Botones del menú
    @FXML private Button btnMiPerfil;
    @FXML private Button btnHistorialCitas;
    @FXML private Button btnEditarInformacion;
    @FXML private Button btnCancelarCita;
    @FXML private Button btnReprogramarCita;
    @FXML private Button btnSalir;
    
    // Variables para el menú expandible
    private Timeline timelineExpansion;
    private boolean menuExpandido = false;

    @FXML
    private void initialize() {
        System.out.println("ReprogramarCitaController inicializando...");
        
        // Primero abrir la ventana de selección de cita
        abrirSeleccionarCita();
        
        // Configurar menú expandible
        configurarMenuExpandible();
        
        // Cargar opciones de hora y fecha
        cargarOpcionesHora();
        cargarOpcionesFecha();
        
        // Configurar efectos hover para los botones
        configurarEfectosHover();
        
        System.out.println("ReprogramarCitaController inicializado correctamente");
    }
    
    private void abrirSeleccionarCita() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/seleccionarCita.fxml"));
            Parent seleccionarRoot = loader.load();
            
            // Obtener el controlador y configurarlo para "reprogramar"
            SeleccionarCitaController controller = loader.getController();
            controller.setAccion("reprogramar");
            
            Stage stage = new Stage();
            stage.setTitle("Seleccionar Cita para Reprogramar");
            stage.setScene(new Scene(seleccionarRoot));
            stage.setResizable(false);
            stage.showAndWait();
            
            // Si se seleccionó una cita, continuar con la reprogramación
            // TODO: Implementar lógica para obtener la cita seleccionada
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la selección de citas", Alert.AlertType.ERROR);
        }
    }
    
    private void cargarOpcionesHora() {
        ObservableList<String> horas = FXCollections.observableArrayList(
            "08:00", "08:30", "09:00", "09:30", "10:00", "10:30",
            "11:00", "11:30", "12:00", "12:30", "13:00", "13:30",
            "14:00", "14:30", "15:00", "15:30", "16:00", "16:30",
            "17:00", "17:30", "18:00", "18:30", "19:00", "19:30"
        );
        cmbHora.setItems(horas);
    }
    
    private void cargarOpcionesFecha() {
        ObservableList<String> fechas = FXCollections.observableArrayList();
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Generar fechas para los próximos 30 días
        for (int i = 1; i <= 30; i++) {
            LocalDate fecha = hoy.plusDays(i);
            fechas.add(fecha.format(formatter));
        }
        
        cmbFecha.setItems(fechas);
    }
    
    private void configurarEfectosHover() {
        // Efecto hover para el botón Cancelar
        btnCancelar.setOnMouseEntered(e -> {
            btnCancelar.setStyle("-fx-background-color: #666666; -fx-cursor: hand;");
        });
        
        btnCancelar.setOnMouseExited(e -> {
            btnCancelar.setStyle("-fx-background-color: #787a7d; -fx-cursor: hand;");
        });
        
        // Efecto hover para el botón Aceptar
        btnAceptar.setOnMouseEntered(e -> {
            btnAceptar.setStyle("-fx-background-color: #2C5A7A; -fx-cursor: hand;");
        });
        
        btnAceptar.setOnMouseExited(e -> {
            btnAceptar.setStyle("-fx-background-color: #3b6f89; -fx-cursor: hand;");
        });
        
        // Efectos hover para los botones del menú lateral
        Button[] botonesMenu = {btnMiPerfil, btnHistorialCitas, btnEditarInformacion, 
                               btnCancelarCita, btnReprogramarCita, btnSalir};
        
        for (Button boton : botonesMenu) {
            if (boton != null) {
                boton.setOnMouseEntered(e -> {
                    boton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2); -fx-background-radius: 20;");
                });
                
                boton.setOnMouseExited(e -> {
                    boton.setStyle("-fx-background-color: transparent;");
                });
            }
        }
    }

    @FXML
    private void aceptar(ActionEvent event) {
        System.out.println("Aceptando reprogramación de cita...");
        
        // Validar campos
        if (!validarCampos()) {
            return;
        }
        
        // Mostrar confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Reprogramación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText(
            "¿Está seguro de que desea reprogramar su cita?\n\n" +
            "Nueva fecha: " + cmbFecha.getValue() + "\n" +
            "Nueva hora: " + cmbHora.getValue() + "\n" +
            "Motivo: " + txtMotivo.getText()
        );
        
        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Simular reprogramación exitosa
                try {
                    System.out.println("Cita reprogramada exitosamente");
                    System.out.println("Nueva fecha: " + cmbFecha.getValue());
                    System.out.println("Nueva hora: " + cmbHora.getValue());
                    System.out.println("Motivo: " + txtMotivo.getText());
                    
                    // Mostrar ventana de confirmación exitosa
                    mostrarConfirmacionExitosa();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    mostrarAlerta("Error", "Error al reprogramar la cita: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        System.out.println("Cancelando operación...");
        
        // Regresar al panel principal del paciente
        regresarAlPanelPrincipal();
    }
    
    private boolean validarCampos() {
        // Validar hora seleccionada
        if (cmbHora.getValue() == null || cmbHora.getValue().isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar una hora", Alert.AlertType.ERROR);
            cmbHora.requestFocus();
            return false;
        }
        
        // Validar fecha seleccionada
        if (cmbFecha.getValue() == null || cmbFecha.getValue().isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar una fecha", Alert.AlertType.ERROR);
            cmbFecha.requestFocus();
            return false;
        }
        
        // Validar motivo
        if (txtMotivo.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar un motivo para la reprogramación", Alert.AlertType.ERROR);
            txtMotivo.requestFocus();
            return false;
        }
        
        if (txtMotivo.getText().trim().length() < 10) {
            mostrarAlerta("Error", "El motivo debe tener al menos 10 caracteres", Alert.AlertType.ERROR);
            txtMotivo.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void mostrarConfirmacionExitosa() {
        try {
            // Cargar la ventana de confirmación exitosa
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/ReprogramarCitaExitosa.fxml"));
            Parent exitosaRoot = loader.load();
            
            Scene nuevaEscena = new Scene(exitosaRoot, 400, 200);
            Stage currentStage = (Stage) btnAceptar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Cita Reprogramada Exitosamente");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Éxito", "Su cita ha sido reprogramada exitosamente", Alert.AlertType.INFORMATION);
            regresarAlPanelPrincipal();
        }
    }
    
    private void regresarAlPanelPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/VistaPaciente.fxml"));
            Parent pacienteRoot = loader.load();
            
            Scene nuevaEscena = new Scene(pacienteRoot, 1024, 768);
            Stage currentStage = (Stage) btnCancelar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Panel Principal - Paciente");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo regresar al panel principal", Alert.AlertType.ERROR);
        }
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    // Métodos del menú expandible
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
    
    // Métodos de navegación del menú
    @FXML
    private void abrirMiPerfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/VistaPaciente.fxml"));
            Parent pacienteRoot = loader.load();
            
            Scene nuevaEscena = new Scene(pacienteRoot, 1366, 768);
            Stage currentStage = (Stage) btnMiPerfil.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Panel Principal - Paciente");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir Mi Perfil", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirHistorialCitas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/HISTCITAS.fxml"));
            Parent historialRoot = loader.load();
            
            Scene nuevaEscena = new Scene(historialRoot, 1366, 768);
            Stage currentStage = (Stage) btnHistorialCitas.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Historial de Citas - Paciente");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir el Historial de Citas", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirEditarInformacion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/EditarInformacion.fxml"));
            Parent editarRoot = loader.load();
            
            Scene nuevaEscena = new Scene(editarRoot, 1366, 768);
            Stage currentStage = (Stage) btnEditarInformacion.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Editar Información - Paciente");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir Editar Información", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirCancelarCita() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/CancelarCita.fxml"));
            Parent cancelarRoot = loader.load();
            
            Scene nuevaEscena = new Scene(cancelarRoot, 1366, 768);
            Stage currentStage = (Stage) btnCancelarCita.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Cancelar Cita - Paciente");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir Cancelar Cita", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirReprogramarCita() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/ReprogramarCita.fxml"));
            Parent reprogramarRoot = loader.load();
            
            Scene nuevaEscena = new Scene(reprogramarRoot, 1366, 768);
            Stage currentStage = (Stage) btnReprogramarCita.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Reprogramar Cita - Paciente");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir Reprogramar Cita", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void salir() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/login.fxml"));
            Parent loginRoot = loader.load();
            
            Scene nuevaEscena = new Scene(loginRoot, 1366, 768);
            Stage currentStage = (Stage) btnSalir.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Inicio de Sesión");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cerrar sesión", Alert.AlertType.ERROR);
        }
    }
}
