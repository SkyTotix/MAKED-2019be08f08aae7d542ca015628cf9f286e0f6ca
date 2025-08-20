package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;

import java.io.IOException;
import java.util.regex.Pattern;
import com.gestorcitasmedicas.utils.SesionManager;
import com.gestorcitasmedicas.model.Paciente;
import com.gestorcitasmedicas.controller.SeleccionarCitaController;

public class EditarInformacionController {

    @FXML private TextField txtCurp;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtMatricula;
    
    // Elementos para mostrar información del paciente
    @FXML private Label lblNombrePaciente;
    @FXML private ImageView imgPerfil;
    
    @FXML private Button btnMiPerfil;

    @FXML private Button btnHistorialCitas;
    @FXML private Button btnEditarInformacion;
    @FXML private Button btnCancelarCita;
    @FXML private Button btnReprogramarCita;
    @FXML private Button btnSalir;
    @FXML private Button btnCancelar;
    @FXML private Button btnActualizar;
    
    // Elementos del menú expandible
    @FXML private VBox menuLateral;
    @FXML private AnchorPane contenidoPrincipal;
    @FXML private VBox menuItemMiPerfil;
    @FXML private VBox menuItemHistorialCitas;
    @FXML private VBox menuItemEditarInformacion;
    @FXML private VBox menuItemCancelarCita;
    @FXML private VBox menuItemReprogramarCita;
    @FXML private VBox menuItemSalir;
    
    // Variables para el menú expandible
    private Timeline timelineExpansion;
    private boolean menuExpandido = false;

    // Datos originales del paciente (obtenidos dinámicamente)
    private String curpOriginal;
    private String telefonoOriginal;
    private String correoOriginal;
    private String matriculaOriginal;

    @FXML
    private void initialize() {
        System.out.println("EditarInformacionController inicializando...");
        
        // Configurar menú expandible
        configurarMenuExpandible();
        
        // Cargar información del paciente actual
        cargarInformacionPaciente();
        
        // Cargar datos actuales del paciente
        cargarDatosActuales();
        
        // Configurar validación en tiempo real
        configurarValidacion();
        
        // Configurar efectos hover para los botones del menú lateral
        configurarEfectosHover();
        
        System.out.println("EditarInformacionController inicializado correctamente");
    }
    
    /**
     * Carga la información del paciente actual de la sesión
     */
    private void cargarInformacionPaciente() {
        // Obtener el paciente actual de la sesión
        Paciente pacienteActual = SesionManager.getInstance().getPacienteActual();
        
        if (pacienteActual != null) {
            // Mostrar información del paciente en la interfaz
            if (lblNombrePaciente != null) {
                lblNombrePaciente.setText(pacienteActual.getNombre());
            }
            
            // Cargar foto de perfil según el género
            cargarFotoPerfil(pacienteActual);
            
            // Cargar datos originales del paciente
            curpOriginal = pacienteActual.getCurp();
            telefonoOriginal = pacienteActual.getTelefono();
            correoOriginal = pacienteActual.getCorreo();
            matriculaOriginal = pacienteActual.getMatricula();
            
            System.out.println("Información del paciente cargada en perfil: " + pacienteActual.getNombre());
        } else {
            System.out.println("No hay paciente en sesión");
            // Valores por defecto si no hay paciente en sesión
            curpOriginal = "";
            telefonoOriginal = "";
            correoOriginal = "";
            matriculaOriginal = "";
        }
    }
    
    /**
     * Carga la foto de perfil apropiada según el género del paciente
     * @param paciente El paciente actual
     */
    private void cargarFotoPerfil(Paciente paciente) {
        if (imgPerfil != null) {
            try {
                String rutaImagen;
                if (paciente.esMujer()) {
                    // Foto para mujer
                    rutaImagen = "/com/gestorcitasmedicas/img/profile.png";
                    System.out.println("Cargando foto de perfil para mujer: " + paciente.getNombre());
                } else if (paciente.esHombre()) {
                    // Foto para hombre
                    rutaImagen = "/com/gestorcitasmedicas/img/miPerfil.png";
                    System.out.println("Cargando foto de perfil para hombre: " + paciente.getNombre());
                } else {
                    // Foto por defecto si no se puede determinar el género
                    rutaImagen = "/com/gestorcitasmedicas/img/iimg.png";
                    System.out.println("Cargando foto de perfil por defecto: " + paciente.getNombre());
                }
                
                Image imagen = new Image(getClass().getResourceAsStream(rutaImagen));
                imgPerfil.setImage(imagen);
                
            } catch (Exception e) {
                System.err.println("Error al cargar la imagen de perfil: " + e.getMessage());
                // En caso de error, usar imagen por defecto
                try {
                    Image imagenDefault = new Image(getClass().getResourceAsStream("/com/gestorcitasmedicas/img/iimg.png"));
                    imgPerfil.setImage(imagenDefault);
                } catch (Exception ex) {
                    System.err.println("Error al cargar imagen por defecto: " + ex.getMessage());
                }
            }
        }
    }
    
    private void cargarDatosActuales() {
        // Cargar los datos actuales en los campos de texto
        txtCurp.setText(curpOriginal);
        txtTelefono.setText(telefonoOriginal);
        txtCorreo.setText(correoOriginal);
        txtMatricula.setText(matriculaOriginal);
        
        // Hacer el CURP y matrícula de solo lectura (no se pueden editar)
        txtCurp.setEditable(false);
        txtCurp.setStyle("-fx-background-color: #F0F0F0; -fx-text-fill: #666666;");
        txtMatricula.setEditable(false);
        txtMatricula.setStyle("-fx-background-color: #F0F0F0; -fx-text-fill: #666666;");
    }
    
