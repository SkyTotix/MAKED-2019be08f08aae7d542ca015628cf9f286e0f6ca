package com.gestorcitasmedicas.controller;

import com.gestorcitasmedicas.model.Medico;
import com.gestorcitasmedicas.model.Paciente;
import com.gestorcitasmedicas.utils.DatosPrueba;
import com.gestorcitasmedicas.utils.SesionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Label olvidoContra;
    @FXML
    private Label crearCuenta;
    @FXML
    private TextField correoIngresar;
    @FXML
    private PasswordField contraIngresar;
    @FXML
    private Button loginButton;

    @FXML
    private void initialize() {
        System.out.println("LoginController inicializado correctamente");
        
        // Inicializar datos de prueba si no están cargados
        if (!DatosPrueba.datosCargados()) {
            DatosPrueba.inicializarDatosPrueba();
        }
        
        // Configurar eventos para los enlaces
        configurarEventos();
    }
    
    private void configurarEventos() {
        // Evento para "¿Has olvidado tu contraseña?"
        olvidoContra.setOnMouseClicked(e -> abrirRecuperarContrasena());
        
        // Evento para "¡Crea una ahora!"
        crearCuenta.setOnMouseClicked(e -> crearCuenta());
    }
    
    @FXML
    private void abrirRecuperarContrasena() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/recuperarContra.fxml"));
            Parent recuperarContraRoot = loader.load();

            Scene nuevaEscena = new Scene(recuperarContraRoot);
            Stage currentStage = (Stage) olvidoContra.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Recuperar Contraseña - Gestor de Citas Médicas");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de recuperación", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void crearCuenta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/Registro.fxml"));
            Parent registroRoot = loader.load();
            
            Scene nuevaEscena = new Scene(registroRoot);
            Stage currentStage = (Stage) crearCuenta.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Registro de Usuario - Gestor de Citas Médicas");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de registro", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void iniciarSesion(ActionEvent event) {
        String correo = correoIngresar.getText();
        String contra = contraIngresar.getText();
        
        System.out.println("Iniciando sesión con: " + correo);
        
        // Verificar si es recepcionista
        if ("recepcionista@test.com".equals(correo) && "recepcionista123".equals(contra)) {
            // Autenticación como recepcionista
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/mainRecepcionista.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Panel Principal - Recepcionista");
                stage.setMaximized(true);
                stage.show();

                // Cerrar la ventana actual
                ((Node) event.getSource()).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo cargar el panel principal del recepcionista", Alert.AlertType.ERROR);
            }
        } else {
            // Intentar autenticar como médico
            Medico medico = Medico.autenticar(correo, contra);
            if (medico != null) {
                // Establecer médico en sesión
                SesionManager.getInstance().setMedicoActual(medico);
                
                // Autenticación como médico exitosa
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/mainDoctor.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Panel Principal - " + medico.getNombre());
                    stage.setMaximized(true);
                    stage.show();

                    // Cerrar la ventana actual
                    ((Node) event.getSource()).getScene().getWindow().hide();
                } catch (IOException e) {
                    e.printStackTrace();
                    mostrarAlerta("Error", "No se pudo cargar el panel principal del médico", Alert.AlertType.ERROR);
                }
                return;
            }
            
            // Intentar autenticar como paciente
            Paciente paciente = Paciente.autenticar(correo, contra);
            if (paciente != null) {
                // Establecer paciente en sesión
                SesionManager.getInstance().setPacienteActual(paciente);
                
                // Autenticación como paciente exitosa
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/VistaPaciente.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Panel Principal - " + paciente.getNombre());
                    stage.setMaximized(true);
                    stage.show();

                    // Cerrar la ventana actual
                    ((Node) event.getSource()).getScene().getWindow().hide();
                } catch (IOException e) {
                    e.printStackTrace();
                    mostrarAlerta("Error", "No se pudo cargar el panel principal del paciente", Alert.AlertType.ERROR);
                }
                return;
            }
            
            // Si no se pudo autenticar con ningún rol
            mostrarAlerta("Error", "Credenciales incorrectas. Verifique su correo y contraseña.", Alert.AlertType.ERROR);
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
