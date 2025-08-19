package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.gestorcitasmedicas.model.Medico;
import com.gestorcitasmedicas.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class AgregarMedicoController {
    
    @FXML
    private TextField txtNombre;
    
    @FXML
    private ComboBox<String> cboEspecialidad;
    
    @FXML
    private TextField txtCedula;
    
    @FXML
    private TextField txtCorreo;
    
    @FXML
    private TextField txtTelefono;
    
    @FXML
    private ComboBox<String> cboHorario;
    
    @FXML
    private ComboBox<String> cboConsultorio;
    
    @FXML
    private PasswordField txtContrasena;
    
    @FXML
    private PasswordField txtConfirmarContrasena;
    
    @FXML
    private void initialize() {
        cargarEspecialidades();
        cargarHorarios();
        cargarConsultorios();
    }
    
    private void cargarEspecialidades() {
        ObservableList<String> especialidades = FXCollections.observableArrayList(
            "Medicina General",
            "Cardiología",
            "Dermatología",
            "Endocrinología",
            "Gastroenterología",
            "Ginecología",
            "Neurología",
            "Oftalmología",
            "Ortopedia",
            "Pediatría",
            "Psiquiatría",
            "Radiología",
            "Traumatología",
            "Urología"
        );
        cboEspecialidad.setItems(especialidades);
    }
    
    private void cargarHorarios() {
        ObservableList<String> horarios = FXCollections.observableArrayList(
            "Lunes a Viernes 8:00 - 14:00",
            "Lunes a Viernes 14:00 - 20:00",
            "Lunes a Viernes 8:00 - 20:00",
            "Lunes, Miércoles, Viernes 8:00 - 14:00",
            "Martes, Jueves 14:00 - 20:00",
            "Sábados 8:00 - 14:00"
        );
        cboHorario.setItems(horarios);
    }
    
    private void cargarConsultorios() {
        ObservableList<String> consultorios = FXCollections.observableArrayList(
            "Consultorio 1",
            "Consultorio 2",
            "Consultorio 3",
            "Consultorio 4",
            "Consultorio 5",
            "Consultorio 6",
            "Consultorio 7",
            "Consultorio 8"
        );
        cboConsultorio.setItems(consultorios);
    }
    
    @FXML
    private void guardarMedico() {
        if (!validarCampos()) {
            return;
        }
        
        Medico medico = new Medico(
            txtNombre.getText().trim(),
            cboEspecialidad.getValue(),
            txtCedula.getText().trim(),
            txtCorreo.getText().trim(),
            txtTelefono.getText().trim(),
            cboHorario.getValue(),
            cboConsultorio.getValue(),
            txtContrasena.getText()
        );
        
        if (guardarMedicoEnBD(medico)) {
            mostrarAlerta("Éxito", "Médico registrado correctamente", Alert.AlertType.INFORMATION);
            cerrarVentana();
        } else {
            mostrarAlerta("Error", "No se pudo registrar el médico", Alert.AlertType.ERROR);
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
        
        // Validar especialidad
        if (cboEspecialidad.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar una especialidad", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar cédula
        if (txtCedula.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar el número de cédula", Alert.AlertType.ERROR);
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
        
        // Validar teléfono
        if (txtTelefono.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar el número de teléfono", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar horario
        if (cboHorario.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar un horario", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar consultorio
        if (cboConsultorio.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar un consultorio", Alert.AlertType.ERROR);
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
    
    private boolean guardarMedicoEnBD(Medico medico) {
        String query = "INSERT INTO medicos (nombre, especialidad, cedula, correo, telefono, horario, consultorio, contrasena, rol) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, medico.getNombre());
            stmt.setString(2, medico.getEspecialidad());
            stmt.setString(3, medico.getCedula());
            stmt.setString(4, medico.getCorreo());
            stmt.setString(5, medico.getTelefono());
            stmt.setString(6, medico.getHorario());
            stmt.setString(7, medico.getConsultorio());
            stmt.setString(8, medico.getContrasena());
            stmt.setString(9, "medico");
            
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
