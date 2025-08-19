package com.gestorcitasmedicas.controller;

import com.gestorcitasmedicas.model.Medico;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Node;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.regex.Pattern;

public class RegMedicosController {

    // Elementos del menú lateral
    @FXML private VBox menuLateral;
    @FXML private VBox menuItemUsuarios;
    @FXML private VBox menuItemMedicos;
    @FXML private VBox menuItemCitas;
    @FXML private VBox menuItemSalir;
    
    // Elementos del formulario
    @FXML private TextField nombreField;
    @FXML private TextField especialidadField;
    @FXML private TextField cedulaField;
    @FXML private TextField correoField;
    @FXML private TextField telefonoField;
    @FXML private TextField horarioField;
    @FXML private TextField consultorioField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    
    // Botones
    @FXML private Button btnRegistrar;
    @FXML private Button btnRegresar;
    @FXML private Button btnLimpiar;
    
    // Variables para el menú expandible
    private Timeline timelineExpansion;
    private boolean menuExpandido = false;

    @FXML
    private void initialize() {
        configurarMenuExpandible();
        configurarEventosMenuLateral();
        configurarEventosBotones();
        configurarValidaciones();
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
        if (menuItemUsuarios != null) {
            menuItemUsuarios.getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> node.setVisible(mostrar));
        }
        if (menuItemMedicos != null) {
            menuItemMedicos.getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> node.setVisible(mostrar));
        }
        if (menuItemCitas != null) {
            menuItemCitas.getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> node.setVisible(mostrar));
        }
        if (menuItemSalir != null) {
            menuItemSalir.getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> node.setVisible(mostrar));
        }
    }
    
    private void configurarEventosMenuLateral() {
        // Configurar eventos de clic para los elementos del menú
        if (menuItemUsuarios != null) {
            menuItemUsuarios.setOnMouseClicked(e -> navegarAGestionUsuarios());
        }
        if (menuItemMedicos != null) {
            menuItemMedicos.setOnMouseClicked(e -> navegarAGestionMedicos());
        }
        if (menuItemCitas != null) {
            menuItemCitas.setOnMouseClicked(e -> navegarAGestionCitas());
        }
        if (menuItemSalir != null) {
            menuItemSalir.setOnMouseClicked(e -> salir());
        }
    }
    
    private void configurarEventosBotones() {
        if (btnRegistrar != null) {
            btnRegistrar.setOnAction(e -> registrarMedico());
        }
        if (btnRegresar != null) {
            btnRegresar.setOnAction(e -> regresar());
        }
        if (btnLimpiar != null) {
            btnLimpiar.setOnAction(e -> limpiarFormulario());
        }
    }
    
    private void configurarValidaciones() {
        // Validación de teléfono (solo números)
        if (telefonoField != null) {
            telefonoField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    telefonoField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });
        }
        
        // Validación de cédula (solo números)
        if (cedulaField != null) {
            cedulaField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    cedulaField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });
        }
    }
    
    @FXML
    private void registrarMedico() {
        if (validarFormulario()) {
            try {
                // Verificar si el correo ya existe
                if (Medico.correoExiste(correoField.getText().trim())) {
                    mostrarAlerta("Error", "El correo electrónico ya está registrado en el sistema", Alert.AlertType.ERROR);
                    correoField.requestFocus();
                    return;
                }
                
                // Verificar si la cédula ya existe
                if (Medico.cedulaExiste(cedulaField.getText().trim())) {
                    mostrarAlerta("Error", "La cédula profesional ya está registrada en el sistema", Alert.AlertType.ERROR);
                    cedulaField.requestFocus();
                    return;
                }
                
                // Crear objeto médico
                Medico medico = new Medico(
                    nombreField.getText().trim(),
                    especialidadField.getText().trim(),
                    cedulaField.getText().trim(),
                    correoField.getText().trim(),
                    telefonoField.getText().trim(),
                    horarioField.getText().trim(),
                    consultorioField.getText().trim(),
                    passwordField.getText()
                );
                
                // Guardar en la base de datos
                boolean guardado = medico.guardar();
                
                if (guardado) {
                    mostrarAlerta("Éxito", 
                        "Médico registrado correctamente\n\n" +
                        "Nombre: " + medico.getNombre() + "\n" +
                        "Especialidad: " + medico.getEspecialidad() + "\n" +
                        "Cédula: " + medico.getCedula() + "\n" +
                        "Correo: " + medico.getCorreo(), 
                        Alert.AlertType.INFORMATION);
                    limpiarFormulario();
                } else {
                    mostrarAlerta("Error", "No se pudo registrar el médico. Por favor, inténtelo nuevamente.", Alert.AlertType.ERROR);
                }
                
            } catch (Exception e) {
                System.err.println("Error al registrar médico: " + e.getMessage());
                e.printStackTrace();
                mostrarAlerta("Error del Sistema", 
                    "Ocurrió un error inesperado durante el registro:\n\n" + e.getMessage() + "\n\n" +
                    "Por favor, verifique su conexión e inténtelo nuevamente.", 
                    Alert.AlertType.ERROR);
            }
        }
    }
    
    private boolean validarFormulario() {
        // Validar campos obligatorios
        if (nombreField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El nombre es obligatorio", Alert.AlertType.ERROR);
            nombreField.requestFocus();
            return false;
        }
        
        if (especialidadField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "La especialidad es obligatoria", Alert.AlertType.ERROR);
            especialidadField.requestFocus();
            return false;
        }
        
        if (cedulaField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "La cédula es obligatoria", Alert.AlertType.ERROR);
            cedulaField.requestFocus();
            return false;
        }
        
        if (correoField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El correo es obligatorio", Alert.AlertType.ERROR);
            correoField.requestFocus();
            return false;
        }
        
        if (telefonoField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El teléfono es obligatorio", Alert.AlertType.ERROR);
            telefonoField.requestFocus();
            return false;
        }
        
        if (horarioField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El horario es obligatorio", Alert.AlertType.ERROR);
            horarioField.requestFocus();
            return false;
        }
        
        if (consultorioField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El consultorio es obligatorio", Alert.AlertType.ERROR);
            consultorioField.requestFocus();
            return false;
        }
        
        if (passwordField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "La contraseña es obligatoria", Alert.AlertType.ERROR);
            passwordField.requestFocus();
            return false;
        }
        
        if (confirmPasswordField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe confirmar la contraseña", Alert.AlertType.ERROR);
            confirmPasswordField.requestFocus();
            return false;
        }
        
        // Validar que las contraseñas coincidan
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            mostrarAlerta("Error", "Las contraseñas no coinciden", Alert.AlertType.ERROR);
            confirmPasswordField.requestFocus();
            return false;
        }
        
        // Validar formato de correo
        if (!isValidEmail(correoField.getText())) {
            mostrarAlerta("Error", "El formato del correo no es válido", Alert.AlertType.ERROR);
            correoField.requestFocus();
            return false;
        }
        
        // Validar longitud de teléfono
        if (telefonoField.getText().length() != 10) {
            mostrarAlerta("Error", "El teléfono debe tener 10 dígitos", Alert.AlertType.ERROR);
            telefonoField.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    
    @FXML
    private void limpiarFormulario() {
        nombreField.clear();
        especialidadField.clear();
        cedulaField.clear();
        correoField.clear();
        telefonoField.clear();
        horarioField.clear();
        consultorioField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        
        // Enfocar el primer campo
        nombreField.requestFocus();
    }
    
    @FXML
    private void regresar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/gestMedicos.fxml"));
            Parent gestMedicosRoot = loader.load();
            
            // Obtener el controlador y refrescar la tabla
            GestMedicosController controller = loader.getController();
            if (controller != null) {
                controller.refrescarTabla();
            }
            
            Scene nuevaEscena = new Scene(gestMedicosRoot, 1024, 768);
            Stage currentStage = (Stage) btnRegresar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Gestión de Médicos - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo regresar a la gestión de médicos", Alert.AlertType.ERROR);
        }
    }
    
    private void navegarAGestionUsuarios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/gestUsuarios.fxml"));
            Parent gestUsuariosRoot = loader.load();
            
            Scene nuevaEscena = new Scene(gestUsuariosRoot, 1024, 768);
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Gestión de Pacientes - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la gestión de usuarios", Alert.AlertType.ERROR);
        }
    }
    
    private void navegarAGestionMedicos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/gestMedicos.fxml"));
            Parent gestMedicosRoot = loader.load();
            
            Scene nuevaEscena = new Scene(gestMedicosRoot, 1024, 768);
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Gestión de Médicos - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la gestión de médicos", Alert.AlertType.ERROR);
        }
    }
    
    private void navegarAGestionCitas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/agendarCitaAdm.fxml"));
            Parent agendarCitaRoot = loader.load();
            
            Scene nuevaEscena = new Scene(agendarCitaRoot, 1024, 768);
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Agendar Cita - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la gestión de citas", Alert.AlertType.ERROR);
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
                    stage.setScene(new Scene(root, 800, 600));
                    stage.setTitle("Bienvenido a tu gestor de citas medicas");
                    stage.show();

                    Stage currentStage = (Stage) menuLateral.getScene().getWindow();
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
    
    @FXML
    private void volverAlMenuPrincipal(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/mainRecepcionista.fxml"));
            Parent mainRecepcionistaRoot = loader.load();
            
            Scene nuevaEscena = new Scene(mainRecepcionistaRoot, 1200, 800);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Panel Principal - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo volver al menú principal", Alert.AlertType.ERROR);
        }
    }
}
