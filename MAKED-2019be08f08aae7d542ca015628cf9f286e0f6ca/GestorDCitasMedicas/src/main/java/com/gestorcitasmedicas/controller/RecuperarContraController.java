package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import com.gestorcitasmedicas.util.DatabaseConnection;

public class RecuperarContraController {

    @FXML
    private TextField txtCorreo;
    
    @FXML
    private PasswordField txtContrasena;
    
    @FXML
    private PasswordField txtConfirmarContrasena;
    
    @FXML
    private Button btnListo;
    
    @FXML
    private Button btnRegresar;
    
    @FXML
    private Button btnRegresarInferior;
    
    @FXML
    private Label lblCrearCuenta;

    @FXML
    private void initialize() {
        System.out.println("RecuperarContraController inicializado");
        
        // Configurar eventos
        configurarEventos();
    }
    
    private void configurarEventos() {
        // Evento para el botón de regresar (ya configurado en FXML con onAction="#regresarALogin")
        // btnRegresar.setOnAction(e -> regresarALogin());
        
        // Evento para crear cuenta
        lblCrearCuenta.setOnMouseClicked(e -> crearCuenta());
        
        System.out.println("Eventos configurados en RecuperarContraController");
    }
    
    @FXML
    private void actualizarContrasena() {
        String correo = txtCorreo.getText().trim();
        String contrasena = txtContrasena.getText();
        String confirmarContrasena = txtConfirmarContrasena.getText();
        
        // Validaciones
        if (!validarCampos(correo, contrasena, confirmarContrasena)) {
            return;
        }
        
        // Verificar si el correo existe en la base de datos
        if (!verificarCorreoEnBD(correo)) {
            mostrarAlerta("Error", "El correo electrónico ingresado no está registrado en la base de datos", Alert.AlertType.ERROR);
            return;
        }
        
        // Actualizar contraseña en la base de datos
        if (actualizarContrasenaEnBD(correo, contrasena)) {
            mostrarAlerta("Éxito", "Contraseña actualizada correctamente", Alert.AlertType.INFORMATION);
            limpiarFormulario();
            regresarALogin();
        } else {
            mostrarAlerta("Error", "No se pudo actualizar la contraseña", Alert.AlertType.ERROR);
        }
    }
    
    private boolean validarCampos(String correo, String contrasena, String confirmarContrasena) {
        // Validar correo electrónico
        if (correo.isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar su correo electrónico", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar formato de correo electrónico
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", correo)) {
            mostrarAlerta("Error", "El formato del correo electrónico no es válido", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar contraseña
        if (contrasena.isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar una contraseña", Alert.AlertType.ERROR);
            return false;
        }
        
        if (contrasena.length() < 6) {
            mostrarAlerta("Error", "La contraseña debe tener al menos 6 caracteres", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar confirmación de contraseña
        if (confirmarContrasena.isEmpty()) {
            mostrarAlerta("Error", "Debe confirmar su contraseña", Alert.AlertType.ERROR);
            return false;
        }
        
        if (!contrasena.equals(confirmarContrasena)) {
            mostrarAlerta("Error", "Las contraseñas no coinciden", Alert.AlertType.ERROR);
            return false;
        }
        
        return true;
    }
    

    

    
    private void limpiarFormulario() {
        txtCorreo.clear();
        txtContrasena.clear();
        txtConfirmarContrasena.clear();
    }
    
    private boolean verificarCorreoEnBD(String correo) {
        String query = "SELECT COUNT(*) FROM usuarios WHERE correo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private boolean actualizarContrasenaEnBD(String correo, String contrasena) {
        String query = "UPDATE usuarios SET contrasena = ? WHERE correo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, contrasena);
            stmt.setString(2, correo);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @FXML
    private void regresarALogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/login.fxml"));
            Parent loginRoot = loader.load();
            Scene nuevaEscena = new Scene(loginRoot);
            Stage currentStage = (Stage) btnRegresar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Bienvenido a tu gestor de citas medicas");
            currentStage.setMaximized(true);
            currentStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo regresar al login", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void crearCuenta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/Registro.fxml"));
            Parent registroRoot = loader.load();
            Scene nuevaEscena = new Scene(registroRoot);
            Stage currentStage = (Stage) lblCrearCuenta.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Registro - Gestor de Citas Médicas");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de registro", Alert.AlertType.ERROR);
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
