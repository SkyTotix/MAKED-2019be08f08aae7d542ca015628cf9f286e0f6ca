package com.gestorcitasmedicas.utils;

import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Gestor para guardar automáticamente los datos cuando se cierra la aplicación
 */
public class AutoSaveManager {
    
    /**
     * Configura el guardado automático para una ventana
     */
    public static void setupAutoSave(Stage stage) {
        stage.setOnCloseRequest((WindowEvent event) -> {
            System.out.println("Cerrando aplicación, guardando datos...");
            
            // Guardar datos antes de cerrar
            DatosPrueba.guardarDatos();
            
            System.out.println("Datos guardados, cerrando aplicación...");
        });
    }
    
    /**
     * Configura el guardado automático para múltiples ventanas
     */
    public static void setupAutoSaveForAllStages() {
        // Agregar un listener global para todas las ventanas
        Platform.runLater(() -> {
            // Este método se ejecutará cuando se cierre cualquier ventana
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Aplicación cerrada, guardando datos finales...");
                DatosPrueba.guardarDatos();
            }));
        });
    }
    
    /**
     * Guarda los datos manualmente
     */
    public static void saveNow() {
        System.out.println("Guardando datos manualmente...");
        DatosPrueba.guardarDatos();
    }
}
