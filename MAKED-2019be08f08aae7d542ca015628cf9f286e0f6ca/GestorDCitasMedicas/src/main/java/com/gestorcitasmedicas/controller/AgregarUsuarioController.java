package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.gestorcitasmedicas.model.Paciente;
import com.gestorcitasmedicas.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class AgregarUsuarioController {
    
    @FXML
    private TextField txtNombre;
    
    @FXML
    private TextField txtCurp;
    
    @FXML
    private TextField txtTelefono;
    
    @FXML
    private ComboBox<String> cboTipo;
    
    @FXML
    private TextField txtMatricula;
    
    @FXML
    private TextField txtCorreo;
    
    @FXML
    private PasswordField txtContrasena;
    
    @FXML
    private PasswordField txtConfirmarContrasena;
    
    @FXML
    private void initialize() {
        cargarTiposUsuario();
    }
    
    private void cargarTiposUsuario() {
        ObservableList<String> tipos = FXCollections.observableArrayList(
            "Estudiante",
            "Personal UTEZ"
        );
        cboTipo.setItems(tipos);
    }
    
    @FXML
    private void guardarUsuario() {
        if (!validarCampos()) {
            return;
        }
        
        Paciente paciente = new Paciente(
            txtNombre.getText().trim(),
            txtCurp.getText().trim(),
            txtTelefono.getText().trim(),
            txtMatricula.getText().trim(),
            txtCorreo.getText().trim(),
            txtContrasena.getText(),
            cboTipo.getValue()
        );
        
        if (guardarUsuarioEnBD(paciente)) {
            mostrarAlerta("Éxito", "Usuario registrado correctamente", Alert.AlertType.INFORMATION);
            cerrarVentana();
        } else {
            mostrarAlerta("Error", "No se pudo registrar el usuario", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void cancelar() {
        cerrarVentana();
    }
    
    private boolean validarCampos() {
        // Validar nombre
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar el nombre completo", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar CURP
        String curp = txtCurp.getText().trim();
        if (curp.isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar el CURP", Alert.AlertType.ERROR);
            return false;
        }
        
        if (!Pattern.matches("^[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[0-9A-Z][0-9]$", curp)) {
            mostrarAlerta("Error", "El formato del CURP no es válido", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar teléfono
        if (txtTelefono.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar el número de teléfono", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar tipo de usuario
        if (cboTipo.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar el tipo de usuario", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar matrícula
        if (txtMatricula.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar la matrícula o número de empleado", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar correo
        String correo = txtCorreo.getText().trim();
        if (correo.isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar el correo electrónico", Alert.AlertType.ERROR);
            return false;
        }
        
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", correo)) {
            mostrarAlerta("Error", "El formato del correo electrónico no es válido", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar contraseña
        String contrasena = txtContrasena.getText();
        if (contrasena.isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar una contraseña", Alert.AlertType.ERROR);
            return false;
        }
        
        if (contrasena.length() < 6) {
            mostrarAlerta("Error", "La contraseña debe tener al menos 6 caracteres", Alert.AlertType.ERROR);
            return false;
        }
        
        if (!contrasena.equals(txtConfirmarContrasena.getText())) {
            mostrarAlerta("Error", "Las contraseñas no coinciden", Alert.AlertType.ERROR);
            return false;
        }
        
        return true;
    }
    
    private boolean guardarUsuarioEnBD(Paciente paciente) {
        String query = "INSERT INTO pacientes (nombre, curp, telefono, tipo, matricula, correo, contrasena, rol) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getCurp());
            stmt.setString(3, paciente.getTelefono());
            stmt.setString(4, paciente.getTipo());
            stmt.setString(5, paciente.getMatricula());
            stmt.setString(6, paciente.getCorreo());
            stmt.setString(7, paciente.getContrasena());
            stmt.setString(8, "paciente");
            
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
    
    private void cerrarVentana() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }
}
