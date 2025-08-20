package com.gestorcitasmedicas;

import com.gestorcitasmedicas.utils.AutoSaveManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/gestorcitasmedicas/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setMaximized(true);
            stage.setTitle("Bienvenido a tu gestor de citas medicas");
            stage.setScene(scene);
            
            // Configurar guardado automático
            AutoSaveManager.setupAutoSave(stage);
            
            stage.show();
        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        try {
            launch();
        } catch (Exception e) {
            System.err.println("Error en main: " + e.getMessage());
            e.printStackTrace();
        }
    }
}