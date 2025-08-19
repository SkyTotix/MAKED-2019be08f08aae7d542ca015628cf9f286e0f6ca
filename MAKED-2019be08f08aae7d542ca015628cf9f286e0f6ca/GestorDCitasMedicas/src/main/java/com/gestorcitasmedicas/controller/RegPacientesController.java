package com.gestorcitasmedicas.controller;

import com.gestorcitasmedicas.model.Paciente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.regex.Pattern;

public class RegPacientesController {

    @FXML
    private TextField txtNombre;
    
    @FXML
    private TextField txtCurp;
    
    @FXML
    private TextField txtTelefono;
    
    @FXML
    private TextField txtMatricula;
    
    @FXML
    private TextField txtCorreo;
    
    @FXML
    private TextField txtContrasena;
    
    @FXML
    private TextField txtConfirmarContrasena;
    
    @FXML
    private Button btnRegistrar;
    
    @FXML
    private Button btnRegresar;

    @FXML
    private void initialize() {
        System.out.println("RegPacientesController inicializando...");
        
        // Configurar validación en tiempo real
        configurarValidacion();
        
        System.out.println("RegPacientesController inicializado correctamente");
    }
    
    private void configurarValidacion() {
        // Validación de CURP (formato básico)
        txtCurp.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !newValue.matches("[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[0-9A-Z][0-9]")) {
                txtCurp.setStyle("-fx-background-color: #FFE6E6; -fx-border-color: #FF0000;");
            } else {
                txtCurp.setStyle("-fx-background-color: #B9C3D5; -fx-border-color: transparent;");
            }
        });
        
        // Validación de teléfono (solo números)
        txtTelefono.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !newValue.matches("\\d{10}")) {
                txtTelefono.setStyle("-fx-background-color: #FFE6E6; -fx-border-color: #FF0000;");
            } else {
                txtTelefono.setStyle("-fx-background-color: #B9C3D5; -fx-border-color: transparent;");
            }
        });
        
        // Validación de correo electrónico
        txtCorreo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !isValidEmail(newValue)) {
                txtCorreo.setStyle("-fx-background-color: #FFE6E6; -fx-border-color: #FF0000;");
            } else {
                txtCorreo.setStyle("-fx-background-color: #B9C3D5; -fx-border-color: transparent;");
            }
        });
        
        // Validación de contraseñas
        txtConfirmarContrasena.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !newValue.equals(txtContrasena.getText())) {
                txtConfirmarContrasena.setStyle("-fx-background-color: #FFE6E6; -fx-border-color: #FF0000;");
            } else {
                txtConfirmarContrasena.setStyle("-fx-background-color: #B9C3D5; -fx-border-color: transparent;");
            }
        });
    }
    
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    @FXML
    private void registrar(ActionEvent event) {
        System.out.println("Intentando registrar paciente...");
        
        // Validar campos
        if (!validarCampos()) {
            return;
        }
        
        try {
            // Verificar si el correo ya existe
            if (Paciente.correoExiste(txtCorreo.getText().trim())) {
                mostrarAlerta("Error", "El correo electrónico ya está registrado en el sistema", Alert.AlertType.ERROR);
                txtCorreo.requestFocus();
                return;
            }
            
            // Verificar si el CURP ya existe
            if (Paciente.curpExiste(txtCurp.getText().trim())) {
                mostrarAlerta("Error", "El CURP ya está registrado en el sistema", Alert.AlertType.ERROR);
                txtCurp.requestFocus();
                return;
            }
            
            // Verificar si la matrícula ya existe
            if (Paciente.matriculaExiste(txtMatricula.getText().trim())) {
                mostrarAlerta("Error", "La matrícula ya está registrada en el sistema", Alert.AlertType.ERROR);
                txtMatricula.requestFocus();
                return;
            }
            
            // Crear objeto paciente
            Paciente paciente = new Paciente(
                txtNombre.getText().trim(),
                txtCurp.getText().trim(),
                txtTelefono.getText().trim(),
                txtMatricula.getText().trim(),
                txtCorreo.getText().trim(),
                txtContrasena.getText(),
                "Estudiante" // Por defecto, asumimos que es estudiante
            );
            
            // Guardar en la base de datos
            boolean guardado = paciente.guardar();
            
            if (guardado) {
                System.out.println("Paciente registrado exitosamente:");
                System.out.println("Nombre: " + paciente.getNombre());
                System.out.println("CURP: " + paciente.getCurp());
                System.out.println("Teléfono: " + paciente.getTelefono());
                System.out.println("Matrícula: " + paciente.getMatricula());
                System.out.println("Correo: " + paciente.getCorreo());
                
                // Mostrar mensaje de éxito
                mostrarAlerta("Éxito", 
                    "Paciente registrado correctamente\n\n" +
                    "Nombre: " + paciente.getNombre() + "\n" +
                    "CURP: " + paciente.getCurp() + "\n" +
                    "Matrícula: " + paciente.getMatricula() + "\n" +
                    "Correo: " + paciente.getCorreo() + "\n\n" +
                    "Ahora puede iniciar sesión con sus credenciales.", 
                    Alert.AlertType.INFORMATION);
                
                // Limpiar campos
                limpiarCampos();
                
                // Regresar al login
                regresarAlLogin();
            } else {
                mostrarAlerta("Error", "No se pudo registrar el paciente. Por favor, inténtelo nuevamente.", Alert.AlertType.ERROR);
            }
            
        } catch (Exception e) {
            System.err.println("Error al registrar paciente: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error del Sistema", 
                "Ocurrió un error inesperado durante el registro:\n\n" + e.getMessage() + "\n\n" +
                "Por favor, verifique su conexión e inténtelo nuevamente.", 
                Alert.AlertType.ERROR);
        }
    }
    
    private boolean validarCampos() {
        // Validar que todos los campos estén llenos
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El nombre es obligatorio", Alert.AlertType.ERROR);
            txtNombre.requestFocus();
            return false;
        }
        
        if (txtCurp.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El CURP es obligatorio", Alert.AlertType.ERROR);
            txtCurp.requestFocus();
            return false;
        }
        
        if (!txtCurp.getText().matches("[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[0-9A-Z][0-9]")) {
            mostrarAlerta("Error", "El formato del CURP no es válido", Alert.AlertType.ERROR);
            txtCurp.requestFocus();
            return false;
        }
        
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
        
        if (txtMatricula.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "La matrícula es obligatoria", Alert.AlertType.ERROR);
            txtMatricula.requestFocus();
            return false;
        }
        
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
        
        if (txtContrasena.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "La contraseña es obligatoria", Alert.AlertType.ERROR);
            txtContrasena.requestFocus();
            return false;
        }
        
        if (txtContrasena.getText().length() < 6) {
            mostrarAlerta("Error", "La contraseña debe tener al menos 6 caracteres", Alert.AlertType.ERROR);
            txtContrasena.requestFocus();
            return false;
        }
        
        if (txtConfirmarContrasena.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe confirmar la contraseña", Alert.AlertType.ERROR);
            txtConfirmarContrasena.requestFocus();
            return false;
        }
        
        if (!txtContrasena.getText().equals(txtConfirmarContrasena.getText())) {
            mostrarAlerta("Error", "Las contraseñas no coinciden", Alert.AlertType.ERROR);
            txtConfirmarContrasena.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void limpiarCampos() {
        txtNombre.clear();
        txtCurp.clear();
        txtTelefono.clear();
        txtMatricula.clear();
        txtCorreo.clear();
        txtContrasena.clear();
        txtConfirmarContrasena.clear();
        
        // Restaurar estilos
        txtNombre.setStyle("-fx-background-color: #B9C3D5; -fx-border-color: transparent;");
        txtCurp.setStyle("-fx-background-color: #B9C3D5; -fx-border-color: transparent;");
        txtTelefono.setStyle("-fx-background-color: #B9C3D5; -fx-border-color: transparent;");
        txtMatricula.setStyle("-fx-background-color: #B9C3D5; -fx-border-color: transparent;");
        txtCorreo.setStyle("-fx-background-color: #B9C3D5; -fx-border-color: transparent;");
        txtContrasena.setStyle("-fx-background-color: #B9C3D5; -fx-border-color: transparent;");
        txtConfirmarContrasena.setStyle("-fx-background-color: #B9C3D5; -fx-border-color: transparent;");
    }
    
    @FXML
    private void regresar(ActionEvent event) {
        regresarAlLogin();
    }
    
    private void regresarAlLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/login.fxml"));
            Parent loginRoot = loader.load();
            
            Scene nuevaEscena = new Scene(loginRoot, 800, 600);
            Stage currentStage = (Stage) btnRegresar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Bienvenido a tu gestor de citas medicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo regresar al login", Alert.AlertType.ERROR);
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
