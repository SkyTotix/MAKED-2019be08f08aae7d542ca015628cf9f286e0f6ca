package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class ReprogramarCitaExitosaController {

    @FXML private Button btnContinuar;

    @FXML
    private void initialize() {
        System.out.println("ReprogramarCitaExitosaController inicializando...");
        
        // Configurar efecto hover para el botón
        configurarEfectoHover();
        
        System.out.println("ReprogramarCitaExitosaController inicializado correctamente");
    }
    
    private void configurarEfectoHover() {
        btnContinuar.setOnMouseEntered(e -> {
            btnContinuar.setStyle("-fx-background-color: #2C5A7A; -fx-cursor: hand;");
        });
        
        btnContinuar.setOnMouseExited(e -> {
            btnContinuar.setStyle("-fx-background-color: #3b6f89; -fx-cursor: hand;");
        });
    }

    @FXML
    private void continuar(ActionEvent event) {
        System.out.println("Continuando al panel principal...");
        
        // Regresar al panel principal del paciente
        regresarAlPanelPrincipal();
    }
    
    private void regresarAlPanelPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/VistaPaciente.fxml"));
            Parent pacienteRoot = loader.load();
            
            Scene nuevaEscena = new Scene(pacienteRoot, 1024, 768);
            Stage currentStage = (Stage) btnContinuar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Panel Principal - Paciente");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
