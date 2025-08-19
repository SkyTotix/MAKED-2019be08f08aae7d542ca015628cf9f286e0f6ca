package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.gestorcitasmedicas.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModificarExpedienteController {
    
    @FXML
    private TextArea txtDiagnostico;
    
    @FXML
    private TextArea txtTratamiento;
    
    @FXML
    private TextArea txtObservaciones;
    
    @FXML
    private TextField txtFC;
    
    @FXML
    private TextField txtFR;
    
    @FXML
    private TextField txtTA;
    
    @FXML
    private TextField txtTC;
    
    @FXML
    private Button btnGuardar;
    
    @FXML
    private Button btnCancelar;
    
    private int pacienteId;
    
    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
        cargarDatosExpediente();
    }
    
    @FXML
    private void initialize() {
        // Configurar eventos
        configurarEventos();
    }
    
    private void configurarEventos() {
        btnGuardar.setOnAction(e -> guardarCambios());
        btnCancelar.setOnAction(e -> cancelar());
    }
    
    private void cargarDatosExpediente() {
        if (pacienteId <= 0) {
            mostrarAlerta("Error", "ID de paciente no válido", Alert.AlertType.ERROR);
            return;
        }
        
        String query = "SELECT diagnostico, tratamiento, observaciones, fc, fr, ta, tc FROM expedientes WHERE id_paciente = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, pacienteId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                txtDiagnostico.setText(rs.getString("diagnostico"));
                txtTratamiento.setText(rs.getString("tratamiento"));
                txtObservaciones.setText(rs.getString("observaciones"));
                txtFC.setText(rs.getString("fc"));
                txtFR.setText(rs.getString("fr"));
                txtTA.setText(rs.getString("ta"));
                txtTC.setText(rs.getString("tc"));
            } else {
                // Si no existe expediente, crear uno nuevo
                mostrarAlerta("Información", "No se encontró expediente para este paciente. Se creará uno nuevo.", Alert.AlertType.INFORMATION);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al cargar datos del expediente", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void guardarCambios() {
        if (!validarCampos()) {
            return;
        }
        
        if (guardarExpediente()) {
            mostrarAlerta("Éxito", "Expediente actualizado correctamente", Alert.AlertType.INFORMATION);
            cerrarVentana();
        } else {
            mostrarAlerta("Error", "No se pudo actualizar el expediente", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void cancelar() {
        cerrarVentana();
    }
    
    private boolean validarCampos() {
        // Validar que al menos un campo tenga contenido
        if (txtDiagnostico.getText().trim().isEmpty() && 
            txtTratamiento.getText().trim().isEmpty() && 
            txtObservaciones.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar al menos un diagnóstico, tratamiento u observación", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar signos vitales (opcionales pero si se ingresan deben ser números)
        if (!txtFC.getText().trim().isEmpty() && !esNumero(txtFC.getText().trim())) {
            mostrarAlerta("Error", "La frecuencia cardíaca debe ser un número", Alert.AlertType.ERROR);
            return false;
        }
        
        if (!txtFR.getText().trim().isEmpty() && !esNumero(txtFR.getText().trim())) {
            mostrarAlerta("Error", "La frecuencia respiratoria debe ser un número", Alert.AlertType.ERROR);
            return false;
        }
        
        if (!txtTA.getText().trim().isEmpty() && !esNumero(txtTA.getText().trim())) {
            mostrarAlerta("Error", "La tensión arterial debe ser un número", Alert.AlertType.ERROR);
            return false;
        }
        
        if (!txtTC.getText().trim().isEmpty() && !esNumero(txtTC.getText().trim())) {
            mostrarAlerta("Error", "La temperatura corporal debe ser un número", Alert.AlertType.ERROR);
            return false;
        }
        
        return true;
    }
    
    private boolean esNumero(String texto) {
        try {
            Double.parseDouble(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private boolean guardarExpediente() {
        // Primero verificar si existe el expediente
        String checkQuery = "SELECT COUNT(*) FROM expedientes WHERE id_paciente = ?";
        String insertQuery = "INSERT INTO expedientes (id_paciente, diagnostico, tratamiento, observaciones, fc, fr, ta, tc) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String updateQuery = "UPDATE expedientes SET diagnostico = ?, tratamiento = ?, observaciones = ?, fc = ?, fr = ?, ta = ?, tc = ? WHERE id_paciente = ?";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            
            // Verificar si existe
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, pacienteId);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            boolean existe = rs.getInt(1) > 0;
            
            PreparedStatement stmt;
            if (existe) {
                // Actualizar
                stmt = conn.prepareStatement(updateQuery);
                stmt.setString(1, txtDiagnostico.getText().trim());
                stmt.setString(2, txtTratamiento.getText().trim());
                stmt.setString(3, txtObservaciones.getText().trim());
                stmt.setString(4, txtFC.getText().trim());
                stmt.setString(5, txtFR.getText().trim());
                stmt.setString(6, txtTA.getText().trim());
                stmt.setString(7, txtTC.getText().trim());
                stmt.setInt(8, pacienteId);
            } else {
                // Insertar
                stmt = conn.prepareStatement(insertQuery);
                stmt.setInt(1, pacienteId);
                stmt.setString(2, txtDiagnostico.getText().trim());
                stmt.setString(3, txtTratamiento.getText().trim());
                stmt.setString(4, txtObservaciones.getText().trim());
                stmt.setString(5, txtFC.getText().trim());
                stmt.setString(6, txtFR.getText().trim());
                stmt.setString(7, txtTA.getText().trim());
                stmt.setString(8, txtTC.getText().trim());
            }
            
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
        Stage stage = (Stage) btnGuardar.getScene().getWindow();
        stage.close();
    }
}
