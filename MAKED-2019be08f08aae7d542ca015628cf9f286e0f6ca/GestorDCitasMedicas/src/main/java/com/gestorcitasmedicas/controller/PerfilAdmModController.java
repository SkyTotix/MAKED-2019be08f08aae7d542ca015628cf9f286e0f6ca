package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.Optional;

public class PerfilAdmModController {

    @FXML
    private Label lblNombre;
    
    @FXML
    private Label lblCurp;
    
    @FXML
    private Label lblNumEmpleado;
    
    @FXML
    private TextField txtTelefono;
    
    @FXML
    private TextField txtCorreo;
    
    @FXML
    private Button btnCancelar;
    
    @FXML
    private Button btnAceptar;
    
    // Botones del menú lateral
    @FXML
    private Button btnUsuarios;
    
    @FXML
    private Button btnMedicos;
    
    @FXML
    private Button btnCitas;
    
    @FXML
    private Button btnSalir;
    
    @FXML
    private Button btnVolverMenu;
    
    @FXML
    private Button btnVolverMenuPrincipal;
    
    // Datos del perfil (simulados)
    private String nombre = "Juan Carlos Pérez López";
    private String curp = "PELJ850315HDFXXX01";
    private String numEmpleado = "EMP001";
    private String telefono = "555-123-4567";
    private String correo = "juan.perez@clinica.com";

    @FXML
    private void initialize() {
        cargarDatosPerfil();
        configurarEventosBotones();
    }
    
    private void cargarDatosPerfil() {
        // Cargar datos del perfil
        lblNombre.setText(nombre);
        lblCurp.setText(curp);
        lblNumEmpleado.setText(numEmpleado);
        txtTelefono.setText(telefono);
        txtCorreo.setText(correo);
    }
    
    private void configurarEventosBotones() {
        btnCancelar.setOnAction(event -> cancelarCambios(event));
        btnAceptar.setOnAction(event -> guardarCambios(event));
    }
    
    @FXML
    private void cambiarContrasena() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/cambiarContrasena.fxml"));
            Parent root = loader.load();
            
            CambiarContrasenaController controller = loader.getController();
            controller.setUserInfo(1, "admin"); // TODO: Obtener ID real del usuario
            
            Stage stage = new Stage();
            stage.setTitle("Cambiar Contraseña");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir el diálogo de cambio de contraseña", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void cambiarEstado() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cambiar Estado");
        alert.setHeaderText(null);
        alert.setContentText("¿Desea cambiar su estado de activo a inactivo (o viceversa)?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // TODO: Implementar cambio de estado en la base de datos
            mostrarAlerta("Éxito", "Estado cambiado correctamente", Alert.AlertType.INFORMATION);
        }
    }
    
    @FXML
    private void cancelarCambios(ActionEvent event) {
        // Restaurar valores originales
        txtTelefono.setText(telefono);
        txtCorreo.setText(correo);
        
        mostrarAlerta("Información", "Cambios cancelados", Alert.AlertType.INFORMATION);
    }
    
    @FXML
    private void guardarCambios(ActionEvent event) {
        if (validarCampos()) {
            // Actualizar datos
            telefono = txtTelefono.getText().trim();
            correo = txtCorreo.getText().trim();
            
            // Aquí se guardarían los cambios en la base de datos
            System.out.println("Perfil actualizado:");
            System.out.println("Teléfono: " + telefono);
            System.out.println("Correo: " + correo);
            
            mostrarAlerta("Éxito", "Perfil actualizado correctamente", Alert.AlertType.INFORMATION);
            
            // Regresar al menú principal
            volverAlMenuPrincipal(null);
        }
    }
    
    private boolean validarCampos() {
        String telefonoText = txtTelefono.getText().trim();
        String correoText = txtCorreo.getText().trim();
        
        if (telefonoText.isEmpty()) {
            mostrarAlerta("Error", "El teléfono no puede estar vacío", Alert.AlertType.ERROR);
            return false;
        }
        
        if (correoText.isEmpty()) {
            mostrarAlerta("Error", "El correo electrónico no puede estar vacío", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar formato de correo electrónico básico
        if (!correoText.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            mostrarAlerta("Error", "Formato de correo electrónico inválido", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar formato de teléfono básico
        if (!telefonoText.matches("^[0-9\\-\\+\\(\\)\\s]+$")) {
            mostrarAlerta("Error", "Formato de teléfono inválido", Alert.AlertType.ERROR);
            return false;
        }
        
        return true;
    }
    
    @FXML
    private void volverAlMenuPrincipal(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/mainRecepcionista.fxml"));
            Parent mainRecepcionistaRoot = loader.load();
            
            Scene nuevaEscena = new Scene(mainRecepcionistaRoot, 1200, 800);
            // Obtener el Stage desde cualquier botón que haya sido presionado
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Panel Principal - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo volver al menú principal", Alert.AlertType.ERROR);
        }
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    // Métodos para el menú lateral
    @FXML
    private void abrirGestionUsuarios(ActionEvent event) {
        try {
            System.out.println("Abriendo gestión de usuarios desde perfil...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/gestUsuarios.fxml"));
            Parent gestUsuariosRoot = loader.load();
            
            Scene nuevaEscena = new Scene(gestUsuariosRoot, 1200, 800);
            Stage currentStage = (Stage) btnUsuarios.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Gestión de Usuarios - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la gestión de usuarios", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirGestionMedicos(ActionEvent event) {
        try {
            System.out.println("Abriendo gestión de médicos desde perfil...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/gestMedicos.fxml"));
            Parent gestMedicosRoot = loader.load();
            
            Scene nuevaEscena = new Scene(gestMedicosRoot, 1200, 800);
            Stage currentStage = (Stage) btnMedicos.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Gestión de Médicos - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la gestión de médicos", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirGestionCitas(ActionEvent event) {
        try {
            System.out.println("Abriendo gestión de citas desde perfil...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/agendarCitaAdm.fxml"));
            Parent agendarCitaRoot = loader.load();
            
            Scene nuevaEscena = new Scene(agendarCitaRoot, 1200, 800);
            Stage currentStage = (Stage) btnCitas.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Agendar Cita Médica - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de agendar citas", Alert.AlertType.ERROR);
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
                    stage.show();

                    Stage currentStage = (Stage) btnSalir.getScene().getWindow();
                    currentStage.hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
