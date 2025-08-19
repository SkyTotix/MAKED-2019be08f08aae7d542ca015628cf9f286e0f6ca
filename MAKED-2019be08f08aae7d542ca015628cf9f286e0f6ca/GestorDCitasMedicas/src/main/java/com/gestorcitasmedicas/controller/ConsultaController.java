package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsultaController {

    // Campos del doctor
    @FXML
    private Label lblNombreDoctor;
    
    @FXML
    private Label lblCedulaDoctor;
    
    @FXML
    private Label lblFechaHora;
    
    @FXML
    private Label lblFolio;
    
    // Campos del paciente
    @FXML
    private Label lblNombrePaciente;
    
    @FXML
    private Label lblCurpPaciente;
    
    @FXML
    private Label lblFechaNacimiento;
    
    @FXML
    private Label lblSintomatologia;
    
    // Campos médicos
    @FXML
    private Label lblFC;
    
    @FXML
    private Label lblFR;
    
    @FXML
    private Label lblTA;
    
    @FXML
    private Label lblTC;
    
    @FXML
    private Label lblDiagnostico;
    
    @FXML
    private Label lblTratamiento;
    
    // Botones
    @FXML
    private Button btnRegresar;
    
    @FXML
    private Button btnPerfil;
    
    @FXML
    private Button btnCitas;
    
    @FXML
    private Button btnExpedientes;
    
    @FXML
    private Button btnSalir;

    // Datos de la consulta actual
    private String pacienteActual;
    private String sintomatologiaActual;

    @FXML
    private void initialize() {
        System.out.println("ConsultaController inicializando...");
        
        // Cargar datos del doctor
        cargarDatosDoctor();
        
        // Cargar datos de la consulta actual
        cargarDatosConsulta();
        
        System.out.println("ConsultaController inicializado correctamente");
    }
    
    private void cargarDatosDoctor() {
        // Datos simulados del doctor
        lblNombreDoctor.setText("Dr. Carlos Mendoza Vega");
        lblCedulaDoctor.setText("Cédula: 12345678");
        
        // Fecha y hora actual
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        lblFechaHora.setText(ahora.format(formatter));
        
        // Folio de consulta (simulado)
        lblFolio.setText("FOL-2024-001");
    }
    
    private void cargarDatosConsulta() {
        // Datos simulados del paciente (se pueden pasar desde la ventana anterior)
        lblNombrePaciente.setText("María González López");
        lblCurpPaciente.setText("GONL850315MDFXXX01");
        lblFechaNacimiento.setText("15/03/1985");
        lblSintomatologia.setText("Dolor de cabeza intenso, fiebre de 38.5°C, náuseas y sensibilidad a la luz. El dolor comenzó hace 2 días y se intensifica con el movimiento.");
        
        // Signos vitales simulados
        lblFC.setText("85");
        lblFR.setText("18");
        lblTA.setText("120/80");
        lblTC.setText("36.8");
        
        // Diagnóstico y tratamiento (inicialmente vacíos para que el doctor los complete)
        lblDiagnostico.setText("Pendiente de diagnóstico");
        lblTratamiento.setText("Pendiente de tratamiento");
        
        // Guardar datos para uso posterior
        pacienteActual = lblNombrePaciente.getText();
        sintomatologiaActual = lblSintomatologia.getText();
    }
    
    // Método para cargar datos de una cita específica (llamado desde otras ventanas)
    public void cargarDatosCita(String nombrePaciente, String sintomatologia) {
        if (nombrePaciente != null && !nombrePaciente.isEmpty()) {
            lblNombrePaciente.setText(nombrePaciente);
            pacienteActual = nombrePaciente;
        }
        
        if (sintomatologia != null && !sintomatologia.isEmpty()) {
            lblSintomatologia.setText(sintomatologia);
            sintomatologiaActual = sintomatologia;
        }
        
        System.out.println("Datos de cita cargados: " + nombrePaciente + " - " + sintomatologia);
    }
    
    @FXML
    private void regresar(ActionEvent event) {
        try {
            System.out.println("Regresando a expedientes médicos...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/expedientesDoctor.fxml"));
            Parent expedientesRoot = loader.load();
            
            Scene nuevaEscena = new Scene(expedientesRoot);
            Stage currentStage = (Stage) btnRegresar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Expedientes Médicos");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo regresar a expedientes médicos", Alert.AlertType.ERROR);
        }
    }
    
    // Métodos para el menú lateral
    @FXML
    private void abrirPerfil(ActionEvent event) {
        try {
            System.out.println("Abriendo perfil del médico...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/perfilDocMod.fxml"));
            Parent perfilRoot = loader.load();
            
            Scene nuevaEscena = new Scene(perfilRoot);
            Stage currentStage = (Stage) btnPerfil.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Mi Perfil - Médico");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el perfil del médico", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirCitas(ActionEvent event) {
        try {
            System.out.println("Abriendo gestión de citas...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/citasDoctor.fxml"));
            Parent citasRoot = loader.load();
            
            Scene nuevaEscena = new Scene(citasRoot);
            Stage currentStage = (Stage) btnCitas.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Gestión de Citas - Médico");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la gestión de citas", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void abrirExpedientes(ActionEvent event) {
        try {
            System.out.println("Abriendo expedientes médicos...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/expedientesDoctor.fxml"));
            Parent expedientesRoot = loader.load();
            
            Scene nuevaEscena = new Scene(expedientesRoot);
            Stage currentStage = (Stage) btnExpedientes.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Expedientes Médicos");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar los expedientes médicos", Alert.AlertType.ERROR);
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
    
    // Método para actualizar diagnóstico (se podría implementar con un botón de edición)
    public void actualizarDiagnostico(String diagnostico) {
        if (diagnostico != null && !diagnostico.isEmpty()) {
            lblDiagnostico.setText(diagnostico);
        }
    }
    
    // Método para actualizar tratamiento (se podría implementar con un botón de edición)
    public void actualizarTratamiento(String tratamiento) {
        if (tratamiento != null && !tratamiento.isEmpty()) {
            lblTratamiento.setText(tratamiento);
        }
    }
    
    // Método para actualizar signos vitales (se podría implementar con campos editables)
    public void actualizarSignosVitales(String fc, String fr, String ta, String tc) {
        if (fc != null) lblFC.setText(fc);
        if (fr != null) lblFR.setText(fr);
        if (ta != null) lblTA.setText(ta);
        if (tc != null) lblTC.setText(tc);
    }
    
    @FXML
    private void modificarExpediente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/modificarExpediente.fxml"));
            Parent root = loader.load();
            
            ModificarExpedienteController controller = loader.getController();
            // TODO: Pasar el ID del paciente actual
            // controller.setPacienteId(pacienteActual.getId());
            
            Stage stage = new Stage();
            stage.setTitle("Modificar Expediente Médico");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();
            
            // Recargar datos del expediente después de la modificación
            cargarDatosExpediente();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana de modificación", Alert.AlertType.ERROR);
        }
    }
    
    private void cargarDatosExpediente() {
        // TODO: Implementar recarga de datos del expediente
        mostrarAlerta("Información", "Datos del expediente actualizados", Alert.AlertType.INFORMATION);
    }
}