    private void configurarValidacion() {
        // Validación de teléfono (solo números, 10 dígitos)
        txtTelefono.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !newValue.matches("\\d{10}")) {
                txtTelefono.setStyle("-fx-background-color: #FFE6E6; -fx-border-color: #FF0000;");
            } else {
                txtTelefono.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: transparent;");
            }
        });
        
        // Validación de correo electrónico
        txtCorreo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !isValidEmail(newValue)) {
                txtCorreo.setStyle("-fx-background-color: #FFE6E6; -fx-border-color: #FF0000;");
            } else {
                txtCorreo.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: transparent;");
            }
        });
    }
    
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
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

            // Animación de expansión del menú
            KeyValue keyValueMenu = new KeyValue(menuLateral.prefWidthProperty(), 200);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValueMenu);
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

            // Animación de contracción del menú
            KeyValue keyValueMenu = new KeyValue(menuLateral.prefWidthProperty(), 60);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValueMenu);
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
        Button[] botonesMenu = {btnMiPerfil, btnHistorialCitas, btnEditarInformacion, 
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

    @FXML
    private void actualizar(ActionEvent event) {
        System.out.println("Actualizando información del paciente...");
        
        // Validar campos
        if (!validarCampos()) {
            return;
        }
        
        // Actualizar información del paciente
        try {
            Paciente pacienteActual = SesionManager.getInstance().getPacienteActual();
            if (pacienteActual != null) {
                // Obtener nuevos valores
                String nuevoTelefono = txtTelefono.getText().trim();
                String nuevoCorreo = txtCorreo.getText().trim();
                
                // Actualizar el objeto paciente
                pacienteActual.setTelefono(nuevoTelefono);
                pacienteActual.setCorreo(nuevoCorreo);
                
                System.out.println("Información actualizada exitosamente:");
                System.out.println("Teléfono: " + nuevoTelefono);
                System.out.println("Correo: " + nuevoCorreo);
                
                // Actualizar datos originales
                telefonoOriginal = nuevoTelefono;
                correoOriginal = nuevoCorreo;
                
                // Mostrar mensaje de éxito
                mostrarAlerta("Éxito", "Información actualizada correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se encontró el paciente en sesión", Alert.AlertType.ERROR);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al actualizar la información: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        System.out.println("Cancelando cambios...");
        
        // Restaurar valores originales
        cargarDatosActuales();
        
        // Mostrar mensaje de confirmación
        mostrarAlerta("Información", "Los cambios han sido cancelados", Alert.AlertType.INFORMATION);
    }
    
    private boolean validarCampos() {
        // Validar teléfono
        if (txtTelefono.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El teléfono es obligatorio", Alert.AlertType.ERROR);
            txtTelefono.requestFocus();
            return false;
        }
        
        if (!txtTelefono.getText().matches("\\d{10}")) {
            mostrarAlerta("Error", "El teléfono debe tener 10 dígitos", Alert.AlertType.ERROR);
            txtTelefono.requestFocus();
            return false;
        }
        
        // Validar correo
        if (txtCorreo.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El correo es obligatorio", Alert.AlertType.ERROR);
            txtCorreo.requestFocus();
            return false;
        }
        
        if (!isValidEmail(txtCorreo.getText())) {
            mostrarAlerta("Error", "El formato del correo no es válido", Alert.AlertType.ERROR);
            txtCorreo.requestFocus();
            return false;
        }
        
        return true;
    }

    @FXML
    private void abrirMiPerfil(ActionEvent event) {
        try {
            System.out.println("Regresando a vista principal del paciente...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/VistaPaciente.fxml"));
            Parent vistaRoot = loader.load();
            
            Scene nuevaEscena = new Scene(vistaRoot);
            Stage currentStage = (Stage) btnMiPerfil.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Vista Principal - Paciente");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la vista principal", Alert.AlertType.ERROR);
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
        // Ya estamos en la edición de información, no hacer nada
        System.out.println("Ya estamos en la edición de información");
    }
    
    @FXML
    private void abrirCancelarCita(ActionEvent event) {
        try {
            System.out.println("Abriendo selección de cita para cancelar...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/seleccionarCita.fxml"));
            Parent seleccionarRoot = loader.load();
            
            // Configurar el controlador para cancelar
            SeleccionarCitaController controller = loader.getController();
            controller.setAccion("cancelar");
            controller.setPacienteId(SesionManager.getInstance().getUsuarioId());
            controller.setVentanaActual((Stage) btnCancelarCita.getScene().getWindow());
            
            Scene nuevaEscena = new Scene(seleccionarRoot);
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
            System.out.println("Abriendo selección de cita para reprogramar...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/seleccionarCita.fxml"));
            Parent seleccionarRoot = loader.load();
            
            // Configurar el controlador para reprogramar
            SeleccionarCitaController controller = loader.getController();
            controller.setAccion("reprogramar");
            controller.setPacienteId(SesionManager.getInstance().getUsuarioId());
            controller.setVentanaActual((Stage) btnReprogramarCita.getScene().getWindow());
            
            Scene nuevaEscena = new Scene(seleccionarRoot);
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
}
