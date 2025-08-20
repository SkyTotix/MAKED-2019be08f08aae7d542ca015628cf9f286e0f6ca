package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.gestorcitasmedicas.model.Paciente;
import com.gestorcitasmedicas.utils.DataPersistence;

import java.io.IOException;
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
        
        if (guardarUsuario(paciente)) {
            mostrarAlerta("Éxito", "Usuario registrado correctamente", Alert.AlertType.INFORMATION);
            regresarAGestionUsuarios();
        } else {
            mostrarAlerta("Error", "No se pudo registrar el usuario", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void cancelar() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Cancelación");
        alert.setHeaderText("Cancelar Registro");
        alert.setContentText("¿Está seguro de que desea cancelar el registro?\nLos datos ingresados se perderán.");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                regresarAGestionUsuarios();
            }
        });
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
    
    private boolean guardarUsuario(Paciente paciente) {
        try {
            // Guardar el paciente usando el sistema de persistencia JSON
            boolean guardado = paciente.guardar();
            
            if (guardado) {
                // Guardar todos los datos para persistencia
                DataPersistence.saveAllData(
                    Paciente.obtenerTodos(),
                    com.gestorcitasmedicas.model.Medico.obtenerTodos(),
                    com.gestorcitasmedicas.model.Consulta.obtenerTodas()
                );
                System.out.println("Usuario guardado exitosamente en JSON");
                return true;
            } else {
                System.err.println("Error al guardar el usuario");
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("Error al guardar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    private void regresarAGestionUsuarios() {
        try {
            System.out.println("Regresando a gestión de usuarios...");
            
            // Cargar la pantalla de gestión de usuarios
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/gestUsuarios.fxml"));
            Parent gestUsuariosRoot = loader.load();
            
            // Crear nueva escena
            Scene nuevaEscena = new Scene(gestUsuariosRoot, 1200, 800);
            
            // Obtener la ventana actual y reemplazarla
            Stage currentStage = (Stage) txtNombre.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Gestión de Usuarios - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
            System.out.println("Navegación a gestión de usuarios exitosa");
            
        } catch (IOException e) {
            System.err.println("Error al cargar gestUsuarios.fxml: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo regresar a la gestión de usuarios: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            System.err.println("Error inesperado al cargar gestUsuarios.fxml: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "Error inesperado al regresar a la gestión de usuarios: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    private void cerrarVentana() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }
}
