package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class PerfilDocModController {

    // Campos del perfil
    @FXML
    private Label lblNombre;
    
    @FXML
    private Label lblCedula;
    
    @FXML
    private Label lblCurp;
    
    @FXML
    private TextField txtEdad;
    
    @FXML
    private TextField txtSexo;
    
    @FXML
    private TextField txtCorreo;
    
    @FXML
    private TextField txtHorario;
    
    @FXML
    private TextField txtTelefono;
    
    @FXML
    private TextField txtConsultorio;
    
    // Botones
    @FXML
    private Button btnCancelar;
    
    @FXML
    private Button btnGuardar;
    
    @FXML
    private Button btnRegresarMenu;
    
    @FXML
    private Button btnVolverMenu;
    
    @FXML
    private Button btnCitas;
    
    @FXML
    private Button btnExpedientes;
    
    @FXML
    private Button btnSalir;

    // Datos del perfil (simulados)
    private String nombre = "Dr. Carlos Mendoza Vega";
    private String cedula = "12345678";
    private String curp = "MEVC870812HDFXXX04";
    private String edad = "37";
    private String sexo = "M";
    private String correo = "carlos.mendoza@clinica.com";
    private String horario = "Lunes a Viernes 8:00-18:00";
    private String telefono = "555-987-6543";
    private String consultorio = "Consultorio 1";

    // Valores originales para restaurar en caso de cancelar
    private String edadOriginal;
    private String sexoOriginal;
    private String correoOriginal;
    private String horarioOriginal;
    private String telefonoOriginal;
    private String consultorioOriginal;

    @FXML
    private void initialize() {
        System.out.println("PerfilDocModController inicializando...");
        
        // Cargar datos del perfil
        cargarDatosPerfil();
        
        // Guardar valores originales
        guardarValoresOriginales();
        
        // Configurar eventos de botones
        configurarEventosBotones();
        
        System.out.println("PerfilDocModController inicializado correctamente");
    }
    
    private void cargarDatosPerfil() {
        // Cargar datos del perfil
        lblNombre.setText(nombre);
        lblCedula.setText(cedula);
        lblCurp.setText(curp);
        txtEdad.setText(edad);
        txtSexo.setText(sexo);
        txtCorreo.setText(correo);
        txtHorario.setText(horario);
        txtTelefono.setText(telefono);
        txtConsultorio.setText(consultorio);
    }
    
    private void guardarValoresOriginales() {
        edadOriginal = edad;
        sexoOriginal = sexo;
        correoOriginal = correo;
        horarioOriginal = horario;
        telefonoOriginal = telefono;
        consultorioOriginal = consultorio;
    }
    
    private void configurarEventosBotones() {
        btnCancelar.setOnAction(event -> cancelarCambios(event));
        btnGuardar.setOnAction(event -> guardarCambios(event));
    }
    
    @FXML
    private void cancelarCambios(ActionEvent event) {
        // Restaurar valores originales
        txtEdad.setText(edadOriginal);
        txtSexo.setText(sexoOriginal);
        txtCorreo.setText(correoOriginal);
        txtHorario.setText(horarioOriginal);
        txtTelefono.setText(telefonoOriginal);
        txtConsultorio.setText(consultorioOriginal);
        
        mostrarAlerta("Información", "Cambios cancelados", Alert.AlertType.INFORMATION);
    }
    
    @FXML
    private void guardarCambios(ActionEvent event) {
        if (validarCampos()) {
            // Actualizar datos
            edad = txtEdad.getText().trim();
            sexo = txtSexo.getText().trim();
            correo = txtCorreo.getText().trim();
            horario = txtHorario.getText().trim();
            telefono = txtTelefono.getText().trim();
            consultorio = txtConsultorio.getText().trim();
            
            // Actualizar valores originales
            guardarValoresOriginales();
            
            // Aquí se guardarían los cambios en la base de datos
            System.out.println("Perfil del médico actualizado:");
            System.out.println("Edad: " + edad);
            System.out.println("Sexo: " + sexo);
            System.out.println("Correo: " + correo);
            System.out.println("Horario: " + horario);
            System.out.println("Teléfono: " + telefono);
            System.out.println("Consultorio: " + consultorio);
            
            mostrarAlerta("Éxito", "Perfil actualizado correctamente", Alert.AlertType.INFORMATION);
        }
    }
    
    private boolean validarCampos() {
        String edadText = txtEdad.getText().trim();
        String sexoText = txtSexo.getText().trim();
        String correoText = txtCorreo.getText().trim();
        String horarioText = txtHorario.getText().trim();
        String telefonoText = txtTelefono.getText().trim();
        String consultorioText = txtConsultorio.getText().trim();
        
        if (edadText.isEmpty()) {
            mostrarAlerta("Error", "La edad no puede estar vacía", Alert.AlertType.ERROR);
            return false;
        }
        
        if (!edadText.matches("^[0-9]+$")) {
            mostrarAlerta("Error", "La edad debe ser un número válido", Alert.AlertType.ERROR);
            return false;
        }
        
        if (sexoText.isEmpty()) {
            mostrarAlerta("Error", "El sexo no puede estar vacío", Alert.AlertType.ERROR);
            return false;
        }
        
        if (!sexoText.matches("^[MF]$")) {
            mostrarAlerta("Error", "El sexo debe ser M o F", Alert.AlertType.ERROR);
            return false;
        }
        
        if (correoText.isEmpty()) {
            mostrarAlerta("Error", "El correo electrónico no puede estar vacío", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar formato de correo electrónico básico
        if (!correoText.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            mostrarAlerta("Error", "Formato de correo electrónico inválido", Alert.AlertType.ERROR);
            return false;
        }
        
        if (horarioText.isEmpty()) {
            mostrarAlerta("Error", "El horario no puede estar vacío", Alert.AlertType.ERROR);
            return false;
        }
        
        if (telefonoText.isEmpty()) {
            mostrarAlerta("Error", "El teléfono no puede estar vacío", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar formato de teléfono básico
        if (!telefonoText.matches("^[0-9\\-\\+\\(\\)\\s]+$")) {
            mostrarAlerta("Error", "Formato de teléfono inválido", Alert.AlertType.ERROR);
            return false;
        }
        
        if (consultorioText.isEmpty()) {
            mostrarAlerta("Error", "El consultorio no puede estar vacío", Alert.AlertType.ERROR);
            return false;
        }
        
        return true;
    }
    
    @FXML
    private void volverAlMenuPrincipal(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/mainDoctor.fxml"));
            Parent mainDoctorRoot = loader.load();
            
            Scene nuevaEscena = new Scene(mainDoctorRoot, 1200, 800);
            Stage currentStage = (Stage) btnVolverMenu.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Panel Principal - Médico");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo volver al menú principal", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void regresarAlMenuPrincipal(ActionEvent event) {
        // Alias del método volverAlMenuPrincipal para el nuevo botón
        volverAlMenuPrincipal(event);
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    // Métodos para el menú lateral
    @FXML
    private void abrirCitas(ActionEvent event) {
        try {
            System.out.println("Abriendo gestión de citas...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/citasDoctor.fxml"));
            Parent citasRoot = loader.load();
            
            Scene nuevaEscena = new Scene(citasRoot, 1200, 800);
            Stage currentStage = (Stage) btnCitas.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Gestión de Citas - Médico");
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
            
            Scene nuevaEscena = new Scene(expedientesRoot, 1200, 800);
            Stage currentStage = (Stage) btnExpedientes.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Expedientes Médicos");
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
                    stage.setScene(new Scene(root, 759, 422));
                    stage.setTitle("Bienvenido a tu gestor de citas medicas");
                    stage.show();

                    Stage currentStage = (Stage) btnSalir.getScene().getWindow();
                    currentStage.hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
