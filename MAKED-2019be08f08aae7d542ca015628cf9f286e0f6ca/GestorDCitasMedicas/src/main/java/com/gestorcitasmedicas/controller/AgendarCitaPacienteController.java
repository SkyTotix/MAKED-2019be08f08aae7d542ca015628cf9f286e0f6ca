package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.gestorcitasmedicas.model.Consulta;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.DayOfWeek;

public class AgendarCitaPacienteController {
    
    @FXML private ComboBox<String> cboConsultorio;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> cboHora;
    @FXML private TextArea txtMotivo;
    @FXML private Button btnCancelar;
    @FXML private Button btnAgendar;
    
    private int pacienteId; // ID del paciente logueado
    
    @FXML
    private void initialize() {
        cargarConsultorios();
        cargarHorarios();
        configurarDatePicker();
        configurarEventos();
    }
    
    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }
    
    private void cargarConsultorios() {
        ObservableList<String> consultorios = FXCollections.observableArrayList(
            "Consultorio 1 - Medicina General",
            "Consultorio 2 - Pediatría", 
            "Consultorio 3 - Ginecología",
            "Consultorio 4 - Cardiología",
            "Consultorio 5 - Dermatología",
            "Consultorio 6 - Psicología"
        );
        cboConsultorio.setItems(consultorios);
    }
    
    private void cargarHorarios() {
        ObservableList<String> horarios = FXCollections.observableArrayList(
            "08:00", "08:30", "09:00", "09:30", "10:00", "10:30",
            "11:00", "11:30", "12:00", "12:30", "13:00", "13:30",
            "14:00", "14:30", "15:00", "15:30", "16:00", "16:30",
            "17:00", "17:30", "18:00", "18:30", "19:00"
        );
        cboHora.setItems(horarios);
    }
    
    private void configurarDatePicker() {
        // Configurar fecha mínima (mañana)
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                
                if (date != null && !empty) {
                    // Solo permitir fechas futuras con al menos 24 horas de anticipación
                    LocalDate fechaMinima = LocalDate.now().plusDays(1);
                    
                    // Solo permitir días de lunes a viernes
                    boolean esDiaLaboral = date.getDayOfWeek() != DayOfWeek.SATURDAY && 
                                         date.getDayOfWeek() != DayOfWeek.SUNDAY;
                    
                    if (date.isBefore(fechaMinima) || !esDiaLaboral) {
                        setDisable(true);
                        setStyle("-fx-background-color: #ffebee;");
                    }
                }
            }
        });
    }
    
    private void configurarEventos() {
        // Configurar fecha por defecto (mañana)
        datePicker.setValue(LocalDate.now().plusDays(1));
    }
    
    @FXML
    private void agendarCita() {
        // Validar campos
        if (!validarCampos()) {
            return;
        }
        
        // Obtener datos del formulario
        String consultorio = cboConsultorio.getValue();
        LocalDate fecha = datePicker.getValue();
        String horaStr = cboHora.getValue();
        String motivo = txtMotivo.getText().trim();
        
        // Convertir hora string a LocalTime
        LocalTime hora = LocalTime.parse(horaStr, DateTimeFormatter.ofPattern("HH:mm"));
        
        // Asignar médico basado en el consultorio seleccionado
        int idMedico = asignarMedicoPorConsultorio(consultorio);
        
        // Crear nueva consulta
        Consulta nuevaCita = new Consulta();
        nuevaCita.setIdPaciente(pacienteId);
        nuevaCita.setIdMedico(idMedico);
        nuevaCita.setFecha(fecha);
        nuevaCita.setHora(hora);
        nuevaCita.setMotivo(motivo.isEmpty() ? "Consulta general" : motivo);
        nuevaCita.setEstado("Programada");
        
        // Guardar en base de datos
        if (guardarCitaEnBD(nuevaCita)) {
            mostrarAlerta("Éxito", "Cita agendada correctamente", Alert.AlertType.INFORMATION);
            cerrarVentana();
        } else {
            mostrarAlerta("Error", "No se pudo agendar la cita. Intente nuevamente.", Alert.AlertType.ERROR);
        }
    }
    
    private boolean validarCampos() {
        // Validar consultorio
        if (cboConsultorio.getValue() == null || cboConsultorio.getValue().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar un consultorio", Alert.AlertType.ERROR);
            cboConsultorio.requestFocus();
            return false;
        }
        
        // Validar fecha
        if (datePicker.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar una fecha", Alert.AlertType.ERROR);
            datePicker.requestFocus();
            return false;
        }
        
        // Validar que la fecha sea al menos mañana
        LocalDate fechaMinima = LocalDate.now().plusDays(1);
        if (datePicker.getValue().isBefore(fechaMinima)) {
            mostrarAlerta("Error", "La cita debe programarse con al menos 24 horas de anticipación", Alert.AlertType.ERROR);
            datePicker.requestFocus();
            return false;
        }
        
        // Validar que sea día laboral
        DayOfWeek diaSemana = datePicker.getValue().getDayOfWeek();
        if (diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY) {
            mostrarAlerta("Error", "No se pueden programar citas en fines de semana", Alert.AlertType.ERROR);
            datePicker.requestFocus();
            return false;
        }
        
        // Validar hora
        if (cboHora.getValue() == null || cboHora.getValue().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar una hora", Alert.AlertType.ERROR);
            cboHora.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private boolean guardarCitaEnBD(Consulta cita) {
        try {
            // Usar el método guardar() de la clase Consulta para guardar en memoria
            boolean resultado = cita.guardar();
            
            if (resultado) {
                System.out.println("Cita agendada exitosamente en memoria: ID=" + cita.getId() + 
                                 ", Paciente=" + cita.getIdPaciente() + 
                                 ", Fecha=" + cita.getFecha() + 
                                 ", Hora=" + cita.getHora() + 
                                 ", Motivo=" + cita.getMotivo());
            }
            
            return resultado;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @FXML
    private void cancelar() {
        cerrarVentana();
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    private int asignarMedicoPorConsultorio(String consultorio) {
        // Asignar médico basado en el consultorio seleccionado
        switch (consultorio) {
            case "Consultorio 1 - Medicina General":
                return 1; // Dr. Juan Pérez
            case "Consultorio 2 - Pediatría":
                return 2; // Dra. María González
            case "Consultorio 3 - Ginecología":
                return 3; // Dr. Carlos Rodríguez
            case "Consultorio 4 - Cardiología":
                return 1; // Dr. Juan Pérez (también cardiólogo)
            case "Consultorio 5 - Dermatología":
                return 2; // Dra. María González (también dermatóloga)
            case "Consultorio 6 - Psicología":
                return 3; // Dr. Carlos Rodríguez (también psicólogo)
            default:
                return 1; // Por defecto, Dr. Juan Pérez
        }
    }
    
    private void cerrarVentana() {
        try {
            // Regresar al menú principal del paciente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/VistaPaciente.fxml"));
            Parent vistaPacienteRoot = loader.load();
            
            Scene nuevaEscena = new Scene(vistaPacienteRoot);
            Stage currentStage = (Stage) btnCancelar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Menú Principal - Paciente");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            // Si falla, al menos cerrar la ventana actual
            Stage stage = (Stage) btnCancelar.getScene().getWindow();
            stage.close();
        }
    }
}
