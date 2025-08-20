package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EditarConsultaController {
    
    @FXML private TextField txtDoctor;
    @FXML private TextField txtConsultorio;
    @FXML private TextField txtPaciente;
    @FXML private TextField txtHoraInicio;
    @FXML private TextField txtHoraFin;
    @FXML private TextArea txtMotivo;
    @FXML private Button btnGuardar;
    @FXML private Button btnCancelar;
    
    // Referencia a la consulta original
    private MainRecepcionistaController.Consulta consultaOriginal;
    private MainRecepcionistaController controladorPrincipal;
    
    @FXML
    private void initialize() {
        System.out.println("EditarConsultaController inicializando...");
        
        // Configurar validación de campos
        configurarValidacion();
        
        System.out.println("EditarConsultaController inicializado correctamente");
    }
    
    /**
     * Configura la consulta a editar
     */
    public void setConsulta(MainRecepcionistaController.Consulta consulta) {
        this.consultaOriginal = consulta;
        cargarDatosConsulta();
    }
    
    /**
     * Configura el controlador principal para actualizar la vista
     */
    public void setControladorPrincipal(MainRecepcionistaController controlador) {
        this.controladorPrincipal = controlador;
    }
    
    /**
     * Carga los datos de la consulta en los campos
     */
    private void cargarDatosConsulta() {
        if (consultaOriginal != null) {
            txtDoctor.setText(consultaOriginal.getDoctor());
            txtConsultorio.setText(consultaOriginal.getConsultorio());
            txtPaciente.setText(consultaOriginal.getPaciente());
            txtHoraInicio.setText(consultaOriginal.getHoraInicio().toString());
            txtHoraFin.setText(consultaOriginal.getHoraFin().toString());
            txtMotivo.setText(consultaOriginal.getMotivo());
        }
    }
    
    /**
     * Configura validación de campos
     */
    private void configurarValidacion() {
        // Validación de hora de inicio
        txtHoraInicio.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !esFormatoHoraValido(newValue)) {
                txtHoraInicio.setStyle("-fx-border-color: red;");
            } else {
                txtHoraInicio.setStyle("");
            }
        });
        
        // Validación de hora de fin
        txtHoraFin.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !esFormatoHoraValido(newValue)) {
                txtHoraFin.setStyle("-fx-border-color: red;");
            } else {
                txtHoraFin.setStyle("");
            }
        });
    }
    
    /**
     * Valida si el formato de hora es válido (HH:MM)
     */
    private boolean esFormatoHoraValido(String hora) {
        try {
            LocalTime.parse(hora, DateTimeFormatter.ofPattern("HH:mm"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    /**
     * Valida que todos los campos estén completos
     */
    private boolean validarCampos() {
        if (txtDoctor.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El campo Doctor es obligatorio", Alert.AlertType.ERROR);
            txtDoctor.requestFocus();
            return false;
        }
        
        if (txtConsultorio.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El campo Consultorio es obligatorio", Alert.AlertType.ERROR);
            txtConsultorio.requestFocus();
            return false;
        }
        
        if (txtPaciente.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El campo Paciente es obligatorio", Alert.AlertType.ERROR);
            txtPaciente.requestFocus();
            return false;
        }
        
        if (txtHoraInicio.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El campo Hora de Inicio es obligatorio", Alert.AlertType.ERROR);
            txtHoraInicio.requestFocus();
            return false;
        }
        
        if (txtHoraFin.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El campo Hora de Fin es obligatorio", Alert.AlertType.ERROR);
            txtHoraFin.requestFocus();
            return false;
        }
        
        if (txtMotivo.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El campo Motivo es obligatorio", Alert.AlertType.ERROR);
            txtMotivo.requestFocus();
            return false;
        }
        
        // Validar formato de horas
        if (!esFormatoHoraValido(txtHoraInicio.getText().trim())) {
            mostrarAlerta("Error", "Formato de hora de inicio inválido. Use HH:MM", Alert.AlertType.ERROR);
            txtHoraInicio.requestFocus();
            return false;
        }
        
        if (!esFormatoHoraValido(txtHoraFin.getText().trim())) {
            mostrarAlerta("Error", "Formato de hora de fin inválido. Use HH:MM", Alert.AlertType.ERROR);
            txtHoraFin.requestFocus();
            return false;
        }
        
        // Validar que la hora de fin sea posterior a la de inicio
        LocalTime horaInicio = LocalTime.parse(txtHoraInicio.getText().trim());
        LocalTime horaFin = LocalTime.parse(txtHoraFin.getText().trim());
        
        if (!horaFin.isAfter(horaInicio)) {
            mostrarAlerta("Error", "La hora de fin debe ser posterior a la hora de inicio", Alert.AlertType.ERROR);
            txtHoraFin.requestFocus();
            return false;
        }
        
        return true;
    }
    
    /**
     * Guarda los cambios de la consulta
     */
    @FXML
    private void guardarCambios(ActionEvent event) {
        System.out.println("Guardando cambios de consulta...");
        
        if (!validarCampos()) {
            return;
        }
        
        try {
            // Crear nueva consulta con los datos modificados
            MainRecepcionistaController.Consulta consultaModificada = new MainRecepcionistaController.Consulta(
                txtDoctor.getText().trim(),
                txtPaciente.getText().trim(),
                txtConsultorio.getText().trim(),
                LocalTime.parse(txtHoraInicio.getText().trim()),
                LocalTime.parse(txtHoraFin.getText().trim()),
                txtMotivo.getText().trim()
            );
            
            // Aquí se implementaría la lógica para actualizar la consulta en la base de datos
            // Por ahora, solo mostrar confirmación
            mostrarAlerta("Éxito", 
                "Consulta actualizada exitosamente:\n" +
                "Doctor: " + consultaModificada.getDoctor() + "\n" +
                "Paciente: " + consultaModificada.getPaciente() + "\n" +
                "Consultorio: " + consultaModificada.getConsultorio() + "\n" +
                "Horario: " + consultaModificada.getHoraInicio() + " - " + consultaModificada.getHoraFin() + "\n" +
                "Motivo: " + consultaModificada.getMotivo(), 
                Alert.AlertType.INFORMATION);
            
            // Cerrar la ventana de edición
            cerrarVentana();
            
            // Actualizar la vista principal si hay controlador
            if (controladorPrincipal != null) {
                controladorPrincipal.actualizarConsulta(consultaOriginal, consultaModificada);
            }
            
        } catch (Exception e) {
            System.err.println("Error al guardar cambios: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "Error al guardar los cambios: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    /**
     * Cancela la edición y cierra la ventana
     */
    @FXML
    private void cancelarEdicion(ActionEvent event) {
        System.out.println("Cancelando edición de consulta...");
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Cancelación");
        alert.setHeaderText("Cancelar Edición");
        alert.setContentText("¿Está seguro de que desea cancelar la edición?\nLos cambios no se guardarán.");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                cerrarVentana();
            }
        });
    }
    
    /**
     * Cierra la ventana de edición
     */
    private void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Muestra una alerta al usuario
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
