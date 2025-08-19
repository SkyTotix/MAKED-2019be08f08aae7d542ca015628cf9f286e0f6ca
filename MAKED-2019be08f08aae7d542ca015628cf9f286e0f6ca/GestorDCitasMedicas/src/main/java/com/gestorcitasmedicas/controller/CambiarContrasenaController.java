package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.gestorcitasmedicas.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CambiarContrasenaController {
    
    @FXML
    private PasswordField txtContrasenaActual;
    
    @FXML
    private PasswordField txtNuevaContrasena;
    
    @FXML
    private PasswordField txtConfirmarContrasena;
    
    private int userId;
    private String userRol;
    
    public void setUserInfo(int userId, String userRol) {
        this.userId = userId;
        this.userRol = userRol;
    }
    
    @FXML
    private void confirmarCambio() {
        String contrasenaActual = txtContrasenaActual.getText();
        String nuevaContrasena = txtNuevaContrasena.getText();
        String confirmarContrasena = txtConfirmarContrasena.getText();
        
        // Validaciones
        if (!validarCampos(contrasenaActual, nuevaContrasena, confirmarContrasena)) {
            return;
        }
        
        // Verificar contraseña actual
        if (!verificarContrasenaActual(contrasenaActual)) {
            mostrarAlerta("Error", "La contraseña actual es incorrecta", Alert.AlertType.ERROR);
            return;
        }
        
        // Actualizar contraseña
        if (actualizarContrasena(nuevaContrasena)) {
            mostrarAlerta("Éxito", "Contraseña actualizada correctamente", Alert.AlertType.INFORMATION);
            cerrarDialogo();
        } else {
            mostrarAlerta("Error", "No se pudo actualizar la contraseña", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void cancelarCambio() {
        cerrarDialogo();
    }
    
    private boolean validarCampos(String contrasenaActual, String nuevaContrasena, String confirmarContrasena) {
        if (contrasenaActual.isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar su contraseña actual", Alert.AlertType.ERROR);
            return false;
        }
        
        if (nuevaContrasena.isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar una nueva contraseña", Alert.AlertType.ERROR);
            return false;
        }
        
        if (nuevaContrasena.length() < 6) {
            mostrarAlerta("Error", "La nueva contraseña debe tener al menos 6 caracteres", Alert.AlertType.ERROR);
            return false;
        }
        
        if (!nuevaContrasena.equals(confirmarContrasena)) {
            mostrarAlerta("Error", "Las contraseñas no coinciden", Alert.AlertType.ERROR);
            return false;
        }
        
        if (contrasenaActual.equals(nuevaContrasena)) {
            mostrarAlerta("Error", "La nueva contraseña debe ser diferente a la actual", Alert.AlertType.ERROR);
            return false;
        }
        
        return true;
    }
    
    private boolean verificarContrasenaActual(String contrasenaActual) {
        String query = "SELECT contrasena FROM usuarios WHERE id_usuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            var rs = stmt.executeQuery();
            
            if (rs.next()) {
                String contrasenaBD = rs.getString("contrasena");
                return contrasenaBD.equals(contrasenaActual);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private boolean actualizarContrasena(String nuevaContrasena) {
        String query = "UPDATE usuarios SET contrasena = ? WHERE id_usuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, nuevaContrasena);
            stmt.setInt(2, userId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    private void cerrarDialogo() {
        Stage stage = (Stage) txtContrasenaActual.getScene().getWindow();
        stage.close();
    }
}
